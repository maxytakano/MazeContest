package strongpineapple.mazing.tests.unit.network;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import strongpineapple.mazing.network.RealmController;
import strongpineapple.mazing.network.RealmView;
import strongpineapple.mazing.server.Realm;

public class RealmTests {

	@Test
	public void testUpdateUsersSingleUser() {
		Realm realm = new Realm();
		RealmController controller = realm.addUser("test");
		RealmView view = mock(RealmView.class);
		controller.setRealmView(view);
		verify(view).updateUsers(Arrays.asList("test"));
	}

	@Test
	public void testUpdateUsersTwoUsers() {
		Realm realm = new Realm();
		RealmController controller1 = realm.addUser("user1");
		RealmView view1 = mock(RealmView.class);
		controller1.setRealmView(view1);
		
		RealmController controller2 = realm.addUser("user2");
		RealmView view2 = mock(RealmView.class);
		controller2.setRealmView(view2);
		
		List<String> expectedUserList = Arrays.asList("user1", "user2");
		verify(view1).updateUsers(expectedUserList);
		verify(view2).updateUsers(expectedUserList);
	}
}
