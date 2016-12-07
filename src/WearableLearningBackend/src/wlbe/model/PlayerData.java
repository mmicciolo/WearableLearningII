package wlbe.model;

public class PlayerData {
	
	private String playerName;
	private int playerId;
	private int currentGameState = 1;
	private ClientData clientData = new ClientData();
	
	public PlayerData() {
		
	}
	
	public PlayerData(String playerName, ClientData clientData) {
		this.playerName = playerName;
		this.clientData = clientData;
	}
	
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public int getPlayerId() {
		return this.playerId;
	}
	
	public int getCurrentGameState() {
		return this.currentGameState;
	}
	
	public ClientData getClientData() {
		 return this.clientData;
	}
}
