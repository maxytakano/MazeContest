package strongpineapple.mazing.engine.runphase;

import com.badlogic.gdx.math.Vector2;

class Runner {
	private final float RUNNER_RADIUS = 1;
	private float baseSpeed;
	
	public Vector2 position = Vector2.Zero;
	public Vector2 velocity = Vector2.Zero;
	
	/**
	 * The time at which the slow effect expires. null if there is no slow effect.
	 */
	public Float slowExpiration;
	
	public Runner(float baseSpeed) {
		this.baseSpeed = baseSpeed;
	}
	
	/**
	 * @return the time that the the runner will take to reach the location based on position and velocity.
	 * Assumes the runner is on track to reach the target (in negative or positive time)
	 */
	public float timeToReachTarget(Vector2 target) {
		Vector2 diff = target.cpy().sub(position);
		float time = diff.len() / velocity.len();
		if (Math.signum(velocity.x) != Math.signum(diff.x) || Math.signum(velocity.y) != Math.signum(diff.y)) {
			time = -time;
		}
		
		return time;
	}

	public float getRadius() {
		return RUNNER_RADIUS;
	}

	public float getBaseSpeed() {
		return baseSpeed;
	}
	
	
}
