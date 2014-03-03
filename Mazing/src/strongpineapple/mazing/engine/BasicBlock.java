
package strongpineapple.mazing.engine;

import strongpineapple.mazing.engine.utils.Point;

/**
 * The fundamental block that players can buy or sell to construct the maze.
 * 
 * @author Dylan
 */
public class BasicBlock extends StandardBlock {
	public static final ResourceBundle COST = new ResourceBundle(1, 0); 
	
	/**
	 * @param x The x coordinate of the top-left corner of this block.
	 * @param y The y coordinate of the top-left corner of this block.
	 */
	public BasicBlock(int x, int y) {
		super(x, y);
	}

	/**
	 * @param location The location of the top-left corner of this block.
	 */
	public BasicBlock(Point location) {
		super(location);
	}

	public BasicBlock(int x, int y, boolean rock) {
		super(x, y, rock);
	}
}
