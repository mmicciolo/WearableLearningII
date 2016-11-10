package wlfe.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;

import wlfe.common.DataTableColumn;

public class GameCreationData {

	private String title;
	private String teamCount;
	private String playersPerTeam;
	private int id;
	private String text;
	private String ledColor;
	private String ledDuration;
	private String buzzerOn;
	private String buzzerDuration;
	private String responseTo;
	private String responseType;
	private List<DataTableColumn> dataTable = new ArrayList<DataTableColumn>();
	private int columnCount;
	private List<String> colorValues = new ArrayList<String>();
	private String[] buttonColors = {"red", "green", "blue", "black"};

	public GameCreationData() {
		
	}
	
	public GameCreationData(int id) {
		this.id = id;
	}
	
	public void updateGeneralSetup(String title, String teamCount, String playersPerTeam) {
		this.title = title;
		this.teamCount = teamCount;
		this.playersPerTeam = playersPerTeam;
	}
	
	public List<String> fillDropDown(String query) {
		List<String> list = new ArrayList<String>();
		list.add("Game Wide");
		int teamC = Integer.parseInt(teamCount);
		int playersPerTeamC = Integer.parseInt(playersPerTeam);
		List<String> tempCollection = new ArrayList<String>();
		tempCollection.add("Game Wide");
		for(int i = 1; i < teamC + 1; i++) {
			tempCollection.add("Team " + i);
			for(int n = 1; n < playersPerTeamC + 1; n++) {
				tempCollection.add("--Player " + n);
			}
		}
		return tempCollection;
	}
	
	public void responseToChanged() {
		dataTable.clear();
		colorValues.clear();
		if(responseType.equals("Single")) {
			if(responseTo.equals("Game Wide")) {
				columnCount = Integer.parseInt(teamCount) + 1;
				int colorCount = 0;
				for(int i = 0; i < (columnCount * 5); i++) {
					if(i == 0) {
						dataTable.add(new DataTableColumn("Text", ""));
					}
					else if(i < columnCount) {
						dataTable.add(new DataTableColumn("Text", "Team " + i));
					}
					else if((i % columnCount) == 0) {
						dataTable.add(new DataTableColumn("Color", buttonColors[colorCount]));
						colorCount++;
					}
					else {
						dataTable.add(new DataTableColumn("SelectOne", ""));
					}
				}
			} else if(!responseTo.equals("")) {
				columnCount = 1;
				for(int i = 0; i < 4; i++) {
					//dataTable.add("Hi");
				}
			}
		} else if(responseType.equals("Sequence")) {
			columnCount = Integer.parseInt(teamCount);
//			dataTable.add("Hi");
//			dataTable.add("Hi");
//			dataTable.add("Hi");
//			dataTable.add("Hi");
//			colorValues.add("Hi");
//			colorValues.add("Hi");
//			colorValues.add("Hi");
//			colorValues.add("Hi");
		}
	}
	
	public void onDrop() {
		
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setLedColor(String ledColor) {
		this.ledColor = ledColor;
	}
	
	public void setLedDuration(String ledDuration) {
		this.ledDuration = ledDuration;
	}
	
	public void setBuzzerOn(String buzzerOn) {
		this.buzzerOn = buzzerOn;
	}
	
	public void setBuzzerDuration(String buzzerDuration) {
		this.buzzerDuration = buzzerDuration;
	}
	
	public void setResponseTo(String responseTo) {
		this.responseTo = responseTo;
	}
	
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	
	public void setDataTable(List<DataTableColumn> dataTable) {
		this.dataTable = dataTable;
	}
	
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}
	
	public void setColorValues(List<String> colorValues) {
		this.colorValues = colorValues;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getText() {
		return this.text;
	}	
	
	public String getLedColor() {
		return this.ledColor;
	}
	
	public String getLedDuration() {
		return this.ledDuration;
	}
	
	public String getBuzzerOn() {
		return this.buzzerOn;
	}
	
	public String getBuzzerDuration() {
		return this.buzzerDuration;
	}
	
	public String getResponseTo() {
		return this.responseTo;
	}
	
	public String getResponseType() {
		return this.responseType;
	}
	
	public List<DataTableColumn> getDataTable() {
		return this.dataTable;
	}
	
	public int getColumnCount() {
		return this.columnCount;
	}
	
	public List<String> getColorValues() {
		return this.colorValues;
	}
}
