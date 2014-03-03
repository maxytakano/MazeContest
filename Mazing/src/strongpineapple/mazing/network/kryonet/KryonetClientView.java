package strongpineapple.mazing.network.kryonet;

import strongpineapple.mazing.network.LoginResult;

public interface KryonetClientView {
	void onConnected();

	void onLoginAttemptCompleted(LoginResult result);

	void onJoinedRealm();
}