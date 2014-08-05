package info.adamjsmith.logrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	
	AssetManager manager;
	
	Texture logo;
	Texture log;
	Texture river;
	Texture bank;
	Texture player;
	Texture cloud;
	Texture bg;
	Texture platform;
	Texture fish;
	Texture chest;
	Texture fossil;
	BitmapFont headerFont;
	BitmapFont menuFont;
	Sound point;
	Sound jump;
	Sound click;
	Skin uiSkin;
	
	public Assets() {
		manager = new AssetManager();
	}
	
	public void load(LogRunner game) {
		manager.load("logo.png", Texture.class);		
		manager.load("log.png", Texture.class);
		manager.load("river.png", Texture.class);
		manager.load("bank.png", Texture.class);
		manager.load("player.png", Texture.class);
		manager.load("cloud.png", Texture.class);
		manager.load("background.png", Texture.class);
		manager.load("platform.png", Texture.class);
		manager.load("header.fnt", BitmapFont.class);
		manager.load("point.ogg", Sound.class);
		manager.load("jump.ogg", Sound.class);
		manager.load("click.ogg", Sound.class);
		manager.load("fish.png", Texture.class);
		manager.load("chest.png", Texture.class);
		manager.load("hedgehog.png", Texture.class);
		manager.finishLoading();
		
		logo = manager.get("logo.png", Texture.class);
		log = manager.get("log.png", Texture.class);
		river = manager.get("river.png", Texture.class);
		bank = manager.get("bank.png", Texture.class);
		player = manager.get("player.png", Texture.class);
		cloud = manager.get("cloud.png", Texture.class);
		bg = manager.get("background.png", Texture.class);
		platform = manager.get("platform.png", Texture.class);
		fish = manager.get("fish.png", Texture.class);
		chest = manager.get("chest.png", Texture.class);
		fossil = manager.get("hedgehog.png", Texture.class);
		
		point = Gdx.audio.newSound(Gdx.files.internal("point.ogg"));
		jump = Gdx.audio.newSound(Gdx.files.internal("jump.ogg"));
		click = Gdx.audio.newSound(Gdx.files.internal("click.ogg"));
		
		headerFont = new BitmapFont(Gdx.files.internal("header.fnt"), Gdx.files.internal("header.png"), false);
		headerFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		menuFont = new BitmapFont(Gdx.files.internal("menu.fnt"), Gdx.files.internal("menu.png"), false);
		menuFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
		uiSkin.getFont("header-font").setMarkupEnabled(true);
		uiSkin.getFont("button-font").setMarkupEnabled(true);
	}
}