package wlfe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

import wlfe.model.GameCreationData;

public class GameCreation {
	
	private List<GameCreationData> accordionPanels = new ArrayList<GameCreationData>();
	
	private String title;
	private String teamCount;
	private String playersPerTeam;
	private int stateCount = 0;
	
	@PostConstruct
	public void init() {
		accordionPanels.add(new GameCreationData(stateCount));
	}
	
	public String flowControl(FlowEvent e) {
		updateGeneralSetup();
		return e.getNewStep();
	}
	
	public List<String> fillStateDropDowns(String query) {
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < accordionPanels.size(); i++) {
			list.add("Go to " + (i + 1));
		}
		return list;
	}
	
	public void addState() {
		stateCount++;
		accordionPanels.add(new GameCreationData(stateCount));
		RequestContext.getCurrentInstance().update("gameState:mainAccordion");
		FacesMessage msg = new FacesMessage("State Added", "State Added");
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void saveData() {
		
	}
		
	public void clear() {
		title = null;
		teamCount = null;
		playersPerTeam = null;
		accordionPanels = new ArrayList<GameCreationData>();
		accordionPanels.add(new GameCreationData(1));
		RequestContext.getCurrentInstance().reset("gameState");
		RequestContext.getCurrentInstance().execute("PF('newGame').loadStep('tab0', false)");
	}
	
	public void updateGeneralSetup() {
		for(GameCreationData data : accordionPanels) {
			data.updateGeneralSetup(title, teamCount, playersPerTeam);
		}
	}
	
	public void setAccordionPanels(List<GameCreationData> accordionPanels) {
		this.accordionPanels = accordionPanels;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setTeamCount(String teamCount) {
		this.teamCount = teamCount;
	}
	
	public void setPlayersPerTeam(String playersPerTeam) {
		this.playersPerTeam = playersPerTeam;
	}
	
	public void setStateCount(int stateCount) {
		this.stateCount = stateCount;
	}
	
	public List<GameCreationData> getAccordionPanels() {
		return this.accordionPanels;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getTeamCount() {
		return this.teamCount;
	}
	
	public String getPlayersPerTeam() {
		return this.playersPerTeam;
	}
	
	public int getStateCount() {
		return this.stateCount;
	}
}
