package strongpineapple.mazing.tests.unit.network;

import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import strongpineapple.mazing.client.kryonet.ProxyKryonetClientController;
import strongpineapple.mazing.network.ClientView;
import strongpineapple.mazing.network.LoginResult;
import strongpineapple.mazing.network.RealmController;
import strongpineapple.mazing.network.RealmView;
import strongpineapple.mazing.server.KryonetMazingServer;

public class KryonetRealmTests {
	
	private KryonetMazingServer server;
	
	@Before
	public void setup() {
		server = new KryonetMazingServer();
	}
	
	@After
	public void teardown() {
		server.close();
	}
	
	@Test
	public void testUpdateUsers() {
		
		ClientView clientView = mock(ClientView.class);
		ProxyKryonetClientController client = new ProxyKryonetClientController(clientView);
		client.connect();
		verify(clientView, timeout(200)).onConnected();
		client.login("test");
		verify(clientView, timeout(200)).onLoginAttemptCompleted(LoginResult.SUCCESS);

		ArgumentCaptor<RealmController> argument = ArgumentCaptor.forClass(RealmController.class);
		verify(clientView, timeout(200)).onJoinedRealm(argument.capture());
		RealmController controller = argument.getValue();
		
		RealmView realmView = mock(RealmView.class);
		controller.setRealmView(realmView);
		verify(realmView, timeout(200)).updateUsers(Arrays.asList("test"));
	}
	
	@Test
	public void testOnGameStart() {
		ClientView clientView = mock(ClientView.class);
		ProxyKryonetClientController client = new ProxyKryonetClientController(clientView);
		client.connect();
		verify(clientView, timeout(200)).onConnected();
		client.login("test");
		verify(clientView, timeout(200)).onLoginAttemptCompleted(LoginResult.SUCCESS);
		
		ArgumentCaptor<RealmController> argument = ArgumentCaptor.forClass(RealmController.class);
		verify(clientView, timeout(200)).onJoinedRealm(argument.capture());
		RealmController controller = argument.getValue();
		
		RealmView realmView = mock(RealmView.class);
		controller.setRealmView(realmView);
		
		verify(realmView, never()).onGameStarted();
		
		controller.setReady(true);
		verify(realmView, timeout(200)).onGameStarted();
	}
}
