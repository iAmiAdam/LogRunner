package info.adamjsmith.logrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

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
	Texture play;
	MainMenuInputProcessor inputProcessor;
	
	public MainMenuScreen(LogRunner game) {
		this.game = game;
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.450f, 0.772f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Table.drawDebug(stage);
		
		batch.setProjectionMatrix(camera.combined);	
		batch.begin();
		batch.draw(bankImage, 0f, 0f, 15f, 7f);
		batch.draw(bg, 0f, 10f, 15f, 4f);
		batch.draw(riverImage, 0f, 7f, 15f, 3f);
		batch.draw(cloudImage, 0f, 15f, 15f, 3f);
		batch.draw(logo, 0f, 21f, 15f, 3.75f);
		batch.draw(play, 2.85f, 17f, 9.3f, 3.1f);
		batch.end();
		
		if (Gdx.input.isTouched()) {
				game.setScreen(new GameScreen(game));
		}
				
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 15f, 25f);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		logImage = game.manager.get("log.png", Texture.class);
		riverImage = game.manager.get("river.png", Texture.class);
		bankImage = game.manager.get("bank.png", Texture.class);
		cloudImage = game.manager.get("clouds.png", Texture.class);
		cloudImage.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bg = game.manager.get("background.png", Texture.class);
		bg.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		logo = game.manager.get("logo.png", Texture.class);
		logo.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		play = game.manager.get("play.png", Texture.class);
		play.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		inputProcessor = new MainMenuInputProcessor(game);
		Gdx.input.setInputProcessor(inputProcessor);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
