package strongpineapple.mazing.engine.pathfinding;

public interface TileMap {
	int getWidth();
	int getHeight();
	boolean isBlocked(int x, int y);
}
