package wlbe.model;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;

import wlbe.modules.Server;

public class ClientData {
	
	private Server serverModule;
	private AsynchronousServerSocketChannel serverSocket;
	private AsynchronousSocketChannel clientSocket;
	private ByteBuffer buffer;
	private boolean isRead;
	private String studentName;
    
    public ClientData() {
    	
    }
    
    public ClientData(Server serverModule, AsynchronousServerSocketChannel serverSocket, AsynchronousSocketChannel clientSocket, boolean isRead) {
    	this.serverModule = serverModule;
    	this.serverSocket = serverSocket;
    	this.clientSocket = clientSocket;
    	this.buffer = ByteBuffer.allocate(2048);
    	this.isRead = isRead;
    }
    
    public void setServerModule(Server serverModule) {
    	this.serverModule = serverModule;
    }
    
    public void setServerSocket(AsynchronousServerSocketChannel serverSocket) {
    	this.serverSocket = serverSocket;
    }
    
    public void setClientSocket(AsynchronousSocketChannel clientSocket) {
    	this.clientSocket = clientSocket;
    }
    
    public void setBuffer(ByteBuffer buffer) {
    	this.buffer = buffer;
    }
    
    public void setIsRead(boolean isRead) {
    	this.isRead = isRead;
    }
    
    public Server getServerModule() {
    	return this.serverModule;
    }
    
    public AsynchronousServerSocketChannel getServerSocket() {
    	return this.serverSocket;
    }
    
    public AsynchronousSocketChannel getClientSocket() {
    	return this.clientSocket;
    }
    
    public ByteBuffer getBuffer() {
    	return this.buffer;
    }
    
    public boolean getIsRead() {
    	return this.isRead;
    }
}
