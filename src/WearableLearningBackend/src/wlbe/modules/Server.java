package wlbe.modules;

import wlbe.ServerTime;
import wlbe.Settings;
import wlbe.module.Module;
import wlbe.module.ModuleManager;
import wlbe.module.ModuleManager.Modules;

public class Server extends Module {
	
	protected ServerTime serverTime;
	private Logger logger;
	private Settings serverSettings;
	private String[] args;
	
	public Server(Modules moduleId, String[] args) {
		super(moduleId);
		this.args = args;
	}
	
	public void setup() {
		logger = (Logger) ModuleManager.getModule(ModuleManager.Modules.LOGGER);
		logger.write("Starting Server...");
		setupServerSettings();
		setupServerTime();
		logger.write("Server Started...");
	}
	
	
	public void update() {
		
	}
	
	public void cleanup() {
		
	}
	
	public void setupServerSettings() {
		serverSettings = new Settings();
		for(String s : args) {
			switch(s) {
				case "-c":
					break;
				case "-v":
					break;
				default:
					break;
			}
		}
	}
	
	public void setupServerTime() {
		logger.write("Setting up Server Time...");
		serverTime = new ServerTime();
	}
	
	public ServerTime getServerTime() {
		return serverTime;
	}
	
	public Settings getServerSettings() {
		return this.serverSettings;
	}
}
