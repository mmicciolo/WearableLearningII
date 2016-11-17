package wlbe.packets;

import java.nio.ByteBuffer;

import wlbe.packet.Packet;

public class EchoPacket extends Packet {
	
	private String echo;
	
	public EchoPacket(ByteBuffer buffer) {
		super(buffer);
		populatePacket();
	}
	
	public void populatePacket() {
		int echoLength = byteBuffer.getInt();
		char echoc[] = new char[echoLength];
		for(int i = 0; i < echoLength; i++) {
			echoc[i] = byteBuffer.getChar();
		}
		echo = String.valueOf(echoc);
	}
	
	public String getEcho() {
		return this.echo;
	}
}
