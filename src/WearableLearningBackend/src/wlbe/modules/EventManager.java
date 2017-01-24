package wlbe.modules;


import wlbe.event.IEvent;
import wlbe.module.Module;
import wlbe.module.ModuleManager;
import wlbe.module.ModuleManager.Modules;
import wlbe.task.Task;

public class EventManager extends Module {
	
	public EventManager(Modules moduleId) {
		super(moduleId);
	}
	
	public void BroadcastEvent(IEvent e) {
		TaskManager taskManager = (TaskManager) ModuleManager.getModule(ModuleManager.Modules.TASK_MANAGER);
		for(Task task : taskManager.getTasks()) {
			task.eventHandler(e);
		}
		for(Module module : ModuleManager.getInstance().getModules()) {
			module.eventHandler(e);
		}
	}
	
	public void setup() {
		
	}
	
	public void update() {

	}
	
	public void cleanup() {
		
	}
}
