package strongpineapple.mazing.tests.unit;

import strongpineapple.mazing.engine.pathfinding.AStarPathFinder;
import strongpineapple.mazing.engine.pathfinding.PathFinder;

import strongpineapple.mazing.engine.utils.Point;


import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;

import junit.framework.TestCase;

// This test doesn't work
@Ignore
public class StandardMazes extends TestCase {
	PathFinder pathFinder;
	SimpleMap map;
	
	// Standard left right rows maze
	public void testHorizontalMaze() {
		map = new SimpleMap(9, 10);
		for(int i = 0; i < 8; i++) {
			map.setBlock(i, 3);
			map.setBlock(i, 7);
		}
		for(int i = 1; i < 9; i++) {
			map.setBlock(i, 1);
			map.setBlock(i, 5);
		}
		
		
		pathFinder = new AStarPathFinder(map); 
		
		List<Point> path = pathFinder.findPath(new Point(4,0), 
				new Point(4,9));
		List<Point> correctPath = new ArrayList<Point>();
		
		correctPath.add(new Point(4,0));
		correctPath.add(new Point(0,0));
		correctPath.add(new Point(0,2));
		correctPath.add(new Point(8,2));
		correctPath.add(new Point(8,4));
		correctPath.add(new Point(0,4));
		correctPath.add(new Point(0,6));
		correctPath.add(new Point(8,6));
		correctPath.add(new Point(8,8));
		correctPath.add(new Point(4,9));
		
	    assertEquals(path.size(), correctPath.size());	
	    
	    for (int i = 0; i < correctPath.size(); i++) {
	    	assertEquals(path.get(i), correctPath.get(i));
	    }
			
	}
	
	// standard spiral maze
	public void testSpiralMaze() {
		map = new SimpleMap(11, 11);
		for(int i = 0; i < 10; i++) {
			map.setBlock(i, 1);
			map.setBlock(i+1, 9);
		}
		for(int i = 0; i < 8; i++) {
			map.setBlock(i+1, 3);
		}
		for(int i = 3; i < 10; i++) {
			map.setBlock(i, 7);
		}
		for(int i = 1; i < 9; i++) {
			map.setBlock(9, i);
		}
		for(int i = 3; i < 10; i++) {
			map.setBlock(2, i);
		}
		for(int i = 3; i < 6; i++) {
			map.setBlock(7, i);
		}
		for(int i = 5; i < 8; i++) {
			map.setBlock(3, i);
		}
		map.setBlock(5, 5);
		
        pathFinder = new AStarPathFinder(map); 
		
		List<Point> path = pathFinder.findPath(new Point(5,0), 
				new Point(5,10));
		List<Point> correctPath = new ArrayList<Point>();
		
		correctPath.add(new Point(5, 0));
		correctPath.add(new Point(10, 0));
		correctPath.add(new Point(10, 8));
		correctPath.add(new Point(2, 8));
		correctPath.add(new Point(2, 4));
		correctPath.add(new Point(4, 4));
		correctPath.add(new Point(4, 6));
		correctPath.add(new Point(6, 6));
		correctPath.add(new Point(8, 6));
		correctPath.add(new Point(8, 2));
		correctPath.add(new Point(0, 2));
		correctPath.add(new Point(0, 10));
		correctPath.add(new Point(5, 10));
		
		assertEquals(path.size(), correctPath.size());	
	    
	    for (int i = 0; i < correctPath.size(); i++) {
	    	// might need to check at i ==6 instead of at i == 5
	    	if(i == 5) {
	    		if( !(path.get(i) == correctPath.get(i)) || !(path.get(i) == (new Point(4,6))) ) fail();
	    	}
	    	assertEquals(path.get(i), correctPath.get(i));
	    }
	}
}
