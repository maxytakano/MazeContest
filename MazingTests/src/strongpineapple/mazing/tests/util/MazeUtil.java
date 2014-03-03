package strongpineapple.mazing.tests.util;

import strongpineapple.mazing.engine.Block;
import strongpineapple.mazing.engine.Maze;
import strongpineapple.mazing.engine.utils.Point;

public class MazeUtil {
	/**
	 * A debug method used to graphically view the maze. Goes over each tile of
	 * the maze and prints 1 if there is a block, else 0.
	 */
	public static void printMaze(Maze maze) {
		for (int y = 0; y < maze.getHeight(); y++) {
			for (int x = 0; x < maze.getWidth(); x++) {
				if (maze.getBlock(x, y) == null) {
					System.out.print("0");
				}
				else {
					System.out.print("1");
				}
			}
			
			System.out.println();
		}
	}
	
	public static void addBlockToGrid(Block[][] blockGrid, Block block) {
		for (Point point : block.getOccupiedTiles()) {
			blockGrid[point.getX()][point.getY()] = block;
		}
	}
}
