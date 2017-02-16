package wl.shared.json.packets;

import java.nio.ByteBuffer;

import wl.shared.json.packet.IJSONPacket;
import wl.shared.json.packet.JSONPacket;
import wl.shared.json.packet.JSONPacketTypes;

public class GameStartPacket extends JSONPacket implements IJSONPacket {
	
	public GameStartPacket() {
		jsonPacketType = JSONPacketTypes.GAME_START;
	}
	
	public ByteBuffer assemblePacket() {
		super.assemblePacket();
		return buffer;
	}
	
	public String getGson() {
		return "";
	}
}
