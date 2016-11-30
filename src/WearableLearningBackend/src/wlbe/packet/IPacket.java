package wlbe.packet;

import wlbe.packet.PacketTypes.PacketType;

/**
 * Interface for a packet. All packets must
 * implement these methods.
 * @author Matthew Micciolo
 *
 */
public interface IPacket {
	
	/**
	 * This method should be called from the constructor
	 * after super(). It is used to translate the ByteBuffer
	 * data to actual packet class data.
	 */
	void populateData();
	
	/**
	 * Returns the type of packet based off of
	 * PacketTypes.PacketType
	 * @return PacketTypes.PacketType
	 */
	PacketType getType();

}
