package wlbe.packet;

import java.nio.ByteBuffer;

import wlbe.packets.ConnectPacket;
import wlbe.packets.EchoPacket;

public class PacketTypes {
	
	public enum PacketType {
		ECHO,
		PLAYER_CONNECT,
		PLAYER_DISCONNECT,
		NEW_GAME_INSTANCE
	};
	
	public static Packet getPacketFromBuffer(ByteBuffer buffer) {
		Packet packet = null;
		PacketType packetType = PacketType.values()[buffer.getInt()];
		switch(packetType) {
			case ECHO:
				packet = new EchoPacket(buffer);
				break;
			case PLAYER_CONNECT:
				packet = new ConnectPacket(buffer);
				break;
			case PLAYER_DISCONNECT:
				break;
			case NEW_GAME_INSTANCE:
				break;
			default:
				break;
		}
		return packet;
	}	
}
