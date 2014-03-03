package strongpineapple.mazing.config;


import strongpineapple.mazing.engine.ResourceBundle;
import strongpineapple.mazing.engine.utils.Point;

public class GameConfig {
	/*
	Development configuration -- fast rounds.
	insert / after the * to disable, delete slash to enable -> */
	
	public static final int BUILD_PHASE_SECS = 15;
	public static final int POST_RUN_PHASE_DELAY_SECS = 1;
	public static final int PRE_RUN_PHASE_DELAY_SECS = 1;
	
	public static final int MAZE_WIDTH = 18;
	public static final int MAZE_HEIGHT = 16;
	
	public static final Point RUNNER_START = new Point(MAZE_WIDTH / 2, 0);
	public static final Point RUNNER_END = new Point(MAZE_WIDTH / 2, MAZE_HEIGHT - 1);
	public static final float RUNNER_BASE_SPEED = 3;
	
	public static final int ROUNDS = 5;
	 /**/
	
	
	/*
	Standard configuration
	*
	
	public static final int BUILD_PHASE_SECS = 60;
	public static final int POST_RUN_PHASE_DELAY_SECS = 5;
	public static final int PRE_RUN_PHASE_DELAY_SECS = 2;
	
	public static final int MAZE_WIDTH = 18;
	public static final int MAZE_HEIGHT = 16;
	
	public static final Point RUNNER_START = new Point(MAZE_WIDTH / 2, 0);
	public static final Point RUNNER_END = new Point(MAZE_WIDTH / 2, MAZE_HEIGHT - 1);
	public static final float RUNNER_BASE_SPEED = 3;
	
	public static final int ROUNDS = 5;
	
	/**/
	
	public static int getRoundBlocks(int round) {
		switch (round) {
		case 1:
			return 17;
		case 2:
			return 6;
		case 3:
			return 19;
		case 4:
			return 12;
		case 5:
			return 17;
		default:
			return 10;
		}
	}
	
	public static int getRoundTowers(int round) {
		switch (round) {
		case 1:
			return 0;
		case 2:
			return 1;
		case 3:
			return 0;
		case 4:
			return 2;
		case 5:
			return 0;
		default:
			return 1;
		}
	}
	
	public static ResourceBundle getRoundResources(int round) {
		switch (round) {
		case 1:
			return new ResourceBundle(22, 2);
		case 2:
			return new ResourceBundle(35, 0);
		case 3:
			return new ResourceBundle(5, 1);
		case 4:
			return new ResourceBundle(14, 0);
		case 5:
			return new ResourceBundle(30, 0);
		default:
			return new ResourceBundle(10, 1);
		}
	}
}
