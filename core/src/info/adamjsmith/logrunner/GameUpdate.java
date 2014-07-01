package info.adamjsmith.logrunner;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameUpdate {
	
	protected World world;
	
	OrthographicCamera camera;
	Player player;
	public Array<Log> logs;
	SpriteBatch batch;
	long lastLogTime;
	Vector2 pos;
	Vector2 logPos;
	Iterator<Log> iter;
	float spawnInterval;
	int spawnedLogs;	
	float logVelocity;
	Rectangle log;
	
	public GameUpdate() {
		
		world = new World(new Vector2(0, -10f), false);
		
		spawnedLogs = 0;
		logVelocity = -5.5f;
		spawnInterval = 0.75f;
		
		player = new Player(new Rectangle(), world);
		
		logs = new Array<Log>();
		log = new Rectangle();
		logs.add(new Log(4f, 10f, logVelocity, world));
		logs.add(new Log(9f, 10f, logVelocity, world));
		logs.add(new Log(15f, 10f, logVelocity, world));
	}
	
	public void update() {
		
		pos = player.playerBody.getPosition();	
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
		
		if(Gdx.input.justTouched() && pos.y > 9 && pos.y < 11f) {
			player.jump();
		}
	
		
		if((TimeUtils.nanoTime() - lastLogTime) / 1000000000.0 > spawnInterval) { 
			logs.add(new Log(15f, 10f, logVelocity, world));
			spawnedLogs++;
			if(spawnedLogs == 10 ) {
				logVelocity -= 0.25f;
				spawnInterval -= 0.02f;
				spawnedLogs = 0;
			}
			lastLogTime = TimeUtils.nanoTime();
		}
		
		world.step(1/45f, 6, 4);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Array<Log> getLogs() {
		return logs;
	}

}
