package info.adamjsmith.logrunner;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
	
	AssetManager manager;
	
	Texture log;
	Texture river;
	Texture bank;
	Texture player;
	Texture clouds;
	Texture bg;
	Sound point;
	Sound jump;
	Sound click;
	
	public Assets() {
		manager = new AssetManager();
	}
	
	public void load(LogRunner game) {
		manager.load("log.png", Texture.class);
		manager.load("river.png", Texture.class);
		manager.load("bank.png", Texture.class);
		manager.load("player.png", Texture.class);
		manager.load("clouds.png", Texture.class);
		manager.load("background.png", Texture.class);
		manager.load("point.ogg", Sound.class);
		manager.load("jump.ogg", Sound.class);
		manager.load("click.ogg", Sound.class);
		manager.finishLoading();
		
		log = manager.get("log.png", Texture.class);
		river = manager.get("river.png", Texture.class);
		bank = manager.get("bank.png", Texture.class);
		player = manager.get("player.png", Texture.class);
		clouds = manager.get("clouds.png", Texture.class);
		bg = manager.get("background.png", Texture.class);
		point = manager.get("point.ogg", Sound.class);
		jump = manager.get("jump.ogg", Sound.class);
		click = manager.get("click.ogg", Sound.class);
	}
}