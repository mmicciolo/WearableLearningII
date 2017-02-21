package wlbe.tasks;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import wl.shared.json.packet.IJSONPacket;
import wl.shared.json.packets.ButtonPacket;
import wl.shared.json.packets.DisplayPacket;
import wl.shared.json.packets.GameStartPacket;
import wl.shared.json.packets.GameStatePacket;
import wl.shared.json.packets.PlayerPacket;
import wl.shared.json.packets.data.ButtonColor;
import wl.shared.json.packets.data.ButtonData;
import wl.shared.json.packets.data.PlayerPacketData;
import wl.shared.model.Button;
import wlbe.event.IEvent;
import wlbe.events.PacketRecieved;
import wlbe.model.GameInstanceData;
import wlbe.model.PlayerData;
import wlbe.module.ModuleManager;
import wlbe.modules.Logger;
import wlbe.modules.Server;
import wlbe.modules.TaskManager;
import wlbe.packet.Packet;
import wlbe.packets.ConnectPacket;
import wlbe.packets.DisconnectPacket;
import wlbe.packets.JSONPacket;
import wlbe.task.Task;

public class GameInstance extends Task {
	
	private TaskManager taskManager;
	private MySQLDaemon mySQLDaemon;
	private ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	private Logger logger;
	private GameInstanceData gameInstanceData = new GameInstanceData();
	
	public GameInstance(int gameInstanceId, int gameId) {
		setName("Game Instance " + gameInstanceId);
		gameInstanceData.setGameInstanceId(gameInstanceId);
		gameInstanceData.setGameId(gameId);
		mySQLDaemon = new MySQLDaemon();
		getGameData();
		taskManager = (TaskManager) ModuleManager.getModule(ModuleManager.Modules.TASK_MANAGER);
		taskManager.addTask(mySQLDaemon);
		logger = (Logger) ModuleManager.getModule(ModuleManager.Modules.LOGGER);
	}
	
	public void update() {
		for(PlayerData player : players) {
			
		}
	}
	
	public void cleanup() {
		for(PlayerData player : players) {
			removePlayer(player);
		}
		taskManager.removeTask(mySQLDaemon);
	}
	
	public void endInstance() {
		taskManager.removeTask(this);
	}
	
	public void eventHandler(IEvent e) {
		if(e instanceof PacketRecieved) {
			PacketRecieved packetRecieved = (PacketRecieved) e;
			Packet packet = (Packet)packetRecieved.getPacket(); if (packet == null) { return; }
			if(packet.getClientData().getGameInstanceId() == gameInstanceData.getGameInstanceId() || packet.getClientData().getGameInstanceId() == -1) {
				switch(packetRecieved.getPacket().getType()) {
				case PLAYER_CONNECT:
					playerConnect((ConnectPacket)packetRecieved.getPacket());
					break;
				case PLAYER_DISCONNECT:
					playerDisconnect((DisconnectPacket)packetRecieved.getPacket());
					break;
				case JSON_PACKET:
					handleJSONPacket((JSONPacket)packetRecieved.getPacket());
					break;
				default:
					break;
				}
			}
		}
	}
	
	public void getGameData() {
		try {
			Statement statement = mySQLDaemon.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM games WHERE gameId=" + gameInstanceData.getGameId());
			if(resultSet.next()) {
				gameInstanceData.setTitle(resultSet.getString("title"));
				gameInstanceData.setTeamCount(resultSet.getInt("teamCount"));
				gameInstanceData.setPlayersPerTeam(resultSet.getInt("playersPerTeam"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playerConnect(ConnectPacket packet) {
		if(packet.getGameInstanceId() == gameInstanceData.getGameInstanceId()) {
			for(PlayerData player : players) {
				if(player.getPlayerName().equals(packet.getStudentName())) {
					player.setClientData(packet.getClientData());
					logger.write("Client Reconnected...");
					sendPlayerData(player);
					sendGameState(player);
					return;
				}
			}
			PlayerData player = new PlayerData(packet.getStudentName(), Integer.parseInt(packet.getSelectedTeam().replace("Team ", "")), packet.getClientData());
			int playerId = -1;
			if((playerId = gameInstanceData.addPlayer(Integer.parseInt(packet.getSelectedTeam().replace("Team ", "")))) != -1) {
				players.add(player);
				player.setPlayerNumber(playerId);
				setupNewPlayer(player);
			} else {
				//Team Full
				IJSONPacket display = new DisplayPacket("Team " + packet.getSelectedTeam() + " is full!");
				JSONPacket jsonPacket = new JSONPacket();
				jsonPacket.setJSONPacket(display);
				Server server = (Server) ModuleManager.getModule(ModuleManager.Modules.SERVER);
				server.write(player.getClientData(), jsonPacket);
				logger.write("Client Disconnected... " + packet.getSelectedTeam() + " is full!");
			}
		}
	}
	
	public void playerDisconnect(DisconnectPacket packet) {
		if(packet.getGameInstanceId() == gameInstanceData.getGameInstanceId()) {
			for(int i = 0; i < players.size(); i++) {
				PlayerData player = (PlayerData) players.toArray()[i];
				if(player != null && player.getPlayerName().equals(packet.getStudentName())) {
					removePlayer(player);
					players.remove(player);
					gameInstanceData.removePlayer(player.getTeamNumber(), player.getPlayerNumber());
					Logger logger = (Logger) ModuleManager.getModule(ModuleManager.Modules.LOGGER);
					logger.write("Client Disconnected...");
					break;
				}
			}
		}
	}
	
	private void setupNewPlayer(PlayerData player) {
		try {
			String names[] = player.getPlayerName().split(", ");
			Statement statement = mySQLDaemon.getConnection().createStatement();
			ResultSet results = statement.executeQuery("SELECT * FROM student WHERE lastName=" + "'" + names[0] + "'" + " AND firstName=" + "'" + names[1] + "'");
			if(results.next()) {
				String returnId[] = {"playerId"};
				PreparedStatement preparedStatement = mySQLDaemon.getConnection().prepareStatement("INSERT INTO players (gameInstanceId, studentId, currentGameState) VALUES (?, ?, ?)", returnId);
				preparedStatement.setInt(1, gameInstanceData.getGameInstanceId());
				preparedStatement.setInt(2, results.getInt("studentId"));
				preparedStatement.setInt(3, player.getCurrentGameState());
				preparedStatement.execute();
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if(rs.next()) {
					player.setPlayerId((int)rs.getLong(1));
				}
				rs.close();
				preparedStatement.close();
				results.close();
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sendPlayerData(player);
		sendGameStart(player);
		sendGameState(player);
	}
	
	private void sendPlayerData(PlayerData player) {
		Server server = (Server) ModuleManager.getModule(ModuleManager.Modules.SERVER);
		PlayerPacket playerPacket = new PlayerPacket();
		playerPacket.setPlayerData(new PlayerPacketData(player.getPlayerId(), gameInstanceData.getGameId(), gameInstanceData.getGameInstanceId()));
		JSONPacket jsonPacket = new JSONPacket();
		jsonPacket.setJSONPacket(playerPacket);
		server.write(player.getClientData(), jsonPacket);
	}
	
	private void sendGameStart(PlayerData player) {
		Server server = (Server) ModuleManager.getModule(ModuleManager.Modules.SERVER);
		GameStartPacket startGamePacket = new GameStartPacket();
		JSONPacket jsonPacket = new JSONPacket();
		jsonPacket.setJSONPacket(startGamePacket);
		server.write(player.getClientData(), jsonPacket);
	}
	
	private void sendGameState(PlayerData player) {
		String text = "";
		try {
			Statement statement = mySQLDaemon.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM gameState WHERE gameId=" + gameInstanceData.getGameId() + " AND gameStateCount=" + player.getCurrentGameState());
			if(resultSet.next()) {
				text = resultSet.getString("textContent");
				player.setCurrentGameStateId(resultSet.getInt("gameStateId"));
			}
		} catch (Exception e) {
			
		}
		
		Server server = (Server) ModuleManager.getModule(ModuleManager.Modules.SERVER);
		GameStatePacket gameStatePacket = new GameStatePacket();
		gameStatePacket.getGameStatePacketData().getDisplayData().text = text;
		JSONPacket jsonPacket = new JSONPacket();
		jsonPacket.setJSONPacket(gameStatePacket);
		server.write(player.getClientData(), jsonPacket);
	}
	
	private boolean removePlayer(PlayerData playerData) {
		try {
			PreparedStatement statement = mySQLDaemon.getConnection().prepareStatement("DELETE FROM players WHERE playerId=" + playerData.getPlayerId());
			statement.execute();
			statement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void handleJSONPacket(JSONPacket packet) {
		switch(packet.getJSONPacket().getType()) {
			case BUTTON:
				ButtonPacket buttonPacket = (ButtonPacket) packet.getJSONPacket();
				handleButtonPress(buttonPacket);
				break;
			default:
				break;
		}
	}
	
	private void handleButtonPress(ButtonPacket buttonPacket) {
		ButtonColor buttonColor = ButtonColor.values()[buttonPacket.getButtonData().getButtonNumber()];
		PlayerData player = null;
		for(PlayerData playerp : players) {
			if(playerp.getPlayerId() == buttonPacket.getButtonData().getplayerId()) {
				player = playerp;
			}
		}
		setNextGameStateForPlayer(buttonColor, player);
		sendGameState(player);
	}

	private void setNextGameStateForPlayer(ButtonColor buttonColor, PlayerData player) {		
		try {
			Statement statementt = mySQLDaemon.getConnection().createStatement();
			ResultSet rs = statementt.executeQuery("SELECT * FROM gameState WHERE gameStateId=" + player.getCurrentGameStateId());
			if(rs.next()) {
				int teamId = rs.getInt("teamId");
				int playerId = rs.getInt("playerId");
				Statement statement = mySQLDaemon.getConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM gameStateTransitions WHERE gameStateId=" + player.getCurrentGameStateId() +" AND singlePushButtonColor=" + buttonColor.ordinal());
				if(teamId == 0 && playerId == 0) {
					if(resultSet.next()) {
						for(int i = 1; i <= gameInstanceData.getTeamCount(); i++) {
							if(i == player.getTeamNumber()) {
								int nextGameState = resultSet.getInt("nextGameStateTransition");
								if(nextGameState != 0) {
									player.setCurrentGameState(nextGameState);
									player.setCurrentGameStateId(resultSet.getInt("gameStateId"));
								}
								break;
							}
							resultSet.next();
						}
					}
//					if(resultSet.next()) {
//						int nextGameState = resultSet.getInt("nextGameStateTransition");
//						if(nextGameState != 0) {
//							player.setCurrentGameState(nextGameState);
//							player.setCurrentGameStateId(resultSet.getInt("gameStateId"));
//						}
//					}
				} else if(teamId > 0 & playerId == 0) {
					if(resultSet.next()) {
						for(int n = 0; n < player.getTeamNumber() - 1; n++) {
							resultSet.next();
						}
						int nextGameState = resultSet.getInt("nextGameStateTransition");
						if(nextGameState != 0) {
							player.setCurrentGameState(nextGameState);
							player.setCurrentGameStateId(resultSet.getInt("gameStateId"));
						}
					}
				} else if(teamId > 0 && playerId == -1) {
					if(resultSet.next()) {
						for(int n = 0; n < player.getPlayerNumber() - 1; n++) {
							resultSet.next();
						}
						int nextGameState = resultSet.getInt("nextGameStateTransition");
						if(nextGameState != 0) {
							player.setCurrentGameState(nextGameState);
							player.setCurrentGameStateId(resultSet.getInt("gameStateId"));
						}
					}
					
				}else if (teamId == 0 && playerId > 0) {
					if(resultSet.next()) {
						int nextGameState = resultSet.getInt("nextGameStateTransition");
						if(nextGameState != 0) {
							player.setCurrentGameState(nextGameState);
							player.setCurrentGameStateId(resultSet.getInt("gameStateId"));
						}
					}
				} else {
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getGameId() {
		return this.gameInstanceData.getGameId();
	}
	
	public int getGameInstanceId() {
		return this.gameInstanceData.getGameInstanceId();
	}
}
