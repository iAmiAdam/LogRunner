package info.adamjsmith.logrunner;

import info.adamjsmith.logrunner.Player.PlayerState;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class LogListener implements ContactListener {

	Player player;
	
	public LogListener(Player playerI) {
		player = playerI;
	}
	@Override
	public void beginContact(Contact contact) {
		player.playerState = PlayerState.LAND;
		
	}

	@Override
	public void endContact(Contact contact) {
		player.playerState = PlayerState.AIR;
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
}