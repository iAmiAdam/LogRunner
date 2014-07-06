package info.adamjsmith.logrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;

public class GameScreen implements Screen {
	protected LogRunner game;
	
	GameRenderer renderer;
	GameUpdate updater;
	
	public GameScreen(LogRunner game) {
		this.game = game;
	}
	@Override
	public void render(float delta) {
		renderer.render();
		updater.update();
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		updater = new GameUpdate(game);
		renderer = new GameRenderer(updater, game);
		Gdx.input.setInputProcessor(new GestureDetector(new GameInputProcessor(updater.getPlayer())));
	}
	
	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		renderer.dispose();
		updater.dispose();
	}
}