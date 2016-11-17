package wlfe.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;

public class VirtualDevice {
	
	private String onOff;
	private String id;
	private String displayText;
	
	AsynchronousSocketChannel server;
	
	private boolean on = false;
	
	@PostConstruct
	public void init() {
		try {
			server = AsynchronousSocketChannel.open();
			SocketAddress serverAddr = new InetSocketAddress("localhost", 3333);
			server.connect(serverAddr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void button1() {
		displayText = "Button 1 Pushed!\nColor: Red";
		RequestContext.getCurrentInstance().update("display");
	}
	
	public void button2() {
		displayText = "Button 2 Pushed!\nColor: Green";
		RequestContext.getCurrentInstance().update("display");
	}
	
	public void button3() {
		displayText = "Button 3 Pushed!\nColor: Blue";
		RequestContext.getCurrentInstance().update("display");
	}
	
	public void button4() {
		displayText = "Button 4 Pushed!\nColor: Black";
		RequestContext.getCurrentInstance().update("display");
	}
	
	public void onOffChange() {
		if(Boolean.valueOf(onOff) == true) {
			on = true;
			ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
			byteBuffer.putInt(0);
			byteBuffer.putInt(123456789);
			server.write(byteBuffer);
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			on = false;
		}
	}
	
	public void setOnOff(String onOff) {
		this.onOff = onOff;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	
	public String getOnOff() {
		return this.onOff;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getDisplayText() {
		return this.displayText;
	}
}
