package wlfe.model;

public class Teacher {
	
	private int teacherId;
	private String email;
	private String firstName;
	private String lastName;
	private String school;
	
	public Teacher() {
		
	}
	
	public Teacher(int teacherId, String email, String firstName, String lastName, String school) {
		this.teacherId = teacherId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.school = school;
	}
	
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setFirstName(String firstName) { 
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setSchool(String school) {
		this.school = school;
	}
	
	public int getTeacherId() {
		return this.teacherId;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getSchool() {
		return this.school;
	}
}
