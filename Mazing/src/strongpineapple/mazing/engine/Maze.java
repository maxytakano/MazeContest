
package strongpineapple.mazing.engine;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import strongpineapple.mazing.engine.pathfinding.TileMap;
import strongpineapple.mazing.engine.utils.Point;

/**
 * A data structure representing a grid of Blocks. A single block may occupy
 * multiple tiles.
 * 
 * @author Dylan
 */
public class Maze implements TileMap {

	private Block[][] blockGrid;

	/**
	 * @param blockGrid A properly formatted 2D array plotting the locations of
	 *            each Block. A null element represents the absence of any
	 *            block.
	 */
	public Maze(Block[][] blockGrid) {
		this.blockGrid = blockGrid;
	}
	
	public Maze(Collection<Block> blocks, int width, int height) {
		this.blockGrid = new Block[width][height];
		for (Block block : blocks) {
			for (Point tile : block.getOccupiedTiles()) {
				blockGrid[tile.getX()][tile.getY()] = block;
			}
		}
	}

	/**
	 * @param location The location of the tile.
	 * @return The block located at the specified tile if there is one,
	 *         otherwise <code>null</code>.
	 */
	public Block getBlock(Point location) {
		return blockGrid[location.getX()][location.getY()];
	}

	/**
	 * @param x The x coordinate of the tile.
	 * @param y The y coordinate of the tile.
	 * @return The block located at the specified tile if there is one,
	 *         otherwise <code>null</code>.
	 */
	public Block getBlock(int x, int y) {
		return blockGrid[x][y];
	}

	public Collection<Block> getBlocks() {
		Set<Block> blocks = new HashSet<Block>();
		for (Block[] blockCols : blockGrid) {
			for (Block block : blockCols) {
				if (block != null) {
					blocks.add(block);
				}
			}
		}
		
		return blocks;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Block> Set<T> getBlocksOfType(Class<T> blockType) {
		Set<T> blocks = new HashSet<T>();
		for (Block[] i : blockGrid) {
			for (Block block : i) {
				if (block != null && blockType.isAssignableFrom(block.getClass())) {
					blocks.add((T) block);
				}
			}
		}
		
		return blocks;
	}

	@Override
	public int getWidth() {
		return this.blockGrid.length;
	}

	@Override
	public int getHeight() {
		return this.blockGrid[0].length;
	}

	@Override
	public boolean isBlocked(int x, int y) {
		return blockGrid[x][y] != null;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (int y = 0; y < getWidth(); y++) {
			for (int x = 0; x < getHeight(); x++) {
				Block block = getBlock(y, x);
				if (block == null) {
					string.append('0');
				}
				else {
					char blockChar;
					if (block instanceof BasicBlock) {
						blockChar = 'b';
					} else if (block instanceof SlowTower) {
						blockChar = 's';
					}
					else {
						blockChar = '?';
					}
					
					if (block instanceof StandardBlock) {
						StandardBlock sBlock = (StandardBlock) block;
						if (sBlock.isRock()) {
							blockChar = Character.toUpperCase(blockChar);
						}
					}
					string.append(blockChar);
				}
				
				string.append(' ');
			}
			string.append('\n');
			string.append('\n');
		}
		
		return string.toString();
	}
}
