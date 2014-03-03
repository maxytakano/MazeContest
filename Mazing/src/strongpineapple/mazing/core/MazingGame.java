package strongpineapple.mazing.core;

import java.util.ArrayList;
import java.util.List;

import strongpineapple.mazing.engine.Block;
import strongpineapple.mazing.engine.GamePhase;
import strongpineapple.mazing.engine.Maze;
import strongpineapple.mazing.network.GameController;


public class MazingGame {
	private List<GamePlayer> players = new ArrayList<GamePlayer>();
	
	private GamePhase gamePhase = GamePhase.NotStarted;
	
	public GameController addPlayer(String username) {
		assert(gamePhase == GamePhase.NotStarted);
		
		GamePlayer player = new GamePlayer(username);
		players.add(player);
		GameControllerEngine controller = new GameControllerEngine(player);
		return controller;
	}
	
	public void startBuildPhase() {
		assert(gamePhase != GamePhase.BuildPhase);
		if (gamePhase == GamePhase.NotStarted) {
			activateControllers();
		}
		
		gamePhase = GamePhase.BuildPhase;
		
		Maze initialMaze = generateMaze();
		for (GamePlayer player : players) {
			player.getGameView().startBuildPhase(initialMaze);
		}
	}
	
	public void startRunPhase() {
		assert(gamePhase == GamePhase.BuildPhase);
		gamePhase = GamePhase.RunPhase;
		
		List<MazeSubmission> submissions = new ArrayList<MazeSubmission>();
		for (GamePlayer player : players) {
			MazeSubmission submission = new MazeSubmission(player.getUsername(), player.getMazeSubmission());
			submissions.add(submission);
		}
		
		for (GamePlayer player : players) {
			player.getGameView().startRunPhase(submissions);
		}
	}

	private void activateControllers() {
		for (GamePlayer player : players) {
			player.getGameController().activate();
		}
	}
	
	private Maze generateMaze() {
		return new Maze(new ArrayList<Block>(), 10, 10);
	}
}
