package strongpineapple.mazing.tests.unit.network;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Test;

import strongpineapple.mazing.client.kryonet.ProxyKryonetGameController;
import strongpineapple.mazing.engine.Block;
import strongpineapple.mazing.engine.Maze;
import strongpineapple.mazing.network.GameController;
import strongpineapple.mazing.network.kryonet.KryonetConnection;
import strongpineapple.mazing.server.kryonet.RealKryonetGameController;



public class KryonetGameControllerTests extends KryonetTestBase {
	
	@Test
	public void testSubmitMaze() {
		GameController mockController = mock(GameController.class);
		KryonetConnection kryonetServerConnection = new KryonetConnection(serverConnection);
		KryonetConnection kryonetClientConnection = new KryonetConnection(clientConnection);
		
		new RealKryonetGameController(mockController, kryonetServerConnection);
		ProxyKryonetGameController proxyController = new ProxyKryonetGameController(kryonetClientConnection);
		Maze maze = new Maze(new ArrayList<Block>(), 5, 5);
		proxyController.submitMaze(maze);
		verify(mockController, timeout(200)).submitMaze(any(Maze.class));
	}
}
