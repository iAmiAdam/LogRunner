package info.adamjsmith.logrunner;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.TimeUtils;

public class LogRunner extends Game implements ApplicationListener {
	
	public Assets assets;
	public ActionResolver actionResolver;
	public Stats stats;
	public float startTime;
	public int sessionDeaths;
	
	public LogRunner(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
		this.assets = new Assets();
		this.stats = new Stats();
		this.startTime = TimeUtils.nanoTime();
		this.sessionDeaths = 0;
	}
	
	@Override
	public void create () {
		setScreen(new LoadingScreen(this));
	}	
}