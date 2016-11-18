package wlbe.packet;

import java.nio.ByteBuffer;

import wlbe.packet.PacketTypes.PacketType;

/**
 * Abstract class for packet.
 * @author Matthew Micciolo
 *
 */
public abstract class Packet implements IPacket {
	
	protected ByteBuffer byteBuffer;
	protected PacketType packetType;

	/**
	 * Constructor for packet. Every
	 * packet must be given a ByteBuffer.
	 * @param buffer input ByteBuffer
	 */
	public Packet(ByteBuffer buffer, PacketType packetType) {
		this.byteBuffer = buffer;
		this.packetType = packetType;
		populateData();
	}
	
	/**
	 * This method should be called from the constructor
	 * after super(). It is used to translate the ByteBuffer
	 * data to actual packet class data.
	 */
	public void populateData() {
		
	}
	
	/**
	 * Returns the type of packet based off of
	 * PacketTypes.PacketType
	 * @return PacketTypes.PacketType
	 */
	public PacketType getType() {
		return this.packetType;
	}
}
