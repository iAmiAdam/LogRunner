package info.adamjsmith.logrunner;

import info.adamjsmith.logrunner.Player.PlayerState;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
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
	
	Player player;
	Array<Log> logs;
	River river;
	Platform platform;
	
	Iterator<Log> iter;
	Random generator;
	
	float spawnInterval;	
	float logVelocity;
	long lastLogTime;
	int spawnedLogs;
	
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
			gameOver();
			break;
		}
	}
	
	public void updateRunning() {
		if(platform.getX() < -25f) platform.platformBody.setLinearVelocity(new Vector2(0, 0));;
		
		logCheck();
		
		world.step(1/45f, 6, 4);
		
		if(player.getY() < 9.35f || player.getX() != 4f) {
			currentState = GameState.GAMEOVER;
		}
	}
	
	public void gameOver() {
		game.sessionDeaths++;
		player.playerState = PlayerState.DEAD;
		game.stats.save(true, player.getScore());
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
		spawnInterval = generator.nextFloat() * (1.1f - 0.75f) + 0.75f;
		
		game.actionResolver.showAds(false);
		
		currentState = GameState.READY;
	}
	
	private void logCheck() {
		iter = logs.iterator();
		while(iter.hasNext()) {
			Log log = iter.next();
			if(log.getX() + 4f == 0) {
				log.destroy();
				log = null;
				iter.remove();
			}
		}
		
		if((TimeUtils.nanoTime() - lastLogTime) / 1000000000.0 > spawnInterval) { 
			logs.add(new Log(25f, logVelocity, world));
			spawnedLogs++;
			spawnInterval = generator.nextFloat() * (1.1f - 0.75f) + 0.75f;
			if(spawnedLogs % 10  == 0) {
				logVelocity -= 0.25f;
			}
			lastLogTime = TimeUtils.nanoTime();
		}
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