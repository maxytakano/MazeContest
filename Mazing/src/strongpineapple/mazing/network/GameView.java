package strongpineapple.mazing.network;

import java.util.List;

import strongpineapple.mazing.core.MazeSubmission;
import strongpineapple.mazing.engine.Maze;

public interface GameView {
	void startBuildPhase(Maze initialMaze);
	void startRunPhase(List<MazeSubmission> mazes);
	void endGame();
}
