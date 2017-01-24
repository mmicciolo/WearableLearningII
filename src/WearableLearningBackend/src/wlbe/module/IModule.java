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
	
	/**
	 * Called to take control of a data item in a running task.
	 * This is used when two threads try accessing data at the same time.
	 */
	void accquire() throws InterruptedException;
	
	/**
	 * Called to release control of a data item in a running task.
	 */
	void release();
}
