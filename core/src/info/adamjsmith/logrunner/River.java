package info.adamjsmith.logrunner;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class River extends GameObject{
	BodyDef riverDef;
	BodyDef floorDef;
	Body floorBody;
	Body riverBody;
	FixtureDef riverFixture;
	public int ID = GameObject.IDRiver;
	
	public River(World worldI) {
		
		riverDef = new BodyDef();
		riverDef.position.set(0f, 1f);
		riverDef.fixedRotation=true;
		
		floorDef = new BodyDef();
		floorDef.position.set(0f, 0f);
		
		floorBody = worldI.createBody(floorDef);		
		riverBody = worldI.createBody(riverDef);
		
		Vector2[] riverVertices = new Vector2[4];
		riverVertices[0] = new Vector2(0f, 0f);
		riverVertices[1] = new Vector2(0f, 9f);
		riverVertices[2] = new Vector2(15f, 9f);
		riverVertices[3] = new Vector2(15f, 0f);
		
		PolygonShape riverShape = new PolygonShape();
		riverShape.set(riverVertices);
		
		PolygonShape floorShape = new PolygonShape();
		floorShape.setAsBox(15f, 0f);
		
		floorBody.createFixture(floorShape, 1.0f);
		
		riverFixture = new FixtureDef();
		riverFixture.shape = riverShape;
		riverFixture.density = 1.0f;
		riverFixture.friction = 0f;
		riverFixture.restitution = 0f;
		riverFixture.isSensor = true;
		riverBody.createFixture(riverFixture);
		riverBody.setUserData(this);
	}
}