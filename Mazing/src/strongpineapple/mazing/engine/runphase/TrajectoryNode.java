package strongpineapple.mazing.engine.runphase;

import strongpineapple.mazing.engine.utils.Function;

import com.badlogic.gdx.math.Vector2;

public class TrajectoryNode {
	private float time;
	private Vector2 position;
	
	/**
	 * A function that takes the run phase elapsed time as input and returns the
	 * runner's position as output.
	 */
	private Function<Vector2, Float> positionFunction;

	public TrajectoryNode(float time, Vector2 position,
			Function<Vector2, Float> positionFunction) {
		this.time = time;
		this.position = position;
		this.positionFunction = positionFunction;
	}

	public TrajectoryNode(final float time, final Vector2 position,
			final Vector2 velocity) {
		this.time = time;
		this.position = position;
		this.positionFunction = new Function<Vector2, Float>() {
			
			@Override
			public Vector2 eval(Float arg) {
				// Calculate the time elapsed since the start of this segment
				float delta = arg - time;
				// position = x + vt
				return position.cpy().add(velocity.cpy().mul(delta));
			}
		};
	}

	public float getTime() {
		return time;
	}

	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Calculates the position of the Runner at the specified time based on the
	 * state of the runner during this segment. Results are valid for values of
	 * <code>time</code> between this segment's start time and end time. If a
	 * value outside of this time-frame is provided, the result is calculated as
	 * if the runner maintained its state at this node for the duration.
	 * 
	 * @param time
	 *            the time since the start of the run phase for which to
	 *            calculate the runner's position.
	 * @return the position of the Runner at the specified time.
	 */
	public Vector2 getRunnerPosition(float time) {
		return positionFunction.eval(time);
	}
}
