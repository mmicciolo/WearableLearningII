package wlbe;

import wlbe.module.ModuleManager;
import wlbe.modules.EventManager;
import wlbe.modules.Logger;
import wlbe.modules.Server;
import wlbe.modules.Settings;
import wlbe.modules.TaskManager;
import wlbe.tasks.GameInstanceDaemon;
import wlbe.tasks.IODaemon;

/**
 * Wearable Learning Back End Server
 * This server is used to server game instances
 * of wearable learning games to wearable learning
 * watch or phone devices.
 * 
 * @author Matthew Micciolo
 * Worcester Polytechnic Institute
 * Major Qualifying Project
 * Physical Games for Learning II
 *
 */
public class WLBE {
	
	private static boolean running = true;
	
	/**
	 * Program enters here.
	 * Setups module manager
	 * Setups modules
	 * Runs until exit
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		ModuleManager moduleManager = ModuleManager.getInstance();
		
		createLogger(moduleManager);
		createServer(moduleManager, args);
		createTaskManager(moduleManager);
		createEventManager(moduleManager);
		createGameInstanceDaemon();
		createIODaemon();
		
		while(running) {
			moduleManager.update();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}
	}
	
	/**
	 * Creates a logger module and adds it to the module manager.
	 * @param moduleManager manager to add the module to
	 */
	public static void createLogger(ModuleManager moduleManager) {
		Logger logger = new Logger(ModuleManager.Modules.LOGGER);
		logger.write("Starting Module Manager...");
		logger.write("Module Manager Started...");
		moduleManager.addModule(logger);
	}
	
	/**
	 * Creates a server module and settings module.
	 * @param moduleManager manager to add the module to
	 * @param args command line args
	 */
	public static void createServer(ModuleManager moduleManager, String[] args) {
		Server server = new Server(ModuleManager.Modules.SERVER, args);
		moduleManager.addModule(server);
		Settings settings = new Settings(ModuleManager.Modules.SETTINGS);
		moduleManager.addModule(settings);
	}
	
	/**
	 * Creates a task manager module and adds it to the module manager.
	 * @param moduleManager manager to add the module to
	 */
	public static void createTaskManager(ModuleManager moduleManager) {
		TaskManager taskManager = new TaskManager(ModuleManager.Modules.TASK_MANAGER);
		moduleManager.addModule(taskManager);
	}
	
	/**
	 * Creates an event manager module and adds it to the module manager.
	 * @param moduleManagermanager to add the module to
	 */
	public static void createEventManager(ModuleManager moduleManager) {
		EventManager eventManager = new EventManager(ModuleManager.Modules.EVENT_MANAGER);
		moduleManager.addModule(eventManager);
	}
	
	public static void createGameInstanceDaemon() {
		TaskManager taskManager = (TaskManager) ModuleManager.getModule(ModuleManager.Modules.TASK_MANAGER);
		GameInstanceDaemon gameInstanceDaemon = new GameInstanceDaemon();
		taskManager.addTask(gameInstanceDaemon);	
	}
	
	public static void createIODaemon() {
		TaskManager taskManager = (TaskManager) ModuleManager.getModule(ModuleManager.Modules.TASK_MANAGER);
		IODaemon ioDaemon = new IODaemon();
		taskManager.addTask(ioDaemon);	
	}
}
