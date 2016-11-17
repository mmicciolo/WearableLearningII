package wlbe.module;

import wlbe.event.IEvent;
import wlbe.module.ModuleManager.Modules;

/**
 * Abstract class that represents a Module.
 * This backend is modular meaning that its 
 * functions are split into different modules.
 * A module is added to a list which contains other modules.
 * These modules are all updated in order. Modules have a moduleID.
 * This is set via the ModuleManager.Modules enum in ModuleManager.Java.
 * @author Matt
 *
 */
public abstract class Module implements IModule {
	
	protected Modules moduleId;
	
	/**
	 * Default constructor
	 */
	public Module() {
		
	}
	
	/**
	 * Module ID from ModuleManager.Modules enum in ModuleManager.java
	 * @param moduleId moduleID from ModuleManager.Modules
	 */
	public Module(Modules moduleId) {
		this.moduleId = moduleId;
		setup();
	}
	
	/**
	 * Called before module starts running.
	 */
	public void setup() {
		
	}
	
	/**
	 * Called when the module is exiting such as being removed
	 * from the ModuleManager.
	 */
	public void cleanup() {
		
	}
	
	/**
	 * Called in a loop while the module is running.
	 */
	public void update() {
		
	}
	
	/**
	 * Called when there is an event waiting
	 * @param e event
	 */
	public void eventHandler(IEvent e) {
		
	}
	
	/**
	 * Set the moduleId
	 * @param moduleId new moduleId
	 */
	public void setModuleId(Modules moduleId) {
		this.moduleId = moduleId;
	}
	
	/**
	 * Get the moduleId
	 * @return moduleId
	 */
	public Modules getModuleId() {
		return this.moduleId;
	}
}
