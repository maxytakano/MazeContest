package strongpineapple.mazing.engine;

import strongpineapple.mazing.engine.utils.Point;

/**
 * A tower that applies a non-stacking slow effect to the runner when it comes within range
 * of the tower.
 * 
 * @author Dylan
 * 
 */
public class SlowTower extends StandardBlock {
	public static final ResourceBundle COST = new ResourceBundle(0, 1);
	public static final float RANGE = 2;
	public static final float SLOW_FACTOR = .5f;
	public static final float SLOW_DURATION = 2f;
	public static final float COOLDOWN = 2f;
	
	public SlowTower(int x, int y) {
		super(x, y);
	}
	
	public SlowTower(int x, int y, boolean rock) {
		super(x, y, rock);
	}

	public SlowTower(Point location) {
		super(location);
	}

	public ResourceBundle getCost() {
		return COST;
	}
	
	public float getRange() {
		return RANGE;
	}

	public float getSpeedModifier() {
		return SLOW_FACTOR;
	}

	public Float getSlowDuration() {
		return SLOW_DURATION;
	}

	public float getCooldown() {
		return COOLDOWN;
	}
}
