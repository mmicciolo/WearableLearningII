package wlbe.modules;

import java.util.ArrayList;

import wlbe.module.Module;
import wlbe.module.ModuleManager;
import wlbe.module.ModuleManager.Modules;
import wlbe.task.Task;
import wlbe.tasks.GameInstanceDaemon;

public class TaskManager extends Module {
	
	ArrayList<Task> tasks = new ArrayList<Task>();
	
	public TaskManager(Modules moduleId) {
		super(moduleId);
	}
	
	public void addTask(Task task) {
		new Thread(task).start();
		tasks.add(task);
	}
	
	public void removeTask(Task task) {
		for(Task t : tasks) {
			if(t.equals(task)) {
				t.shutdown();
			}
		}
		tasks.remove(task);
	}
	
	public void setup() {
		
	}
	
	public void update() {

	}
	
	public void cleanup() {
		
	}
	
	public ArrayList<Task> getTasks() {
		return this.tasks;
	}
}
