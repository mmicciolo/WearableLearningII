package wlbe;

import wlbe.module.ModuleManager;
import wlbe.modules.Logger;
import wlbe.modules.Server;
import wlbe.modules.Settings;
import wlbe.modules.TaskManager;

public class WLBE {
	
	private static boolean running = true;
	
	public static void main(String[] args) {
		
		ModuleManager moduleManager = ModuleManager.getInstance();
		
		createLogger(moduleManager);
		createServer(moduleManager, args);
		createTaskManager(moduleManager);
	
		
		while(running) {
			moduleManager.update();
		}
		
		moduleManager.cleanup();
	}
	
	public static void createLogger(ModuleManager moduleManager) {
		Logger logger = new Logger(ModuleManager.Modules.LOGGER);
		logger.write("Starting Module Manager...");
		logger.write("Module Manager Started...");
		moduleManager.addModule(logger);
	}
	
	public static void createServer(ModuleManager moduleManager, String[] args) {
		Server server = new Server(ModuleManager.Modules.SERVER, args);
		moduleManager.addModule(server);
		Settings settings = new Settings(ModuleManager.Modules.SETTINGS);
		moduleManager.addModule(settings);
	}
	
	public static void createTaskManager(ModuleManager moduleManager) {
		TaskManager taskManager = new TaskManager(ModuleManager.Modules.TASK_MANAGER);
		moduleManager.addModule(taskManager);
	}
}
