package wlbe.packet;

import java.nio.ByteBuffer;

import wlbe.model.ClientData;
import wlbe.packet.PacketTypes.PacketType;

/**
 * Abstract class for packet.
 * @author Matthew Micciolo
 *
 */
public abstract class Packet implements IPacket {
	
	protected ByteBuffer byteBuffer;
	protected PacketType packetType;
	protected ClientData clientData;

	/**
	 * Constructor for packet. Every
	 * packet must be given a ByteBuffer.
	 * @param buffer input ByteBuffer
	 */
	public Packet(ByteBuffer buffer, PacketType packetType, ClientData clientData) {
		this.byteBuffer = buffer;
		this.packetType = packetType;
		this.clientData = clientData;
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
	
	/**
	 * Returns the client data associated with this packet.
	 * @return returns client data associated with packet
	 */
	public ClientData getClientData() {
		return this.clientData;
	}
	
	/**
	 * Returns a string from a char count and a char buffer
	 * @param buffer char buffer
	 * @return string from char buffer
	 */
	public String getString() {
		String returnString = "";
		int charCount = byteBuffer.getInt();
		for(int i = 0; i < charCount; i++) {
			returnString += (char) byteBuffer.get();
		}
		return returnString;
	}
}
