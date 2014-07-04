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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
	Skin skin;
	
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
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		skin.getAtlas().getTextures().iterator().next().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		skin.getFont("header-font").setMarkupEnabled(true);
		skin.getFont("button-font").setMarkupEnabled(true);
		TextButtonStyle buttonStyle = skin.get("default", TextButtonStyle.class);
		
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
		
		Table table = new Table();
		stage.addActor(table);
		table.setPosition(200,65);
		
		table.debug();
		
		TextButton playButton = new TextButton("Play", buttonStyle);
		
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
		
		TextButton scoresButton = new TextButton("Highscores", buttonStyle);
		
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
		
		TextButton achievementsButton = new TextButton("Achievements", buttonStyle);
		
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
		
		table.add(new Label("Log Runner", skin));
		table.row();
		table.add(playButton);
		table.row();
		table.add(scoresButton);
		table.row();
		table.add(achievementsButton);
		table.pack();
		
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
