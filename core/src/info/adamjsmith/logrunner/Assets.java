package info.adamjsmith.logrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
	
	AssetManager manager;
	
	Texture log;
	Texture river;
	Texture bank;
	Texture player;
	Texture clouds;
	Texture bg;
	BitmapFont headerFont;
	BitmapFont menuFont;
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
		manager.load("header.fnt", BitmapFont.class);
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
		point = Gdx.audio.newSound(Gdx.files.internal("point.ogg"));
		jump = Gdx.audio.newSound(Gdx.files.internal("jump.ogg"));
		click = Gdx.audio.newSound(Gdx.files.internal("click.ogg"));
		
		headerFont = new BitmapFont(Gdx.files.internal("header.fnt"), Gdx.files.internal("header.png"), false);
		headerFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		menuFont = new BitmapFont(Gdx.files.internal("menu.fnt"), Gdx.files.internal("menu.png"), false);
		menuFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
}