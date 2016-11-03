package wlfe.model;

public class DeviceData {
	
	private int deviceId = 0;
	private String ipAddress = "";
	private String macAddress = "";
	private int studentId = 0;
	private int connected = 0;
	
	public DeviceData() {
		
	}
	
	public DeviceData(int deviceId, String ipAddress, String macAddress, int studentId, int connected) {
		this.deviceId = deviceId;
		this.ipAddress = ipAddress;
		this.macAddress = macAddress;
		this.studentId = studentId;
		this.connected = connected;
	}
	
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public void setConnected(int connected) {
		this.connected = connected;
	}
	
	public int getDeviceId() {
		return this.deviceId;
	}
	
	public String getIpAddress() {
		return this.ipAddress;
	}
	
	public String getMacAddress() {
		return this.macAddress;
	}
	
	public int getStudentId() {
		return this.studentId;
	}
	
	public int getConnected() {
		return this.connected;
	}
}
