package wlbe.modules;

import java.io.File;
import java.io.PrintWriter;

import wlbe.module.Module;
import wlbe.module.ModuleManager.Modules;

public class Logger extends Module {
	
	private PrintWriter writer;
	
	public Logger(Modules moduleId) {
		super(moduleId);
	}
	
	public void write(String output) {
		writer.println(output);
		writer.flush();
		System.out.println(output);
	}
	
	public void setup() {
		try {
			File file = new File("wlbelog.log");
			file.createNewFile();
			writer = new PrintWriter(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cleanup() {
		writer.close();
	}
}
