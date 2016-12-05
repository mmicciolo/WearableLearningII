package wlfe.model;

public class GameInstanceData {
	
	private int gameInstanceId;
	private int gameId;
	private GameData gameData;
	
	public GameInstanceData() {
		
	}
	
	public GameInstanceData(int gameInstanceId, int gameId, GameData gameData) {
		this.gameInstanceId = gameInstanceId;
		this.gameId = gameId;
		this.gameData = gameData;
	}
	
	public void setGameInstanceId(int gameInstanceId) {
		this.gameInstanceId = gameInstanceId;
	}
	
	public void setGameId(int gameId) {
		this.gameId = gameId;
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
	
	public GameData getGameData() {
		return this.gameData;
	}
}
