package info.adamjsmith.logrunner;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
	Texture currentPlayer;
	Array<Log> logs;
	SpriteBatch batch;
	Iterator<Log> iter;
	Texture fishImage;
	Texture hillsImage;
	Texture bg;
	TextureRegion[] walkFrames;
	TextureRegion currentFrame;
	Animation walkAnimation;
	float stateTime;
	Vector2 pos;
	
	Player player;
	
	private static final int FRAME_COLS = 8;
	private static final int FRAME_ROWS = 1;
	
	public GameRenderer(GameUpdate gameUpdater, LogRunner gameI) {
		updater = gameUpdater;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 15f, 25f);
		game = gameI;
		
		currentPlayer = game.manager.get("jump.png", Texture.class);	
		currentPlayer.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		logImage = game.manager.get("log.png", Texture.class);
		riverImage = game.manager.get("river.png", Texture.class);
		bankImage = game.manager.get("bank.png", Texture.class);
		playerImage = game.manager.get("player.png", Texture.class);
		playerImage.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		cloudImage = game.manager.get("clouds.png", Texture.class);
		cloudImage.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		fishImage = game.manager.get("fish.png", Texture.class);
		hillsImage = game.manager.get("hills.png", Texture.class);
		hillsImage.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bg = game.manager.get("background.png", Texture.class);
		bg.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		TextureRegion[][] tmp = TextureRegion.split(playerImage, 32, 64);
		
		walkFrames = new TextureRegion [FRAME_COLS * FRAME_ROWS];
		int index = 0;
		
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		
		player = updater.getPlayer();
		logs = updater.getLogs();
		
		walkAnimation = new Animation(0.10f, walkFrames);
		stateTime = 0f;
		batch = new SpriteBatch();
	}
	
	public void render() {
		Gdx.gl.glClearColor(0.450f, 0.772f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		
		logs = updater.getLogs();
		
		batch.setProjectionMatrix(camera.combined);			
		batch.begin();
		batch.draw(bankImage, 0f, 0f, 15f, 7f);
		batch.draw(bg, 0f, 10f, 15f, 4f);
		for(Log log : logs) {
			batch.draw(logImage, log.x, log.y, log.width, log.height);
		}
		batch.draw(riverImage, 0f, 7f, 15f, 3f);
		batch.draw(cloudImage, 0f, 15f, 15f, 3f);
		if (player.getY() > 10.6f) {
			batch.draw(currentPlayer, player.getX(), player.getY(), player.getWidth(), player.getHeight());
		} else {
			batch.draw(currentFrame, player.getX(), player.getY(), player.getWidth(), player.getHeight());
		}
		batch.end();
	}
	
	public void dispose() {
		logImage.dispose();
		riverImage.dispose();
		playerImage.dispose();
		bankImage.dispose();
		batch.dispose();
	}
}
