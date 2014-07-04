package info.adamjsmith.logrunner;

import info.adamjsmith.logrunner.Player.PlayerState;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class LogListener implements ContactListener {

	Player player;
	GameUpdate updater;
	
	public LogListener(GameUpdate updaterI) {
		player = updaterI.player;
		updater = updaterI;
	}
	@Override
	public void beginContact(Contact contact) {
		if(contact.getFixtureA().getBody().getUserData() == "player" &&
				contact.getFixtureB().getBody().getUserData() == "log") {
			contact.getFixtureB().getBody().setLinearVelocity(new Vector2(updater.logVelocity, -0.75f));
			player.playerState = PlayerState.LAND;		
			player.score();
		}
	}

	@Override
	public void endContact(Contact contact) {
		if(contact.getFixtureA().getBody().getUserData() == "player" &&
				contact.getFixtureB().getBody().getUserData() == "log") {
			player.playerState = PlayerState.AIR;
			contact.getFixtureB().getBody().setLinearVelocity(new Vector2(updater.logVelocity, 0.15f));
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
}