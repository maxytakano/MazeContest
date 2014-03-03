package strongpineapple.mazing.network;

public interface ClientView {
	void onConnected();

	void onLoginAttemptCompleted(LoginResult result);

	void onJoinedRealm(RealmController controller);
}
