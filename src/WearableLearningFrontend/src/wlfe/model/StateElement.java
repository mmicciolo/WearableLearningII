package wlfe.model;

public class StateElement {
	
	private String stateName = "";
	
	public StateElement() {
		
	}
	
	public StateElement(String stateName) {
		this.stateName = stateName;
	}
	
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	public String getStateName() {
		return this.stateName;
	}
}
