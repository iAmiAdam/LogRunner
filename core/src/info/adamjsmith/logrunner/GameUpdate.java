package info.adamjsmith.logrunner;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameUpdate {
	
	protected World world;
	private GameState currentState;
	public enum GameState {
		READY, RUNNING
	}
	
	Player player;
	public Array<Log> logs;
	long lastLogTime;
	Vector2 pos;
	Vector2 logPos;
	Iterator<Log> iter;
	float spawnInterval;
	int spawnedLogs;	
	float logVelocity;
	Array<Body> bodies;
	
	public GameUpdate() {
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
		}
	}
	
	public void updateRunning() {
		iter = logs.iterator();
		while(iter.hasNext()) {
			Log log = iter.next();
			logPos = log.logBody.getPosition();
			log.x = logPos.x;
			if(log.x + 4f == 0) {
				iter.remove();
				log = null;
			}
		}
		
		if(Gdx.input.justTouched()) {
			player.jump();
		}
	
		if((TimeUtils.nanoTime() - lastLogTime) / 1000000000.0 > spawnInterval) { 
			logs.add(new Log(15f, logVelocity, world));
			spawnedLogs++;
			if(spawnedLogs % 10  == 0) {
				logVelocity -= 0.25f;
				spawnInterval -= 0.02f;
			}
			lastLogTime = TimeUtils.nanoTime();
		}
		
		world.step(1/45f, 6, 4);
		
		if(player.getY() < 9f) {
			reset();
		}
	}
	
	public void reset() {
		player.destroy();
		world.dispose();
		world = null;
		player = null;
		logs = null;
		init();
	}
	
	private void init() {
		world = new World(new Vector2(0, -10f), false);
		player = new Player(new Rectangle(), world);
		logs = new Array<Log>();
		
		world.setContactListener(new LogListener(player));
		
		spawnedLogs = 0;
		logVelocity = -5.5f;
		spawnInterval = 0.85f;
		
		logs.add(new Log(4f, logVelocity, world));
		logs.add(new Log(9.5f, logVelocity, world));	
		
		currentState = GameState.READY;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Array<Log> getLogs() {
		return logs;
	}

}
