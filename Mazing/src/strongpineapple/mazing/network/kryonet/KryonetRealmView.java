package strongpineapple.mazing.network.kryonet;

import java.util.List;

public interface KryonetRealmView {
	void onGameStarted();
	void updateUsers(List<String> usernames);
}
