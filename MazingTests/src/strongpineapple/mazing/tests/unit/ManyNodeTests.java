package strongpineapple.mazing.tests.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import strongpineapple.mazing.engine.pathfinding.AStarPathFinder;
import strongpineapple.mazing.engine.pathfinding.PathFinder;
import strongpineapple.mazing.engine.utils.Point;

public class ManyNodeTests {
	PathFinder pathFinder;
	SimpleMap map;

	// grid of blocks one space apart
	@Test
	public void testOneSpaceBlocks() {
		map = new SimpleMap(10, 10);
		for (int i = 0; i < 10; i = i + 2) {
			for (int j = 0; j < 10; j = j + 2) {
				map.setBlock(i, j);
			}
		}

		pathFinder = new AStarPathFinder(map);

		List<Point> path = pathFinder.findPath(new Point(5, 0),
				new Point(5, 9));
		List<Point> correctPath = new ArrayList<Point>();

		correctPath.add(new Point(5, 0));
		correctPath.add(new Point(5, 9));

		assertEquals(path.size(), correctPath.size());

		for (int i = 0; i < path.size(); i++) {
			assertEquals(path.get(i), correctPath.get(i));
		}

	}

	/**
	 *  Correct path winds around, tests for Astar's decision making
	 */
	@Ignore // This test doesn't work
	@Test
	public void testWindmill() {
		map = new SimpleMap(10, 10);
		for (int i = 5; i < 10; i++) {
			map.setBlock(i, 4);
		}

		map.setBlock(5, 1);
		map.setBlock(5, 3);
		map.setBlock(5, 5);
		map.setBlock(5, 6);
		map.setBlock(5, 7);
		map.setBlock(5, 8);
		map.setBlock(3, 4);
		map.setBlock(0, 1);
		map.setBlock(2, 3);
		map.setBlock(3, 8);
		map.setBlock(8, 6);

		pathFinder = new AStarPathFinder(map);

		List<Point> path = pathFinder.findPath(new Point(9, 0),
				new Point(9, 9));
		List<Point> correctPath = new ArrayList<Point>();

		correctPath.add(new Point(9, 0));
		correctPath.add(new Point(6, 2));
		correctPath.add(new Point(4, 2));
		correctPath.add(new Point(4, 3));
		correctPath.add(new Point(4, 5));
		correctPath.add(new Point(4, 9));
		correctPath.add(new Point(6, 9));
		correctPath.add(new Point(9, 9));
		

		assertEquals(path.size(), correctPath.size());

		for (int i = 0; i < path.size(); i++) {
			assertEquals(path.get(i), correctPath.get(i));
		}
	}
	
	
}
