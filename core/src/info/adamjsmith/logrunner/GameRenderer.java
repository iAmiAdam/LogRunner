package info.adamjsmith.logrunner;

import info.adamjsmith.logrunner.Player.PlayerState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;

public class GameRenderer {
	
	private GameUpdate updater;
	private OrthographicCamera camera;
	private LogRunner game;
	
	Texture logImage;
	Rectangle log;
	Texture riverImage;
	Rectangle river;
	Texture bankImage;
	Rectangle bank;
	Texture cloudImage;
	Rectangle clouds;
	Texture playerImage;
	TextureRegion jump;
	Array<Log> logs;
	SpriteBatch batch;
	Iterator<Log> iter;
	Texture bg;
	Texture numbersImage;
	TextureRegion[] walkFrames;
	TextureRegion currentFrame;
	Animation walkAnimation;
	float stateTime;
	Vector2 pos;
	//TextureRegion[][] numbers;
	
	BitmapFont numbers;

	Player player;
	
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	
	private static final int FRAME_COLS = 3;
	private static final int FRAME_ROWS = 1;
	
	public GameRenderer(GameUpdate gameUpdater, LogRunner gameI) {
		updater = gameUpdater;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 15f, 25f);
		game = gameI;
		
		logImage = game.manager.get("log.png", Texture.class);
		riverImage = game.manager.get("river.png", Texture.class);
		bankImage = game.manager.get("bank.png", Texture.class);
		playerImage = game.manager.get("player.png", Texture.class);
		playerImage.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		cloudImage = game.manager.get("clouds.png", Texture.class);
		cloudImage.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bg = game.manager.get("background.png", Texture.class);
		bg.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		numbersImage = game.manager.get("numbers.png", Texture.class);
		
		numbers = new BitmapFont(Gdx.files.internal("header.fnt"), Gdx.files.internal("header.png"), false);
		//numbers = TextureRegion.split(numbersImage, 65, 38);
		TextureRegion[][] tmp = TextureRegion.split(playerImage, 50, 60);
		
		jump = tmp[0][0];
		
		walkFrames = new TextureRegion [FRAME_COLS * FRAME_ROWS];
		int index = 0;
		
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		
		player = updater.getPlayer();
		logs = updater.getLogs();
		
		walkAnimation = new Animation(0.15f, walkFrames);
		stateTime = 0f;
		batch = new SpriteBatch();
	}
	
	public void render() {
		Gdx.gl.glClearColor(0.450f, 0.772f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		int score = player.getScore();
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		
		player = updater.getPlayer();
		logs = updater.getLogs();
		
		batch.setProjectionMatrix(camera.combined);			
		batch.begin();
		batch.draw(bankImage, 0f, 0f, 15f, 7f);
		batch.draw(bg, 0f, 10f, 15f, 4f);
		batch.draw(cloudImage, 0f, 15f, 15f, 3f);
		for(Log log : logs) {
			batch.draw(logImage, log.getX(), log.getY(), log.width, log.height);
		}
		if (player.playerState == PlayerState.AIR) {
			batch.draw(jump, 4f, player.getY(), player.getWidth(), player.getHeight());
		} else {
			batch.draw(currentFrame, 4f, player.getY(), player.getWidth(), player.getHeight());
		}
		batch.draw(riverImage, 0f, 7f, 15f, 3f);
		batch.end();
		
		Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0, 480, 800);
		batch.setProjectionMatrix(normalProjection);
		batch.begin();
		numbers.draw(batch, String.valueOf(score), 20, 570);
		batch.end();
		
		//renderScore();
		
		debugRenderer.render(updater.world, camera.combined);
	}
	
	public void renderScore() {
		int score = player.getScore();
		List<Integer>  digits = new ArrayList<Integer>();
		if(score != 0) {
			while (score > 0) {
				digits.add(0, score % 10);
				score /= 10;
			}
		} else {
			digits.add(0);
		}
		float width = 2 * digits.size();
		float x = (15f - width) / 2;
		batch.begin();
		numbers.draw(batch, String.valueOf(score), 2f, 570);
		//for(int i = 0; i < digits.size() ; i++) {
			//int digit = digits.get(i);
			//batch.draw(numbers[0][digit], x + (i * 2), 18f, 2f, 1f);
		//}
		batch.end();
		digits = null;
	}
	
	public void dispose() {
		logImage.dispose();
		riverImage.dispose();
		playerImage.dispose();
		bankImage.dispose();
		cloudImage.dispose();
		bg.dispose();
		batch.dispose();
	}
}
