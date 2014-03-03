/**
 *  
 */

package strongpineapple.mazing.engine.runphase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import strongpineapple.mazing.config.GameConfig;
import strongpineapple.mazing.engine.Maze;
import strongpineapple.mazing.engine.runphase.events.CompletedPathEvent;
import strongpineapple.mazing.engine.runphase.events.RunPhaseEvent;
import strongpineapple.mazing.engine.utils.Point;

import com.badlogic.gdx.math.Vector2;

/*
 */
public class RunPhaseEngine  {

	private static float BASE_SPEED = GameConfig.RUNNER_BASE_SPEED;

	private Point runnerStart;
	private Point runnerEnd;
	private Maze maze;
	
	private Trajectory calculatedTrajectory;
	private List<RunPhaseEvent> calculatedEvents;
	
	public RunPhaseEngine(Maze maze, Point runnerStart, Point runnerEnd)
	{
		this.maze = maze;
		this.runnerStart = runnerStart;
		this.runnerEnd = runnerEnd;
		calculate();
	}
	
	/**
	 * Uses game config values for runner start and runner end.
	 */
	public RunPhaseEngine(Maze maze) {
		this(maze, GameConfig.RUNNER_START, GameConfig.RUNNER_END);
	}
	
	public Trajectory getTrajectory() {
		return calculatedTrajectory;
	}
	
	public List<RunPhaseEvent> getEvents() {
		return calculatedEvents;
	}
	
	private void calculate() {
		Runner runner = new Runner(BASE_SPEED);
		runner.position = this.runnerStart.toVector2();
		runner.velocity = new Vector2(BASE_SPEED, 0);
		float time = 0;
		
		List<RunPhaseProcessor> processors = new ArrayList<RunPhaseProcessor>();
		processors.add(new PathProcessor(maze, runner, runnerStart, runnerEnd));
		processors.add(new SlowTowerProcessor(maze, runner));
		processors.add(new SlowEffectExpirationProcessor(runner));
		
		List<TrajectoryNode> nodes = new ArrayList<TrajectoryNode>();
		
		List<RunPhaseEvent> events = new ArrayList<RunPhaseEvent>();
		RunPhaseEvent nextEvent;
		do {
			nextEvent = null;
			RunPhaseProcessor nextEventProcessor = null;
			
			for (RunPhaseProcessor processor : processors) {
				RunPhaseEvent event = processor.calculateSoonestEvent(time);
				if (RunPhaseEvent.first(event, nextEvent) == event) {
					nextEvent = event;
					nextEventProcessor = processor;
				}
			}
			
			nodes.add(nextEventProcessor.processCalculatedEvent(time));
			events.add(nextEvent);
			time = nextEvent.getTime();
		} while (nextEvent.getClass() != CompletedPathEvent.class);
		
		this.calculatedEvents = events;
		this.calculatedTrajectory = new Trajectory(nodes);
	}
	
	/**
	 * @return The base speed of the runner.
	 */
	public float getRunnerBaseSpeed() {
		return BASE_SPEED;
	}

	public float getRunDuration() {
		return calculatedTrajectory.getRunDuration();
	}
	
	public static class RunDurationComparator implements Comparator<RunPhaseEngine> {

		@Override
		public int compare(RunPhaseEngine o1, RunPhaseEngine o2) {
			return Float.compare(o1.getRunDuration(), o2.getRunDuration());
		}
		
	}
}
