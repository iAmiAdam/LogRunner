package info.adamjsmith.logrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class MainMenuScreen implements Screen {
	protected LogRunner game;
	private Stage stage;
	private OrthographicCamera camera;
	
	SpriteBatch batch;
	int screenWidth;
	float buttonX;
	float buttonY;
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
		batch.draw(game.assets.bank, 0f, 0f, 15f, 7f);
		batch.draw(game.assets.bg, 0f, 10f, 15f, 4f);
		batch.draw(game.assets.river, 0f, 7f, 15f, 3f);
		batch.draw(game.assets.clouds, 0f, 15f, 15f, 3f);
		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		batch.begin();
		stage.draw();
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
		game.actionResolver.showAds(true);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		skin.getAtlas().getTextures().iterator().next().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		skin.getFont("header-font").setMarkupEnabled(true);
		skin.getFont("button-font").setMarkupEnabled(true);
		TextButtonStyle buttonStyle = skin.get("default", TextButtonStyle.class);
		
		stage = new Stage();
		
		Table table = new Table();
		stage.addActor(table);
		
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
		achievementsButton.pad(10);
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
		table.setSize(480, 800);
		Label logo = new Label("Log\nRunner", skin);		
		logo.setAlignment(Align.center);
		table.add(logo).minWidth(480).minHeight(350).fill();
		table.row();
		table.add(playButton).pad(10);
		table.row();
		table.add(scoresButton).pad(10);
		table.row();
		table.add(achievementsButton).pad(10);
		table.pack();
		table.setPosition((Gdx.graphics.getWidth() - table.getWidth()) / 2, (Gdx.graphics.getHeight() - table.getHeight()) / 2);
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}}