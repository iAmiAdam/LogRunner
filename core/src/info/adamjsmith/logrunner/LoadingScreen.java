package info.adamjsmith.logrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen implements Screen {
	
	protected LogRunner game;
	private SpriteBatch batch;
	
	public LoadingScreen(LogRunner game) {
		this.game = game;
		this.game.assets.load(game);
		this.game.stats.load();
	}

	@Override
	public void render(float delta) {
		if(game.assets.manager.update()) {
			game.setScreen(new MainMenuScreen(game));
		}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(game.assets.logo, 0, (Gdx.graphics.getHeight() - 30) / 2, Gdx.graphics.getWidth(), 30);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
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
	}
}
