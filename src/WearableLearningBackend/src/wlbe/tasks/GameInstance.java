package wlbe.tasks;

import java.io.IOException;
import java.util.ArrayList;

import wlbe.event.Event;
import wlbe.event.IEvent;
import wlbe.events.PacketRecieved;
import wlbe.model.ClientData;
import wlbe.model.PlayerData;
import wlbe.module.ModuleManager;
import wlbe.modules.Server;
import wlbe.modules.TaskManager;
import wlbe.packets.ConnectPacket;
import wlbe.packets.DisconnectPacket;
import wlbe.task.Task;

public class GameInstance extends Task {
	
	private int gameInstanceId;
	private int gameId;
	private TaskManager taskManager;
	private MySQLDaemon mySQLDaemon;
	private ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	
	public GameInstance(int gameInstanceId, int gameId) {
		this.gameInstanceId = gameInstanceId;
		this.gameId = gameId;
		mySQLDaemon = new MySQLDaemon();
		taskManager = (TaskManager) ModuleManager.getModule(ModuleManager.Modules.TASK_MANAGER);
		taskManager.addTask(mySQLDaemon);
	}
	
	public void update() {
		for(PlayerData player : players) {
			
		}
	}
	
	public void cleanup() {
		taskManager.removeTask(mySQLDaemon);
	}
	
	public void eventHandler(IEvent e) {
		if(e instanceof PacketRecieved) {
			PacketRecieved packetRecieved = (PacketRecieved) e;
			switch(packetRecieved.getPacket().getType()) {
				case PLAYER_CONNECT:
					playerConnect((ConnectPacket)packetRecieved.getPacket());
					break;
				case PLAYER_DISCONNECT:
					playerDisconnect((DisconnectPacket)packetRecieved.getPacket());
					break;
				default:
					break;
			}
		}
	}
	
	public void playerConnect(ConnectPacket packet) {
		if(packet.getGameInstanceId() == gameInstanceId) {
			players.add(new PlayerData(packet.getStudentName(), packet.getClientData()));
		}
	}
	
	public void playerDisconnect(DisconnectPacket packet) {
		if(packet.getGameInstanceId() == gameId) {
			for(PlayerData player : players) {
				if(player.getPlayerName().equals(packet.getStudentName())) {
					try {
						player.getClientData().getClientSocket().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					players.remove(player);
					break;
				}
			}
		}
	}
	
	public int getGameId() {
		return this.gameId;
	}
}
