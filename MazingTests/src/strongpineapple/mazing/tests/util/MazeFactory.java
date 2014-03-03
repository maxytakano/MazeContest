package strongpineapple.mazing.tests.util;

import strongpineapple.mazing.engine.*;

public class MazeFactory {
	/**
	 * Will not cover the first and last row.
	 * @param width The width of the maze. Must be greater than 2.
	 * @param height The height of the maze. Must be greater than 2.
	 * @return A maze which begins zig-zagging from the left on the second row.
	 */
	public static Maze createZigZag(int width, int height) {
		Block[][] blockGrid = new Block[width][height];
		
		// Track whether we are on left edge or right edge
		boolean left = true;

		for (int y = 1; y < height - 1; y += 2) {
			
			int start = left ? 0 : 1;
			int end = left ? width - 1 : width;
			
			for (int x = start ; x < end; x++) {
				Block block = new BasicBlock(x, y);
				blockGrid[x][y] = block;
			}
			
			left = !left;
		}
		
		return new Maze(blockGrid);
	}
}
