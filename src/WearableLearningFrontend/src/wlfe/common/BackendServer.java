package wlfe.common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

class Client {
	AsynchronousSocketChannel client;
	ByteBuffer buffer;
	boolean isRead;
	boolean readDone;
	BackendServer backendServer;
}

public class BackendServer {
	
	AsynchronousSocketChannel server;
	
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
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(ByteBuffer byteBuffer) {
		Client client = new Client();
		client.client = this.server;
		client.buffer = byteBuffer;
		client.buffer.flip();
		client.isRead = false;
		ReadWriteHandler readWriteHandler = new ReadWriteHandler();
		server.write(client.buffer, client, readWriteHandler);
		while(!client.readDone) {
			
		}
	}
	
	public ByteBuffer read() {
		Client client = new Client();
		client.client = this.server;
		client.buffer = ByteBuffer.allocate(2048);
		client.isRead = true;
		client.readDone = false;
		client.backendServer = this;
		ReadWriteHandler readWriteHandler = new ReadWriteHandler();
		server.read(client.buffer, client, readWriteHandler);
		while(!client.readDone) {
			
		}
		return client.buffer;
	}
}

class ReadWriteHandler implements CompletionHandler<Integer, Client> {
	
	@Override
	public void completed(Integer results, Client client) {
		if(client.isRead) {
			client.readDone = true;

		} else {
			client.readDone = true;
		}
	}

	@Override
	public void failed(Throwable exc, Client attachment) {
		exc.printStackTrace();
	}
}