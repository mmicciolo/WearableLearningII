package wlst;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.swing.table.DefaultTableModel;

import wl.shared.json.packet.IJSONPacket;
import wl.shared.json.packet.JSONPacket;
import wl.shared.json.packets.DisplayPacket;
import wl.shared.json.packets.GameStatePacket;
import wl.shared.json.packets.PlayerPacket;
import wl.shared.json.packets.data.GameStatePacketData;
import wl.shared.json.packets.data.PlayerPacketData;
import wlst.ServerData;

class Client {
	AsynchronousSocketChannel client;
	ByteBuffer buffer;
	boolean isRead;
	boolean readDone;
	BackendServer backendServer;
}

public class BackendServer {
	
	private AsynchronousSocketChannel server;
	private boolean disconnect = false;
	private PlayerPacketData playerData = null;
	private MainFrame mainFrame = null;
	private int id;
	
	public BackendServer(String ipAddress, int portNumber, MainFrame mainFrame, int id) {
		try {
			this.mainFrame = mainFrame;
			this.id = id;
			connect(ipAddress, portNumber);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	private void connect(String ipAddress, int portNumber) throws InterruptedException, ExecutionException {
		try {
			server = AsynchronousSocketChannel.open();
			SocketAddress serverAddr = new InetSocketAddress(ipAddress, portNumber);
			Future<Void> result = server.connect(serverAddr);
			result.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		disconnect = true;
	}
	
	public void checkDisconnected() {
		if(disconnect) {
			try {
				server.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
	
	public void putString(String s, ByteBuffer buffer) {
		buffer.putInt(s.length());
		for(byte b : s.getBytes()) {
			buffer.put(b);
		}
	}
	
	public void write(ByteBuffer byteBuffer) {
		ServerData serverData = new ServerData(this.server, false, false, this);
		serverData.setBuffer(byteBuffer);
		serverData.getBuffer().flip();
		ReadWriteHandler readWriteHandler = new ReadWriteHandler();
		server.write(serverData.getBuffer(), serverData, readWriteHandler);
	}
	
	public ByteBuffer read() {
		ServerData serverData = new ServerData(this.server, true, false, this);
		serverData.setBuffer(ByteBuffer.allocate(65536));
		ReadWriteHandler readWriteHandler = new ReadWriteHandler();
		server.read(serverData.getBuffer(), serverData, readWriteHandler);
		return serverData.getBuffer();
	}
	
	public void readComplete(ByteBuffer buffer) {
		buffer.flip();
		int type = buffer.getInt();
		if(type == 3) {
			IJSONPacket packet = new JSONPacket();
			IJSONPacket p = packet.disassemblePacket(buffer);
			packetRecieved(p);
		}
	}
	
	public void packetRecieved(IJSONPacket packet) {
		DefaultTableModel model = (DefaultTableModel) mainFrame.getTable().getModel();;
		switch(packet.getType()) {
		case GAME_START:
			model.setValueAt("Game Starting...", id, 5);
			break;
		case GAME_END:
			break;
		case PLAYER_DATA:
			PlayerPacket playerPacket = (PlayerPacket) packet;
			playerData = playerPacket.getPlayerData();
			break;
		case GAME_STATE:
			mainFrame.Log();
			GameStatePacket gameStatePacket = (GameStatePacket) packet;
			model.setValueAt(gameStatePacket.getGameStatePacketData().getDisplayData().text, id, 5);
			break;
		case DISPLAY:
			DisplayPacket displayPacket = (DisplayPacket) packet;
			model = (DefaultTableModel) mainFrame.getTable().getModel();
			model.setValueAt(displayPacket.getDisplayData().text, id, 5);
			break;
		default:
			break;
	}
		read();
	}
	
	public boolean getDisconnect() {
		 return this.disconnect;
	}
	
	public PlayerPacketData getPlayerData() {
		return this.playerData;
	}
}

class ReadWriteHandler implements CompletionHandler<Integer, ServerData> {
	
	@Override
	public void completed(Integer results, ServerData serverData) {
		if(serverData.getBackendServer().getDisconnect() == false) {
			if(serverData.getIsRead()) {
				serverData.setOperationDone(true);
				serverData.getBackendServer().readComplete(serverData.getBuffer());
			} else {
				serverData.setOperationDone(true);
			}
			serverData.getBackendServer().checkDisconnected();
		}
	}

	@Override
	public void failed(Throwable exc, ServerData attachment) {
		if(!exc.getMessage().equals("The specified network name is no longer available.\r\n")) {
			exc.printStackTrace();
		}
	}
}