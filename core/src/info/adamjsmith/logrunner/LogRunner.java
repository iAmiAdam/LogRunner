package info.adamjsmith.logrunner;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class LogRunner extends ApplicationAdapter {
	OrthographicCamera camera;
	Texture logImage;
	Rectangle log;
	Texture riverImage;
	Rectangle river;
	Texture bankImage;
	Rectangle bank;
	Texture playerImage;
	Player player;
	Array<Log> logs;
	SpriteBatch batch;
	long lastLogTime;
	World world;
	Box2DDebugRenderer debugRenderer;
	BodyDef playerDef;
	Body playerBody;
	Fixture fixture;
	Rectangle rectangle;
	Boolean landed;
	Vector2 pos;
	Vector2 logPos;
	Iterator<Log> iter;
	Sprite playerSprite;
	FPSLogger fpsLogger = new FPSLogger();
	double accumulator;
	double currentTime;
	float step = 1.0f/ 60.0f;
	BodyDef riverDef;
	
	
	@Override
	public void create () {
		accumulator = 1/60f;
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
		
		logImage = new Texture(Gdx.files.internal("log.png"));
		riverImage = new Texture(Gdx.files.internal("river.png"));
		bankImage = new Texture(Gdx.files.internal("bank.png"));
		playerImage = new Texture(Gdx.files.internal("player.png"));
		playerSprite = new Sprite(playerImage);
				
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 25f, 15f);
		
		river = new Rectangle();
		river.x = 0;
		river.y = 2f;
		river.width = 25f;
		river.height = 2f;
		
		bank = new Rectangle();
		bank.x = 0;
		bank.y = 0;
		bank.width = 25f;
		bank.height = 2f;
		
		player = new Player(new Rectangle(), world);
		
		batch = new SpriteBatch();
		
		logs = new Array<Log>();
		log = new Rectangle();
		logs.add(new Log(log, 16f, 4f, world));
		spawnLog();
		
		
		
		landed = false;
	}
	
	private void spawnLog() {
		log = new Rectangle();
		logs.add(new Log(log, 25f, 4f, world));
		lastLogTime = TimeUtils.nanoTime();
	}

	@Override
	public void render() {
		double newTime =  TimeUtils.millis() / 1000.0;
		double frameTime =  Math.min(newTime - currentTime, 0.25);
		float deltaTime =  (float)frameTime;
		
		accumulator += deltaTime;
		
		currentTime = newTime;
		
		Gdx.gl.glClearColor(0.5f, 0.7f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
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
		
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		batch.draw(bankImage, bank.x, bank.y, bank.width, bank.height);
		for(Log log: logs) {
			batch.draw(logImage, log.x, log.y, log.width, log.height);
		}
		batch.draw(riverImage, river.x, river.y, river.width, river.height);
		batch.draw(playerImage, player.x, pos.y, player.width, player.height);
		batch.end();
		
		if(Gdx.input.justTouched() && pos.y > 4f && pos.y < 5f) {
				player.playerBody.applyLinearImpulse(0, 10, pos.x, pos.y, true);
				landed = false;
		}	
		
		
		debugRenderer.render(world, camera.combined);
		world.step(step, 6, 4);
		
		if((TimeUtils.nanoTime() - lastLogTime) / 1000000000 > 0.8) spawnLog();
	}
	
	@Override
	public void dispose() {
		logImage.dispose();
		riverImage.dispose();
		playerImage.dispose();
		bankImage.dispose();
		batch.dispose();
	}
	
	public void interpolate() {
		iter = logs.iterator();
		while(iter.hasNext()) {
			Log log = iter.next();
			logPos = log.logBody.getPosition();
			log.x = (int) logPos.x;
			if(log.x + 4f == 0) {
				iter.remove();
				log = null;
			}
		}
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override 
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		
	}
}
