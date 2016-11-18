package wlbe.tasks;

import java.sql.Connection;
import java.sql.DriverManager;

import wlbe.module.ModuleManager;
import wlbe.modules.Settings;
import wlbe.task.Task;

public class MySQLDaemon extends Task {
	
	private Settings settings;
	private Connection connection = null;
	
	public MySQLDaemon() {
		Connect();
	}
	
	public void update() {
		
	}
	
	public void cleanup() {
		Disconnect();
	}
	
	private boolean Connect() {
		if(connection == null) {
			try {
				settings = (Settings) ModuleManager.getModule(ModuleManager.Modules.SETTINGS);
				Class.forName(settings.JDBC_DRIVER);
				connection = DriverManager.getConnection(settings.DATABASE_URL, settings.USERNAME, settings.PASSWORD);
				if(connection != null) {
					return true;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		} else {
			return true;
		}	
	}
	
	private boolean Disconnect() {
		if(connection != null) {
			try {
				connection.close();
				connection = null;
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public Connection getConnection() {
		return this.connection;
	}
}
