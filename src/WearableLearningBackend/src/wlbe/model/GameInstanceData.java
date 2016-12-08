package wlbe.model;

public class GameInstanceData {
	
	private String title;
	private int teamCount;
	private int playersPerTeam;
	private int gameId;
	private int gameInstanceId;
	private boolean[][] playerArray = null;
	public int[] playerCount;
	
	public GameInstanceData() {
		
	}
	
	public int addPlayer(int team) {
		if(playerArray == null) {
			playerArray = new boolean[teamCount][playersPerTeam];
			playerCount = new int[teamCount];
			for(int i = 0; i < teamCount; i++) { playerCount[i] = 1; }
		} 
		for(int i = 0; i < playersPerTeam; i++) {
			if(playerArray[team - 1][i] == false) {
				playerArray[team - 1][i] = true;
				return i + 1;
			}
		}
		return -1;
	}
	
//	public boolean addPlayer(int team) {
//		if(playerArray == null) {
//			playerArray = new boolean[teamCount][playersPerTeam];
//			playerCount = new int[teamCount];
//			for(int i = 0; i < teamCount; i++) { playerCount[i] = 1; }
//			playerArray[team - 1][playerCount[team - 1]++ - 1] = true;
//			return true;
//		} else {
//			if(playerCount[team - 1] <= playersPerTeam) {
//				playerArray[team - 1][playerCount[team - 1]++] = true;
//				return true;
//			}
//		}
//		return false;
//	}
	
	public void removePlayer(int team, int player) {
		playerArray[team - 1][player - 1] = true;
	}
	
	public boolean getPlayer(int team, int player) {
		return playerArray[team - 1][player - 1];
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
	
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
	public void setGameInstanceId(int gameInstanceId) {
		this.gameInstanceId = gameInstanceId;
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
	
	public int getGameId() {
		return this.gameId;
	}
	
	public int getGameInstanceId() {
		return this.gameInstanceId;
	}
}
