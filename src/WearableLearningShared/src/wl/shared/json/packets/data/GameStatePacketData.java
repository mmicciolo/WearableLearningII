package wl.shared.json.packets.data;

public class GameStatePacketData {
	
	private DisplayData displayData = new DisplayData();

	public GameStatePacketData() {
		
	}
	
	public DisplayData getDisplayData() {
		return this.displayData;
	}
}
