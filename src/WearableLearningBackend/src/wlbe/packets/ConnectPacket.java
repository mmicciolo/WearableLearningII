package wlbe.packets;

import java.nio.ByteBuffer;

import wlbe.packet.Packet;

public class ConnectPacket extends Packet {
	
	private String username;
	
	public ConnectPacket(ByteBuffer buffer) {
		super(buffer);
		PopulatePacket();
	}
	
	public void PopulatePacket() {
		int usernameLength = byteBuffer.getInt();
		for(int i = 0; i < usernameLength; i++) {
			username += byteBuffer.get();
		}
	}
}
