package wlfe.common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import wl.shared.json.packet.IJSONPacket;
import wl.shared.json.packet.JSONPacket;
import wlfe.controller.VirtualDevice;
import wlfe.model.ServerData;

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
	private VirtualDevice virtualDevice;
	
	public BackendServer(VirtualDevice virtualDevice) {
		this.virtualDevice = virtualDevice;
		try {
			connect();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	private void connect() throws InterruptedException, ExecutionException {
		try {
			server = AsynchronousSocketChannel.open();
			SocketAddress serverAddr = new InetSocketAddress("localhost", 3333);
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
//		while(!serverData.getOperationDone()) {
//			
//		}
//		serverData.getBuffer().clear();
	}
	
	public ByteBuffer read() {
		ServerData serverData = new ServerData(this.server, true, false, this);
		serverData.setBuffer(ByteBuffer.allocate(65536));
		ReadWriteHandler readWriteHandler = new ReadWriteHandler();
		server.read(serverData.getBuffer(), serverData, readWriteHandler);
//		while(!serverData.getOperationDone()) {
//			
//		}
		//serverData.getBuffer().clear();
		return serverData.getBuffer();
	}
	
	public void readComplete(ByteBuffer buffer) {
		buffer.flip();
		int type = buffer.getInt();
		if(type == 3) {
			IJSONPacket packet = new JSONPacket();
			IJSONPacket p = packet.disassemblePacket(buffer);
			virtualDevice.packetRecieved(p);
		}
	}
	
	public boolean getDisconnect() {
		 return this.disconnect;
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