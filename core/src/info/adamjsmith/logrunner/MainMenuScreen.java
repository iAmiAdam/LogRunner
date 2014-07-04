package info.adamjsmith.logrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MainMenuScreen implements Screen {
	protected LogRunner game;
	private Stage stage;
	private OrthographicCamera camera;
	
	SpriteBatch batch;
	Texture logo;
	Texture logImage;
	Texture riverImage;
	Texture bankImage;
	Texture cloudImage;
	Texture bg;
	Texture buttonUp;
	Texture buttonDown;
	int screenWidth;
	float buttonX;
	float buttonY;
	BitmapFont buttonFont;
	BitmapFont headerFont;
	
	public MainMenuScreen(LogRunner game) {
		this.game = game;
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.450f, 0.772f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);	
		batch.begin();
		batch.draw(bankImage, 0f, 0f, 15f, 7f);
		batch.draw(bg, 0f, 10f, 15f, 4f);
		batch.draw(riverImage, 0f, 7f, 15f, 3f);
		batch.draw(cloudImage, 0f, 15f, 15f, 3f);
		batch.draw(logo, 0f, 21f, 15f, 3.75f);
		batch.end();
				
		Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0, 480, 800);
		batch.setProjectionMatrix(normalProjection);
		batch.begin();
		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());
		Table.drawDebug(stage);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 15f, 25f);
		
		stage = new Stage();
		buttonX = (Gdx.graphics.getWidth() - 400) / 2;
		buttonY = Gdx.graphics.getHeight() / 2;
		
		logImage = game.manager.get("log.png", Texture.class);
		riverImage = game.manager.get("river.png", Texture.class);
		bankImage = game.manager.get("bank.png", Texture.class);
		cloudImage = game.manager.get("clouds.png", Texture.class);
		cloudImage.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bg = game.manager.get("background.png", Texture.class);
		bg.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		logo = game.manager.get("logo.png", Texture.class);
		logo.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		
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
		
		
		
		TextButton playButton = new TextButton("Play", style);
		playButton.setX(buttonX);
		playButton.setY(buttonY);
		
		playButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new GameScreen(game));
			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		
		stage.addActor(playButton);
		
		TextButton scoresButton = new TextButton("Highscores", style);
		scoresButton.setX(buttonX);
		scoresButton.setY(buttonY - 170);
		
		scoresButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (game.actionResolver.getSignedInGPGS()) game.actionResolver.getLeaderboardGPGS();
				else {
					game.actionResolver.loginGPGS();
					if (game.actionResolver.getSignedInGPGS()) game.actionResolver.getLeaderboardGPGS();
				}
			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		
		stage.addActor(scoresButton);
		
		TextButton achievementsButton = new TextButton("Achievements", style);
		achievementsButton.setX(buttonX);
		achievementsButton.setY(buttonY - 340);
		achievementsButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (game.actionResolver.getSignedInGPGS()) game.actionResolver.getAchievementsGPGS();
				else {
					game.actionResolver.loginGPGS();
					if (game.actionResolver.getSignedInGPGS()) game.actionResolver.getAchievementsGPGS();
				}
			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		
		stage.addActor(achievementsButton);
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}}
