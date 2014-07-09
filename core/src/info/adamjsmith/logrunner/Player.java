package info.adamjsmith.logrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Player extends GameObject {
	protected LogRunner game;
	float x;
	float y;
	float width;
	float height;
	public int score;
	BodyDef playerDef;
	FixtureDef playerFixtures;
	Body playerBody;
	Fixture fixture;
	Vector2 pos;
	World world;
	public enum PlayerState {
		AIR, LAND, DEAD
	}
	public PlayerState playerState;
	Rectangle player;
	
	public int ID = GameObject.IDPlayer;
	
	public Player(World worldI, LogRunner gameI) {
		player = new Rectangle();
		this.x = 4f;
		this.y = 10.8f; 
		this.width = 1.2f;
		this.height = 1.35f;
		this.ID = GameObject.IDPlayer;
		
		game = gameI;
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
		fixture.setUserData(this.ID);
		playerBody.setUserData(this);
		
		this.playerState = PlayerState.AIR;
		this.score = 0;
	}
	
	public void jump(float force) {
		if(this.playerState == PlayerState.LAND) {
			game.assets.jump.play(0.1f);
			pos = playerBody.getPosition();
			float actualForce = ((-force / Gdx.graphics.getDensity()) / 1000) * 3f;
			if (actualForce > 8) actualForce = 8;
			playerBody.applyLinearImpulse(0, actualForce, pos.x, pos.y, true);
			playerState = PlayerState.AIR;
		}
	}
	
	public void score() {
		game.assets.point.play(0.1f);
		score++;
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