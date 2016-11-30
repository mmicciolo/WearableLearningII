package wlfe.common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
	
	public BackendServer() {
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
				e.printStackTrace();
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
		while(!serverData.getOperationDone()) {
			
		}
//		Client client = new Client();
//		client.client = this.server;
//		client.buffer = byteBuffer;
//		client.buffer.flip();
//		client.isRead = false;
//		client.backendServer = this;
//		ReadWriteHandler readWriteHandler = new ReadWriteHandler();
//		server.write(client.buffer, client, readWriteHandler);
//		while(!client.readDone) {
//			
//		}
	}
	
	public ByteBuffer read() {
		ServerData serverData = new ServerData(this.server, true, false, this);
		serverData.setBuffer(ByteBuffer.allocate(2048));
		ReadWriteHandler readWriteHandler = new ReadWriteHandler();
		server.read(serverData.getBuffer(), serverData, readWriteHandler);
		while(!serverData.getOperationDone()) {
			
		}
		return serverData.getBuffer();
//		Client client = new Client();
//		client.client = this.server;
//		client.buffer = ByteBuffer.allocate(2048);
//		client.isRead = true;
//		client.readDone = false;
//		client.backendServer = this;
//		ReadWriteHandler readWriteHandler = new ReadWriteHandler();
//		server.read(client.buffer, client, readWriteHandler);
//		while(!client.readDone) {
//			
//		}
//		return client.buffer;
	}
}

//class ReadWriteHandler implements CompletionHandler<Integer, Client> {
//	
//	@Override
//	public void completed(Integer results, Client client) {
//		if(client.isRead) {
//			client.readDone = true;
//
//		} else {
//			client.readDone = true;
//		}
//		client.backendServer.checkDisconnected();
//	}
//
//	@Override
//	public void failed(Throwable exc, Client attachment) {
//		exc.printStackTrace();
//	}
//}

class ReadWriteHandler implements CompletionHandler<Integer, ServerData> {
	
	@Override
	public void completed(Integer results, ServerData serverData) {
		if(serverData.getIsRead()) {
			serverData.setOperationDone(true);

		} else {
			serverData.setOperationDone(true);
		}
		serverData.getBackendServer().checkDisconnected();
	}

	@Override
	public void failed(Throwable exc, ServerData attachment) {
		exc.printStackTrace();
	}
}