package wlst;

public class PlayerInfo {
	
	private String playerName;
	private int gameInstanceId;
	private int teamNumber;
	private int teamPlayerNumber;
	private BackendServer backend;

	
	public PlayerInfo() {
		
	}
	
	public PlayerInfo(String playerName, int gameInstanceId, int teamNumber, int teamPlayerNumber) {
		this.playerName = playerName;
		this.gameInstanceId = gameInstanceId;
		this.teamNumber = teamNumber;
		this.teamPlayerNumber = teamPlayerNumber;
	}
	
	public void setBackendServer(BackendServer backend) {
		this.backend = backend;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public void setTeamPlayerNumber(int teamPlayerNumber) {
		this.teamPlayerNumber = teamPlayerNumber;
	}
	
	public int getGameInstanceId() {
		return this.gameInstanceId;
	}
	
	public int getTeamNumber() {
		 return this.teamNumber;
	}
	
	public int getTeamPlayerNumber() {
		return this.teamPlayerNumber;
	}
	
	public BackendServer getBackendServer() {
		return this.backend;
	}
}
