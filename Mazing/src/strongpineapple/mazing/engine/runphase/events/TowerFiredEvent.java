package strongpineapple.mazing.engine.runphase.events;

import com.badlogic.gdx.math.Vector2;

import strongpineapple.mazing.engine.SlowTower;

public class TowerFiredEvent extends RunPhaseEvent {
	private SlowTower slowTower;
	private Vector2 runnerPosition;

	public TowerFiredEvent(float time, SlowTower slowTower, Vector2 runnerPosition) {
		super(time);
		this.slowTower = slowTower;
		this.runnerPosition = runnerPosition;
	}
	
	public SlowTower getSlowTower() {
		return slowTower;
	}
	
	public Vector2 getRunnerPosition() {
		return runnerPosition;
	}
}
