package wlfe.model;

public class GameData {
	
	private int gameId = 0;
	private String title = "";
	private int teamCount ;
	private int playersPerTeam;
	
	public GameData() {
		
	}
	
	public GameData(int gameId, String title, int teamCount, int playersPerTeam) {
		this.gameId = gameId;
		this.title = title;
		this.teamCount = teamCount;
		this.playersPerTeam = playersPerTeam;
	}
	
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setTeamCount(int teamCount) {
		this.teamCount = teamCount;
	}
	
	public void setPlayersPerTeam(int playersPerTeam) {
		this.playersPerTeam = playersPerTeam;
	}
	
	public int getGameId() {
		return this.gameId;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public int getTeamCount() {
		return this.teamCount;
	}
	
	public int getPlayersPerTeam() {
		return this.playersPerTeam;
	}
}
