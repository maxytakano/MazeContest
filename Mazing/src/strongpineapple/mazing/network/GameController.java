package strongpineapple.mazing.network;

import strongpineapple.mazing.engine.Maze;

public interface GameController {
	void submitMaze(Maze maze);

	void setGameView(GameView view);
}
