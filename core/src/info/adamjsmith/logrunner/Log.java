package info.adamjsmith.logrunner;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Log extends Object {
	
	BodyDef logDef;
	FixtureDef logFixtures;
	Body logBody;
	Fixture fixture;
	public int x;
	public int y;
	float width;
	float height;
	
	public Log(Rectangle log, int x, int y, World world) {
		this.x = x;
		this.y = y;
		this.width = 150f;
		this.height = 30f;
		
		logDef = new BodyDef();
		logDef.type = BodyType.KinematicBody;
		logDef.position.set(x, y);
		
		PolygonShape shape = new PolygonShape();
		
		Vector2[] vertices = new Vector2[4];
		vertices[0] = new Vector2(0f, 0f);
		vertices[1] = new Vector2(0f, 30f);
		vertices[2] = new Vector2(200f, 30f);
		vertices[3] = new Vector2(200f, 0f);
		
		shape.set(vertices);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.1f;
		fixtureDef.friction = 0f;
		fixtureDef.restitution = 0f;
		
		logBody = world.createBody(logDef);
		logBody.setLinearDamping(0);
		logBody.setLinearVelocity(new Vector2(-10000, 0));
		logBody.createFixture(fixtureDef);

		
	}
}