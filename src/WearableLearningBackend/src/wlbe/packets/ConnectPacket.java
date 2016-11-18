package wlbe.packets;

import java.nio.ByteBuffer;

import wlbe.packet.Packet;
import wlbe.packet.PacketTypes;

public class ConnectPacket extends Packet {
	
	private String studentName;
	private int selectedGameInstance;
	private String team;
	
	public ConnectPacket(ByteBuffer buffer) {
		super(buffer, PacketTypes.PacketType.PLAYER_CONNECT);
		populatePacket();
	}
	
	public void populatePacket() {
		
		int usernameLength = byteBuffer.getInt();
		for(int i = 0; i < usernameLength; i++) {
			studentName += byteBuffer.get();
		}
		
		selectedGameInstance = byteBuffer.getInt();
		
		int teamLength = byteBuffer.getInt();
		for(int i = 0; i < teamLength; i++) {
			studentName += byteBuffer.get();
		}
	}
}
