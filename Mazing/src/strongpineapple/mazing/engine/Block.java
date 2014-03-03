
package strongpineapple.mazing.engine;

import strongpineapple.mazing.engine.utils.Point;

/**
 * Tile based objects that interact with the Runner.
 * 
 * @author Dylan
 */
public interface Block {

	/**
	 * @return <code>true</code> if this Block does not affect pathing,
	 *         otherwise <code>false</code>.
	 */
	boolean isPassable();

	/**
	 * @return The single point representing the location of this Block, as
	 *         defined by its implementation.
	 */
	Point getLocation();

	/**
	 * @return A collection of locations of the tiles that this block occupies.
	 */
	Iterable<Point> getOccupiedTiles();
}
