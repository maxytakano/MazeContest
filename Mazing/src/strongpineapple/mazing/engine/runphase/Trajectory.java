/**
 * 
 */

package strongpineapple.mazing.engine.runphase;

import java.util.*;


import com.badlogic.gdx.math.Vector2;

/**
 * This class describes the runner's motion and state for the entire duration of the run phase.
 */
public class Trajectory  {
	
	private List<TrajectoryNode> nodes;
	
	/**
	 * Creates a Trajectory from the segments.
	 * 
	 * @param nodes
	 *            A temporally ordered list of trajectory nodes. First node must
	 *            occur at time 0 and last node is not expected to provide a
	 *            position function.
	 */
	public Trajectory(List<TrajectoryNode> nodes) {
		this.nodes = nodes;
	}

	public Vector2 getRunnerPosition(float time) {
		if (time < 0 || time > nodes.get(nodes.size() - 1).getTime())
			throw new RuntimeException("Time must be within trajectory duration.");
		
		for (int i = 0; i < nodes.size() - 1; i++) {
			if (time <= nodes.get(i + 1).getTime()) {
				return nodes.get(i).getRunnerPosition(time);
			}
		}
		
		throw new RuntimeException("Didn't expect to get here");
	}
	
	/**
	 * @return The amount of time the runner takes to complete the trajectory.
	 */
	public float getRunDuration() {
		return nodes.get(nodes.size() - 1).getTime();
	}
	
	public Vector2 getStartLocation() {
		return nodes.get(0).getPosition();
	}
}