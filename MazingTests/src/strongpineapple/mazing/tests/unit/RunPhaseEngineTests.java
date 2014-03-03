package strongpineapple.mazing.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import strongpineapple.mazing.engine.BasicBlock;
import strongpineapple.mazing.engine.Block;
import strongpineapple.mazing.engine.Maze;
import strongpineapple.mazing.engine.SlowTower;
import strongpineapple.mazing.engine.runphase.RunPhaseEngine;
import strongpineapple.mazing.engine.runphase.Trajectory;
import strongpineapple.mazing.engine.runphase.events.CompletedPathEvent;
import strongpineapple.mazing.engine.runphase.events.RunPhaseEvent;
import strongpineapple.mazing.engine.utils.Point;
import strongpineapple.mazing.tests.util.CustomSlowTower;
import strongpineapple.mazing.tests.util.MazeFactory;
import strongpineapple.mazing.tests.util.MazeUtil;

public class RunPhaseEngineTests {

	@Test
	public void testEmptyMaze() {
		Block[][] blockGrid = new Block[5][5];
		Maze maze = new Maze(blockGrid);
		Point runnerStart = new Point(2, 0);
		Point runnerEnd = new Point(2, 4);
		RunPhaseEngine engine = new RunPhaseEngine(maze, runnerStart, runnerEnd);
		Trajectory trajectory = engine.getTrajectory();
		
		float expectedDuration = runnerEnd.toVector2().dst(runnerStart.toVector2()) / engine.getRunnerBaseSpeed(); 
		assertEquals(expectedDuration, trajectory.getRunDuration(), 0);
	}
	
	@Test
	public void testZigZag() {
		final int TOTAL_RUN_DISTANCE = 12;
		
		Maze maze = MazeFactory.createZigZag(5, 5);
		Point runnerStart = new Point(2, 0);
		Point runnerEnd = new Point(2, 4);
		RunPhaseEngine engine = new RunPhaseEngine(maze, runnerStart, runnerEnd);
		Trajectory trajectory = engine.getTrajectory();
		
		float expectedDuration = TOTAL_RUN_DISTANCE / engine.getRunnerBaseSpeed();
		assertEquals(expectedDuration, trajectory.getRunDuration(), 0);
	}
	
	@Test
	public void testMazeGetBlocksOfType() {
		Block[][] blockGrid = new Block[5][5];
		Maze maze = new Maze(blockGrid);
		
		assertEquals(0, maze.getBlocksOfType(BasicBlock.class).size());
		assertEquals(0, maze.getBlocksOfType(SlowTower.class).size());
		
		Block block = new BasicBlock(0, 0);
		Block slowTower = new SlowTower(3, 3);
		Block slowTower2 = new SlowTower(0, 3);
		Block customSlowTower = new CustomSlowTower(2, 0);
		
		MazeUtil.addBlockToGrid(blockGrid, block);
		assertEquals(1, maze.getBlocksOfType(BasicBlock.class).size());
		MazeUtil.addBlockToGrid(blockGrid, slowTower);
		assertEquals(1, maze.getBlocksOfType(SlowTower.class).size());
		MazeUtil.addBlockToGrid(blockGrid, slowTower2);
		assertEquals(2, maze.getBlocksOfType(SlowTower.class).size());
		MazeUtil.addBlockToGrid(blockGrid, customSlowTower);
		assertEquals(3, maze.getBlocksOfType(SlowTower.class).size());
		assertEquals(1, maze.getBlocksOfType(CustomSlowTower.class).size());
	}
	
	@Test
	public void simpleSlowTowerTest() {
		Block[][] blockGrid = new Block[5][5];
		Maze maze = new Maze(blockGrid);
		MazeUtil.addBlockToGrid(blockGrid, new CustomSlowTower(1, 2));
		
		RunPhaseEngine engine = new RunPhaseEngine(maze, new Point(2, 0), new Point(2, 4));
		Collection<RunPhaseEvent> events = engine.getEvents();
		
		boolean containsTowerFireEvent = false;
		for (RunPhaseEvent event : events) {
			if (event instanceof RunPhaseEvent) {
				containsTowerFireEvent = true;
			}
		}
		assertTrue(containsTowerFireEvent);
		
		// Replace the slow tower with a regular block
		MazeUtil.addBlockToGrid(blockGrid, new BasicBlock(1, 2));
		RunPhaseEngine engine2 = new RunPhaseEngine(maze, new Point(2, 0), new Point(2, 4));
		assertTrue(engine.getTrajectory().getRunDuration() != engine2.getTrajectory().getRunDuration());
		
		// Make sure CompletedPathEvent was the last event 
		assertEquals(CompletedPathEvent.class, engine.getEvents().get(engine.getEvents().size() - 1).getClass());
	}
}
