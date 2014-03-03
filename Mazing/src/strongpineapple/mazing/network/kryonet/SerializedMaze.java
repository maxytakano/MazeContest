package strongpineapple.mazing.network.kryonet;

import java.util.ArrayList;
import java.util.Collection;

import strongpineapple.mazing.engine.Block;
import strongpineapple.mazing.engine.Maze;

public class SerializedMaze {
	
	private Collection<Block> blocks;
	private int width;
	private int height;
	
	public SerializedMaze(Maze maze) {
		this.blocks = new ArrayList<Block>(maze.getBlocks());
		this.width = maze.getWidth();
		this.height = maze.getHeight();
	}
	
	public Maze toMaze() {
		return new Maze(blocks, width, height);
	}
}
