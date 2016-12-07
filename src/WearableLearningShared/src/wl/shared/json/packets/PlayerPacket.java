package wl.shared.json.packets;

import java.nio.ByteBuffer;

import wl.shared.json.packet.IJSONPacket;
import wl.shared.json.packet.JSONPacket;
import wl.shared.json.packet.JSONPacketTypes;
import wl.shared.json.packets.data.PlayerPacketData;

public class PlayerPacket extends JSONPacket implements IJSONPacket {
	
	private PlayerPacketData data = new PlayerPacketData();
	
	public PlayerPacket() {
		this.jsonPacketType = JSONPacketTypes.PLAYER_DATA;
	}
	
	public ByteBuffer assemblePacket() {
		super.assemblePacket();
		String out = gson.toJson(data);
		this.putString(out, buffer);
		return buffer;
	}
	
	public String getGson() {
		return gson.toJson(data);
	}
	
	public void setPlayerData(PlayerPacketData data) {
		this.data = data;
	}
	
	public PlayerPacketData getPlayerData() {
		return this.data;
	}
}
