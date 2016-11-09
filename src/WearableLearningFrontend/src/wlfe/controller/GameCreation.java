package wlfe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

import wlfe.model.GameCreationData;

public class GameCreation {
	
	private List<GameCreationData> accordionPanels = new ArrayList<GameCreationData>();
	
	private String title;
	private String teamCount;
	private String playersPerTeam;
	
	@PostConstruct
	public void init() {
		accordionPanels.add(new GameCreationData(1));
	}
	
	public String flowControl(FlowEvent e) {
		updateGeneralSetup();
		return e.getNewStep();
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
}
