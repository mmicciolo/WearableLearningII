package wlbe.modules;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;

import wlbe.ServerTime;
import wlbe.model.ClientData;
import wlbe.module.Module;
import wlbe.module.ModuleManager;
import wlbe.module.ModuleManager.Modules;
import wlbe.packet.Packet;
import wlbe.packet.PacketTypes;
import wlbe.packets.EchoPacket;
import wlbe.tasks.GameInstance;

public class Server extends Module {
	
	protected ServerTime serverTime;
	private Logger logger;
	private String[] args;
	private AsynchronousServerSocketChannel server;
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
	
	public void handlePacket(Packet packet) {
		switch(packet.getType()) {
			case ECHO:
				EchoPacket echoPacket = (EchoPacket) packet;
				String echo = echoPacket.getEcho();
				logger.write(echo);
				break;
			case PLAYER_CONNECT:
				break;
			case PLAYER_DISCONNECT:
				break;
			case NEW_GAME_INSTANCE:
				GameInstance gameInstance = new GameInstance();
				TaskManager taskManager = (TaskManager) ModuleManager.getModule(ModuleManager.Modules.TASK_MANAGER);
				taskManager.addTask(gameInstance);
				break;
			default:
				break;
		}
	}
	
	public void cleanup() {
		try {
			server.close();
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
			server = AsynchronousServerSocketChannel.open();
			InetSocketAddress sAddr = new InetSocketAddress("localhost", 3333);
			server.bind(sAddr);
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
	
	private void AcceptIncomingConnections() {
		ClientData clientData = new ClientData();
		clientData.server = this;
		clientData.serverSocket = server;
		clientData.buffer = ByteBuffer.allocate(2048);
		try {
			server.accept(clientData, new ServerConnectionHandler());
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
		clientData.serverSocket.accept(clientData, this);
		clientData.server.addClient(clientData);
		clientData.clientSocket = client;
		clientData.isRead = true;
		client.read(clientData.buffer, clientData, rwHandler);
	}
	
	@Override
	public void failed(Throwable arg0, ClientData clientData) {
		arg0.printStackTrace();
	}
}

class ServerRequestReadWriteHandler implements CompletionHandler<Integer, ClientData> {

	@Override
	public void completed(Integer result, ClientData clientData) {
		// TODO Auto-generated method stub
		if(clientData.isRead) {
			clientData.server.handlePacket(PacketTypes.getPacketFromBuffer(clientData.buffer));
		} else {
			
		}
	}

	@Override
	public void failed(Throwable exc, ClientData clientData) {
		// TODO Auto-generated method stub
		
	}
}
