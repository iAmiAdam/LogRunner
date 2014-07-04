package info.adamjsmith.logrunner.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

import info.adamjsmith.logrunner.LogRunner;

public class AndroidLauncher extends AndroidApplication implements GameHelperListener, info.adamjsmith.logrunner.ActionResolver {
	private GameHelper gameHelper;

	@Override	
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		RelativeLayout layout = new RelativeLayout(this);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		
		View gameView = initializeForView(new LogRunner(this));
		
		AdView adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId("ca-app-pub-5708097368765164/8542436531");
		
		AdRequest adRequest = new AdRequest.Builder()
		.addTestDevice("EFDE8B52D744910BE7EB01DEC797353A")
		.build();
		
		adView.loadAd(adRequest);
		
		layout.addView(gameView);
		
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		layout.addView(adView, adParams);
		
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useAccelerometer = false;
		cfg.useCompass = false;
		
		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(true);
		
		gameHelper.setup(this);
		//initialize(new LogRunner(this), cfg);
		
		setContentView(layout);
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}

	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
				
			});
		} catch (final Exception e) {
			Toast.makeText(this, "Could not log into Google Game Services", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void submitScoreGPGS(int score) {
		Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkIqve61Y4EEAIQBQ", score);
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
	}

	@Override
	public void getLeaderboardGPGS() {
		startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),"CgkIqve61Y4EEAIQBQ"), 100);
	}

	@Override
	public void getAchievementsGPGS() {
		startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
	}

	@Override
	public void onSignInFailed() {
	}

	@Override
	public void onSignInSucceeded() {
	}
}
