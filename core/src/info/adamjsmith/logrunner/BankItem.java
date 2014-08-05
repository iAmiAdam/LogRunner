package info.adamjsmith.logrunner;

import java.util.Random;

public class BankItem {
	public float x;
	public float y;
	public int id;
	private Random rand = new Random();
	
	public BankItem() {
		this.id = type();
		this.x = 15f;
		this.y = newY();
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void update() {
		x = x - 0.1f;
	}
	
	private int type() {
		return rand.nextInt((3 - 1) + 1) + 1;
	}
	
	private float newY() {
		return (float)rand.nextInt((int) ((6f - 1) + 1)) + 1;
	}

}