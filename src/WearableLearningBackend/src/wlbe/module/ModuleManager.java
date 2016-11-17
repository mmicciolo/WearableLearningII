package wlbe.module;

import java.util.ArrayList;

public class ModuleManager {
	
	private static ModuleManager instance = null;
	private static ArrayList<Module> modules = new ArrayList<Module>();
	
	public static enum Modules {
		LOGGER,
		SERVER
	};
	
	protected ModuleManager() {
		setShutdownHook();
	}
	
	public static ModuleManager getInstance() {
		if(instance == null) {
			instance = new ModuleManager();
		}
		return instance;
	}
	
	public static Module getModule(Modules module) {
		for(Module m : modules) {
			if(m.getModuleId().equals(module)) {
				return m;
			}
		}
		return null;
	}
	
	public void update() {
		for(Module m : modules) {
			m.update();
		}
	}
	
	private void setShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				cleanup();
			}
		}));
	}
	
	public void addModule(Module m) {
		modules.add(m);
	}
	
	public void removeModule(Module m) {
		modules.remove(m);
	}
	
	public void cleanup() {
		for(Module m : modules) {
			m.cleanup();
		}
	}
}