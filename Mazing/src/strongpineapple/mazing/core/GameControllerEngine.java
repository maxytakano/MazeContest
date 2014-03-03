package strongpineapple.mazing.core;

import strongpineapple.mazing.engine.Maze;
import strongpineapple.mazing.network.GameController;
import strongpineapple.mazing.network.GameView;

public class GameControllerEngine implements GameController {

	private boolean activated = false;
	private GamePlayer player;
	
	public GameControllerEngine(GamePlayer player) {
		this.player = player;
		player.setGameController(this);
	}
	
	public void activate() {
		activated = true;
	}

	@Override
	public void submitMaze(Maze maze) {
		assert(activated);
		player.setMazeSubmission(maze);
	}

	@Override
	public void setGameView(GameView view) {
		player.setGameView(view);
	}
}
