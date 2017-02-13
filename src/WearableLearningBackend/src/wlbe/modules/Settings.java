package wlbe.modules;

import wlbe.module.Module;
import wlbe.module.ModuleManager.Modules;

public class Settings extends Module {
	
	public final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//	public final String DATABASE_URL = "jdbc:mysql://mmicciolo.tk/wearablelearning?useSSL=false";
	public final String DATABASE_URL = "jdbc:mysql://localhost/wearablelearning?useSSL=false";
	public final String USERNAME = "wlfe";
	public final String PASSWORD = "matthew";
	
	private boolean useConfig = false;
	private String configName = "";
	private boolean verbose = false;
	private int portNumber = 3333;
	
	public Settings() {
		
	}
	
	public Settings(Modules moduleId) {
		this.moduleId = moduleId;
		setup();
	}
	
	public void setup() {
		
	}
	
	public void cleanup() {
		
	}
	
	public void update() {
		
	}
}
