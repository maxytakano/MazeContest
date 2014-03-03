package strongpineapple.mazing.tests.unit.network;

import static org.mockito.Mockito.*;

import org.junit.Test;

import strongpineapple.mazing.network.ClientController;
import strongpineapple.mazing.network.ClientView;
import strongpineapple.mazing.network.LoginResult;
import strongpineapple.mazing.server.World;

public class WorldTests {
	
	@Test
	public void testWorldLogin() {
		ClientView mockListener = mock(ClientView.class);
		World world = new World();
		ClientController client = world.connect(mockListener);
		client.login("test");
		verify(mockListener).onLoginAttemptCompleted(LoginResult.SUCCESS);
	}
}
