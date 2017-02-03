package wlfe.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.primefaces.model.diagram.Element;

import wlfe.common.DataTableColumn;

public class GameCreationData2 {

	private int id;
	private int teamId;
	private int playerId;
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
	private Element element;

	public GameCreationData2() {
		
	}
	
	public GameCreationData2(int id) {
		this.id = id;
	}
	
	public GameCreationData2(int id, int teamId, int playerId, String text, String ledColor, String ledDuration, String buzzerOn, String buzzerDuration, String responseType, Element element) {
		this.id = id;
		this.teamId = teamId;
		this.playerId = playerId;
		this.text = text;
		this.ledColor = ledColor;
		this.ledDuration = ledDuration;
		this.buzzerOn = buzzerOn;
		this.buzzerDuration = buzzerDuration;
		this.responseType = responseType;
		this.element = element;
		if(teamId == 0 && playerId == 0) { responseTo = "Game Wide"; }
		else if(teamId > 0 ) { responseTo = "Team " + teamId; }
		else if(playerId > 0) { responseTo = "--Player " + playerId; }
		else {}
	}
	
	public int responseToTeamId() {
		if(responseTo.contains("Team")) {
			return Integer.parseInt(responseTo.replace("Team ", ""));
		}
		return 0;
	}
	
	public int responseToPlayerId() {
		if(responseTo.contains("Player")) {
			return Integer.parseInt(responseTo.replace("--Player ", ""));
		}
		return 0;	
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
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
		return this.id + 1;
	}
	
	public int getTeamId() {
		return this.teamId;
	}
	
	public int getPlayerId() {
		return this.playerId;
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
	
	public Element getElement() {
		return this.element;
	}
}
