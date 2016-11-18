package wlbe.tasks;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import wlbe.event.Event;
import wlbe.module.ModuleManager;
import wlbe.modules.TaskManager;
import wlbe.task.Task;

public class GameInstanceDaemon extends Task {
	
	MySQLDaemon mysqlDaemon;
	
	public GameInstanceDaemon() {
		mysqlDaemon = new MySQLDaemon();
	}
	
	public void update() {
		mysqlPoll();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cleanup() {
		mysqlDaemon.cleanup();
	}
	
	public void eventHandler(Event e) {
		
	}
	
	private void mysqlPoll() {
		try {
			Statement statement = mysqlDaemon.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM gameInstance");
			boolean gameInstanceFound = false;
			while(resultSet.next()) {
				TaskManager taskManager = (TaskManager) ModuleManager.getModule(ModuleManager.Modules.TASK_MANAGER);
				for(int i = 0; i < taskManager.getTasks().size(); i++) {
					if(taskManager.getTasks().toArray()[i] instanceof GameInstance) {
						GameInstance gameInstance = (GameInstance) taskManager.getTasks().toArray()[i];
						if(resultSet.getInt("gameId") != gameInstance.getGameId()) {
							GameInstance newGameInstance = new GameInstance(resultSet.getInt("gameId") );
							taskManager.addTask(newGameInstance);
						}
					}
				}
				if(!gameInstanceFound) {
					GameInstance newGameInstance = new GameInstance(resultSet.getInt("gameId") );
					taskManager.addTask(newGameInstance);
				}
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
