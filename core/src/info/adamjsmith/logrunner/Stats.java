package info.adamjsmith.logrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Stats {
	
	FileHandle stats = Gdx.files.internal("stats.txt");
	int deaths;
	int logs;
	int hiScore;
	
	public Stats() {	
		if(stats.exists()){
			load();
		} else {
			create();
		}
	}
	
	public void create() {
		stats.writeBytes(new byte[] {0, 0, 0}, false);
	}
	
	public void load() {
		byte[] bytes = stats.readBytes();
		deaths = bytes[0];
		logs = bytes[1];
		hiScore = bytes[2];
	}
	
	public void write(boolean death, int score) {
		logs += score;
		if(death) deaths++;
		stats.writeBytes(new byte[] {(byte)deaths, (byte)logs, (byte)score}, false);
	}
}
