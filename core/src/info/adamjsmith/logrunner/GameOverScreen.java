package info.adamjsmith.logrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;

public class GameOverScreen implements Screen {
	
	LogRunner game;
	Skin skin;
	Stage stage;
	SpriteBatch batch;
	OrthographicCamera camera;
	Array<Log> logs;
	TextureRegion jump;
	int score;
	float y;
	float hiScoreX;
	
	public GameOverScreen(LogRunner game, int score, float y, Array<Log> logs) {
		this.game = game;
		this.score = score;
		this.y = y;
		this.logs = logs;
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 15f, 25f);
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
		batch.draw(game.assets.clouds, 0f, 15f, 15f, 3f);
		for(Log log : logs) {
			batch.draw(game.assets.log, log.getX(), log.getY(), log.width, log.height);
		}
		
		batch.draw(jump, 4f, y, 1.2f, 1.35f);
		
		batch.draw(game.assets.river, 0f, 7f, 15f, 3f);
		game.assets.headerFont.draw(batch, String.valueOf(game.stats.hiScore), hiScoreX, 560);
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
		TextureRegion[][] tmp = TextureRegion.split(game.assets.player, 50, 60);
		jump = tmp[0][0];
		hiScoreX = (480 - String.valueOf(game.stats.hiScore).length() * 50) / 2;
		createStage();
	}
	
	public void createStage() {
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		skin.getFont("header-font").setMarkupEnabled(true);
		skin.getFont("button-font").setMarkupEnabled(true);
		TextButtonStyle buttonStyle = skin.get("default", TextButtonStyle.class);
		
		float buttonHeight = (Gdx.graphics.getHeight() / 4) / 4;
		float fontScale = (Gdx.graphics.getWidth() / 52) / 9;
		if(fontScale < 1) fontScale = 1;
		
		stage = new Stage();		
		Table table = new Table();
		stage.addActor(table);
		
		TextButton restartButton = new TextButton("Restart", buttonStyle);
		
		restartButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new GameScreen(game));
			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.assets.click.play(1f);
				return true;
			}
		});
		
		TextButton scoresButton = new TextButton("Submit Score", buttonStyle);
		scoresButton.pad(10);
		scoresButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (game.actionResolver.getSignedInGPGS()) game.actionResolver.submitScoreGPGS(score); 
				else {
					game.actionResolver.loginGPGS();
					if(game.actionResolver.getSignedInGPGS()) game.actionResolver.submitScoreGPGS(score);
				}
			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.assets.click.play(1f);
				return true;
			}
		});
		
		Label gameOver = new Label("Game Over", skin);
		gameOver.setFontScale(fontScale);
		gameOver.setAlignment(Align.center);
		table.add(gameOver);		
		table.row();
		table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		table.add(restartButton).padTop(20).padBottom(30).height(buttonHeight).minWidth(300);
		table.row();
		table.add(scoresButton).padTop(20).padBottom(30).height(buttonHeight).minWidth(300);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
