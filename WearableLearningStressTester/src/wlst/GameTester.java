package wlst;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import wl.shared.json.packets.ButtonPacket;
import wl.shared.json.packets.data.ButtonData;

public class GameTester extends Thread {
	
	private boolean running = true;
	private List<PlayerInfo> playerInfo;
	
	public GameTester(List<PlayerInfo> playerInfo) {
		this.playerInfo = playerInfo;
	}
	
	public void run() {
		while(running) {
			for(PlayerInfo player : playerInfo) {
				ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
				byteBuffer.putInt(3);
				ButtonPacket buttonPacket = new ButtonPacket();
				ButtonData data = new ButtonData(player.getBackendServer().getPlayerData().getPlayerId(), ThreadLocalRandom.current().nextInt(0, 3 + 1));
				buttonPacket.setButtonData(data);
				byteBuffer.putInt(0);
				player.getBackendServer().putString(buttonPacket.getGson(), byteBuffer);
				player.getBackendServer().write(byteBuffer);
			}
			try {
				//Thread.sleep(200);
				Thread.sleep(ThreadLocalRandom.current().nextInt(50, 101));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	public void quit() {
		running = false;
	}
}
