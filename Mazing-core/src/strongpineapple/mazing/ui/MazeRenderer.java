package strongpineapple.mazing.ui;

import com.badlogic.gdx.math.*;

import strongpineapple.mazing.engine.*;
import strongpineapple.mazing.engine.runphase.Trajectory;
import strongpineapple.mazing.engine.runphase.events.RunPhaseEvent;
import strongpineapple.mazing.engine.utils.Point;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import java.util.*;

public class MazeRenderer {
	private float screenX;
	private float screenY;
	private float screenWidth;
	private float screenHeight;
	private int mazeWidth;
	private int mazeHeight;
	private float tileWidth;
	private float tileHeight;
	private SpriteBatch spriteBatch;
	private Rectangle boundingRectangle;
	
	private float stampX;
	private float stampY;

	private List<BasicBlock> basicBlocks = new ArrayList<BasicBlock>();
	private List<SlowTower> slowTowers = new ArrayList<SlowTower>();
	private List<Point> endPoints = new ArrayList<Point>();
	private Trajectory runnerTrajectory;
	
	boolean runPhase = false;
	private float currentTime;
	@SuppressWarnings("unused")
	private List<RunPhaseEvent> events;

	/**
	 * @param screenX The x coordinate of the maze relative to the viewport.
	 * @param screenY The y coordinate of the maze relative to the viewport.
	 * @param screenWidth The width of the maze in the context of the viewport.
	 * @param screenHeight The height of the maze in the context of the viewport.
	 * @param mazeWidth The width of the maze in tiles.
	 * @param mazeHeight The height of the maze in tiles.
	 */
	public MazeRenderer(float screenX, float screenY, float screenWidth, float screenHeight, 
			int mazeWidth, int mazeHeight, SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
		this.screenX = screenX;
		this.screenY = screenY;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.mazeWidth = mazeWidth;
		this.mazeHeight = mazeHeight;
		this.tileWidth = screenWidth / mazeWidth;
		this.tileHeight = screenHeight / mazeHeight;
		this.boundingRectangle = new Rectangle(screenX, screenY, screenX + screenWidth, screenY + screenHeight);
	}
	
	public void removeBlock(Block block) {
		if (block instanceof BasicBlock) {
			this.basicBlocks.remove(block);
		}
		else if (block instanceof SlowTower) {
			this.slowTowers.remove(block);
		}
	}
	
	public void addBasicBlock(BasicBlock block) {
		this.basicBlocks.add(block);
	}
	
	public void addSlowTower(SlowTower slowTower) {
		this.slowTowers.add(slowTower);
	}
	
	public void addBlocks(Collection<Block> blocks) {
		for (Block block : blocks) {
			if (block instanceof BasicBlock) {
				addBasicBlock((BasicBlock) block);
			}
			else if (block instanceof SlowTower) {
				addSlowTower((SlowTower) block);
			}
		}
	}
	
	public void updateStamp(float x, float y) {
		stampX = x;
		stampY = y;
	}
	
	public void startRunPhase(Trajectory runnerTrajectory, List<RunPhaseEvent> events) {
		this.runPhase = true;
		this.events = events;
		this.runnerTrajectory = runnerTrajectory;
		this.currentTime = 0;
	}
	
	public void setEndPointTiles(Point runnerStart, Point runnerEnd) {
		this.endPoints.add(runnerStart);
		this.endPoints.add(runnerEnd);
	}
	
	public Rectangle getBoundingRectangle() {
		return boundingRectangle;
	}

	public void render(float delta) {
		if (runPhase) {
			currentTime += delta;
		}
		
		renderGrid();
		renderEndPoints();
		renderBasicBlocks(delta);
		renderSlowTowers();
		
		renderStamp();
		
		if (runPhase) {
			renderRunner();
			//if(events.fireEvent instanceof TowerFiredEvent) 
		}
	}
	
	private void renderStamp() {
		TextureRegion stamp = Textures.BASIC_BLOCK;
		spriteBatch.draw(stamp, stampX, stampY, tileWidth * 2 - 2, tileHeight * 2 - 2);	
	}

	private void renderBasicBlocks(float delta) {
		for (BasicBlock block : basicBlocks) {
			Vector2 location = tileToScreen(block.getLocation().toVector2());
			TextureRegion texture = block.isRock() ? Textures.BASIC_BLOCK_ROCK : Textures.BASIC_BLOCK;
			spriteBatch.draw(texture, location.x + 1, location.y + 1, tileWidth * 2 - 2, tileHeight * 2 - 2);
		}
	}

	private void renderSlowTowers() {
		for (SlowTower block : slowTowers) {
			Vector2 location = tileToScreen(block.getLocation().toVector2());
			TextureRegion texture = block.isRock() ? Textures.SLOW_TOWER_ROCK : Textures.SLOW_TOWER;
			spriteBatch.draw(texture, location.x + 1, location.y + 1, tileWidth * 2 - 2, tileHeight * 2 - 2);
		}
	}
	
	private void renderRunner() {
		Vector2 position;
		if (currentTime > runnerTrajectory.getRunDuration()) {
			position = runnerTrajectory.getRunnerPosition(runnerTrajectory.getRunDuration());
		}
		else {
			position = runnerTrajectory.getRunnerPosition(currentTime);
		}
		position = tileToScreen(position);
		spriteBatch.draw(Textures.RUNNER, position.x - 11, position.y - 5, tileWidth + 22, tileHeight + 10);
	}
	
	private void renderEndPoints() {
		for (Point point : endPoints) {
			Vector2 location = tileToScreen(point.toVector2());
			spriteBatch.draw(Textures.RUNNER_ENDPOINT, location.x + 1, location.y + 1, tileWidth - 2, tileHeight - 2);
		}
	}
	
	private void renderGrid() {
		spriteBatch.end();
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
		renderer.setColor(new Color(.0f, .9f, .0f, 1f));
		renderer.begin(ShapeType.Line);
		for (int x = 0; x <= mazeWidth; x++) {
			renderer.line(screenX + x * tileWidth, screenY, screenX + x * tileWidth, screenY + screenHeight);
		}
		
		for (int y = 0; y <= mazeHeight; y++) {
			renderer.line(screenX, screenY + y * tileHeight, screenX + screenWidth, screenY + y * tileHeight);
		}
		renderer.end();
		spriteBatch.begin();
	}

	/**
	 * Converts absolute screen coordinates to tile coordinates. Used for processing input.
	 * @param screenCoordinates The screen point to convert.
	 * @return The coordinates of the tile corresponding to the screen coordinates.
	 */
	public Vector2 screenToTile(Vector2 screenCoordinates) {
		return screenCoordinates.sub(screenX, screenY).div(tileWidth, tileHeight);
	}
	
	/**
	 * Converts tile coordinates to absolute screen coordinates. Used for drawing.
	 * @param tileCoordinates The coordinates of the tile to locate.
	 * @return The screen coordinates of the specified tile.
	 */
	private Vector2 tileToScreen(Vector2 tileCoordinates) {
		return tileCoordinates.mul(tileWidth, tileHeight).add(screenX, screenY);
	}
}
