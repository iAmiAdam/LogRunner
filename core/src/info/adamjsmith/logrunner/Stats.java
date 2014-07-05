package info.adamjsmith.logrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Stats {
	
	int deaths;
	int logs;
	int hiScore;

	protected Preferences getStats() {
		return Gdx.app.getPreferences("stats");
	}
	
	public void load(Preferences stats) {
		getStats();
		this.deaths = stats.getInteger("deaths", 0);
		this.logs = stats.getInteger("logs", 0);
		this.hiScore = stats.getInteger("hiScore", 0);
	}
	
	public void write(boolean death, int score, Preferences stats) {
		if(death) this.deaths++;
		stats.putInteger("deaths", this.deaths);
		stats.putInteger("logs", this.logs + score);
		if (score > this.hiScore) this.hiScore = score;
		stats.putInteger("hiScore", this.hiScore);
	}
}
