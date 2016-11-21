package wlbe.packets;

import java.nio.ByteBuffer;

import wlbe.model.ClientData;
import wlbe.packet.Packet;
import wlbe.packet.PacketTypes;

public class ConnectPacket extends Packet {
	
	private String studentName;
	private String selectedTeam;
	private int gameInstanceId;
	
	public ConnectPacket(ByteBuffer buffer, ClientData clientData) {
		super(buffer, PacketTypes.PacketType.PLAYER_CONNECT, clientData);
		populatePacket();
	}
	
	public void populatePacket() {	
		studentName = getString();
		selectedTeam = getString();
		gameInstanceId = byteBuffer.getInt();	
	}
	
	public String getStudentName() {
		return this.studentName;
	}
	
	public String getSelectedTeam() {
		return this.selectedTeam;
	}
	
	public int getGameInstanceId() {
		return this.gameInstanceId;
	}
}
