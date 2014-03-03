package strongpineapple.mazing.tests.unit.network;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;

import strongpineapple.mazing.network.NetworkUtil;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class KryonetTestBase {

	private Server server;
	private Client client;
	protected Connection serverConnection;
	protected Connection clientConnection;

	public KryonetTestBase() {
		super();
	}

	@Before
	public void setup() {
		server = new Server();
		NetworkUtil.registerClasses(server);
		server.addListener(new Listener() {
			@Override
			public void connected(Connection connection) {
				serverConnection = connection;
			}
		});
		try {
			server.bind(NetworkUtil.port);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		
		server.start();
		
		client = new Client();
		NetworkUtil.registerClasses(client);
		client.addListener(new Listener() {
			@Override
			public void connected(Connection connection) {
				clientConnection = connection;
			}
		});
		
		client.start();
		
		try {
			client.connect(60000, NetworkUtil.host, NetworkUtil.port);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void teardown() {
		client.close();
		server.close();
	}

}