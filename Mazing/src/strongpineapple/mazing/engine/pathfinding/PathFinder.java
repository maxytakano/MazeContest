package strongpineapple.mazing.engine.pathfinding;

import java.util.List;

import strongpineapple.mazing.engine.utils.Point;

public interface PathFinder {
	/**
	 * Uses the map associated with this PathFinder to find the optimal path
	 * from the source to the destination, assuming the mover is the size of a
	 * tile. While this method finds a continuous path through the map (the
	 * mover can travel in between tiles), the waypoints in the path will always
	 * land on a tile, by nature.
	 * 
	 * @param source
	 *            The tile from which the path originates.
	 * @param destination
	 *            The tile on which the path ends.
	 * @return A list of waypoints of the path in the order that they should be
	 *         traveled if a path was found, else null.
	 */
	List<Point> findPath(Point source, Point destination);
}
