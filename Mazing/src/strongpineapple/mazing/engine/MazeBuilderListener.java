
package strongpineapple.mazing.engine;

/**
 * A class can implement the MazeBuilderListener interface when it wants to be
 * informed of events in a {@link MazeBuilder}.
 * 
 * @author Dylan
 */
public interface MazeBuilderListener {
	/**
	 * This method is called when a block was successfully bought.
	 * 
	 * @param block The location of the block.
	 * @param blockID The ID of the block that was bought. Used to manipulate
	 *            the block (for example, {@link MazeBuilder#sellBlock(int)}).
	 */
	void basicBlockBought(int x, int y, int blockID);

	/**
	 * This method is called when a block was sold.
	 * 
	 * @param blockID The ID of the block that was sold.
	 */
	void blockSold(int blockID);

	void slowTowerBought(int x, int y, int nextBlockID);
}
