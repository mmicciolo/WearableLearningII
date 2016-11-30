package wl.shared.json.packet;

import java.nio.ByteBuffer;

public interface IJSONPacket {
	
	ByteBuffer assemblePacket();
	
	JSONPacket disassemblePacket(ByteBuffer buffer);
	
	String getGson();
	
	JSONPacketTypes getType();
}
