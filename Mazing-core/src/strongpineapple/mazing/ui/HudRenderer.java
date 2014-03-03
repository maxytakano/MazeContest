package strongpineapple.mazing.ui;

import java.text.DecimalFormat;
import strongpineapple.mazing.engine.MazeBuilder;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class HudRenderer extends InputAdapter {
	private static final BitmapFont FONT = new BitmapFont(true);
	private static final DecimalFormat TIME_FORMAT = new DecimalFormat();
	
	static {
		FONT.setColor(Color.GREEN);
		FONT.setScale(2);
		
		TIME_FORMAT.setMinimumIntegerDigits(2);
		TIME_FORMAT.setMaximumIntegerDigits(2);
		TIME_FORMAT.setMinimumFractionDigits(3);
		TIME_FORMAT.setMaximumFractionDigits(3);
	}
	
	private float x;
	private float y;
	private SpriteBatch batch;
	private boolean buildPhase = true;
	private MazeBuilder mazeBuilder;
	private MazeController mazeController;
	
	private CountdownTimer countDownTimer = new CountdownTimer();
	
	public HudRenderer(float x, float y, SpriteBatch batch, MazeBuilder mazeBuilder, MazeController mazeController) {
		this.x = x;
		this.y = y;
		this.batch = batch;
		this.mazeBuilder = mazeBuilder;
		this.mazeController = mazeController;
	}
	
	public void startRunPhase(final float duration) {
		buildPhase = false;
		countDownTimer.start(duration);
	}
	
	public void startBuildPhase(float duration) {
		buildPhase = true;
		countDownTimer.start(duration);
	}
	
	public void render() {
		renderButtons();
		
		StringBuilder str = new StringBuilder();
		
		if (buildPhase) {
			str.append("Build time: ");
			str.append(TIME_FORMAT.format(countDownTimer.getRemainingTime()));
		}
		else {
			str.append("Score: ");
			str.append(TIME_FORMAT.format(countDownTimer.getElapsedTime()));
		}
		
		if (buildPhase) {
			str.append('\n');
			str.append("Blocks: ");
			str.append(mazeBuilder.getResources().getBlock());
			str.append('\n');
			str.append("Towers: ");
			str.append(mazeBuilder.getResources().getTower());
		}
		
		FONT.drawMultiLine(batch, str, x + 250, y + 20);
	}
	
	private void renderButtons() {
		if(mazeController.getTowerType() == 1) {
			batch.draw(Textures.BASIC_BLOCK_SELECTED, x + 15, y + 40, 80, 80);
			batch.draw(Textures.SLOW_TOWER, x + 115, y + 40, 80, 80);
		}
		else if(mazeController.getTowerType() == 2) {
			batch.draw(Textures.BASIC_BLOCK, x + 15, y + 40, 80, 80);
			batch.draw(Textures.SLOW_TOWER_SELECTED, x + 115, y + 40, 80, 80);
		}
		
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Rectangle basicTower = new Rectangle(x + 25, y + 40, 80, 80);
		Rectangle slowTower = new Rectangle(x + 125, y + 40, 80, 80);
		if(basicTower.contains(screenX, screenY)) {
			mazeController.buildBasicBlock();
		}
		else if(slowTower.contains(screenX, screenY)){
			mazeController.buildSlowTower();
		}
		return true;
	}
	
	//@Override
	public boolean touchMoved (int screenX, int screenY) {
		
		return false;
	}
}
