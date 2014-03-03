
package strongpineapple.mazing.engine;

import strongpineapple.mazing.engine.utils.Point;

import java.util.*;

/**
 * A standard impassable 2x2 tiled block.
 * 
 * @author Dylan
 */
public abstract class StandardBlock implements Block {

	private Point location;
	private List<Point> occupiedTiles = new ArrayList<Point>(4);
	private boolean rock = false;

	/**
	 * @param x The x coordinate of the top-left corner of this block.
	 * @param y The y coordinate of the top-left corner of this block.
	 */
	public StandardBlock(int x, int y) {
		this(new Point(x, y));
	}
	
	public StandardBlock(int x, int y, boolean rock) {
		this.location = new Point(x, y);
		this.rock = rock;
		occupiedTiles.add(location);
		occupiedTiles.add(location.add(0, 1));
		occupiedTiles.add(location.add(1, 0));
		occupiedTiles.add(location.add(1, 1));
	}

	/**
	 * @param location The location of the top-left corner of this block.
	 */
	public StandardBlock(Point location) {
		this.location = location;
		occupiedTiles.add(location);
		occupiedTiles.add(location.add(0, 1));
		occupiedTiles.add(location.add(1, 0));
		occupiedTiles.add(location.add(1, 1));
	}
	
	public boolean isRock() {
		return rock;
	}

	/**
	 * @return <code>false</code>. StandardBlocks are impassable.
	 */
	@Override
	public boolean isPassable() {
		return false;
	}

	/**
	 * A square, with this Block's <code>location</code> being the top left
	 * tile.
	 */
	@Override
	public Iterable<Point> getOccupiedTiles() {
		return occupiedTiles;
	}

	/**
	 * The top left corner of this block.
	 */
	@Override
	public Point getLocation() {
		return location;
	}
	
	/**
	 * @return The center of this standard block.
	 */
	public Point getCenter() {
		return location.add(1, 1);
	}
}
