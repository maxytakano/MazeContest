package strongpineapple.mazing.network;

import java.util.ArrayList;

import org.objenesis.strategy.StdInstantiatorStrategy;

import strongpineapple.mazing.network.kryonet.KryonetClientController;
import strongpineapple.mazing.network.kryonet.KryonetClientView;
import strongpineapple.mazing.network.kryonet.KryonetGameController;
import strongpineapple.mazing.network.kryonet.KryonetGameView;
import strongpineapple.mazing.network.kryonet.KryonetRealmController;
import strongpineapple.mazing.network.kryonet.KryonetRealmView;
import strongpineapple.mazing.network.kryonet.RemoteKryonetConnection;
import strongpineapple.mazing.network.kryonet.SerializedMaze;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

public class NetworkUtil {
	public static final String host = "127.0.0.1";
	public static final int port = 30000;
	
	public static void registerClasses(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
//		kryo.setRegistrationRequired(false);
		
		// Allows classes without default constructors to be serialized
		kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
		
		ObjectSpace.registerClasses(kryo);
		kryo.register(Class.class);
		kryo.register(RemoteKryonetConnection.class);
		kryo.register(KryonetClientController.class);
		kryo.register(KryonetRealmController.class);
		kryo.register(KryonetRealmView.class);
		kryo.register(KryonetClientView.class);
		kryo.register(KryonetGameController.class);
		kryo.register(KryonetGameView.class);
		kryo.register(LoginResult.class);
		kryo.register(ArrayList.class);
		
		kryo.register(SerializedMaze.class);
	}
}
