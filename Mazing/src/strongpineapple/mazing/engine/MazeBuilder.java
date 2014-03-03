
package strongpineapple.mazing.engine;

import java.util.List;

import strongpineapple.mazing.engine.utils.Point;

/**
 * An interface for maze construction. A class can register itself to be
 * notified of events in the MazeBuilder with
 * {@link #setListener(MazeBuilderListener)}.
 * 
 * @author Dylan
 */
public interface MazeBuilder {
	/**
	 * Sets the listener of this MazeBuilder.
	 * 
	 * @param listener The listener that will be notified of events.
	 */
	void setListener(MazeBuilderListener listener);

	/**
	 * Attempts to buy a {@link BasicBlock}. Has no effect if the block is
	 * unable to be placed.
	 * 
	 * @param x The x coordinate of the block.
	 * @param y The y coordinate of the block.
	 */
	void buyBasicBlock(int x, int y);
	
	void buySlowTower(int x, int y);

	/**
	 * Sells the block specified by <code>blockID</code>.
	 * 
	 * @param blockID
	 */
	void sellBlock(int blockID);

	/**
	 * @return A copy of the maze constructed from this MazeBuilder.
	 */
	Maze toMaze();

	/**
	 * @return The width of the maze in tiles.
	 */
	int getWidth();

	/**
	 * @return The height of the maze in tiles.
	 */
	int getHeight();
	
	Point getRunnerStart();
	
	Point getRunnerEnd();

	ResourceBundle getResources();
	
	List<BasicBlock> getBasicBlockRocks();
	
	List<SlowTower> getSlowTowerRocks();
}
