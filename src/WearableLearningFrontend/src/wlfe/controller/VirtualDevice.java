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

import wl.shared.json.packet.IJSONPacket;
import wl.shared.json.packets.ButtonPacket;
import wl.shared.json.packets.DisplayPacket;
import wl.shared.json.packets.GameStatePacket;
import wl.shared.json.packets.PlayerPacket;
import wl.shared.json.packets.data.ButtonData;
import wl.shared.json.packets.data.PlayerPacketData;
import wlfe.common.BackendServer;
import wlfe.common.MySQLAccessor;
import wlfe.model.GameData;

public class VirtualDevice {
	
	private String displayText = "";
	private String studentName;
	private List<GameData> gameInstances = new ArrayList<GameData>();
	private GameData selectedGame;
	private String selectedTeam;
	private boolean on = false;
	private PlayerPacketData playerData = null;
	
	private BackendServer backendServer;
	
	@PostConstruct
	public void init() {
		
	}
	
	public void button1() {
		if(on) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
			byteBuffer.putInt(3);
			ButtonPacket buttonPacket = new ButtonPacket();
			ButtonData data = new ButtonData(playerData.getPlayerId(), 0);
			buttonPacket.setButtonData(data);
			byteBuffer.putInt(0);
			backendServer.putString(buttonPacket.getGson(), byteBuffer);
			backendServer.write(byteBuffer);
		}
	}
	
	public void button2() {
		if(on) {
			displayText = "Button 2 Pushed!\nColor: Green";
		}
	}
	
	public void button3() {
		if(on) {
			displayText = "Button 3 Pushed!\nColor: Blue";
		}
	}
	
	public void button4() {
		if(on) {
			displayText = "Button 4 Pushed!\nColor: Black";
		}
	}
	
	private void connectToBackend() {
		backendServer = new BackendServer(this);
		ByteBuffer buffer = ByteBuffer.allocate(65536);
		buffer.putInt(1);
		backendServer.putString(studentName, buffer);
		backendServer.putString(selectedTeam, buffer);
		buffer.putInt(selectedGame.getGameId());
		backendServer.write(buffer);
		if(!on) { backendServer.read(); }
		on = true;
	}
	
	private void disconnectFromBackend() {
		ByteBuffer buffer = ByteBuffer.allocate(65536);
		buffer.putInt(2);
		buffer.putInt(selectedGame.getGameId());
		backendServer.putString(studentName, buffer);
		backendServer.write(buffer);
		backendServer.disconnect();
		displayText = "Disconnected!";
		on = false;
	}
	
	public void disconnect() {
		disconnectFromBackend();
	}
	
	public void packetRecieved(IJSONPacket packet) {
		switch(packet.getType()) {
			case GAME_START:
				displayText += "Game Starting...\n";
				break;
			case GAME_END:
				break;
			case PLAYER_DATA:
				PlayerPacket playerPacket = (PlayerPacket) packet;
				playerData = playerPacket.getPlayerData();
				break;
			case GAME_STATE:
				GameStatePacket gameStatePacket = (GameStatePacket) packet;
				displayText = gameStatePacket.getGameStatePacketData().getDisplayData().text;
				break;
			case DISPLAY:
				DisplayPacket displayPacket = (DisplayPacket) packet;
				displayText = displayPacket.getDisplayData().text;
				break;
			default:
				break;
		}
		backendServer.read();
	}
	
	public String onFlowProcess(FlowEvent event) {
		if(event.getNewStep().equals("activeGames")) {
			loadActiveGames();
		}
		else if(event.getNewStep().equals("virtualDevice")) {
			if(!on) {displayText = "";}
			connectToBackend();
			while(displayText.equals("")) {RequestContext.getCurrentInstance();}
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
