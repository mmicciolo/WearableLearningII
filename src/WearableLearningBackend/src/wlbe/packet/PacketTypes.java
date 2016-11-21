package wlbe.packet;

import java.nio.ByteBuffer;

import wlbe.model.ClientData;
import wlbe.packets.ConnectPacket;
import wlbe.packets.DisconnectPacket;
import wlbe.packets.EchoPacket;
import wlbe.packets.JSONPacket;

/**
 * Contains an enumeration of the different types of packets
 * as well as a mean of converting from a ByteBuffer to these
 * different types of packets
 * @author Matthew Micciolo
 *
 */
public class PacketTypes {
	
	/**
	 * Enumeration of the type of packet. Add different packets here as
	 * well as in their respective spots to add more types.
	 * @author Matthew Micciolo
	 *
	 */
	public enum PacketType {
		ECHO,
		PLAYER_CONNECT,
		PLAYER_DISCONNECT,
		JSON_PACKET
	};
	
	/**
	 * The first int of every packet is a packetType. This method pulls
	 * that first int off and then switches based on packetType based off of this
	 * int. It then creates the according packet class.
	 * @param buffer ByteBuffer input from socket
	 * @return Packet class
	 */
	public static IPacket getPacketFromBuffer(ByteBuffer buffer, ClientData clientData) {
		Packet packet = null;
		PacketType packetType = PacketType.values()[buffer.getInt()];
		switch(packetType) {
			case ECHO:
				packet = new EchoPacket(buffer);
				break;
			case PLAYER_CONNECT:
				packet = new ConnectPacket(buffer, clientData);
				break;
			case PLAYER_DISCONNECT:
				packet = new DisconnectPacket(buffer, clientData);
				break;
			case JSON_PACKET:
				packet = new JSONPacket(buffer, clientData);
				break;
			default:
				break;
		}
		return packet;
	}	
}
