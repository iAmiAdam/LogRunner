package info.adamjsmith.logrunner;

public class Achievements {
	
	LogRunner game;
	
	public Achievements(LogRunner game) {
		this.game = game;
	}
	
	public void check() {
		if (game.stats.hiScore >= 10) {
			unlock("CgkIqve61Y4EEAIQAA");
		}
		if (game.stats.hiScore >= 50) {
			unlock("CgkIqve61Y4EEAIQAQ");
		}
		if (game.stats.hiScore >= 100) {
			unlock("CgkIqve61Y4EEAIQAg");
		}
		if (game.stats.deaths >  50) {
			unlock("CgkIqve61Y4EEAIQAw");
		}
		if (game.sessionDeaths >= 10 && game.startTime / 1000000000.0 < 180) {
			unlock("CgkIqve61Y4EEAIQBA");
		}
	}
	
	public void unlock(String id) {
		game.actionResolver.unlockAchievementGPGS(id);
	}
}