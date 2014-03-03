
package strongpineapple.mazing.tests.unit;

import static org.junit.Assert.*;

import strongpineapple.mazing.engine.utils.Point;

import strongpineapple.mazing.engine.PathBlockDetector;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Test suite for {@link PathBlockDetector}.
 * 
 * @author Dylan
 */
public class PathBlockTests {

	@Before
	public void setUp() {

	}

	/**
	 * Verifies that path block is detected when a horizontal line is placed.
	 */
	@Test
	public void testHorizontalPathBlockSimultaneous() {
		SimpleMap map = new SimpleMap(5, 5);
		PathBlockDetector detector = new PathBlockDetector(map, new Point(2, 0), new Point(2, 4));
		List<Point> points = new ArrayList<Point>();

		for (int x = 0; x < 5; x++) {
			points.add(new Point(x, 1));
		}

		assertTrue(detector.willBlockPath(points));
	}
	
	/**
	 * Verifies that path block is detected at the end when blocks are placed horizontally one by one.
	 */
	@Test
	public void testHorizontalPathBlockSequential() {
		SimpleMap map = new SimpleMap(5, 5);
		PathBlockDetector detector = new PathBlockDetector(map, new Point(2, 0), new Point(2, 4));
		List<Point> points = new ArrayList<Point>();
		
		for (int x = 0; x < 4; x++) {
			points.add(new Point(x, 1));
			assertFalse(detector.willBlockPath(points));
			map.setBlock(x, 1);
			points.clear();
		}
		
		points.add(new Point(4, 1));
		assertTrue(detector.willBlockPath(points));
	}

	/**
	 * Verifies that path block is detected on the start point and end point.
	 */
	@Test
	public void testStartEndNodes() {
		SimpleMap map = new SimpleMap(5, 5);
		Point start = new Point(2, 0);
		Point end = new Point(2, 4);
		PathBlockDetector detector = new PathBlockDetector(map, start, end);
		
		List<Point> points = new ArrayList<Point>();
		points.add(start);
		assertTrue(detector.willBlockPath(points));
		
		points.clear();
		points.add(end);
		assertTrue(detector.willBlockPath(points));
	}
}
