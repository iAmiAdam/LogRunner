package info.adamjsmith.logrunner;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class GameInputProcessor implements GestureListener{
	
	Player player;
	int preY;
	int postY;
	
	public GameInputProcessor(Player player) {
		this.player = player;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}
	
	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}
		
	@Override
	public boolean longPress(float x, float y) {
		return false;
	}
		
	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		this.player.jump(velocityY);
		return true;
	}
	
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}
		
	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}
	
	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}
		
	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
}