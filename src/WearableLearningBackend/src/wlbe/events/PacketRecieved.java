package wlbe.events;

import wlbe.event.Event;
import wlbe.packet.Packet;

public class PacketRecieved extends Event {
	
	private Packet packet;
	
	public PacketRecieved(Packet packet) {
		this.packet = packet;
	}
	
	public Packet getPacket() {
		return this.packet;
	}
}
