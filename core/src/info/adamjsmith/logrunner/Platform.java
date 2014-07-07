package info.adamjsmith.logrunner;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends GameObject{
	
	BodyDef platformDef;
	Body platformBody;
	FixtureDef platformFixture;
	Fixture fixture;
	World world;
	Vector2 pos;
	
	public int ID = GameObject.IDPlatform;
	
	public Platform(World world) {
		this.world = world;
		platformDef = new BodyDef();
		platformDef.type = BodyType.KinematicBody;
		platformDef.position.set(0f, 6.65f);
		platformDef.fixedRotation=true;
				
		platformBody = world.createBody(platformDef);
		platformBody.setLinearVelocity(new Vector2(-6f, 0));
		platformBody.setLinearDamping(0f);
		
		Vector2[] platformVertices = new Vector2[4];
		platformVertices[0] = new Vector2(0f, 0f);
		platformVertices[1] = new Vector2(0f, 4f);
		platformVertices[2] = new Vector2(25f, 4f);
		platformVertices[3] = new Vector2(25f, 0f);
		
		PolygonShape platformShape = new PolygonShape();
		platformShape.set(platformVertices);
		
		platformFixture = new FixtureDef();
		platformFixture.shape = platformShape;
		platformFixture.density = 1.0f;
		platformFixture.friction = 0f;
		platformFixture.restitution = 0f;

		fixture = platformBody.createFixture(platformFixture);
		fixture.setUserData(this.ID);
		platformBody.setUserData(this);
	}
	
	public float getY() {
		pos = platformBody.getPosition();
		return pos.y;
	}
	
	public float getX() {
		pos = platformBody.getPosition();
		return pos.x;
	}
}
