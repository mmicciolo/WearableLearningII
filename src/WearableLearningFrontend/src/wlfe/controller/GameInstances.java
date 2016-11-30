package wlfe.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;

import wlfe.common.Common;
import wlfe.common.MySQLAccessor;
import wlfe.model.GameData;
import wlfe.model.GameInstanceData;

public class GameInstances {
	
	private ArrayList<GameInstanceData> accordionData = new ArrayList<GameInstanceData>();
	private ArrayList<GameData> games = new ArrayList<GameData>();
	private String selectGameToStart;
	
	public GameInstances() {
		
	}
	
	@PostConstruct
	public void init() {
		getCurrentGameInstances();
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
						String[] key = {"gameInstanceId"};
						int returnId = 0;
						PreparedStatement preparedStatement = accessor.GetConnection().prepareStatement("INSERT INTO gameInstance (gameId, currentGameStateId) VALUES (?, ?)", key);
						preparedStatement.setInt(1, gameData.getGameId());
						preparedStatement.setInt(2, 1);
						preparedStatement.execute();
						ResultSet rs = preparedStatement.getGeneratedKeys();
						if(rs.next()) {
							returnId = (int)rs.getLong(1);
						}
						preparedStatement.close();
						Statement statement = accessor.GetConnection().createStatement();
						ResultSet resultSet = statement.executeQuery("SELECT * FROM games WHERE gameId=" + gameData.getGameId());
						if(resultSet.next()) {
							accordionData.add(new GameInstanceData(returnId, gameData.getGameId(), 1, new GameData(0, gameData.getTitle(), 0, 0)));
						}
						resultSet.close();
						statement.close();
						accessor.Disconnect();
					} catch (Exception e) {
						e.printStackTrace();
						accessor.Disconnect();
					}
					RequestContext.getCurrentInstance().update("main:accordion");
					RequestContext.getCurrentInstance().execute("PF('NewDialog').hide();");
					break;
				}
			}
		}
	}
	
	public void deleteInstance(GameInstanceData data) {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				PreparedStatement statement = accessor.GetConnection().prepareStatement("DELETE FROM gameInstance WHERE gameInstanceId=" + data.getGameInstanceId());
				statement.executeUpdate();
				statement.close();
			} catch(Exception e) {
				e.printStackTrace();
				Common.ErrorMessage();
				accessor.Disconnect();
				return;
			}
			accordionData.remove(data);
			RequestContext.getCurrentInstance().update("main:accordion");
			//Common.SuccessMessage();
			accessor.Disconnect();
		}
	}
	
	private void getCurrentGameInstances() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				Statement statement = accessor.GetConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM gameInstance");
				while(resultSet.next()) {
					Statement statement2 = accessor.GetConnection().createStatement();
					ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM games WHERE gameId=" + resultSet.getInt("gameId"));
					if(resultSet2.next()) {
						accordionData.add(new GameInstanceData(resultSet.getInt("gameInstanceId"), resultSet.getInt("gameId"), resultSet.getInt("currentGameStateId"), new GameData(0, resultSet2.getString("title"), 0, 0)));
					}
					resultSet2.close();
					statement2.close();
				}
				resultSet.close();
				statement.close();
				accessor.Disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void poll() {
		if(accordionData.size() > 0) {
			//Update the data
		}
	}
	
	public void setAccordionData(ArrayList<GameInstanceData> accordionData) {
		this.accordionData = accordionData;
	}
	
	public void setSelectGameToStart(String selectGameToStart) {
		this.selectGameToStart = selectGameToStart;
	}
	
	public ArrayList<GameInstanceData> getAccordionData() {
		return this.accordionData;
	}
	
	public String getSelectGameToStart() {
		return this.selectGameToStart;
	}
}
