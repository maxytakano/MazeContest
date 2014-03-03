package strongpineapple.mazing.network.kryonet;

import java.util.List;

import strongpineapple.mazing.core.MazeSubmission;
import strongpineapple.mazing.engine.Maze;

public interface KryonetGameView {
	void startBuildPhase(Maze initialMaze);
	void startRunPhase(List<MazeSubmission> mazes);
	void endGame();
}
