package wlfe.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;

import wlfe.common.MySQLAccessor;
import wlfe.model.GameData;
import wlfe.model.GameInstanceData;

public class GameInstances {
	
	private ArrayList<String> accordionData = new ArrayList<String>();
	private ArrayList<GameInstanceData> gameInstanceData = new ArrayList<GameInstanceData>();
	private ArrayList<GameData> games = new ArrayList<GameData>();
	private String selectGameToStart;
	
	private int gameInstanceCount = 1;
	
	public GameInstances() {
		
	}
	
	@PostConstruct
	public void init() {
		gameInstanceData.add(new GameInstanceData());
	}
	
	public List<String> listOfGames(String query) {
		ArrayList<String> returnList = new ArrayList<String>();
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				Statement statement = accessor.GetConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM games");
				while(resultSet.next()) {
					GameData gameData = new GameData(resultSet.getInt("gameId"), resultSet.getString("title"), resultSet.getInt("teamCount"), resultSet.getInt("playersPerTeam"));
					games.add(gameData);
					returnList.add(resultSet.getString("title"));
				}
				resultSet.close();
				statement.close();
				accessor.Disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				accessor.Disconnect();
			}
		}
		return returnList;
	}
	
	public void startGame() {
		for(GameData gameData : games) {
			if(gameData.getTitle().equals(selectGameToStart)) {
				MySQLAccessor accessor = MySQLAccessor.getInstance();
				if(accessor.Connect()) {
					try {
						PreparedStatement preparedStatement = accessor.GetConnection().prepareStatement("INSERT INTO gameInstance (gameId, currentGameStateId) VALUES (?, ?)");
						preparedStatement.setInt(1, gameData.getGameId());
						preparedStatement.setInt(2, 1);
						preparedStatement.execute();
						preparedStatement.close();
						accessor.Disconnect();
					} catch (Exception e) {
						e.printStackTrace();
						accessor.Disconnect();
					}
					accordionData.add("Game Instance " + gameInstanceCount++);
					RequestContext.getCurrentInstance().update("main:accordion");
					RequestContext.getCurrentInstance().execute("PF('NewDialog').hide();");
					break;
				}
			}
		}
	}
	
	public void setAccordionData(ArrayList<String> accordionData) {
		this.accordionData = accordionData;
	}
	
	public void setGameInstanceData(ArrayList<GameInstanceData> gameInstanceData) {
		this.gameInstanceData = gameInstanceData;
	}
	
	public void setSelectGameToStart(String selectGameToStart) {
		this.selectGameToStart = selectGameToStart;
	}
	
	public ArrayList<String> getAccordionData() {
		return this.accordionData;
	}
	
	public ArrayList<GameInstanceData> getGameInstanceData() {
		return this.gameInstanceData;
	}
	
	public String getSelectGameToStart() {
		return this.selectGameToStart;
	}
}
