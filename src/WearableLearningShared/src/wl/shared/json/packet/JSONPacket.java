package wl.shared.json.packet;

import java.nio.ByteBuffer;

import com.google.gson.Gson;

import wl.shared.json.packets.DisplayData;
import wl.shared.json.packets.DisplayPacket;
import wl.shared.json.packets.GameStartPacket;

public class JSONPacket implements IJSONPacket {
	
	protected JSONPacketTypes jsonPacketType;
	protected Gson gson = new Gson();
	protected ByteBuffer buffer;
	
	public JSONPacket() {
		
	}
	
	public ByteBuffer assemblePacket() {
		buffer = ByteBuffer.allocate(65536);
		buffer.putInt(jsonPacketType.ordinal());
		return buffer;
	}
	
	public JSONPacket disassemblePacket(ByteBuffer buffer) {
		JSONPacketTypes packetType = JSONPacketTypes.values()[buffer.getInt()];
		JSONPacket returnPacket = null;
		switch(packetType) {
			case GAME_START:
				GameStartPacket gameStartPacket = new GameStartPacket();
				returnPacket = gameStartPacket;
				break;
			case DISPLAY:
				DisplayPacket displayPacket = new DisplayPacket();
				String s = getString(buffer);
				DisplayData data = gson.fromJson(s, DisplayData.class);
				displayPacket.setData(data);
				returnPacket = displayPacket;
				break;
			default:
				break;
		}
		return returnPacket;
	}
	
	public String getGson() {
		return gson.toJson(this);
	}
	
	protected void putString(String s, ByteBuffer buffer) {
		buffer.putInt(s.length());
		for(char c : s.toCharArray()) {
			buffer.putChar(c);
		}
	}
	
	protected String getString(ByteBuffer buffer) {
		String returnString = "";
		int count = buffer.getInt();
		for(int i = 0; i < count; i++) {
			returnString += buffer.getChar();
		}
		return returnString;
	}
	
	public JSONPacket(JSONPacketTypes jsonPacketType) {
		this.jsonPacketType = jsonPacketType;
	}
	
	public void setJSONPacketType(JSONPacketTypes jsonPacketType) {
		this.jsonPacketType = jsonPacketType;
	}
	
	public JSONPacketTypes getJSONPacketType() {
		return this.jsonPacketType;
	}

	public JSONPacketTypes getType() {
		return this.jsonPacketType;
	}
}
