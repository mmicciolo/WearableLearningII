package wlxi;

import java.util.List;

public class GameStateInfo {
	
	private int gameStateId;
	private int gameStateCount;
	private int teamId;
	private int playerId;
	private String text;
	private String buttonInputType;
	private List<Integer> buttons;

	
	public GameStateInfo() {
		
	}
	
	public GameStateInfo(int gameStateId, int gameStateCount, int teamId, int playerId, String text, String buttonInputType, List<Integer> buttons) {
		this.gameStateId = gameStateId;
		this.gameStateCount = gameStateCount;
		this.teamId = teamId;
		this.playerId = playerId;
		this.text = text;
		this.buttonInputType = buttonInputType;
		this.buttons = buttons;;	
	}
	
	public void setGameStateId(int gameStateId) {
		this.gameStateId = gameStateId;
	}
	
	public int getGameStateId() {
		return this.gameStateCount;
	}
	
	public int getGameStateCount() {
		return this.gameStateCount;
	}
	
	public int getTeamId() {
		return this.teamId;
	}
	
	public int getPlayerId() {
		return this.playerId;
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getButtonInputType() {
		return this.buttonInputType;
	}
	
	public List<Integer> getButtons() {
		return this.buttons;
	}
}
