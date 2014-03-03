package strongpineapple.mazing.network.kryonet;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.esotericsoftware.kryonet.rmi.RemoteObject;

public class KryonetConnection {
	private Connection connection;
	private RemoteKryonetConnection endpoint;
	private Map<Class<?>, Object> proxyObjects = new HashMap<Class<?>, Object>();
	private ObjectSpace publisher = new ObjectSpace();
	private int publishID = 1;
	
	public KryonetConnection(Connection connection) {
		this.connection = connection;
		publisher.addConnection(connection);
		
		publisher.register(0, new KryonetConnectionEndpoint());
		this.endpoint = ObjectSpace.getRemoteObject(connection, 0, RemoteKryonetConnection.class);
	}
	
	/**
	 * Should be called after publishRemoteObject is called on the other end.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getRemoteObject(Class<T> iface) {
		assert(proxyObjects.containsKey(iface));
		T result = (T) proxyObjects.get(iface);
		proxyObjects.remove(iface);
		return result;
	}
	
	/**
	 * Should be called before getRemoteObject is called on the other end.
	 */
	public void publishRemoteObject(Object obj, Class<?> iface) {
		publisher.register(publishID, obj);
		publishID++;
		endpoint.onRemoteObjectReady(publishID - 1, iface);
	}
	
	private class KryonetConnectionEndpoint implements RemoteKryonetConnection {
		public void onRemoteObjectReady(int objectSpaceID, Class<?> iface) {
			assert(!proxyObjects.containsKey(iface));
			
			RemoteObject remoteObj = (RemoteObject) ObjectSpace.getRemoteObject(connection, objectSpaceID, iface);
			remoteObj.setNonBlocking(true);
			remoteObj.setTransmitExceptions(false);
			proxyObjects.put(iface, remoteObj);
		}
	}
}
