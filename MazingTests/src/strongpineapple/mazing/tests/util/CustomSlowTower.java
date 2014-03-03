package strongpineapple.mazing.tests.util;

import strongpineapple.mazing.engine.SlowTower;

/**
 * A slow tower with a range of 1.
 * @author Dylan
 *
 */
public class CustomSlowTower extends SlowTower {

	public CustomSlowTower(int x, int y) {
		super(x, y);
	}

	@Override
	public float getRange() {
		// TODO Auto-generated method stub
		return 1;
	}
}
