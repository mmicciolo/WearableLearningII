package wlfe.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.context.RequestContext;

import wlfe.common.BaseHeaderMenuTableContentFooter;
import wlfe.common.Common;
import wlfe.common.DataTableColumn;
import wlfe.common.MySQLAccessor;
import wlfe.model.GameData;

public class Games extends BaseHeaderMenuTableContentFooter<GameData> {
	
	protected boolean initColumns() {
		if(columns.add(new DataTableColumn("Title", "title")) &&
		   columns.add(new DataTableColumn("Team Count", "teamCount")) &&
		   columns.add(new DataTableColumn("Players Per Team", "playersPerTeam"))) {
		   fields.put("gameId", new DataTableColumn("Game Id", ""));
		   fields.put("title", new DataTableColumn("Title", ""));
		   fields.put("teamCount", new DataTableColumn("Team Count", ""));
		   fields.put("playersPerTeam", new DataTableColumn("Players Per Team", ""));
		   return true;
		}
		return false;
	}
	
	public boolean initData() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				Statement statement = accessor.GetConnection().createStatement();
				ResultSet results = statement.executeQuery("SELECT * from games");
				while(results.next()) {
					GameData gameData = new GameData();
					String returnId[] = {""};
					MySQLSetGet(false, null, returnId, results, fields, gameData, 1);
					tableObjects.add(gameData);
				}
				results.close();
				statement.close();
				accessor.Disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				Common.ErrorMessage();
				accessor.Disconnect();
				return false;
			}
			return true;
		}
		return false;
	}
	
	public void createPressed() {
		super.createPressed();
	}
	
	public void editPressed() {
		super.editPressed();
	}
	
	public void editConfirmPressed(GameCreation gameCreation) {
		deletePressed("");
		gameCreation.saveData();
		RequestContext.getCurrentInstance().update("main:mainTable");
		RequestContext.getCurrentInstance().execute("PF('EditDialog').hide();");
		RequestContext.getCurrentInstance().reset("gameStateEdit");
		RequestContext.getCurrentInstance().execute("PF('editGame').loadStep('tab0', false)");
	}
	
	public void deletePressed(String query) {
		if(selectedObject != null) {
			MySQLAccessor accessor = MySQLAccessor.getInstance();
			if(accessor.Connect()) {
				try {
					//Get all of the gameStateIds that coordinate with gameId
					//Delete all of the above from gameStateTransition
					//Delete all gameIds from gameState
					//Delete game from games
					Statement statement = accessor.GetConnection().createStatement();
					ResultSet resultSet = statement.executeQuery("SELECT * FROM gameState WHERE gameId=" + selectedObject.getGameId());
					List<Integer> gameStateIds = new ArrayList<Integer>();
					while(resultSet.next()) {
						gameStateIds.add(resultSet.getInt("gameStateId"));
					}
					resultSet.close();
					statement.close();
					
					PreparedStatement preparedStatement;
					for(Integer i : gameStateIds) {
						preparedStatement = accessor.GetConnection().prepareStatement("DELETE FROM gameStateTransitions WHERE gameStateId=" + i);
						preparedStatement.executeUpdate();
						preparedStatement.close();
					}
					
					preparedStatement = accessor.GetConnection().prepareStatement("DELETE FROM gameState WHERE gameId=" + selectedObject.getGameId());
					preparedStatement.executeUpdate();
					preparedStatement.close();
					accessor.Disconnect();
					super.deletePressed("DELETE FROM games WHERE gameId=" + selectedObject.getGameId());
				} catch(Exception e) {
					e.printStackTrace();
					accessor.Disconnect();
					return;
				}
			}
		}
		//super.deletePressed("DELETE FROM games WHERE gameId=" + selectedObject.getGameId());
	}
}
