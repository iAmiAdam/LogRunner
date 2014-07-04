package info.adamjsmith.logrunner;

import com.badlogic.gdx.Screen;

public class LoadingScreen implements Screen {
	
	protected LogRunner game;
	
	public LoadingScreen(LogRunner game) {
		this.game = game;
		game.assets.load(game);
	}

	@Override
	public void render(float delta) {
		if(game.assets.manager.update()) {
			game.setScreen(new MainMenuScreen(game));
		}
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
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
	}
}
