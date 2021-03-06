package info.adamjsmith.logrunner;

import info.adamjsmith.logrunner.Player.PlayerState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameRenderer {
	
	private GameUpdate updater;
	private OrthographicCamera camera;
	private LogRunner game;
	Player player;
	Platform platform;
	
	TextureRegion jump;
	Array<Log> logs;
	SpriteBatch batch;
	TextureRegion[] walkFrames;
	TextureRegion currentFrame;
	Animation walkAnimation;
	float stateTime;
	
	private static final int FRAME_COLS = 3;
	private static final int FRAME_ROWS = 1;
	
	public GameRenderer(GameUpdate updater, LogRunner game) {
		this.updater = updater;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 15f, 25f);
		this.game = game;
		
		TextureRegion[][] tmp = TextureRegion.split(game.assets.player, 50, 60);
		
		jump = tmp[0][0];
		
		walkFrames = new TextureRegion [FRAME_COLS * FRAME_ROWS];
		int index = 0;
		
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		
		platform = updater.getPlatform();
		player = updater.getPlayer();
		logs = updater.getLogs();
		
		walkAnimation = new Animation(0.15f, walkFrames);
		walkAnimation.setPlayMode(PlayMode.LOOP_PINGPONG);
		stateTime = 0f;
		batch = new SpriteBatch();
	}
	
	public void render() {
		Gdx.gl.glClearColor(0.450f, 0.772f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		
		player = updater.getPlayer();
		logs = updater.getLogs();
		
		batch.setProjectionMatrix(camera.combined);			
		batch.begin();
		batch.draw(game.assets.bank, 0f, 0f, 15f, 7f);
		renderItem();
		batch.draw(game.assets.bg, 0f, 10f, 15f, 4f);
		renderClouds();
		for(Log log : logs) {
			batch.draw(game.assets.log, log.getX(), log.getY(), log.width, log.height);
		}
		if (player.playerState == PlayerState.AIR || player.playerState == PlayerState.DEAD) {
			batch.draw(jump, 4f, player.getY(), player.getWidth(), player.getHeight());
		} else {
			batch.draw(currentFrame, 4f, player.getY(), player.getWidth(), player.getHeight());
		}
		batch.draw(game.assets.river, 0f, 7f, 15f, 3f);
		batch.draw(game.assets.platform, platform.getX(), 0, 25f, 10.8f);
		batch.end();
		
		renderScore();
	}
	
	private void renderScore() {
		int score = player.getScore();
		float width = String.valueOf(score).length();
		float x = (480 - width * 50) / 2;
		Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0, 480, 800);
		batch.setProjectionMatrix(normalProjection);
		batch.begin();
		game.assets.headerFont.draw(batch, String.valueOf(score), x, 650);
		game.assets.headerFont.setScale(0.64f);
		game.assets.headerFont.draw(batch, "Tap to start", platform.getX() * (Gdx.graphics.getWidth() / 15) + 60, (platform.getY() * ( (Gdx.graphics.getHeight() / 25)) / 2 ) + 50);
		game.assets.headerFont.draw(batch, "Swipe up to jump", platform.getX() * (Gdx.graphics.getWidth() / 15), (platform.getY() * (Gdx.graphics.getHeight() / 25)) / 2);
		game.assets.headerFont.setScale(1);
		batch.end();
	}
	
	private void renderClouds() {
		Vector2[] clouds = updater.getClouds();
		for (int i = 0; i < 5; i++) {
			batch.draw(game.assets.cloud, clouds[i].x, clouds[i].y, 1f, 1f);
		}
	}
	
	private void renderItem() {
		switch(updater.getItem().id) {
			case 1: 
				batch.draw(game.assets.fish, updater.getItem().getX(), updater.getItem().getY(), 0.8f, 0.8f);
				break;
			case 2: 
				batch.draw(game.assets.chest, updater.getItem().getX(), updater.getItem().getY(), 1.3f, 1.3f);
				break;
			case 3: 
				batch.draw(game.assets.fossil, updater.getItem().getX(), updater.getItem().getY(), 0.8f, 0.8f);
				break;
		}
	}
	
	public void dispose() {
		batch.dispose();
	}
}