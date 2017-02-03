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
import wlfe.model.GameData;
import wlfe.model.StateElement;

public class GameCreation2 {
	
	private DefaultDiagramModel model;
	
	private final String STATE_STYLE = "ui-diagram-element-state";
	private final String STATE_STYLE_CLICKED = "ui-diagram-element-state-clicked"; 
	
	private int gameId;
	private String title;
	private String teamCount;
	private String playersPerTeam;
	private int stateCount = 0;
	private ArrayList<Element> states;
	private Element selectedState;
	private boolean renderOptions = false;
	private boolean ignoreClick = false;
	
	@PostConstruct
	public void init() {
		initDiagram();
        initStartState();
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
    }
    
    public void onConnect() {
    	
    }
    
    public void onNodeMove2(ActionEvent param) {
    	Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("node_id");
        int pos = id.lastIndexOf("-"); // Remove Client ID part
        if (pos != -1) {
           id = id.substring(pos + 1);
        }
        Element element = model.findElement(id);
        if(element == null) {
        	 selectedState.setStyleClass(STATE_STYLE);
             selectedState = null;
             RequestContext.getCurrentInstance().update("gameState:diagram");
        }
    }
  
    public void onNodeMove(ActionEvent param) {
    	if(ignoreClick == true) { ignoreClick = false; return; }
    	Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("node_id");
        String x = params.get("node_x");
        String y = params.get("node_y");
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
		   element.setStyleClass(STATE_STYLE_CLICKED);
		   RequestContext.getCurrentInstance().update("gameState:diagram");
		   renderOptions = true;
		   RequestContext.getCurrentInstance().update(":gameState:mainOptions");
		   selectedState = element;
		   ignoreClick = true;
        } else {
        	if(ignoreClick == false) {
                selectedState.setStyleClass(STATE_STYLE);
                selectedState = null;
                RequestContext.getCurrentInstance().update("gameState:diagram");
                ignoreClick = false;
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
	
	public boolean getRenderOptions() {
		return this.renderOptions;
	}
}
