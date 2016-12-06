package wl.shared.json.packets;

import java.nio.ByteBuffer;

import wl.shared.json.packet.IJSONPacket;
import wl.shared.json.packet.JSONPacket;
import wl.shared.json.packet.JSONPacketTypes;
import wl.shared.json.packets.data.DisplayData;

public class DisplayPacket extends JSONPacket implements IJSONPacket {
	
	private DisplayData data = new DisplayData();
	
	public DisplayPacket() {
		jsonPacketType = JSONPacketTypes.DISPLAY;
	}
	
	public DisplayPacket(String text) {
		jsonPacketType = JSONPacketTypes.DISPLAY;
		this.data.text = text;
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
	
	public void setText(String text) {
		this.data.text = text;
	}
	
	public void setData(DisplayData data) {
		this.data = data;
	}
	
	public String getText() {
		return this.data.text;
	}
	
	public DisplayData getDisplayData() {
		return this.data;
	}
}
