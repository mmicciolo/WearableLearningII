package wlbe.packet;

import java.nio.ByteBuffer;

import wlbe.packet.PacketTypes.PacketType;

public abstract class Packet implements IPacket {
	
	protected ByteBuffer byteBuffer;
	protected PacketType packetType;

	public Packet(ByteBuffer buffer) {
		this.byteBuffer = buffer;
		this.byteBuffer.flip();
		packetType = PacketType.values()[buffer.getInt()];
		PopulateData();
	}
	
	public void PopulateData() {
		
	}
	
	public PacketType getType() {
		return this.packetType;
	}
}
