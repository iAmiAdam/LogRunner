package info.adamjsmith.logrunner;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
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
	
	@Override
	public void create () {
		
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
		spawnLog();
	}
	
	private void spawnLog() {
		Rectangle log = new Rectangle();
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
		
		if(Gdx.input.isTouched()) {
			
			
		}
		
		if((TimeUtils.nanoTime() - lastLogTime) / 1000000000 > 1) spawnLog();
		
		Iterator<Rectangle> iter = logs.iterator();
		while(iter.hasNext()) {
			Rectangle log = iter.next();
			log.x -= 250 * Gdx.graphics.getDeltaTime();
			if(log.x + 230 == 0) iter.remove();
		}
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
