package wlbe.model;

public class PlayerData {
	
	private String playerName;
	private ClientData clientData = new ClientData();
	
	public PlayerData() {
		
	}
	
	public PlayerData(String playerName, ClientData clientData) {
		this.playerName = playerName;
		this.clientData = clientData;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public ClientData getClientData() {
		 return this.clientData;
	}
}
