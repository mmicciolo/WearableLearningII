package wlbe.task;

/**
 * Interface for task. All methods defined here
 * must be implemented in anything that interfaces with this.
 * @author Matthew Micciolo
 *
 */
public interface ITask {
	
	/**
	 * Called in a loop while the thread is
	 * still running.
	 */
	void update();
	
	/**
	 * Called before exiting
	 */
	void cleanup();
}
