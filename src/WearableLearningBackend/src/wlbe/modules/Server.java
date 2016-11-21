package wlbe.modules;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;

import wlbe.ServerTime;
import wlbe.event.IEvent;
import wlbe.events.PacketRecieved;
import wlbe.model.ClientData;
import wlbe.module.Module;
import wlbe.module.ModuleManager;
import wlbe.module.ModuleManager.Modules;
import wlbe.packet.IPacket;
import wlbe.packet.PacketTypes;
import wlbe.packets.EchoPacket;

public class Server extends Module {
	
	protected ServerTime serverTime;
	private Logger logger;
	private String[] args;
	private AsynchronousServerSocketChannel serverSocket;
	private ArrayList<ClientData> clients = new ArrayList<ClientData>();
	
	public Server(Modules moduleId, String[] args) {
		super(moduleId);
		this.args = args;
	}
	
	public void setup() {
		logger = (Logger) ModuleManager.getModule(ModuleManager.Modules.LOGGER);
		logger.write("Starting Server...");
		setupServerSettings();
		setupServerTime();
		setupServerSocket();
		logger.write("Server Started...");
	}
	
	public void update() {
		
	}
	
	public void handlePacket(IPacket packet) {
		EventManager eventManager = (EventManager) ModuleManager.getModule(ModuleManager.Modules.EVENT_MANAGER);
		eventManager.BroadcastEvent((IEvent)new PacketRecieved(packet));
		switch(packet.getType()) {
			case ECHO:
				EchoPacket echoPacket = (EchoPacket) packet;
				String echo = echoPacket.getEcho();
				logger.write(echo);
			default:
				break;
		}
	}
	
	public void cleanup() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setupServerSettings() {
		if(args != null) {
			for(String s : args) {
				switch(s) {
					case "-c":
						break;
					case "-v":
						break;
					default:
						break;
				}
			}
		}
	}
	
	private void setupServerTime() {
		logger.write("Setting up Server Time...");
		serverTime = new ServerTime();
	}
	
	private void setupServerSocket() {
		try {
			serverSocket = AsynchronousServerSocketChannel.open();
			InetSocketAddress sAddr = new InetSocketAddress("localhost", 3333);
			serverSocket.bind(sAddr);
			logger.write("Binded...");
			AcceptIncomingConnections();
			logger.write("Waiting for incoming connections...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addClient(ClientData clientData) {
		this.clients.add(clientData);
	}
	
	public void removeClient(ClientData clientData) {
		this.clients.remove(clientData);
	}
	
	private void AcceptIncomingConnections() {
		ClientData clientData = new ClientData(this, serverSocket, null, true);
		try {
			serverSocket.accept(clientData, new ServerConnectionHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ServerTime getServerTime() {
		return serverTime;
	}
}

class ServerConnectionHandler implements CompletionHandler<AsynchronousSocketChannel, ClientData> {
	
	Logger logger = (Logger) ModuleManager.getModule(ModuleManager.Modules.LOGGER);
	
	@Override
	public void completed(AsynchronousSocketChannel client, ClientData clientData) {
		logger.write("Connection Accepted...");
		ServerRequestReadWriteHandler rwHandler = new ServerRequestReadWriteHandler();
		clientData.getServerSocket().accept(clientData, this);
		clientData.getServerModule().addClient(clientData);
		clientData.setClientSocket(client);
		client.read(clientData.getBuffer(), clientData, rwHandler);
	}
	
	@Override
	public void failed(Throwable arg0, ClientData clientData) {
		arg0.printStackTrace();
	}
}

class ServerRequestReadWriteHandler implements CompletionHandler<Integer, ClientData> {

	@Override
	public void completed(Integer result, ClientData clientData) {
		if(clientData.getIsRead()) {
			clientData.getBuffer().flip();
			clientData.getServerModule().handlePacket(PacketTypes.getPacketFromBuffer(clientData.getBuffer(), clientData));
			clientData.getBuffer().clear();
			clientData.getClientSocket().read(clientData.getBuffer(), clientData, this);
		} else {
			
		}
	}

	@Override
	public void failed(Throwable exc, ClientData clientData) {
		exc.printStackTrace();
	}
}
