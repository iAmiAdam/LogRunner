package info.adamjsmith.logrunner;

import info.adamjsmith.logrunner.Player.PlayerState;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameUpdate {
	
	protected World world;
	private LogRunner game;
	public GameState currentState;
	public enum GameState {
		READY, RUNNING, GAMEOVER
	}
	
	public Player player;
	public Array<Log> logs;
	long lastLogTime;
	Vector2 pos;
	Vector2 logPos;
	Iterator<Log> iter;
	float spawnInterval;
	int spawnedLogs;	
	float logVelocity;
	Array<Body> bodies;
	River river;
	Platform platform;

	Random generator;
	
	public GameUpdate(LogRunner game) {
		this.game = game;
		generator = new Random();
		init();
	}
	
	public void update() {
		switch (currentState) {
		case READY:
			if(Gdx.input.justTouched())
				currentState = GameState.RUNNING;
			break;
		case RUNNING:
			updateRunning();
			break;
		case GAMEOVER:
			break;
		}
	}
	
	public void updateRunning() {
		iter = logs.iterator();
		while(iter.hasNext()) {
			Log log = iter.next();
			logPos = log.logBody.getPosition();
			log.x = logPos.x;
			if(log.x + 4f == 0) {
				log.destroy();
				log = null;
				iter.remove();
			}
		}
	
		if((TimeUtils.nanoTime() - lastLogTime) / 1000000000.0 > spawnInterval) { 
			logs.add(new Log(15f, logVelocity, world));
			spawnedLogs++;
			spawnInterval = generator.nextFloat() * (0.85f - 0.55f) + 0.55f;
			if(spawnedLogs % 10  == 0) {
				logVelocity -= 0.25f;
			}
			lastLogTime = TimeUtils.nanoTime();
		}
		
		world.step(1/45f, 6, 4);
		
		if(player.getY() < 9.35f || player.getX() != 4f) {
			currentState = GameState.GAMEOVER;
			gameOver();
		}
	}
	
	public void gameOver() {
		game.sessionDeaths++;
		if((game.startTime / 100000000.0) > 180 ) game.startTime = TimeUtils.nanoTime();
		
		if (game.actionResolver.getSignedInGPGS()) {
			
			if (player.score >= 10) {
				game.actionResolver.unlockAchievementGPGS("CgkIqve61Y4EEAIQAA");
			}
			if (player.score >= 50) {
				game.actionResolver.unlockAchievementGPGS("CgkIqve61Y4EEAIQAQ");
			}
			if (player.score >= 100) {
				game.actionResolver.unlockAchievementGPGS("CgkIqve61Y4EEAIQAg");
			}
			if (game.stats.deaths >  50) {
				game.actionResolver.unlockAchievementGPGS("CgkIqve61Y4EEAIQAw");
			}
			if (game.sessionDeaths >= 10 && game.startTime / 1000000000.0 < 180) {
				game.actionResolver.unlockAchievementGPGS("CgkIqve61Y4EEAIQBA");
			}
		}
		player.playerState = PlayerState.DEAD;
		game.actionResolver.showAds(true);
		game.stats.save(true, player.score);
		game.setScreen(new GameOverScreen(game, player.getScore(), player.getY(), logs));
	}
	
	public void dispose() {
		player.destroy();
		world.dispose();
		world = null;
		player = null;
		logs = null;
		river = null;
		platform = null;
	}
	
	private void init() {
		world = new World(new Vector2(0, -10f), false);
		player = new Player(world, game);
		river = new River(world);
		logs = new Array<Log>();
		platform = new Platform(world);
		
		world.setContactListener(new LogListener());
		
		spawnedLogs = 0;
		logVelocity = -5.55f;
		spawnInterval = generator.nextFloat() * (0.85f - 0.60f) + 0.60f;
		
		logs.add(new Log(4f, logVelocity, world));
		logs.add(new Log(9.5f, logVelocity, world));	
		
		game.actionResolver.showAds(false);
		
		currentState = GameState.READY;
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Array<Log> getLogs() {
		return logs;
	}
	
	public Platform getPlatform() {
		return platform;
	}
}