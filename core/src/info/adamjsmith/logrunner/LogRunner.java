package info.adamjsmith.logrunner;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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
	Rectangle player;
	Array<Rectangle> logs;
	SpriteBatch batch;
	long lastLogTime;
	World world;
	Box2DDebugRenderer debugRenderer;
	BodyDef playerDef;
	Body playerBody;
	Fixture fixture;
	Rectangle rectangle;
	Boolean landed;
	
	@Override
	public void create () {
		
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
		
		logImage = new Texture(Gdx.files.internal("log.png"));
		riverImage = new Texture(Gdx.files.internal("river.png"));
		bankImage = new Texture(Gdx.files.internal("bank.png"));
		playerImage = new Texture(Gdx.files.internal("player.png"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		river = new Rectangle();
		river.x = 0;
		river.y = 30;
		river.width = 800;
		river.height = 70;
		
		bank = new Rectangle();
		bank.x = 0;
		bank.y = 0;
		bank.width = 800;
		bank.height = 30;
		
		player = new Rectangle();
		player.x = 390;
		player.y = 110; 
		player.width = 20;
		player.height = 40;
		
		batch = new SpriteBatch();
		
		logs = new Array<Rectangle>();
		log = new Rectangle();
		log.x = 200;
		log.y = 80;
		log.width = 230;
		log.height = 30;
		logs.add(log);
		spawnLog();
		
		playerDef = new BodyDef();
		playerDef.type = BodyType.DynamicBody;
		playerDef.position.set(390, 140);
		
		playerBody = world.createBody(playerDef);
		
		CircleShape circle = new CircleShape();
		circle.setRadius(0.2f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.2f;
		fixture = playerBody.createFixture(fixtureDef);
		
		landed = false;
		
	}
	
	private void spawnLog() {
		log = new Rectangle();
		log.x = 800;
		log.y = 80;
		log.width = 230;
		log.height = 30;
		logs.add(log);
		lastLogTime = TimeUtils.nanoTime();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.7f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Vector2 pos = playerBody.getPosition();
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		batch.draw(bankImage, bank.x, bank.y);
		for(Rectangle log: logs) {
			batch.draw(logImage, log.x, log.y);
		}
		batch.draw(riverImage, river.x, river.y);
		batch.draw(playerImage, player.x, player.y);
		batch.end();
		
		Iterator<Rectangle> iter = logs.iterator();
		while(iter.hasNext()) {
			Rectangle log = iter.next();
			log.x -= 250 * Gdx.graphics.getDeltaTime();
			if(log.x + 230 == 0) iter.remove();
			if(player.overlaps(log))
				landed = true;
			else
				landed = false;
		}
		if(Gdx.input.isTouched() && landed == true ) {
				playerBody.applyForceToCenter(0, 15f, true);
		}	
		
		if (landed == true) {
			player.y = 115;
		} else {
			pos = playerBody.getPosition();
			player.y = pos.y;
		}
		
		if((TimeUtils.nanoTime() - lastLogTime) / 1000000000 > 1) spawnLog();
		
		debugRenderer.render(world, camera.combined);
		world.step(1/45f, 6, 2);
	}
	
	@Override
	public void dispose() {
		logImage.dispose();
		riverImage.dispose();
		playerImage.dispose();
		bankImage.dispose();
		batch.dispose();
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
