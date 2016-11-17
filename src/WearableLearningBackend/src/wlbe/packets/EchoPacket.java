package wlbe.packets;

import java.nio.ByteBuffer;

import wlbe.packet.Packet;

public class EchoPacket extends Packet {
	
	private String echo;
	
	public EchoPacket(ByteBuffer buffer) {
		super(buffer);
		PopulatePacket();
	}
	
	public void PopulatePacket() {
		while(byteBuffer.remaining() > 0) {
			echo += String.valueOf(byteBuffer.get());
		}
	}
	
	public String getEcho() {
		return this.echo;
	}
}
