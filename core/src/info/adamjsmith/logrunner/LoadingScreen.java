package info.adamjsmith.logrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class LoadingScreen implements Screen {
	
	protected LogRunner game;
	
	public LoadingScreen(LogRunner game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		if(game.manager.update()) {
			if(Gdx.input.isTouched()) {
				game.setScreen(new MainMenuScreen(game));
			}
		}
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		game.manager.load("log.png", Texture.class);
		game.manager.load("river.png", Texture.class);
		game.manager.load("bank.png", Texture.class);
		game.manager.load("player.png", Texture.class);
		game.manager.load("clouds.png", Texture.class);
		game.manager.load("jump.png", Texture.class);
		game.manager.load("fish.png", Texture.class);
		game.manager.load("hills.png", Texture.class);
		game.manager.load("background.png", Texture.class);
		game.manager.load("logo.png", Texture.class);
		game.manager.finishLoading();
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
