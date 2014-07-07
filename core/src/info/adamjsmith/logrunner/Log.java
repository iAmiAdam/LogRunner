package info.adamjsmith.logrunner;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Log extends GameObject {
	
	BodyDef logDef;
	FixtureDef logFixtures;
	Body logBody;
	Fixture fixture;
	float x;
	float y;
	float width;
	float height;
	float velocity;
	Vector2 pos;
	World world;
	
	public int spawnedLogs;
	public float lastLogTime;
	public float logVelocity;
	public float spawnInterval;
	public boolean scored;
	public int ID = GameObject.IDLog;
	
	public Log(float x, float velocity, World world) {
		this.x = x;
		this.y = 9.9f;
		this.world = world;
		this.ID = GameObject.IDLog;
		
		Random generator = new Random();
		float width = generator.nextFloat() * (3.5f - 2.3f) + 2.3f; 
		float height = generator.nextFloat() * (0.7f - 0.3f) + 0.3f;
		
		this.width = width;
		this.height = height;
		this.logVelocity = velocity;
		
		logDef = new BodyDef();
		logDef.type = BodyType.KinematicBody;
		logDef.position.set(x, this.y);
		logDef.fixedRotation=true;
		
		PolygonShape shape = new PolygonShape();
		
		Vector2[] vertices = new Vector2[7];
		vertices[0] = new Vector2(0.5f, 0f);
		vertices[1] = new Vector2(0f, this.height / 2.0f);
		vertices[2] = new Vector2(0.5f, this.height);
		vertices[3] = new Vector2(this.width - 0.5f, this.height);
		vertices[4] = new Vector2(this.width, this.height / 2.0f);
		vertices[5] = new Vector2(this.width - 0.5f, 0f);
		vertices[6] = new Vector2(0.5f, 0f);
		
		shape.set(vertices);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.3f;
		fixtureDef.friction = 0f;
		fixtureDef.restitution = 0f;
		
		
		logBody = world.createBody(logDef);
		logBody.setLinearDamping(0);
		logBody.setLinearVelocity(new Vector2(velocity, 0));
		fixture = logBody.createFixture(fixtureDef);
		fixture.setUserData(this.ID);
		logBody.setUserData(this);
	}
	
	public float getX() {
		pos = logBody.getPosition();
		return pos.x;
	}
	
	public float getY() {
		pos = logBody.getPosition();
		return pos.y;
	}
	
	public void destroy() {
		world.destroyBody(logBody);
	}
	
	public boolean scored(Log log) {
		return log.scored;
	}
}