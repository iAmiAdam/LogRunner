package info.adamjsmith.logrunner;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
	protected LogRunner game;
	OrthographicCamera camera;
	Texture logImage;
	Rectangle log;
	Texture riverImage;
	Rectangle river;
	Texture bankImage;
	Rectangle bank;
	Texture cloudImage;
	Rectangle clouds;
	Texture playerImage;
	Texture currentPlayer;
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
	Texture fishImage;
	Texture hillsImage;
	Texture bg;
	
	float spawnInterval;
	
	int spawnedLogs;	
	float logVelocity;
	
	TextureRegion playerI;
	
	Animation walkAnimation;
	TextureRegion[] walkFrames;
	TextureRegion currentFrame;
	float stateTime;
	
	private static final int FRAME_COLS = 8;
	private static final int FRAME_ROWS = 1;
	
	public GameScreen(LogRunner game) {
		this.game = game;
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.450f, 0.772f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		pos = player.playerBody.getPosition();		
		
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		
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
		batch.draw(bg, 0f, 10f, 15f, 4f);
		for(Log log: logs) {
			batch.draw(logImage, log.x, log.y, log.width, log.height);
		}
		batch.draw(riverImage, river.x, river.y, river.width, river.height);
		batch.draw(cloudImage, clouds.x, clouds.y, clouds.width, clouds.height);
		if (pos.y > 10.6f) {
			batch.draw(currentPlayer, player.x, pos.y, player.width, player.height);
		} else {
			batch.draw(currentFrame, player.x, pos.y, player.width, player.height);
		}
		batch.end();
		
		if(Gdx.input.justTouched() && pos.y > 9 && pos.y < 11f) {
				player.playerBody.applyLinearImpulse(0, 4, pos.x, pos.y, true);
				landed = false;
		}	
		
		
		debugRenderer.render(world, camera.combined);
		world.step(1/45f, 6, 4);
		
		if((TimeUtils.nanoTime() - lastLogTime) / 1000000000.0 > spawnInterval) spawnLog();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
		
		currentPlayer = game.manager.get("jump.png", Texture.class);	
		currentPlayer.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		logImage = game.manager.get("log.png", Texture.class);
		riverImage = game.manager.get("river.png", Texture.class);
		bankImage = game.manager.get("bank.png", Texture.class);
		playerImage = game.manager.get("player.png", Texture.class);
		playerImage.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		cloudImage = game.manager.get("clouds.png", Texture.class);
		cloudImage.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		fishImage = game.manager.get("fish.png", Texture.class);
		hillsImage = game.manager.get("hills.png", Texture.class);
		hillsImage.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bg = game.manager.get("background.png", Texture.class);
		bg.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		spawnedLogs = 0;
		logVelocity = -5.5f;
		spawnInterval = 0.75f;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 15f, 25f);
		
		river = new Rectangle();
		river.x = 0;
		river.y = 7f;
		river.width = 15f;
		river.height = 3f;
		
		bank = new Rectangle();
		bank.x = 0;
		bank.y = 0;
		bank.width = 15f;
		bank.height = 7f;
		
		clouds = new Rectangle();
		clouds.x = 0;
		clouds.y = 15f;
		clouds.width= 15f;
		clouds.height = 3f;
		
		player = new Player(new Rectangle(), world);
		
		batch = new SpriteBatch();
		
		logs = new Array<Log>();
		log = new Rectangle();
		logs.add(new Log(log, 4f, 10f, logVelocity, world));
		logs.add(new Log(log, 9f, 10f, logVelocity, world));
		spawnLog();
		
		TextureRegion[][] tmp = TextureRegion.split(playerImage, 32, 64);
		
		walkFrames = new TextureRegion [FRAME_COLS * FRAME_ROWS];
		int index = 0;
		
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		
		walkAnimation = new Animation(0.10f, walkFrames);
		stateTime = 0f;

	}
	
	private void spawnLog() {
		log = new Rectangle();
		logs.add(new Log(log, 15f, 10f, logVelocity, world));
		spawnedLogs++;
		if(spawnedLogs == 10 ) {
			logVelocity -= 0.25f;
			spawnInterval -= 0.05f;
			spawnedLogs = 0;
		}
		lastLogTime = TimeUtils.nanoTime();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		logImage.dispose();
		riverImage.dispose();
		playerImage.dispose();
		bankImage.dispose();
		batch.dispose();
	}

}
