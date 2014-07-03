package info.adamjsmith.logrunner;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Player extends Object {
	float x;
	float y;
	float width;
	float height;
	public int score;
	enum state { air, land };
	BodyDef playerDef;
	FixtureDef playerFixtures;
	Body playerBody;
	Fixture fixture;
	Vector2 pos;
	World world;
	public enum PlayerState {
		AIR, LAND
	}
	public PlayerState playerState;
	
	
	public Player(Rectangle player, World worldI) {
		player = new Rectangle();
		this.x = 4f;
		this.y = 10.4f; 
		this.width = 1.2f;
		this.height = 1.35f;
		
		world = worldI;
		
		playerDef = new BodyDef();
		playerDef.type = BodyType.DynamicBody;
		playerDef.position.set(this.x, this.y);
		playerDef.fixedRotation=true;
		
		playerBody = world.createBody(playerDef);
		
		PolygonShape shape = new PolygonShape();
		
		Vector2[] vertices = new Vector2[4];
		vertices[0] = new Vector2(0f, 0f);
		vertices[1] = new Vector2(0f, this.height);
		vertices[2] = new Vector2(this.width, this.height);
		vertices[3] = new Vector2(this.width, 0f);
		
		shape.set(vertices);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.9f;
		fixtureDef.friction = 0f;
		fixtureDef.restitution = 0f;
		fixture = playerBody.createFixture(fixtureDef);
		playerBody.setUserData("player");
		
		playerState = PlayerState.AIR;
		
		score = 0;
	}
	
	public void jump() {
		if(playerState == PlayerState.LAND) {
			pos = playerBody.getPosition();
			playerBody.applyLinearImpulse(0, 7.5f, pos.x, pos.y, true);
			playerState = PlayerState.AIR;
		}
	}
	
	public void destroy() {
		world.destroyBody(playerBody);
	}
	
	public float getX() {
		pos = playerBody.getPosition();
		return pos.x;
	}
	
	public float getY() {
		pos = playerBody.getPosition();
		return pos.y;
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.height;
	}	
	
	public int getScore() {
		return score;
	}
}