package strongpineapple.mazing.engine;

import strongpineapple.mazing.engine.runphase.RunPhaseEngine;

public interface MazingGameListener {
	void startGame();
	void startRound();
	void startBuildPhase(int buildPhaseSecs, MazeBuilder mazeBuilder);
	void startRunPhase(RunPhaseEngine engine);
	void endGame();
}
