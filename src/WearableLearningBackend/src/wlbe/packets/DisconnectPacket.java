package wlbe.packets;

import java.nio.ByteBuffer;

import wlbe.model.ClientData;
import wlbe.packet.Packet;
import wlbe.packet.PacketTypes;

public class DisconnectPacket extends Packet {
	
	private int gameInstanceId;
	private String studentName;
	
	public DisconnectPacket(ByteBuffer buffer, ClientData clientData) {
		super(buffer, PacketTypes.PacketType.PLAYER_DISCONNECT, clientData);
		populatePacket();
	}
	
	public void populatePacket() {
		 gameInstanceId = byteBuffer.getInt();
		 studentName = getString();
	}
	
	public String getStudentName() {
		return this.studentName;
	}
	
	public int getGameInstanceId() {
		return this.gameInstanceId;
	}
}
