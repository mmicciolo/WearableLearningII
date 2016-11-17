package wlbe.tasks;

import wlbe.module.ModuleManager;
import wlbe.modules.TaskManager;
import wlbe.task.Task;

public class GameInstance extends Task {
	
	private TaskManager taskManager;
	private MySQLDaemon mySQLDaemon;
	
	public GameInstance() {
		mySQLDaemon = new MySQLDaemon();
		taskManager = (TaskManager) ModuleManager.getModule(ModuleManager.Modules.TASK_MANAGER);
		taskManager.addTask(mySQLDaemon);
	}
	
	public void update() {
		
	}
	
	public void cleanup() {
		taskManager.removeTask(mySQLDaemon);
	}
}
