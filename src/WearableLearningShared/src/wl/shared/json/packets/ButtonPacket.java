package wl.shared.json.packets;

import java.nio.ByteBuffer;

import wl.shared.json.packet.IJSONPacket;
import wl.shared.json.packet.JSONPacket;
import wl.shared.json.packet.JSONPacketTypes;
import wl.shared.json.packets.data.ButtonData;

public class ButtonPacket extends JSONPacket implements IJSONPacket {
	
	ButtonData data = new ButtonData();
	
	public ButtonPacket() {
		this.jsonPacketType = JSONPacketTypes.BUTTON;
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
	
	public void setButtonData(ButtonData data) {
		this.data = data;
	}
	
	public ButtonData getButtonData() {
		return this.data;
	}
}
