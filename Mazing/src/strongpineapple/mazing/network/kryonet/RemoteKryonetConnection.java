package strongpineapple.mazing.network.kryonet;

public interface RemoteKryonetConnection {
	void onRemoteObjectReady(int objectSpaceID, Class<?> iface);
}