package wlfe.controller;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StateMachineConnector;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.endpoint.RectangleEndPoint;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;

import wlfe.common.Common;
import wlfe.common.DataTableColumn;
import wlfe.common.MySQLAccessor;
import wlfe.model.GameCreationData;
import wlfe.model.GameCreationData2;
import wlfe.model.GameData;
import wlfe.model.StateElement;

public class GameCreation2 {
	
	private ArrayList<GameCreationData2> gameStates = new ArrayList<GameCreationData2>();
	
	private DefaultDiagramModel model;
	
	private final String STATE_STYLE = "ui-diagram-element-state";
	private final String STATE_STYLE_CLICKED = "ui-diagram-element-state-clicked"; 
	
	private int gameId;
	private String title;
	private String teamCount;
	private String playersPerTeam;
	private int stateCount = 0;
	private int stateCountU = 0;
	private ArrayList<Element> states;
	private Element selectedState;
	private boolean renderFilter = false;
	private boolean renderOptions = false;
	private boolean ignoreClick = false;
	
	private String buzzerState;
	private String buzzerDuration;
	private String ledColor;
	private String ledDuration;
	private String text = "";
	
	private Element oldElement;
	
	@PostConstruct
	public void init() {
		initDiagram();
        //initStartState();
	}
	
	private void initDiagram() {
		model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
        
        model.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'#98AFC7', lineWidth:3}");
        connector.setHoverPaintStyle("{strokeStyle:'#5C738B'}");
        model.setDefaultConnector(connector);
        
        states = new ArrayList<Element>();
         
//        StateMachineConnector connector = new StateMachineConnector();
//        connector.setOrientation(StateMachineConnector.Orientation.ANTICLOCKWISE);
//        connector.setPaintStyle("{strokeStyle:'#7D7463',lineWidth:3}");
//        model.setDefaultConnector(connector);
	}
	
	private void initStartState() {
		Element start = new Element(new StateElement("Start State"), "50%", "10%");
		EndPoint endPointCA = createRectangleEndPoint(EndPointAnchor.BOTTOM);
        endPointCA.setSource(true);
        start.setId("start");
        start.addEndPoint(endPointCA);
        start.setStyleClass(STATE_STYLE);
        model.addElement(start);
        states.add(start);
        gameStates.add(new GameCreationData2((stateCount + 1), 0, 0, "", "", "", "", "", "", start));
	}
	
	public void createGame() {
		if((!title.equals("") && (!teamCount.equals("") && (!playersPerTeam.equals(""))))) {
			initStartState();
			RequestContext.getCurrentInstance().update("gameState:diagram");
			renderFilter = true;
			RequestContext.getCurrentInstance().update("gameState:filter");
		}
	}
	
    public void addState() {
        Element state = new Element(new StateElement("State " + (stateCount + 1)), "10%", "10%");
        state.setStyleClass(STATE_STYLE);
        state.setId("State " + (stateCount+ 1));
        stateCount++;
        EndPoint top = createDotEndPoint(EndPointAnchor.TOP);
        EndPoint bottom = createRectangleEndPoint(EndPointAnchor.BOTTOM);
        bottom.setSource(true);
        state.addEndPoint(top);
        state.addEndPoint(bottom);
        model.addElement(state);
        states.add(state);
        gameStates.add(new GameCreationData2((stateCount + 1), 0, 0, "", "", "", "", "", "", state));
    }
    
    public void onConnect() {
    	
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
    
    public void onNodeMove(ActionEvent param) {
    	if(ignoreClick == true) { ignoreClick = false; return; }
    	Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("node_id");
        String x = params.get("node_x");
        String y = params.get("node_y");
        //String text = params.get("text");
        int pos = id.lastIndexOf("-"); // Remove Client ID part
        if (pos != -1) {
           id = id.substring(pos + 1);
        }
        Element element = model.findElement(id);
        if (element != null) {
           element.setX(x);
           element.setY(y);
           if(selectedState != null) {
    		   selectedState.setStyleClass(STATE_STYLE);
		   }
           StateElement stateElement = (StateElement)element.getData();
           text = stateElement.getText();
		   element.setStyleClass(STATE_STYLE_CLICKED);
		   RequestContext.getCurrentInstance().update("gameState:diagram");
		   renderOptions = true;
		   RequestContext.getCurrentInstance().update("gameState:mainOptions");
		   selectedState = element;
		   ignoreClick = true;
		   oldElement = element;
        } else {
        	if(ignoreClick == false && !text.equals("")) {
        		for(GameCreationData2 data : gameStates) {
        			if(data.getElement().equals(oldElement)) {
        				StateElement stateElement = (StateElement)oldElement.getData();
        				stateElement.setText(text);
        				data.setText(text);
        				text = "";
        				data.setBuzzerOn(buzzerState);
        				data.setBuzzerDuration(buzzerDuration);
        				data.setLedColor(ledColor);
        				data.setLedDuration(ledDuration);
        				break;
        			}
        		}
                selectedState.setStyleClass(STATE_STYLE);
                selectedState = null;
                RequestContext.getCurrentInstance().update("gameState:diagram");
                ignoreClick = false;
                renderOptions = false;
                RequestContext.getCurrentInstance().update("gameState:mainOptions");
        	}
        }
    }

    private EndPoint createDotEndPoint(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor);
        endPoint.setScope("network");
        endPoint.setTarget(true);
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");
         
        return endPoint;
    }
     
    private EndPoint createRectangleEndPoint(EndPointAnchor anchor) {
        RectangleEndPoint endPoint = new RectangleEndPoint(anchor);
        endPoint.setScope("network");
        endPoint.setSource(true);
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");
         
        return endPoint;
    }
		
	public void clear() {
		title = null;
		teamCount = null;
		playersPerTeam = null;
		model = null;
		stateCount = 0;
		renderFilter = false;
		renderOptions = false;
		init();
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
	
	public void setBuzzerState(String buzzerState) {
		this.buzzerState = buzzerState;
	}
	
	public void setBuzzerDuration(String buzzerDuration) {
		this.buzzerDuration = buzzerDuration;
	}
	
	public void setLedColor(String ledColor) {
		this.ledColor = ledColor;
	}
	
	public void setLedDuration(String ledDuration) {
		this.ledDuration = ledDuration;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
    public DiagramModel getModel() {
        return model;
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
	
	public boolean getRenderFilter() {
		return this.renderFilter;
	}
	
	public boolean getRenderOptions() {
		return this.renderOptions;
	}
	
	public String getBuzzerState() {
		return this.buzzerState;
	}
	
	public String getBuzzerDuration() {
		return this.buzzerDuration;
	}
	
	public String getLedColor() {
		return this.ledColor;
	}
	
	public String getLedDuration() {
		return this.ledDuration;
	}
	
	public String getText() {
		return this.text;
	}
}
