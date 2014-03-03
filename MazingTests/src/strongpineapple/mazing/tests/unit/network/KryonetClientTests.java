package strongpineapple.mazing.tests.unit.network;

import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import static org.junit.Assert.*;

import strongpineapple.mazing.client.kryonet.ProxyKryonetClientController;
import strongpineapple.mazing.network.ClientView;
import strongpineapple.mazing.network.LoginResult;
import strongpineapple.mazing.server.KryonetMazingServer;


public class KryonetClientTests {
	
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
	public void testLoginFunctional() {
		ClientView mockListener = mock(ClientView.class);
		ProxyKryonetClientController client = new ProxyKryonetClientController(mockListener);
		client.connect();
		verify(mockListener, timeout(200)).onConnected();
		client.login("test");
		verify(mockListener, timeout(200)).onLoginAttemptCompleted(LoginResult.SUCCESS);
	}
	
	@Test
	public void testAlreadyLoggedIn() {
		ClientView view = mock(ClientView.class);
		ProxyKryonetClientController client = new ProxyKryonetClientController(view);
		client.connect();
		verify(view, timeout(200)).onConnected();
		client.login("test");
		verify(view, timeout(200)).onLoginAttemptCompleted(LoginResult.SUCCESS);
		
		ClientView view2 = mock(ClientView.class);
		ProxyKryonetClientController client2 = new ProxyKryonetClientController(view2);
		client2.connect();
		verify(view2, timeout(200)).onConnected();
		client2.login("test");
		verify(view2, timeout(200)).onLoginAttemptCompleted(LoginResult.USERNAME_ALREADY_LOGGED_IN);
	}
}
