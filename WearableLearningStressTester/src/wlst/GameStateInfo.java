package wlst;

import java.util.List;

public class GameStateInfo {
	
	private int gameStateId;
	private int gameStateCount;
	private int teamNumber;
	private int teamPlayerNumber;
	private String textContent;
	private List<Integer> buttons;

	
	public GameStateInfo() {
		
	}
	
	public GameStateInfo(int gameStateId, int gameStateCount, int teamNumber, int teamPlayerNumber, String textContext, List<Integer> buttons) {
		this.gameStateId = gameStateId;
		this.gameStateCount = gameStateCount;
		this.teamNumber = teamNumber;
		this.teamPlayerNumber = teamPlayerNumber;
		this.textContent = textContext;
		this.buttons = buttons;;	
	}
	
	public String getTextContent() {
		return this.textContent;
	}
}
