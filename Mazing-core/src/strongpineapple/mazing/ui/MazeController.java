package strongpineapple.mazing.ui;

import java.util.HashMap;
import java.util.Map.Entry;

import strongpineapple.mazing.engine.BasicBlock;
import strongpineapple.mazing.engine.Block;
import strongpineapple.mazing.engine.MazeBuilder;
import strongpineapple.mazing.engine.SlowTower;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import strongpineapple.mazing.engine.*;
import strongpineapple.mazing.engine.utils.Point;

public class MazeController extends InputAdapter implements MazeBuilderListener {
	private MazeRenderer mazeRenderer;
	private MazeBuilder mazeBuilder;
	private int towerType = 1;
	
	private HashMap<Integer, Block> sellableBlocks = new HashMap<Integer, Block>();
	
	/**
	 * @param mazeRenderer
	 * @param mazeBuilder
	 */
	public MazeController(MazeRenderer mazeRenderer, MazeBuilder mazeBuilder) {
		this.mazeRenderer = mazeRenderer;
		this.mazeBuilder = mazeBuilder;
		this.mazeRenderer.setEndPointTiles(mazeBuilder.getRunnerStart(), mazeBuilder.getRunnerEnd());
		for (BasicBlock block : mazeBuilder.getBasicBlockRocks()) {
			mazeRenderer.addBasicBlock(block);
		}
		
		for (SlowTower tower : mazeBuilder.getSlowTowerRocks()) {
			mazeRenderer.addSlowTower(tower);
		}
	}
	
	public boolean keyDown(int keycode) {
	    if(keycode == Input.Keys.D) {
	    	 towerType = 1;	
	    }
	    
	    else if(keycode == Input.Keys.F) {
	    	 towerType = 2;	
	    }
		   
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		if (!mazeRenderer.getBoundingRectangle().contains(screenX, screenY))
			return false;
		
		Vector2 tile = mazeRenderer.screenToTile(new Vector2(screenX, screenY));
		if (button == 0) {
			if(towerType == 1)
				mazeBuilder.buyBasicBlock((int) tile.x, (int) tile.y);
			else if(towerType== 2)
				mazeBuilder.buySlowTower((int) tile.x, (int) tile.y);
			else return false;
		}
		else if (button == 1) {
			Point tilePoint = new Point(tile);
			Integer blockID = null;
			for (Entry<Integer, Block> entry : sellableBlocks.entrySet()) {
				for (Point point :entry.getValue().getOccupiedTiles()) {
					if (point.equals(tilePoint)) {
						blockID = entry.getKey();
					}
				}
			}
			
			if (blockID != null) {
				mazeBuilder.sellBlock(blockID);
			}
		}
		
		return true;
	}
	
	public boolean mouseMoved(int screenX, int screenY) {
		if (!mazeRenderer.getBoundingRectangle().contains(screenX, screenY))
			return false;
		
		mazeRenderer.updateStamp(screenX, screenY);
		return true;
	}

	@Override
	public void basicBlockBought(int x, int y, int blockID) {
		BasicBlock block = new BasicBlock(x, y);
		sellableBlocks.put(blockID, block);
		mazeRenderer.addBasicBlock(block);
	}
	
	@Override
	public void slowTowerBought(int x, int y, int blockID) {
		SlowTower block = new SlowTower(x, y);
		sellableBlocks.put(blockID, block);
		mazeRenderer.addSlowTower(block);
		if(mazeBuilder.getResources().getTower() == 0) {
			towerType = 1;
		}
	}

	public void buildBasicBlock() {
		towerType = 1;
	}
	
	public void buildSlowTower() {
		towerType = 2;
		
	}
	
	public int getTowerType() {
		return towerType;
	}
	
	@Override
	public void blockSold(int blockID) {
		Block block = sellableBlocks.remove(blockID);
		mazeRenderer.removeBlock(block);
	}
}
