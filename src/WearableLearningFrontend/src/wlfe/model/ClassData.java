package wlfe.model;

public class ClassData {
	
	private int classId = 0;
	private String className = "";
	private String teacher = "";
	private String school = "";
	private int grade = 0;
	private int year = 0;
	
	
	public ClassData() {
		
	}
	
	public ClassData(int classId, String className, String teacher, String school, int grade, int year) {
		this.classId = classId;
		this.className = className;
		this.teacher = teacher;
		this.school = school;
		this.grade = grade;
		this.year = year;
	}
	
	public void setClassId(int classId) {
		this.classId = classId;
	}
	
	public void setClassName(String className) {
		this.className = className;
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
	
	public String getClassName() {
		return this.className;
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
