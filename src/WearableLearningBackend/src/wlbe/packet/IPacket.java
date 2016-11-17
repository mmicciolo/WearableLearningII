package wlbe.packet;

import wlbe.packet.PacketTypes.PacketType;

/**
 * Interface for a packet. All packets must
 * implement these methods.
 * @author Matthew Micciolo
 *
 */
public interface IPacket {
	
	void populateData();
	
	PacketType getType();

}
