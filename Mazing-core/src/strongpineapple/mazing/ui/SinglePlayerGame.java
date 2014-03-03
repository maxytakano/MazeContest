package strongpineapple.mazing.ui;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import strongpineapple.mazing.config.GameConfig;
import strongpineapple.mazing.engine.Maze;
import strongpineapple.mazing.engine.MazeBuilder;
import strongpineapple.mazing.engine.MazeBuilderEngine;
import strongpineapple.mazing.engine.MazingGameListener;
import strongpineapple.mazing.engine.runphase.RunPhaseEngine;

public class SinglePlayerGame {
	private final MazingGameListener listener;
	private final int maxRound;
	private int round = 0;
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private MazeBuilder mazeBuilder;
	
	
	public SinglePlayerGame(MazingGameListener listener) {
		this.listener = listener;
		this.maxRound = GameConfig.ROUNDS;
	}

	public void start() {
		listener.startGame();
		startRound();
	}
	
	private void startRound() {
		round++;
		listener.startRound();
		this.mazeBuilder = new MazeBuilderEngine(round);
		listener.startBuildPhase(GameConfig.BUILD_PHASE_SECS, mazeBuilder);
		executor.schedule(new PostBuildPhaseCallback(), GameConfig.BUILD_PHASE_SECS + GameConfig.PRE_RUN_PHASE_DELAY_SECS, TimeUnit.SECONDS);
	}
	
	private class PostBuildPhaseCallback implements Runnable {

		@Override
		public void run() {
			try {
				Maze maze = mazeBuilder.toMaze();
				RunPhaseEngine engine = new RunPhaseEngine(maze);
				listener.startRunPhase(engine);
				executor.schedule(new PostRunPhaseCallback(), 
						(long) ((engine.getRunDuration() + GameConfig.POST_RUN_PHASE_DELAY_SECS) * 1000), TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private class PostRunPhaseCallback implements Runnable {

		@Override
		public void run() {
			if (round == maxRound) {
				listener.endGame();
			}
			else {
				startRound();
			}
		}
	}
}
