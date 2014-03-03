package strongpineapple.mazing.engine.runphase.events;

import strongpineapple.mazing.engine.SlowTower;

public class CooldownExpiredEvent extends RunPhaseEvent {
	private SlowTower slowTower;

	public CooldownExpiredEvent(float time, SlowTower slowTower) {
		super(time);
		this.slowTower = slowTower;
	}
	
	public SlowTower getSlowTower() {
		return slowTower;
	}
}
