package info.adamjsmith.logrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

public class MainMenuScreen implements Screen {
	protected LogRunner game;
	private Stage stage;
	private OrthographicCamera camera;
	
	SpriteBatch batch;
	int screenWidth;
	float buttonX;
	float buttonY;
	
	private Vector2[] clouds = new Vector2[6];
	private float cloudTime;
	private boolean flip = true;
	
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
		
		for (int i = 0; i < 5; i++) {
			batch.draw(game.assets.cloud, clouds[i].x, clouds[i].y, 1f, 1f);
		}
		
		if((TimeUtils.nanoTime() - cloudTime) / 1000000000.0 > 1.0) {
			cloudTime = TimeUtils.nanoTime();
			if (flip == true) {
				for (int i = 0; i < 5; i = i + 2) {
					clouds[i].x -= 0.15f;
					if (clouds[i].x < -1f) clouds[i].x = 15f;
				}
				flip = false;
			} else {
				for (int i = 1; i < 5; i = i + 2) {
					clouds[i].x -= 0.15f;
					if (clouds[i].x < -1f) clouds[i].x = 15f;
				}
				flip = true;
			}
		}
		
		
		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
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
		
		clouds[0] = new Vector2(2f, 16f);
		clouds[1] = new Vector2(5f, 17f);
		clouds[2] = new Vector2(8f, 15f);
		clouds[3] = new Vector2(11f, 17f);
		clouds[4] = new Vector2(14f, 16f);
		
		cloudTime = TimeUtils.nanoTime();
		
		float buttonHeight = (Gdx.graphics.getHeight() / 4) / 3;
		float fontScale = (Gdx.graphics.getWidth() / 52) / 7;
		if (fontScale < 0) fontScale = 1;
		
		stage = new Stage ();
		
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		TextButton playButton = new TextButton("Play", game.assets.uiSkin);
		
		playButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new GameScreen(game));
			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.assets.click.play(1.0f);
				return true;
			}
		});
		
		TextButton scoresButton = new TextButton("Highscores", game.assets.uiSkin);
		
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
				game.assets.click.play(1.0f);
				return true;
			}
		});
		
		TextButton achievementsButton = new TextButton("Achievements", game.assets.uiSkin);
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
				game.assets.click.play(1.0f);
				return true;
			}
		});
		
		Label logo = new Label("Log\nRunner", game.assets.uiSkin);
		logo.setAlignment(Align.center);
		table.add(logo);
		table.row();
		table.add(playButton).padTop(20).padBottom(30).height(buttonHeight).minWidth(300);
		table.row();
		table.add(scoresButton).padTop(20).padBottom(30).height(buttonHeight).minWidth(300);
		table.row();
		table.add(achievementsButton).padTop(20).padBottom(20).height(buttonHeight).minWidth(300);
		table.pack();
		table.setPosition((Gdx.graphics.getWidth() - table.getWidth()) / 2, (Gdx.graphics.getHeight() - table.getHeight()) / 2);
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		this.dispose();
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