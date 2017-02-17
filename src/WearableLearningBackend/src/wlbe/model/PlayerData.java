package wlbe.model;

public class PlayerData {
	
	private String playerName;
	private int playerId;
	private int playerNumber;
	private int teamNumber;
	private int currentGameStateId;
	private int currentGameState = 1;
	private ClientData clientData = new ClientData();
	
	public PlayerData() {
		
	}
	
	public PlayerData(String playerName, int teamNumber, ClientData clientData) {
		this.playerName = playerName;
		this.teamNumber = teamNumber;
		this.clientData = clientData;
	}
	
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	public void setCurrentGameStateId(int currentGameStateId) {
		this.currentGameStateId = currentGameStateId;
	}
	
	public void setCurrentGameState(int currentGameState) {
		this.currentGameState = currentGameState;
	}
	
	public void setClientData(ClientData clientData) {
		this.clientData = clientData;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public int getPlayerNumber() {
		return this.playerNumber;
	}
	
	public int getTeamNumber() {
		 return this.teamNumber;
	}
	
	public int getPlayerId() {
		return this.playerId;
	}
	
	public int getCurrentGameStateId() {
		return this.currentGameStateId;
	}
	
	public int getCurrentGameState() {
		return this.currentGameState;
	}
	
	public ClientData getClientData() {
		 return this.clientData;
	}
}
