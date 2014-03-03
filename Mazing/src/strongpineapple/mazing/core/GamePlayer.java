package strongpineapple.mazing.core;

import strongpineapple.mazing.engine.Maze;
import strongpineapple.mazing.network.GameView;

public class GamePlayer {

	private String username;
	private GameControllerEngine controller;
	private GameView view;
	private Maze mazeSubmission = null;

	public GamePlayer(String username) {
		this.username = username;
	}

	public void setGameController(GameControllerEngine controller) {
		this.controller = controller;
	}

	public void setGameView(GameView view) {
		this.view = view;
	}

	public String getUsername() {
		return username;
	}

	public GameControllerEngine getGameController() {
		return controller;
	}

	public GameView getGameView() {
		return view;
	}

	public void setMazeSubmission(Maze maze) {
		this.mazeSubmission = maze;
	}
	
	public Maze getMazeSubmission() {
		return mazeSubmission;
	}
}