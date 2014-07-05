package info.adamjsmith.logrunner;
 
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.files.FileHandle;
 
 public class Stats {
 	
 	int deaths;
 	int logs;
 	int hiScore;
 	
 	public void create() {
 		FileHandle stats = Gdx.files.local("stats.txt");
 		stats.writeBytes(new byte[] {0, 0, 0}, false);
 	}
 	
 	public void load() {
 		FileHandle stats = Gdx.files.local("stats.txt");
 		if(stats.exists()) {
 			byte[] bytes = stats.readBytes();
 			deaths = bytes[0];
 			logs = bytes[1];
 			hiScore = bytes[2];
 		} else {
 			create();
 		}
 	}
 	
 	public void save(boolean death, int score) {
 		FileHandle stats = Gdx.files.local("stats.txt");
 		logs += score;
 		if(death) deaths++;
 		if(score < hiScore) score = hiScore; 
 		stats.writeBytes(new byte[] {(byte)deaths, (byte)logs, (byte)score}, false);
 		stats = null;
 		load();
 	}
 }