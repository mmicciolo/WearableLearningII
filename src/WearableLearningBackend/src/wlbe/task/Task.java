package wlbe.task;

import wlbe.event.Event;

public abstract class Task extends Thread implements ITask {
	
	private boolean running = true;
	private String name;
	
	public void run() {
		while(running) {
			update();
		}
		cleanup();
	}
	
	public void shutdown() {
		running = false;
	}
	
	public void update() {
		
	}
	
	public void cleanup() {
		
	}
	
	public void eventHandler(Event e) {
		
	}
}
