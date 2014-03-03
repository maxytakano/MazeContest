package strongpineapple.mazing.tests.unit;

import strongpineapple.mazing.engine.pathfinding.*;

import strongpineapple.mazing.engine.utils.Point;


import java.util.*;


import junit.framework.TestCase;

public class PathingTests extends TestCase {
	PathFinder pathFinder;
	SimpleMap map;

	public void testHorizontalLine() {
		for (int y = 1; y < 9; y++) {
			map = new SimpleMap(10, 10);

			for (int x = 0; x < 9; x++)
				map.setBlock(x, y);

			pathFinder = new AStarPathFinder(map);

			List<Point> path = pathFinder.findPath(new Point(4, 0),
					new Point(4, 9));
			List<Point> correctPath = new ArrayList<Point>();

			correctPath.add(new Point(4, 0));
			correctPath.add(new Point(9, y - 1 ));
			correctPath.add(new Point(9, y + 1 ));
			correctPath.add(new Point(4, 9));

			assertEquals(path.size(), correctPath.size());

			for (int i = 0; i < path.size(); i++) {
				assertEquals(path.get(i), correctPath.get(i));
			}
		}

	}
}
