package wlbe.module;

/**
 * Interface for Modules.
 * All modules must define these methods.
 * @author Matthew Micciolo
 *
 */
public interface IModule {

	void setup();
	
	void cleanup();
	
	void update();
}
