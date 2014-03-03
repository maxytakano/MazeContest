/**
 * 
 */

package strongpineapple.mazing.engine;

import strongpineapple.mazing.config.GameConfig;
import strongpineapple.mazing.engine.utils.Point;
import java.util.*;

/**
 * @author Dylan
 */
public class MazeBuilderEngine implements MazeBuilder {

	private int randomBlocks = 10;
	private int randomTowers = 1;
	private final Point runnerStart = GameConfig.RUNNER_START;
	private final Point runnerEnd = GameConfig.RUNNER_END;

	/**
	 * The underlying data structure of the maze. Each element in the grid is a
	 * reference to the Block that occupies that tile. An element is null if no
	 * Block occupies the tile.
	 */
	private Block[][] blockGrid;
	
	/**
	 * A wrapper for blockGrid used by pathBlockDetector.
	 */
	private Maze maze;
	
	private PathBlockDetector pathBlockDetector;
	private MazeBuilderListener listener = new DummyListener();
	private ResourceBundle resources = new ResourceBundle(15, 1);

	/**
	 * A dictionary of Blocks used to refer to blocks that can be acted on.
	 */
	private Map<Integer, Block> blockDict = new HashMap<Integer, Block>();

	/**
	 * The blockID counter. The counter is incremented when a block is
	 * successfully purchased.
	 */
	private int nextBlockID = 0;
	
	private List<BasicBlock> basicBlockRocks = new ArrayList<BasicBlock>();
	private List<SlowTower> slowTowerRocks = new ArrayList<SlowTower>();

	/**
	 * @param maze
	 */
	public MazeBuilderEngine(int width, int height, int round) {
		this.randomBlocks = GameConfig.getRoundBlocks(round);
		this.randomTowers = GameConfig.getRoundTowers(round);
		this.resources = GameConfig.getRoundResources(round);
		this.blockGrid = new Block[width][height];
		this.maze = new Maze(blockGrid);
		this.pathBlockDetector = new PathBlockDetector(maze, runnerStart, runnerEnd);
		generateRocks();
	}
	
	/**
	 * Uses game config maze width and height.
	 */
	public MazeBuilderEngine(int round) {
		this(GameConfig.MAZE_WIDTH, GameConfig.MAZE_HEIGHT, round);
	}

	/**
	 * Uses game config maze width and height.
	 * @param rocks
	 */
	public MazeBuilderEngine(Collection<Block> rocks, ResourceBundle resources) {
		this.blockGrid = new Block[GameConfig.MAZE_WIDTH][GameConfig.MAZE_HEIGHT];
		for (Block rock : rocks) {
			for (Point tile : rock.getOccupiedTiles()) {
				blockGrid[tile.getX()][tile.getY()] = rock;
			}
		}
		
		this.resources = resources;
		this.maze = new Maze(blockGrid);
		this.pathBlockDetector = new PathBlockDetector(maze, runnerStart, runnerEnd);
	}

	public List<SlowTower> getSlowTowerRocks() {
		return slowTowerRocks;
	}
	
	public List<BasicBlock> getBasicBlockRocks() {
		return basicBlockRocks;
	}
	
	private void generateRocks() {
		Random random = new Random();
	
		BasicBlock block;
		for (int i = 0; i < randomBlocks; i++) {
			while (true) {
				block = new BasicBlock(random.nextInt(getWidth()), random.nextInt(getHeight()), true);
				if (tryPlaceBlock(block)) {
					basicBlockRocks.add(block);
					break;
				}
			}
		}

		SlowTower slowTower;
		for (int i = 0; i < randomTowers; i++) {
			while (true) {
				slowTower = new SlowTower(random.nextInt(getWidth()), random.nextInt(getHeight()), true);
				if (tryPlaceBlock(slowTower)) {
					slowTowerRocks.add(slowTower);
					break;
				}
			}
		}
	}

	@Override
	public void setListener(MazeBuilderListener listener) {
		this.listener = listener;
	}
	
	@Override
	public ResourceBundle getResources() {
		return this.resources;
	}
	
	@Override
	public void buyBasicBlock(int x, int y) {
		if (!resources.canBuy(BasicBlock.COST))
			return;

		BasicBlock block = new BasicBlock(x, y);

		if (canPlaceBlock(block)) {
			int blockID = nextBlockID++;
			blockDict.put(blockID, block);
			for (Point point : block.getOccupiedTiles()) {
				blockGrid[point.getX()][point.getY()] = block;
			}

			resources = resources.sub(BasicBlock.COST);
			listener.basicBlockBought(x, y, blockID);
		}
	}
	
	@Override
	public void buySlowTower(int x, int y) {
		if (!resources.canBuy(SlowTower.COST))
			return;
		
		SlowTower block = new SlowTower(x, y);

		if (canPlaceBlock(block)) {
			int blockID = nextBlockID++;
			blockDict.put(blockID, block);
			for (Point point : block.getOccupiedTiles()) {
				blockGrid[point.getX()][point.getY()] = block;
			}

			resources = resources.sub(SlowTower.COST);
			listener.slowTowerBought(x, y, blockID);
		}
	}

	public boolean tryPlaceBlock(Block block) {
		if (canPlaceBlock(block)) {
			for (Point point : block.getOccupiedTiles()) {
				blockGrid[point.getX()][point.getY()] = block;
			}
			
			return true;
		}
		
		return false;
	}

	/**
	 * Checks path blocking and checks if blocks already exist at the target
	 * site.
	 * 
	 * @param block The block to place.
	 * @return true if the block can be placed, else false.
	 */
	private boolean canPlaceBlock(Block block) {
		// Validate bounds
		for (Point point: block.getOccupiedTiles()) {
			if (point.getX() < 0 || point.getX() >= getWidth()
					|| point.getY() < 0 || point.getY() >= getHeight()) {
				return false;
			}
		}
		
		// Check path blocking
		if (pathBlockDetector.willBlockPath(block.getOccupiedTiles()))
			return false;

		// Check if blocks already exist there
		for (Point point : block.getOccupiedTiles()) {
			if (blockGrid[point.getX()][point.getY()] != null) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void sellBlock(int blockID) {
		if (blockDict.containsKey(blockID)) {

			Block block = blockDict.remove(blockID);
			
			for (Point point : block.getOccupiedTiles()) {
				blockGrid[point.getX()][point.getY()] = null;
			}
			
			if (block instanceof BasicBlock) {
				resources = resources.add(BasicBlock.COST);
			}
			else if (block instanceof SlowTower) {
				resources = resources.add(SlowTower.COST);
			}

			listener.blockSold(blockID);
		}
	}

	@Override
	public Maze toMaze() {
		return new Maze(blockGrid.clone());
	}
	
	@Override
	public int getWidth() {
		return blockGrid.length;
	}

	@Override
	public int getHeight() {
		return blockGrid[0].length;
	}
	
	@Override
	public Point getRunnerStart() {
		return runnerStart;
	}
	
	@Override
	public Point getRunnerEnd() {
		return runnerEnd;
	}
	

	/**
	 * A dummy MazeBuilderListener so that we don't have to null-check the
	 * listener.
	 */
	private class DummyListener implements MazeBuilderListener {
		@Override
		public void basicBlockBought(int x, int y, int blockID) {
		}

		@Override
		public void blockSold(int blockID) {
		}

		@Override
		public void slowTowerBought(int x, int y, int nextBlockID) {
		}
	}
}
