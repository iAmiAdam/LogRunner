package info.adamjsmith.logrunner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class LogRunner extends Game {
	
	public AssetManager manager = new AssetManager();
	
	@Override
	public void create () {
		setScreen(new LoadingScreen(this));
	}	
}