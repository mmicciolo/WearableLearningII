package wlbe.module;

import java.util.ArrayList;

import wlbe.modules.Logger;

/**
 * This class takes care of managing all of the modules added to it.
 * This class is a singleton so it can be accessed anywhere meaning that
 * you can add modules any where and any time you want without needing to have a 
 * reference passed in from somewhere else.
 * @author Matt
 *
 */
public class ModuleManager {
	
	private static ModuleManager instance = null;
	private static ArrayList<Module> modules = new ArrayList<Module>();
	
	/**
	 * Enumeration of avaliable modules
	 * @author Matt
	 *
	 */
	public static enum Modules {
		LOGGER,
		SERVER,
		SETTINGS,
		TASK_MANAGER,
		EVENT_MANAGER
	};
	
	/**
	 * Constructor. Set the shutdownhook, i.e call clean
	 * on thread shutdown.
	 */
	protected ModuleManager() {
		setShutdownHook();
	}
	
	/**
	 * Get the module manager singleton instance
	 * @return ModuleManager singleton instance
	 */
	public static ModuleManager getInstance() {
		if(instance == null) {
			instance = new ModuleManager();
		}
		return instance;
	}
	
	/**
	 * Gets a module from the list specified by the Modules
	 * enumeration.
	 * @param module modules enumeration
	 * @return Module
	 */
	public static Module getModule(Modules module) {
		for(Module m : modules) {
			if(m.getModuleId().equals(module)) {
				return m;
			}
		}
		return null;
	}
	
	/**
	 * Update all of modules in the list by calling
	 * all of their updates in a for loop.
	 */
	public void update() {
		for(Module m : modules) {
			m.update();
		}
	}
	
	/**
	 * Set the shutdownhook, i.e call clean on thread shutdown.
	 */
	private void setShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				cleanup();
			}
		}));
	}
	
	/**
	 * Add a module to the module manager list
	 * @param m module to add
	 */
	public void addModule(Module m) {
		modules.add(m);
		Logger logger = (Logger) ModuleManager.getModule(ModuleManager.Modules.LOGGER);
		logger.write("Module " + ModuleManager.Modules.values()[m.getModuleId().ordinal()] + " Added...");
	}
	
	/**
	 * Remove a module from the module manager list
	 * @param m module to remove
	 */
	public void removeModule(Module m) {
		modules.remove(m);
	}
	
	/**
	 * Call all module cleanups in a for loop
	 */
	public void cleanup() {
		for(Module m : modules) {
			m.cleanup();
		}
	}
	
	/**
	 * Get a list of all of the modules
	 * @return Arraylist of all of the modules
	 */
	public ArrayList<Module> getModules() {
		return this.modules;
	}
}
