package wlbe.task;

/**
 * Interface for task. All methods defined here
 * must be implemented in anything that interfaces with this.
 * @author Matthew Micciolo
 *
 */
public interface ITask {
	
	void update();
	
	void cleanup();

}
