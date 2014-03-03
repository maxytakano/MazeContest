package strongpineapple.mazing.network;
/*
 * Question
 * Could we use this data structure to hold datas for both client and server side?
 */

import strongpineapple.mazing.engine.Maze;

public class User {
	private final String name;
	private float score;
	private float[] scoreList;
	private Maze Maze;
	private int clientID;
	private String currentPhase;
	private float totalScore;
	
	public User(){
		this.name = "Dummy";
	}
	
	public User(String name, int clientID, int numOfRounds){
		this.name = name;
		this.clientID =clientID;
		scoreList = new float[numOfRounds];
	}
	
	public String getName(){
		return name;
	}
	
	public float getScore(){
		return score;
	}
	
	public Maze getMaze(){
		return Maze;
	}
	
	public int getClientID(){
		return clientID;
	}
	
	public void setScore(float score){
		this.score = score;
	}
	
	public void endOfRound(int roundNum){
		scoreList[roundNum - 1] = score;
		score = 0.0f;
	}
	
	public float getTotalScore(){
		totalScore = 0.0f;
		for(int i = 0; i < scoreList.length; i++){
			totalScore += scoreList[i];
		}
		return totalScore;
	}
	
	public String getCurrentPhase(){
		return currentPhase;
	}
	
	public void setCurrentPhase(String phase){
		currentPhase = phase;
	}
	
	public void setMaze(Maze Maze){
		this.Maze = Maze;
	}
}
