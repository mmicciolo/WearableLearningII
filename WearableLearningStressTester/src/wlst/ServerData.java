package wlst;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

import wlst.BackendServer;

public class ServerData {
	
	private AsynchronousSocketChannel serverSocket;
	private ByteBuffer buffer;
	private boolean isRead;
	private boolean operationDone;
	private BackendServer backendServer;
	
	public ServerData() {
		
	}
	
	public ServerData(AsynchronousSocketChannel serverSocket, boolean isRead, boolean operationDone, BackendServer backendServer) {
		this.serverSocket = serverSocket;
		this.buffer = ByteBuffer.allocate(65536);
		this.isRead = isRead;
		this.operationDone = operationDone;
		this.backendServer = backendServer;
	}
	
	public void setServerSocket(AsynchronousSocketChannel serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public void setBuffer(ByteBuffer buffer) {
		this.buffer = buffer;
	}
	
	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}
	
	public void setOperationDone(boolean operationDone) {
		this.operationDone = operationDone;
	}
	
	public void setBackendServer(BackendServer backendServer) {
		this.backendServer = backendServer;
	}
	
	public AsynchronousSocketChannel getServerSocket() {
		return this.serverSocket;
	}
	
	public ByteBuffer getBuffer() {
		return this.buffer;
	}
	
	public boolean getIsRead() {
		return this.isRead;
	}
	
	public boolean getOperationDone() {
		return this.operationDone;
	}
	
	public BackendServer getBackendServer() {
		return this.backendServer;
	}
}
