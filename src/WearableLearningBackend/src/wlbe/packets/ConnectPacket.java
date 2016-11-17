package wlbe.packets;

import java.nio.ByteBuffer;

import wlbe.packet.Packet;

public class ConnectPacket extends Packet {
	
	private String username;
	
	public ConnectPacket(ByteBuffer buffer) {
		super(buffer);
		populatePacket();
	}
	
	public void populatePacket() {
		int usernameLength = byteBuffer.getInt();
		for(int i = 0; i < usernameLength; i++) {
			username += byteBuffer.get();
		}
	}
}
