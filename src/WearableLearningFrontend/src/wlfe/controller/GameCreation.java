package wlfe.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

import wlfe.common.Common;
import wlfe.common.DataTableColumn;
import wlfe.common.MySQLAccessor;
import wlfe.model.GameCreationData;
import wlfe.model.GameData;

public class GameCreation {
	
	private List<GameCreationData> accordionPanels = new ArrayList<GameCreationData>();
	
	private int gameId;
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
		GameCreationData gameCreationData = new GameCreationData(stateCount);
		gameCreationData.updateGeneralSetup(title, teamCount, playersPerTeam);
		accordionPanels.add(gameCreationData);
		RequestContext.getCurrentInstance().update("gameState:mainAccordion");
		FacesMessage msg = new FacesMessage("State Added", "State Added");
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void saveData() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				String returnId[] = {"gameId"};
				PreparedStatement preparedStatement = accessor.GetConnection().prepareStatement("INSERT INTO games (title, teamCount, playersPerTeam) VALUES (?, ?, ?)", returnId);
				preparedStatement.setString(1, title);
				preparedStatement.setInt(2, Integer.parseInt(teamCount));
				preparedStatement.setInt(3, Integer.parseInt(playersPerTeam));
				preparedStatement.executeUpdate();
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				if(resultSet.next()) { gameId = (int)resultSet.getLong(1); }
				resultSet.close();
				preparedStatement.close();
				
				preparedStatement = accessor.GetConnection().prepareStatement("INSERT INTO gameState (gameId, teamId, playerId, hintSetId, textContent, ledColor, ledDuration, buzzerState, buzzerDuration, buttonInputType, rfid, gameStateCount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				for(GameCreationData gameCreationData : accordionPanels) {
					preparedStatement.setInt(1, gameId);
					preparedStatement.setInt(2,  0);
					preparedStatement.setInt(3,  0);
					preparedStatement.setInt(4,  0);
					preparedStatement.setString(5, gameCreationData.getText());
					preparedStatement.setString(6, gameCreationData.getLedColor());
					preparedStatement.setInt(7, 0);
					preparedStatement.setBoolean(8, Boolean.parseBoolean(gameCreationData.getBuzzerOn()));
					preparedStatement.setInt(9, Integer.parseInt(gameCreationData.getBuzzerDuration()));
					preparedStatement.setString(10,  gameCreationData.getResponseType());
					preparedStatement.setString(11, "");
					preparedStatement.setInt(12, gameCreationData.getId());
					preparedStatement.executeUpdate();
				}
				preparedStatement.close();
				
				Statement statement = accessor.GetConnection().createStatement();
				resultSet = statement.executeQuery("SELECT * FROM gameState WHERE gameId=" + gameId);
				while(resultSet.next()) {
					for(GameCreationData gameCreationData : accordionPanels) {
						if(gameCreationData.getId() == resultSet.getInt("gameStateCount")) {
							preparedStatement = accessor.GetConnection().prepareStatement("INSERT INTO gameStateTransitions (gameStateId, singlePushButtonColor, fourButtonPush0, fourButtonPush1, fourButtonPush2, fourButtonPush3, nextGameStateTransition) VALUES (?, ?, ?, ?, ?, ?, ?)");
							if(gameCreationData.getResponseType().equals("Single")) {
								int colorCounter = 0;
								for(DataTableColumn c: gameCreationData.getDataTable()) {
									if(c.getHeader().equals("SelectOne")) {
										int gameStateCount = gameCreationData.getId();
										Statement stateCount = accessor.GetConnection().createStatement();
										ResultSet stateCountResult = stateCount.executeQuery("SELECT * FROM gameState WHERE gameId=" + gameId +  " AND gameStateCount=" + gameStateCount);
										stateCountResult.next();
										int gameStateId = stateCountResult.getInt("gameStateId");
										preparedStatement.setInt(1, resultSet.getInt("gameStateId"));
										preparedStatement.setInt(2, colorCounter);
										preparedStatement.setString(3, "");
										preparedStatement.setString(4, "");
										preparedStatement.setString(5, "");
										preparedStatement.setString(6, "");
										preparedStatement.setInt(7, convertStateStringtoInt(c.getProperty(), gameCreationData.getId() + 1));
										if(colorCounter == 3) { colorCounter = 0; }
										else { colorCounter++; }
										preparedStatement.executeUpdate();
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				accessor.Disconnect();
				return;
			}
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			Games games = (Games) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "games");
			games.getTableObjects().add(new GameData(gameId, title, Integer.parseInt(teamCount), Integer.parseInt(playersPerTeam)));
			RequestContext.getCurrentInstance().update("main:mainTable");
			RequestContext.getCurrentInstance().execute("PF('NewDialog').hide();");
			Common.SuccessMessage();
			accessor.Disconnect();
		}
	}
	
	public int convertStateStringtoInt(String s, int id) {
		int i = 0;
		if(!s.equals("")) {
			String temp = s.replace("Go to ", "");
			i = Integer.parseInt(temp);
		} else {
			i = id;
		}
		return i;
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
