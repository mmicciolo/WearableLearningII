package wlfe.model;

public class StateElement {
	
	private String stateName = "";
	private String text = "Click to enter text";
	
	public StateElement() {
		
	}
	
	public StateElement(String stateName) {
		this.stateName = stateName;
	}
	
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getStateName() {
		return this.stateName;
	}
	
	public String getText() {
		return this.text;
	}
}
