package info.adamjsmith.logrunner;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

public class LogRunner extends Game implements ApplicationListener {
	
	public Assets assets;
	ActionResolver actionResolver;
	
	public LogRunner(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
		this.assets = new Assets();
	}
	
	@Override
	public void create () {
		setScreen(new LoadingScreen(this));
	}	
}