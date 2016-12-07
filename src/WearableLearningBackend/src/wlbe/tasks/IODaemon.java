package wlbe.tasks;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

import wlbe.model.ClientData;
import wlbe.packet.IPacket;
import wlbe.task.Task;

public class IODaemon extends Task {
	
	private Queue<IOPacket> packets = new LinkedList<IOPacket>();
	
	public IODaemon() {
		setName("IODaemon");
	}
	
	public void update() {
		if(packets.size() > 0) {
			IOPacket packet = packets.remove();
			ByteBuffer buffer = packet.packet.assemblePacket();
			buffer.flip();
			packet.clientData.getClientSocket().write(buffer);
		}
	}
	
	public void cleanup() {
		
	}
	
	public void sendPacket(IPacket packet, ClientData clientData) {
		packets.add(new IOPacket(packet, clientData));
	}
}

class IOPacket {
	IPacket packet;
	ClientData clientData;
	
	public IOPacket(IPacket packet, ClientData clientData) {
		this.packet = packet;
		this.clientData = clientData;
	}
}
