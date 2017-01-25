package wlst;

public class ServerInfo {
	
	private String serverIp;
	private int portNumber;
	
	public ServerInfo() {
		
	}
	
	public ServerInfo(String serverIp, int portNumber) {
		this.serverIp = serverIp;
		this.portNumber = portNumber;
	}
	
	public String getServerIp() {
		return this.serverIp;
	}
	
	public int getPortNumber() {
		return this.portNumber;
	}
}
