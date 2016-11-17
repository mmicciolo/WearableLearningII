package wlbe.tasks;

import java.util.ArrayList;

import wlbe.event.Event;
import wlbe.events.PacketRecieved;
import wlbe.model.PlayerData;
import wlbe.module.ModuleManager;
import wlbe.modules.TaskManager;
import wlbe.task.Task;

public class GameInstance extends Task {
	
	private TaskManager taskManager;
	private MySQLDaemon mySQLDaemon;
	private ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	
	public GameInstance() {
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
	
	public void eventHandler(Event e) {
		if(e instanceof PacketRecieved) {
			PacketRecieved packetRecieved = (PacketRecieved) e;
			switch(packetRecieved.getPacket().getType()) {
				case PLAYER_CONNECT:
					break;
				case PLAYER_DISCONNECT:
					break;
				default:
					break;
			}
		}
	}
}
