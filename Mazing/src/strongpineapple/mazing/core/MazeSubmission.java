package strongpineapple.mazing.core;

import strongpineapple.mazing.engine.Maze;

public class MazeSubmission {
	private String username;
	private Maze maze;
	
	public MazeSubmission(String username, Maze maze) {
		this.username = username;
		this.maze = maze;
	}
	
	public String getUsername() {
		return username;
	}
	public Maze getMaze() {
		return maze;
	}
}
