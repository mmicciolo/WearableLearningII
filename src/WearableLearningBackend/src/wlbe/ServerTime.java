package wlbe;

public class ServerTime {
	
	private final long startTime;
	
	public ServerTime() {
		startTime = System.currentTimeMillis();
	}
	
	public double elapsedTime() {
		return System.currentTimeMillis() - startTime;
	}
	
	public long getStartTime() {
		return this.startTime;
	}
}
