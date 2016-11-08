package wlfe.model;

import java.util.ArrayList;
import java.util.List;

public class GameCreationData {
	
	
	private final String text = "Text";
	private final String led = "LED";
	private final String buzzer = "Buzzer";
	
	private int id;
	
	private String outputType = "Text";

	public GameCreationData() {
		
	}
	
	public GameCreationData(int id) {
		this.id = id;
	}
	
	public void outputTypeChanged() {
		if(outputType.equals(text)) {
			outputType = text;
			
		} else if(outputType.equals(led)) {
			outputType = led;
			
		} else if(outputType.equals(buzzer)) {
			outputType = buzzer;
		} else {
			//Error
			outputType = "";
		}
	}
	
	public List<String> fillDropDown(String key) {
		List<String> list = new ArrayList<String>();
		list.add(text);
		list.add(led);
		list.add(buzzer);
		return list;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}
	
	public int getId() {
		return this.id;
	}
		
	public String getOutputType() {
		return this.outputType;
	}
}
