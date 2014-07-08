package info.adamjsmith.logrunner;

import info.adamjsmith.logrunner.Player.PlayerState;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class LogListener implements ContactListener {

	Player player;
	Log log;
	
	GameObject goA;
	GameObject goB;
	Integer fixA;
	Integer fixB;
	
	@Override
	public void beginContact(Contact contact) {
		fixA = (Integer)contact.getFixtureA().getUserData();
		fixB = (Integer)contact.getFixtureB().getUserData();
		
		if (fixA == GameObject.IDPlayer && fixB == GameObject.IDLog ){
			player = (Player)contact.getFixtureA().getBody().getUserData();
			log = (Log)contact.getFixtureB().getBody().getUserData();
			
			player.playerState = PlayerState.LAND;		
			log.logBody.setLinearVelocity(new Vector2(log.logVelocity, -0.75f));
			
			if (log.scored == false) {
				log.scored = true;
				player.score();
			}
		}
		
		if (fixA == GameObject.IDPlayer  && fixB == GameObject.IDPlatform){
			player = (Player)contact.getFixtureA().getBody().getUserData();
			player.playerState = PlayerState.LAND;
		}
	}

	@Override
	public void endContact(Contact contact) {
		fixA = (Integer)contact.getFixtureA().getUserData();
		fixB = (Integer)contact.getFixtureB().getUserData();
		goA = (GameObject)contact.getFixtureA().getBody().getUserData();
		goB = (GameObject)contact.getFixtureB().getBody().getUserData();
		
		if (fixA == GameObject.IDPlayer && fixB == GameObject.IDLog ){
			player = (Player)contact.getFixtureA().getBody().getUserData();
			log = (Log)contact.getFixtureB().getBody().getUserData();
			
			player.playerState = PlayerState.AIR;		
			log.logBody.setLinearVelocity(new Vector2(log.logVelocity, 0.10f));
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
}