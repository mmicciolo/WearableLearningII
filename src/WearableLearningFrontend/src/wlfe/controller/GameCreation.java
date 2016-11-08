package wlfe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import wlfe.model.GameCreationData;

public class GameCreation {
	
	private List<GameCreationData> accordionPanels = new ArrayList<GameCreationData>();
	
	@PostConstruct
	public void init() {
		accordionPanels.add(new GameCreationData(1));
	}
	
	public void setAccordionPanels(List<GameCreationData> accordionPanels) {
		this.accordionPanels = accordionPanels;
	}
	
	public List<GameCreationData> getAccordionPanels() {
		return this.accordionPanels;
	}
}
