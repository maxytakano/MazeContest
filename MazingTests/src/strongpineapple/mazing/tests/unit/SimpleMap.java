package strongpineapple.mazing.tests.unit;

import strongpineapple.mazing.engine.pathfinding.TileMap;

public class SimpleMap implements TileMap {

	private int width;
	private int height;
	private boolean[][] map;
	
	public void setBlock(int x, int y) {
		map[x][y] = true;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public boolean isBlocked(int x, int y) {
		return map[x][y];
	}

	public SimpleMap(int width, int height) {
		this.width = width;
		this.height = height;
		this.map = new boolean[width][height];
	}

}
