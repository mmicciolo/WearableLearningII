package wlfe.model;

public class GameInstanceData {
	
	private int gameInstanceId;
	private int gameId;
	private int currentGameStateId;
	
	public GameInstanceData() {
		
	}
	
	public GameInstanceData(int gameInstanceId, int gameId, int currentGameStateId) {
		this.gameInstanceId = gameInstanceId;
		this.gameId = gameId;
		this.currentGameStateId = currentGameStateId;
	}
	
	public void setGameInstanceId(int gameInstanceId) {
		this.gameInstanceId = gameInstanceId;
	}
	
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
	public void setCurrentGameStateId(int currentGameStateId) {
		this.currentGameStateId = currentGameStateId;
	}
	
	public int getGameInstanceId() {
		return this.gameInstanceId;
	}
	
	public int getGameId() {
		return this.gameId;
	}
	
	public int getCurrentGameStateId() {
		return this.currentGameStateId;
	}
}
