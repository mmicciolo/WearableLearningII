package wl.shared.json.packets;

import java.nio.ByteBuffer;

import wl.shared.json.packet.IJSONPacket;
import wl.shared.json.packet.JSONPacket;
import wl.shared.json.packet.JSONPacketTypes;
import wl.shared.json.packets.data.GameStatePacketData;

public class GameStatePacket extends JSONPacket implements IJSONPacket {
	
	private GameStatePacketData gameStatePacketData = new GameStatePacketData();
	
	public GameStatePacket() {
		jsonPacketType = JSONPacketTypes.GAME_STATE;
	}
	
	public ByteBuffer assemblePacket() {
		super.assemblePacket();
		String out = gson.toJson(gameStatePacketData);
		this.putString(out, buffer);
		return buffer;
	}
	
	public String getGson() {
		return gson.toJson(gameStatePacketData);
	}
	
	public void setData(GameStatePacketData gameStatePacketData) {
		this.gameStatePacketData = gameStatePacketData;
	}
	
	public GameStatePacketData getGameStatePacketData() {
		return this.gameStatePacketData;
	}
}
