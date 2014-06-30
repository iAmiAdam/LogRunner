package info.adamjsmith.logrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class MainMenuScreen implements Screen {
	protected LogRunner game;
	
	public MainMenuScreen(LogRunner game) {
		this.game = game;
	}


	@Override
	public void render(float delta) {
		if(Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
		}		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}}
