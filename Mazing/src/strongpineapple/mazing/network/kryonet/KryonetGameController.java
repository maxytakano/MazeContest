package strongpineapple.mazing.network.kryonet;


public interface KryonetGameController {
	void submitMaze(SerializedMaze maze);
	void setGameView();
}
