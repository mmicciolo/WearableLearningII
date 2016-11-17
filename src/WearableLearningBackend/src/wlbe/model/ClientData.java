package wlbe.model;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;

import wlbe.modules.Server;

public class ClientData {
	
	public Server server;
	public AsynchronousServerSocketChannel serverSocket;
    public AsynchronousSocketChannel clientSocket;
    public ByteBuffer buffer;
    public boolean isRead;
    
}
