package wl.shared.json.packets.data;

public class PlayerPacketData  {
	
	private int playerId;
	private int gameId;
	private int gameInstanceId;
	
	public PlayerPacketData() {
		
	}
	
	public PlayerPacketData(int playerId, int gameId, int gameInstanceId) {
		this.playerId = playerId;
		this.gameId = gameId;
		this.gameInstanceId = gameInstanceId;
	}
	
	public int getPlayerId() {
		return this.playerId;
	}
	
	public int getGameId() {
		return this.gameId;
	}
	
	public int getGameInstanceId() {
		return this.gameInstanceId;
	}
}
