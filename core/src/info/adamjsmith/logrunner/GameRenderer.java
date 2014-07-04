package info.adamjsmith.logrunner;

import info.adamjsmith.logrunner.GameUpdate.GameState;
import info.adamjsmith.logrunner.Player.PlayerState;

import java.util.Iterator;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
	BitmapFont numbers;
	
	Stage stage;
	Texture buttonUp;
	Texture buttonDown;
	BitmapFont buttonFont;
	float buttonX;
	float buttonY;

	Player player;
	
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	
	private static final int FRAME_COLS = 3;
	private static final int FRAME_ROWS = 1;
	
	public GameRenderer(GameUpdate gameUpdater, LogRunner gameI) {
		updater = gameUpdater;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 15f, 25f);
		game = gameI;
		
		numbers = new BitmapFont(Gdx.files.internal("header.fnt"), Gdx.files.internal("header.png"), false);
		numbers.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion[][] tmp = TextureRegion.split(game.assets.player, 50, 60);
		
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
		createStage();
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
		batch.draw(game.assets.bg, 0f, 10f, 15f, 4f);
		batch.draw(game.assets.clouds, 0f, 15f, 15f, 3f);
		for(Log log : logs) {
			batch.draw(game.assets.log, log.getX(), log.getY(), log.width, log.height);
		}
		if (player.playerState == PlayerState.AIR) {
			batch.draw(jump, 4f, player.getY(), player.getWidth(), player.getHeight());
		} else {
			batch.draw(currentFrame, 4f, player.getY(), player.getWidth(), player.getHeight());
		}
		batch.draw(game.assets.river, 0f, 7f, 15f, 3f);
		batch.end();
		
		if(updater.currentState == GameState.GAMEOVER) {
			Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0, 480, 800);
			batch.setProjectionMatrix(normalProjection);
			batch.begin();
			stage.draw();
			stage.act(Gdx.graphics.getDeltaTime());
			Table.drawDebug(stage);
			batch.end();
		}
		
		renderScore();
	}
	
	public void createStage() {
		stage = new Stage();
		buttonX = (Gdx.graphics.getWidth() - 400) / 2;
		buttonY = Gdx.graphics.getHeight() / 2;
		
		buttonUp = game.manager.get("buttonup.png", Texture.class);
		buttonDown = game.manager.get("buttondown.png", Texture.class);
		
		buttonFont = new BitmapFont(Gdx.files.internal("menu.fnt"), Gdx.files.internal("menu.png"), false);
		buttonFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion buttonUpRegion = new TextureRegion(buttonUp, 0, 0, 400, 150);
		TextureRegion buttonDownRegion = new TextureRegion(buttonDown, 0, 0, 400, 150);
		
		TextButtonStyle style = new TextButtonStyle();
		style.up =  new TextureRegionDrawable(buttonUpRegion);
		style.down = new TextureRegionDrawable(buttonDownRegion);
		style.font = buttonFont;
		
		TextButton playButton = new TextButton("Restart", style);
		playButton.setX(buttonX);
		playButton.setY(buttonY);
		
		playButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				updater.reset();
			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		
		stage.addActor(playButton);
		
		TextButton scoresButton = new TextButton("Submit Score", style);
		scoresButton.setX(buttonX);
		scoresButton.setY(buttonY - 170);
		scoresButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (game.actionResolver.getSignedInGPGS()) game.actionResolver.submitScoreGPGS(player.score); 
				else {
					game.actionResolver.loginGPGS();
					if(game.actionResolver.getSignedInGPGS()) game.actionResolver.submitScoreGPGS(player.score);
				}
			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		stage.addActor(scoresButton);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	public void renderScore() {
		int score = player.getScore();
		float width = String.valueOf(score).length();
		float x = (480 - width * 50) / 2;
		Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0, 480, 800);
		batch.setProjectionMatrix(normalProjection);
		batch.begin();
		numbers.draw(batch, String.valueOf(score), x, 600);
		batch.end();
	}
	
	public void dispose() {
		logImage.dispose();
		riverImage.dispose();
		playerImage.dispose();
		bankImage.dispose();
		cloudImage.dispose();
		bg.dispose();
		batch.dispose();
		stage.dispose();
	}
}