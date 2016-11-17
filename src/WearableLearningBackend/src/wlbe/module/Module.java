package wlbe.module;

import wlbe.event.Event;
import wlbe.module.ModuleManager.Modules;

public abstract class Module implements IModule {
	
	protected Modules moduleId;
	
	public Module() {
		
	}
	
	public Module(Modules moduleId) {
		this.moduleId = moduleId;
		setup();
	}
	
	public void setup() {
		
	}
	
	public void cleanup() {
		
	}
	
	public void update() {
		
	}
	
	public void eventHandler(Event e) {
		
	}
	
	public void setModuleId(Modules moduleId) {
		this.moduleId = moduleId;
	}
	
	public Modules getModuleId() {
		return this.moduleId;
	}
}
