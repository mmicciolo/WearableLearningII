package wlbe.tasks;

import java.util.ArrayList;

import wl.shared.model.Button;
import wlbe.event.IEvent;
import wlbe.events.PacketRecieved;
import wlbe.model.PlayerData;
import wlbe.module.ModuleManager;
import wlbe.modules.Logger;
import wlbe.modules.TaskManager;
import wlbe.packets.ConnectPacket;
import wlbe.packets.DisconnectPacket;
import wlbe.packets.JSONPacket;
import wlbe.task.Task;

public class GameInstance extends Task {
	
	private int gameInstanceId;
	private int gameId;
	private TaskManager taskManager;
	private MySQLDaemon mySQLDaemon;
	private ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	Logger logger;
	
	public GameInstance(int gameInstanceId, int gameId) {
		this.gameInstanceId = gameInstanceId;
		this.gameId = gameId;
		mySQLDaemon = new MySQLDaemon();
		taskManager = (TaskManager) ModuleManager.getModule(ModuleManager.Modules.TASK_MANAGER);
		taskManager.addTask(mySQLDaemon);
		logger = (Logger) ModuleManager.getModule(ModuleManager.Modules.LOGGER);
	}
	
	public void update() {

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
				case JSON_PACKET:
					handleJSONPacket((JSONPacket)packetRecieved.getPacket());
					break;
				default:
					break;
			}
		}
	}
	
	public void playerConnect(ConnectPacket packet) {
		if(packet.getGameInstanceId() == gameInstanceId) {
			for(PlayerData player : players) {
				if(player.getPlayerName().equals(packet.getStudentName())) {
					logger.write("Client Reconnected...");
					return;
				}
			}
			players.add(new PlayerData(packet.getStudentName(), packet.getClientData()));
		}
	}
	
	public void playerDisconnect(DisconnectPacket packet) {
		if(packet.getGameInstanceId() == gameInstanceId) {
			for(int i = 0; i < players.size(); i++) {
				PlayerData player = (PlayerData) players.toArray()[i];
				if(player.getPlayerName().equals(packet.getStudentName())) {
					players.remove(player);
					Logger logger = (Logger) ModuleManager.getModule(ModuleManager.Modules.LOGGER);
					logger.write("Client Disconnected...");
				}
			}
		}
	}
	
	public void handleJSONPacket(JSONPacket packet) {
		Button button = packet.getGson().fromJson(packet.getGsonString(), Button.class);
		button.toString();
	}
	
	public int getGameId() {
		return this.gameId;
	}
}
