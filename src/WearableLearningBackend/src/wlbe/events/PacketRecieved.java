package wlbe.events;

import wlbe.event.Event;
import wlbe.packet.IPacket;

public class PacketRecieved extends Event {
	
	private IPacket packet;
	
	public PacketRecieved(IPacket packet) {
		this.packet = packet;
	}
	
	public IPacket getPacket() {
		return this.packet;
	}
}
