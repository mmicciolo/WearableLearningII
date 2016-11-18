package wlfe.controller;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

import wlfe.common.BackendServer;
import wlfe.common.MySQLAccessor;
import wlfe.model.GameData;

public class VirtualDevice {
	
	private String onOff;
	private String id;
	private String displayText;
	private String studentName;
	private List<GameData> gameInstances = new ArrayList<GameData>();
	private GameData selectedGame;
	private String selectedTeam;
	
	private BackendServer backendServer;
	
	private boolean on = false;
	
	@PostConstruct
	public void init() {
		
	}
	
	public void button1() {
		displayText = "Button 1 Pushed!\nColor: Red";
		RequestContext.getCurrentInstance().update("display");
	}
	
	public void button2() {
		displayText = "Button 2 Pushed!\nColor: Green";
		RequestContext.getCurrentInstance().update("display");
	}
	
	public void button3() {
		displayText = "Button 3 Pushed!\nColor: Blue";
		RequestContext.getCurrentInstance().update("display");
	}
	
	public void button4() {
		displayText = "Button 4 Pushed!\nColor: Black";
		RequestContext.getCurrentInstance().update("display");
	}
	
	public void onOffChange() {
		if(Boolean.valueOf(onOff) == true) {
			connectToBackend();
			on = true;
//			backendServer = new BackendServer();
//			on = true;
//			ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
//			byteBuffer.putInt(0);
//			String send = "Hello World!";
//			byteBuffer.putInt(send.length());
//			for(char c : send.toCharArray()) {
//				byteBuffer.putChar(c);
//			}
//			backendServer.write(byteBuffer);
//			backendServer.disconnect();
//			backendServer = null;
		} else {
			//on = false;
			//backendServer.disconnect();
			//backendServer = null;
		}
	}
	
	private void connectToBackend() {
		backendServer = new BackendServer();
		ByteBuffer buffer = ByteBuffer.allocate(2048);
		buffer.putInt(1);
		buffer.putInt(selectedGame.getTitle().length());
		for(Byte b : selectedGame.getTitle().getBytes()) {
			buffer.put(b);
		}
		buffer.putInt(selectedGame.getGameId());
		buffer.putInt(selectedTeam.length());
		for(Byte b : selectedTeam.getBytes()) {
			buffer.put(b);
		}
		backendServer.write(buffer);
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
	
	public void setOnOff(String onOff) {
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
	
	public String getOnOff() {
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
