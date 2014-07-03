package info.adamjsmith.logrunner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class LogRunner extends Game {
	
	public AssetManager manager = new AssetManager();
	ActionResolver actionResolver;
	
	public LogRunner(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}
	
	@Override
	public void create () {
		setScreen(new LoadingScreen(this));
	}	
}