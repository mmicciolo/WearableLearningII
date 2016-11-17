package wlfe.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;

import wlfe.common.BackendServer;

public class VirtualDevice {
	
	private String onOff;
	private String id;
	private String displayText;
	
	private BackendServer backendServer;
	
	private boolean on = false;
	
	@PostConstruct
	public void init() {
		backendServer = new BackendServer();
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
			String send = "Hello World!";
			byteBuffer.putInt(send.length());
			for(char c : send.toCharArray()) {
				byteBuffer.putChar(c);
			}
			backendServer.write(byteBuffer);
			backendServer.disconnect();
			backendServer = null;
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
