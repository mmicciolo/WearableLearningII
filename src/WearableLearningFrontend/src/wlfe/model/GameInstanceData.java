package wlfe.model;

public class GameInstanceData {
	
	private int gameInstanceId;
	private int gameId;
	private int currentGameStateId;
	private GameData gameData;
	
	public GameInstanceData() {
		
	}
	
	public GameInstanceData(int gameInstanceId, int gameId, int currentGameStateId, GameData gameData) {
		this.gameInstanceId = gameInstanceId;
		this.gameId = gameId;
		this.currentGameStateId = currentGameStateId;
		this.gameData = gameData;
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
	
	public void setGameData(GameData gameData) {
		this.gameData = gameData;
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
	
	public GameData getGameData() {
		return this.gameData;
	}
}
