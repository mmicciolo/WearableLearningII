package wlfe.controller;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

import com.google.gson.Gson;

import wl.shared.model.Button;
import wlfe.common.BackendServer;
import wlfe.common.MySQLAccessor;
import wlfe.model.GameData;

public class VirtualDevice {
	
	private boolean onOff;
	private String id;
	private String displayText;
	private String studentName;
	private List<GameData> gameInstances = new ArrayList<GameData>();
	private GameData selectedGame;
	private String selectedTeam;
	
	private BackendServer backendServer;
	
	@PostConstruct
	public void init() {
		
	}
	
	public void button1() {
		if(onOff) {
			displayText = "Button 1 Pushed!\nColor: Red";
			Gson gson = new Gson();
			Button button = new Button("Button 1");
			String out = gson.toJson(button);
			ByteBuffer buffer = ByteBuffer.allocate(2048);
			buffer.putInt(3);
			backendServer.putString(out, buffer);
			backendServer.write(buffer);
		}
	}
	
	public void button2() {
		if(onOff) {
			displayText = "Button 2 Pushed!\nColor: Green";
		}
	}
	
	public void button3() {
		if(onOff) {
			displayText = "Button 3 Pushed!\nColor: Blue";
		}
	}
	
	public void button4() {
		if(onOff) {
			displayText = "Button 4 Pushed!\nColor: Black";
		}
	}
	
	public void onOffChange() {
		try {
			if(onOff) {
				connectToBackend();
			} else {
				disconnectFromBackend();
			}
		} catch (Exception e) {
			backendServer.disconnect();
			backendServer.checkDisconnected();
		}
	}
	
	private void connectToBackend() {
		backendServer = new BackendServer();
		ByteBuffer buffer = ByteBuffer.allocate(2048);
		buffer.putInt(1);
		backendServer.putString(studentName, buffer);
		backendServer.putString(selectedTeam, buffer);
		buffer.putInt(selectedGame.getGameId());
		backendServer.write(buffer);
	}
	
	private void disconnectFromBackend() {
		ByteBuffer buffer = ByteBuffer.allocate(2048);
		buffer.putInt(2);
		buffer.putInt(selectedGame.getGameId());
		backendServer.putString(studentName, buffer);
		backendServer.write(buffer);
		backendServer.disconnect();
	}
	
	public String onFlowProcess(FlowEvent event) {
		if(event.getNewStep().equals("activeGames")) {
			loadActiveGames();
		}
		return event.getNewStep();
	}
	
	public List<String> getStudentNames(String query) {
		List<String> returnList = new ArrayList<String>();
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				Statement statement = accessor.GetConnection().createStatement();
				ResultSet results = statement.executeQuery("SELECT * from student"); //Common.getTeacherForSession().getTeacherId());
				while(results.next()) {
					returnList.add(results.getString("lastName") + ", " + results.getString("firstName" ));
				}
				Collections.sort(returnList);
				results.close();
				statement.close();
				accessor.Disconnect();
				return returnList;
			} catch(Exception e) {
				e.printStackTrace();
				accessor.Disconnect();
			}
		}
		return returnList;
	}
	
	public List<String> getTeams(String query) {
		List<String> returnList = new ArrayList<String>();
		for(int i = 0; i < selectedGame.getTeamCount(); i++) {
			returnList.add("Team " + (i + 1));
		}
		return returnList;
	}
	
	private void loadActiveGames() {
		gameInstances.clear();
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				Statement statement = accessor.GetConnection().createStatement();
				ResultSet results = statement.executeQuery("SELECT * FROM gameInstance");
				while(results.next()) {
					Statement statement2 = accessor.GetConnection().createStatement();
					ResultSet results2 = statement2.executeQuery("SELECT * FROM games WHERE gameId=" + results.getInt("gameId"));
					if(results2.next()) {
						GameData gameData = new GameData(results.getInt("gameInstanceId"), results2.getString("title"), results2.getInt("teamCount"), results2.getInt("playersPerTeam"));
						gameInstances.add(gameData);
					}
					results2.close();
					statement2.close();
				}
				results.close();
				statement.close();
				accessor.Disconnect();
			} catch (Exception e) {
				accessor.Disconnect();
				e.printStackTrace();
			}
		}
	}
	
	public void setOnOff(boolean onOff) {
		this.onOff = onOff;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public void setGameInstances(List<GameData> gameInstances) {
		this.gameInstances = gameInstances;
	}
	
	public void setSelectedGame(GameData selectedGame) {
		this.selectedGame = selectedGame;
	}
	
	public void setSelectedTeam(String selectedTeam) {
		this.selectedTeam = selectedTeam;
	}
	
	public boolean getOnOff() {
		return this.onOff;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getDisplayText() {
		return this.displayText;
	}
	
	public String getStudentName() {
		return this.studentName;
	}
	
	public List<GameData> getGameInstances() {
		return this.gameInstances;
	}
	
	public GameData getSelectedGame() {
		return this.selectedGame;
	}
	
	public String getSelectedTeam() {
		return this.selectedTeam;
	}
}
