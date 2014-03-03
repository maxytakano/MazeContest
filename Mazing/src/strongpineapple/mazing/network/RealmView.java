package strongpineapple.mazing.network;

import java.util.List;

public interface RealmView {
	void onGameStarted();

	void updateUsers(List<String> usernames);
}
