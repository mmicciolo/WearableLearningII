package wlfe.model;

public class ClassData {
	
	private int classId;
	private String name;
	private String teacher;
	private String school;
	private int grade;
	private int year;
	
	
	public ClassData() {
		
	}
	
	public ClassData(int classId, String name, String teacher, String school, int grade, int year) {
		this.classId = classId;
		this.name = name;
		this.teacher = teacher;
		this.school = school;
		this.grade = grade;
		this.year = year;
	}
	
	public void setClassId(int classId) {
		this.classId = classId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	public void setSchool(String school) {
		this.school = school;
	}
	
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getClassId() {
		return this.classId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getTeacher() {
		return this.teacher;
	}
	
	public String getSchool() {
		return this.school;
	}
	
	public int getGrade() {
		return this.grade;
	}
	
	public int getYear() {
		return this.year;
	}
}
