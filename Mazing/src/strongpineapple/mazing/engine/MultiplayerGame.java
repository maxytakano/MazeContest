package strongpineapple.mazing.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import strongpineapple.mazing.config.GameConfig;
import strongpineapple.mazing.engine.runphase.RunPhaseEngine;

public class MultiplayerGame {
	private final MultiplayerGameListener listener;
	private final int maxRound;
	private int round = 0;
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	private Collection<? extends MazingPlayer> players;
	
	public MultiplayerGame(Collection<? extends MazingPlayer> players, MultiplayerGameListener listener) {
		this.listener = listener;
		this.maxRound = GameConfig.ROUNDS;
		this.players = players;
	}

	public void start() {
		startRound();
	}
	
	private void startRound() {
		round++;
		listener.startRound();
		MazeBuilder mazeBuilder = new MazeBuilderEngine(round);
		listener.startBuildPhase(GameConfig.BUILD_PHASE_SECS, mazeBuilder);
		executor.schedule(new PostBuildPhaseCallback(), GameConfig.BUILD_PHASE_SECS + GameConfig.PRE_RUN_PHASE_DELAY_SECS, TimeUnit.SECONDS);
	}
	
	private class PostBuildPhaseCallback implements Runnable {

		@Override
		public void run() {
			try {
				Map<MazingPlayer, RunPhaseEngine> engines = new HashMap<MazingPlayer, RunPhaseEngine>();
				for (MazingPlayer player : players) {
					engines.put(player, new RunPhaseEngine(player.getMaze()));
				}
				
				RunPhaseEngine winner = Collections.max(engines.values(), new RunPhaseEngine.RunDurationComparator());
				listener.startRunPhase(engines);
				executor.schedule(new PostRunPhaseCallback(), 
						(long) ((winner.getRunDuration() + GameConfig.POST_RUN_PHASE_DELAY_SECS) * 1000), TimeUnit.MILLISECONDS);
			}
			catch (Exception ex) {
				ex.printStackTrace();
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
