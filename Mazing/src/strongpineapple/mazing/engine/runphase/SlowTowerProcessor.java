package strongpineapple.mazing.engine.runphase;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

import strongpineapple.mazing.engine.Maze;
import strongpineapple.mazing.engine.SlowTower;
import strongpineapple.mazing.engine.runphase.events.CooldownExpiredEvent;
import strongpineapple.mazing.engine.runphase.events.RunPhaseEvent;
import strongpineapple.mazing.engine.runphase.events.TowerFiredEvent;
import strongpineapple.mazing.engine.utils.VectorUtil;

import com.badlogic.gdx.math.Vector2;

public class SlowTowerProcessor implements RunPhaseProcessor {

	private Collection<SlowTower> slowTowers;
	private Queue<CooldownExpiredEvent> cooldownQueue = new ArrayDeque<CooldownExpiredEvent>();
	private Collection<SlowTower> towersOffCooldown;
	private Runner runner;
	
	private RunPhaseEvent calculatedEvent;
	
	public SlowTowerProcessor(Maze maze, Runner runner) {
		this.runner = runner;
		this.slowTowers = maze.getBlocksOfType(SlowTower.class);
		this.towersOffCooldown = new ArrayList<SlowTower>(slowTowers);
	}

	@Override
	public RunPhaseEvent calculateSoonestEvent(float currentTime) {
		Vector2 currentLocation = runner.position;
		Vector2 currentVelocity = runner.velocity;
		
		List<RunPhaseEvent> events = new ArrayList<RunPhaseEvent>();
		events.add(cooldownQueue.peek());
		
		for (SlowTower cursor : towersOffCooldown) {
			float combinedRadius = runner.getRadius() + cursor.getRange();
			
			List<Vector2> intersections = VectorUtil.intersectRayCircle(currentLocation, currentVelocity, 
					cursor.getCenter().toVector2(), combinedRadius);
			
			if (intersections.size() == 0)
				continue;
			
			Vector2 intersection1 = intersections.get(0);
			Vector2 intersection2 = intersections.size() == 1 ? intersections.get(0) : intersections.get(1);
			
			float windowOpening = runner.timeToReachTarget(intersection1);
			float windowClosing = runner.timeToReachTarget(intersection2);
			
			if (windowClosing < windowOpening) {
				float tmp = windowOpening;
				windowOpening = windowClosing;
				windowClosing = tmp;
				Vector2 tmpV = intersection1;
				intersection1 = intersection2;
				intersection2 = tmpV;
			}
			
			if (windowClosing >= 0) {
				if (windowOpening < 0) {
					// Runner is inside of the tower range
					events.add(new TowerFiredEvent(currentTime, cursor, runner.position));
				}
				else {
					// Runner is approaching tower range
					events.add(new TowerFiredEvent(currentTime + windowOpening, cursor, intersection1));
				}
			}
		}
		
		calculatedEvent = RunPhaseEvent.first(events);
		return calculatedEvent;
	}

	@Override
	public TrajectoryNode processCalculatedEvent(float currentTime) {
		if (calculatedEvent instanceof TowerFiredEvent) {
			TowerFiredEvent fireEvent = (TowerFiredEvent) calculatedEvent;
			SlowTower slowTower = fireEvent.getSlowTower();
			
			cooldownQueue.add(new CooldownExpiredEvent(currentTime + slowTower.getCooldown(), slowTower));
			towersOffCooldown.remove(slowTower);
			
			runner.velocity = runner.velocity.cpy().nor().mul(runner.getBaseSpeed() * slowTower.getSpeedModifier());
			runner.position = fireEvent.getRunnerPosition();
			runner.slowExpiration = fireEvent.getTime() + slowTower.getSlowDuration();
			
			return new TrajectoryNode(fireEvent.getTime(), runner.position, runner.velocity);
		}
		else {
			CooldownExpiredEvent cooldownExpiration = (CooldownExpiredEvent) calculatedEvent;
			cooldownQueue.remove();
			towersOffCooldown.add(cooldownExpiration.getSlowTower());
			
			float newTime = cooldownExpiration.getTime();
			runner.position = runner.position.cpy().add(runner.velocity.cpy().mul(newTime - currentTime));
			return new TrajectoryNode(newTime, runner.position, runner.velocity);
		}
	}
}
