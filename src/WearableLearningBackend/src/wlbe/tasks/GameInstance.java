package wlbe.tasks;

import java.util.ArrayList;

import wlbe.model.Player;
import wlbe.module.ModuleManager;
import wlbe.modules.TaskManager;
import wlbe.task.Task;

public class GameInstance extends Task {
	
	private TaskManager taskManager;
	private MySQLDaemon mySQLDaemon;
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public GameInstance() {
		mySQLDaemon = new MySQLDaemon();
		taskManager = (TaskManager) ModuleManager.getModule(ModuleManager.Modules.TASK_MANAGER);
		taskManager.addTask(mySQLDaemon);
	}
	
	public void update() {
		for(Player player : players) {
			
		}
	}
	
	public void cleanup() {
		taskManager.removeTask(mySQLDaemon);
	}
}
