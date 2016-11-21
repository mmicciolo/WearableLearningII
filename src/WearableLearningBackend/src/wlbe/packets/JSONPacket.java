package wlbe.packets;

import java.nio.ByteBuffer;

import com.google.gson.Gson;

import wlbe.model.ClientData;
import wlbe.packet.Packet;
import wlbe.packet.PacketTypes;

public class JSONPacket extends Packet {
	
	private Gson gson;
	private String gsonString = "";
	
	public JSONPacket(ByteBuffer buffer, ClientData clientData) {
		super(buffer, PacketTypes.PacketType.JSON_PACKET, clientData);
		populatePacket();
	}
	
	public void populatePacket() {
		int byteCount = byteBuffer.getInt();
		gson = new Gson();
		gsonString = "";
		for(int i = 0; i < byteCount; i++) {
			gsonString += (char)byteBuffer.get();
		}
	}
	
	public Gson getGson() {
		return this.gson;
	}
	
	public String getGsonString() {
		return this.gsonString;
	}
}
