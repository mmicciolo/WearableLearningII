package wlbe.packets;

import java.nio.ByteBuffer;

import com.google.gson.Gson;

import wl.shared.json.packet.IJSONPacket;
import wl.shared.json.packet.JSONPacketTypes;
import wl.shared.json.packets.ButtonPacket;
import wl.shared.json.packets.DisplayPacket;
import wl.shared.json.packets.data.ButtonData;
import wlbe.model.ClientData;
import wlbe.packet.Packet;
import wlbe.packet.PacketTypes;

public class JSONPacket extends Packet {
	
	private Gson gson = new Gson();
	private IJSONPacket packet;
	
	public JSONPacket() {
		this.packetType = PacketTypes.PacketType.JSON_PACKET;
	}
	
	public JSONPacket(ByteBuffer buffer, ClientData clientData) {
		super(buffer, PacketTypes.PacketType.JSON_PACKET, clientData);
		populatePacket();
	}
	
	public void populatePacket() {
		switch(JSONPacketTypes.values()[byteBuffer.getInt()]) {
			case BUTTON:
				String gsonStringButton = getString();
				ButtonPacket buttonPacket = new ButtonPacket();
				buttonPacket.setButtonData(gson.fromJson(gsonStringButton, ButtonData.class));
				packet = buttonPacket;
				break;
			case DISPLAY:
				break;
			default:
				break;
		}
	}
	
	public ByteBuffer assemblePacket() {
		super.assemblePacket();
		byteBuffer.putInt(packet.getType().ordinal());
		setString(packet.getGson());
		return byteBuffer;
	}
	
	public void setJSONPacket(IJSONPacket packet) {
		this.packet = packet;
	}
	
	public IJSONPacket getJSONPacket() {
		return this.packet;
	}
	
	public Gson getGson() {
		return this.gson;
	}
}
