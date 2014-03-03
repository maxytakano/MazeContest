package strongpineapple.mazing.tests.unit.network;

import static org.mockito.Mockito.*;

import org.junit.Test;

import strongpineapple.mazing.network.kryonet.KryonetGameController;
import strongpineapple.mazing.network.kryonet.KryonetGameView;
import strongpineapple.mazing.network.kryonet.KryonetConnection;
import strongpineapple.mazing.network.kryonet.KryonetRealmController;

public class KryonetConnectionTests extends KryonetTestBase {
	
	@Test
	public void testFunctional() {
		KryonetGameView view = mock(KryonetGameView.class);
		KryonetConnection connection1 = new KryonetConnection(clientConnection);
		KryonetConnection connection2 = new KryonetConnection(serverConnection);
		connection1.publishRemoteObject(view, KryonetGameView.class);
		KryonetGameView proxyView = connection2.getRemoteObject(KryonetGameView.class);
		proxyView.endGame();
		verify(view, timeout(200)).endGame();
		
		// Test publishing two objects from same end
		KryonetGameController controller = mock(KryonetGameController.class);
		connection1.publishRemoteObject(controller, KryonetGameController.class);
		KryonetGameController proxyController = connection2.getRemoteObject(KryonetGameController.class);
		proxyController.setGameView();
		verify(controller, timeout(200)).setGameView();
		
		// Test publishing object on the other end
		KryonetRealmController realmController = mock(KryonetRealmController.class);
		connection2.publishRemoteObject(realmController, KryonetRealmController.class);
		KryonetRealmController proxyRealmController = connection1.getRemoteObject(KryonetRealmController.class);
		proxyRealmController.setRealmView();
		verify(realmController, timeout(200)).setRealmView();
	}
}
