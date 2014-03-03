package strongpineapple.mazing.engine.runphase;

import java.util.List;

import strongpineapple.mazing.engine.Maze;
import strongpineapple.mazing.engine.pathfinding.AStarPathFinder;
import strongpineapple.mazing.engine.pathfinding.PathFinder;
import strongpineapple.mazing.engine.runphase.events.CompletedPathEvent;
import strongpineapple.mazing.engine.runphase.events.RunPhaseEvent;
import strongpineapple.mazing.engine.runphase.events.RunnerTurnEvent;
import strongpineapple.mazing.engine.utils.Point;

import com.badlogic.gdx.math.Vector2;

public class PathProcessor implements RunPhaseProcessor {
	private Runner runner;
	private List<Point> path;
	private int targetIndex = 0;
	
	private RunPhaseEvent calculatedEvent;
	
	public PathProcessor(Maze maze, Runner runner, Point runnerStart, Point runnerEnd) {
		this.runner = runner;
		PathFinder pathFinder = new AStarPathFinder(maze);
		this.path = pathFinder.findPath(runnerStart, runnerEnd);
	}
	
	@Override
	public RunPhaseEvent calculateSoonestEvent(float currentTime) {
		if (targetIndex >= path.size()) {
			return null;
		}
		
		if (runner.position.equals(path.get(targetIndex).toVector2())) {
			calculatedEvent = new RunnerTurnEvent(currentTime);
		}
		else {
			float time = currentTime + runner.timeToReachTarget(path.get(targetIndex).toVector2());
			if (targetIndex == path.size() - 1) {
				calculatedEvent = new CompletedPathEvent(time);
			}
			else {
				calculatedEvent = new RunnerTurnEvent(time);
			}
		}
		
		return calculatedEvent;
	}

	@Override
	public TrajectoryNode processCalculatedEvent(float currentTime) {
		Vector2 startLocation = path.get(targetIndex).toVector2();
		runner.position = startLocation;
		
		if (calculatedEvent instanceof CompletedPathEvent) {
			runner.velocity = Vector2.Zero;
		}
		else {
			Vector2 endLocation = path.get(targetIndex + 1).toVector2();
			Vector2 direction = endLocation.cpy().sub(startLocation).nor();
			runner.velocity = direction.mul(runner.velocity.len());
		}
		
		targetIndex++;
		return new TrajectoryNode(calculatedEvent.getTime(), startLocation, runner.velocity);
	}
}
