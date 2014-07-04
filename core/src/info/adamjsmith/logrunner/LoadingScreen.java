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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
		
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
		// TODO Auto-generated method stub
		
	}
}
