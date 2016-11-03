package wlfe.model;

public class StudentData {
	
	private int studentId = 0;
	private String firstName = "";
	private String lastName = "";
	private String gender = "";
	private int age = 0;
	private int classId = 0;
	private String className = "";
	
	public StudentData() {
		
	}
	
	public StudentData(int studentId, String firstName, String lastName, String gender, int age, int classId, String className) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
		this.classId = classId;
		this.className = className;
	}
	
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setClassId(int classId) {
		this.classId = classId;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public int getStudentId() {
		return this.studentId;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public int getClassId() {
		return this.classId;
	}
	
	public String getClassName() {
		return this.className;
	}
}
