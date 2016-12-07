package wl.shared.json.packets.data;

public class ButtonData {
	
	private int playerId;
	private int buttonNumber;
	
	public ButtonData() {
		
	}
	
	public ButtonData(int playerId, int buttonNumber) {
		this.playerId = playerId;
		this.buttonNumber = buttonNumber;
	}
	
	public void setButtonNumber(int buttonNumber) {
		this.buttonNumber = buttonNumber;
	}
	
	public int getplayerId() {
		return this.playerId;
	}
	
	public int getButtonNumber() {
		return this.buttonNumber;
	}
}
