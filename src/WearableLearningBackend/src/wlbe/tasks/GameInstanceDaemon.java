package wlbe.tasks;

import java.sql.PreparedStatement;
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
			while(resultSet.next()) {
				TaskManager taskManager = (TaskManager) ModuleManager.getModule(ModuleManager.Modules.TASK_MANAGER);
				for(Task t : taskManager.getTasks()) {
					if(t instanceof GameInstance) {
						GameInstance gameInstance = (GameInstance) t;
						if(resultSet.getInt("gameId") != gameInstance.getGameId()) {
							GameInstance newGameInstance = new GameInstance(resultSet.getInt("gameId") );
							taskManager.addTask(newGameInstance);
						}
					} else {
						GameInstance newGameInstance = new GameInstance(resultSet.getInt("gameId") );
						taskManager.addTask(newGameInstance);
					}
				}
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
