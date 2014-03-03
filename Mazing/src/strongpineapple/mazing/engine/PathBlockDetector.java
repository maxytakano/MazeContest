
package strongpineapple.mazing.engine;

import strongpineapple.mazing.engine.pathfinding.PathFinder;

import strongpineapple.mazing.engine.pathfinding.AStarPathFinder;

import strongpineapple.mazing.engine.utils.Point;

import strongpineapple.mazing.engine.pathfinding.TileMap;

/**
 * Detects path-blocking along a specified path in a specified maze. Methods
 * {@link #willBlockPath(Iterable)} and
 * {@link #calculateBlockingTiles(Iterable)} are used to test if blocking
 * certain points will cause a path block.
 * 
 * @author Dylan
 */
public class PathBlockDetector {

	private TileMap maze;
	private Point pathStart;
	private Point pathEnd;

	/**
	 * Creates a PathBlockDetector that detects path-blocking on the specified
	 * maze between the points {@code pathStart} and {@code pathEnd}.
	 * 
	 * @param maze The maze for which to detect path-blocking.
	 * @param pathStart The start of the path.
	 * @param pathEnd THe end of the path.
	 */
	public PathBlockDetector(TileMap maze, Point pathStart, Point pathEnd) {
		this.maze = maze;
		this.pathStart = pathStart;
		this.pathEnd = pathEnd;
	}

	/**
	 * @return The maze for which to detect path-blocking.
	 */
	public TileMap getMaze() {
		return maze;
	}

	/**
	 * @return The start of the path.
	 */
	public Point getPathStart() {
		return pathStart;
	}

	/**
	 * @return The end of the path.
	 */
	public Point getPathEnd() {
		return pathEnd;
	}

	/**
	 * Calculates whether blocking all of the specified points will cause a path
	 * block between the start and end points.
	 * 
	 * @param points The points to test.
	 * @return true if blocking the points causes a path block, otherwise false.
	 */
	public boolean willBlockPath(Iterable<Point> points) {
		// Return true if the start or end points are blocked
		for (Point point : points) {
			if (point.equals(pathStart) || point.equals(pathEnd))
				return true;
		}
		
		PathFinder pathFinder = new AStarPathFinder(new PotentialMap(points));
		return pathFinder.findPath(pathStart, pathEnd) == null;
	}

	/**
	 * Calculates whether blocking all of the specified points will cause a path
	 * block between the start and end points.
	 * 
	 * @param points The points to test.
	 * @return <code>null</code> if blocking the points will not cause a path
	 *         block, else a collection of points that would take part in the
	 *         path block, not including the ones specified in
	 *         <code>points</code>.
	 */
	public Iterable<Point> calculateBlockingTiles(Iterable<Point> points) {
		throw new UnsupportedOperationException();
	}

	/**
	 * A TileMap based on the <code>maze</code> field of this class, but
	 * overrides isBlocked() for specified points.
	 * 
	 * @author Dylan
	 */
	private class PotentialMap implements TileMap {
		private Iterable<Point> points;

		/**
		 * @param points The list of points to block.
		 */
		public PotentialMap(Iterable<Point> points) {
			this.points = points;
		}

		@Override
		public int getWidth() {
			return maze.getWidth();
		}

		@Override
		public int getHeight() {
			return maze.getHeight();
		}

		@Override
		public boolean isBlocked(int x, int y) {
			for (Point point : points) {
				if (point.equals(new Point(x, y)))
					return true;
			}

			return maze.isBlocked(x, y);
		}
	}
}
