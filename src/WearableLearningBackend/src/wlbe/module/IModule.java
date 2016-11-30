package wlbe.module;

/**
 * Interface for Modules.
 * All modules must define these methods.
 * @author Matthew Micciolo
 *
 */
public interface IModule {

	/**
	 * Called before module starts running.
	 */
	void setup();
	
	/**
	 * Called when the module is exiting such as being removed
	 * from the ModuleManager.
	 */
	void cleanup();
	
	/**
	 * Called in a loop while the module is running.
	 */
	void update();
}
