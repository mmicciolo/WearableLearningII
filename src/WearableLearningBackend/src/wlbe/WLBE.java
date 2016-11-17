package wlbe;

import wlbe.module.ModuleManager;
import wlbe.modules.Logger;
import wlbe.modules.Server;

public class WLBE {
	
	private static boolean running = true;
	
	public static void main(String[] args) {
		
		ModuleManager moduleManager = ModuleManager.getInstance();
		
		createLogger(moduleManager);
		createServer(moduleManager, args);
	
		
		while(running) {
			moduleManager.update();
		}
		
		moduleManager.cleanup();
	}
	
	public static void createLogger(ModuleManager moduleManager) {
		Logger logger = new Logger(ModuleManager.Modules.LOGGER);
		moduleManager.addModule(logger);
		
		logger.write("Starting Module Manager...");
		logger.write("Module Manager Started...");
		logger.write("Starting Logger...");
		logger.write("Logger Started...");
	}
	
	public static void createServer(ModuleManager moduleManager, String[] args) {
		Server server = new Server(ModuleManager.Modules.SERVER, args);
		moduleManager.addModule(server);
	}
}
