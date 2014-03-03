package strongpineapple.mazing.engine;

import java.util.Map;

import strongpineapple.mazing.engine.runphase.RunPhaseEngine;

public interface MultiplayerGameListener {
	void startRound();
	void startBuildPhase(int seconds, MazeBuilder mazeBuilder);
	void startRunPhase(Map<MazingPlayer, RunPhaseEngine> engines);
	void endGame();
}
