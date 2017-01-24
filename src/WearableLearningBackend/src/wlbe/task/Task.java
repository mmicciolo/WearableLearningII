package wlbe.task;

import java.util.concurrent.Semaphore;

import wlbe.event.IEvent;

/**
 * Abstract class for Task. A task is a separate program that the server
 * can spawn onto a different thread. It can execute independently of the server
 * and perform a task of its own. For example, a task could be a game instance
 * or game engine that runs in a separate thread. It however can still receive server
 * data via events. This class extends Thread so can be started as a thread.
 * @author Matthew Micciolo
 *
 */
public abstract class Task extends Thread implements ITask {
	
	private boolean running = true;
	private String name;
	private final Semaphore available = new Semaphore(10, true);
	
	/**
	 * This is where the thread first enters when it is first
	 * created.
	 */
	public void run() {
		while(running) {
			update();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		cleanup();
	}
	
	/**
	 * Called to shutdown thread (quit).
	 */
	public void shutdown() {
		running = false;
	}
	
	/**
	 * Called in a loop while the thread is
	 * still running.
	 */
	public void update() {
		
	}
	
	/**
	 * Called before exiting
	 */
	public void cleanup() {
		
	}
	
	/**
	 * Called when an event has arrived
	 * @param e event
	 */
	public void eventHandler(IEvent e) {
		
	}
	
	/**
	 * Called to accquire a resource
	 * @throws InterruptedException 
	 */
	public void accquire() throws InterruptedException {
		available.acquire();
	}
	
	/** 
	 * Called to release a resource
	 */
	public void release() {
		available.release();
	}
}
