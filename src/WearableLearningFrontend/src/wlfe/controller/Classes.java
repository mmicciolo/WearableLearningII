package wlfe.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import wlfe.common.DataTableColumn;
import wlfe.common.MySQLAccessor;
import wlfe.model.ClassData;

public class Classes {
	
	private List<ClassData> classes = new ArrayList<ClassData>();;
	private List<DataTableColumn> columns = new ArrayList<DataTableColumn>();;
	
	private ClassData selectedClass;
	
	private int classId;
	private String className;
	private String school;
	private int grade;
	private int year;
	
	@PostConstruct
	public void init() {
		initColumns();
		initData();
	}
	
	private void initColumns() {
		columns.add(new DataTableColumn("Name", "name"));
		columns.add(new DataTableColumn("Teacher", "teacher"));
		columns.add(new DataTableColumn("School", "school"));
		columns.add(new DataTableColumn("Grade", "grade"));
		columns.add(new DataTableColumn("Year", "year"));
	}
	
	private void initData() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				Statement statement = accessor.GetConnection().createStatement();
				ResultSet results = statement.executeQuery("SELECT * from class where teacherId=1");
				while(results.next()) {
					int classId = results.getInt("classId");
					String className = results.getString("className");
					String teacher = "Teacher";
					int grade = results.getInt("grade");
					int year = results.getInt("year");
					String school = results.getString("school");
					classes.add(new ClassData(classId, className, teacher, school, grade, year));
				}
				results.close();
				statement.close();
			} catch(Exception e) {
				e.printStackTrace();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error has occured");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}
		}
	}
	
	public void createPressed() {
		if(!className.equals("") && !school.equals("") && (grade > 0) && year > 0 ) {
			MySQLAccessor accessor = MySQLAccessor.getInstance();
			int newId = -1;
			if(accessor.Connect()) {
				try {
					String key[] = {"classId"};
					PreparedStatement statement = accessor.GetConnection().prepareStatement("INSERT INTO class (teacherId, className, grade, school, year) VALUES (?, ?, ?, ?, ?)", key);
					//statement.setInt(1, Teacher.getInstance().GetID());
					statement.setInt(1, 1);
					statement.setString(2, className);
					statement.setInt(3, grade);
					statement.setString(4, school);
					statement.setInt(5, year);
					statement.executeUpdate();
					ResultSet rs = statement.getGeneratedKeys();
					if(rs.next()) { newId = (int)rs.getLong(1); }
					statement.close();
				} catch (Exception e) {
					e.printStackTrace();
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error has occured");
					FacesContext.getCurrentInstance().addMessage(null, message);
					return;
				}	
				classes.add(new ClassData(newId, className, "Teacher", school, grade, year));
				RequestContext.getCurrentInstance().update("main:mainTable");
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Sucessfully Deleted");
				FacesContext.getCurrentInstance().addMessage(null, message);
				RequestContext.getCurrentInstance().execute("PF('NewClass').hide();");
			}
		}
		else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error has occured");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}
	}
	
	public void editPressed() {
		if(selectedClass != null) {
			classId = selectedClass.getClassId();
			className = selectedClass.getName();
			school = selectedClass.getSchool();
			grade = selectedClass.getGrade();
			year = selectedClass.getYear();
			RequestContext.getCurrentInstance().execute("PF('EditClass').show();");
		}
	}
	
	public void editConfirmPressed() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				PreparedStatement statement = accessor.GetConnection().prepareStatement("UPDATE class SET className = ?, school = ?, grade = ?, year = ? WHERE classId=" + classId);
				statement.setString(1, className);
				statement.setString(2, school);
				statement.setInt(3, grade);
				statement.setInt(4, year);
				statement.executeUpdate();
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error has occured");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}
			RequestContext.getCurrentInstance().update("main:mainTable");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Sucessfully Deleted");
			FacesContext.getCurrentInstance().addMessage(null, message);
			classes.get(classes.lastIndexOf(selectedClass)).setName(className);
			classes.get(classes.lastIndexOf(selectedClass)).setSchool(school);
			classes.get(classes.lastIndexOf(selectedClass)).setGrade(grade);
			classes.get(classes.lastIndexOf(selectedClass)).setYear(year);
			RequestContext.getCurrentInstance().update("main:mainTable");
			RequestContext.getCurrentInstance().execute("PF('EditClass').hide();");
		}
	}
	
	public void deletePressed() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				PreparedStatement statement = accessor.GetConnection().prepareStatement("DELETE FROM class WHERE classId=" + selectedClass.getClassId());
				statement.executeUpdate();
				statement.close();
			} catch(Exception e) {
				e.printStackTrace();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error has occured");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}
			classes.remove(selectedClass);
			RequestContext.getCurrentInstance().update("main:mainTable");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Sucessfully Deleted");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public void clear() {
		className = "";
		school = "";
		grade = 0;
		year = 0;
	}
	
	public void setClasses(List<ClassData> classes) {
		this.classes = classes;
	}
	
	public void setColumns(List<DataTableColumn> columns) {
		this.columns= columns;
	}
	
	public void setSelectedClass(ClassData selectedClass) {
		this.selectedClass = selectedClass;
	}
	
	public void setClassName(String className) {
		this.className = className;
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
	
	public List<ClassData> getClasses() {
		return this.classes;
	}
	
	public List<DataTableColumn> getColumns() {
		return this.columns;
	}
	
	public ClassData getSelectedClass() {
		return this.selectedClass;
	}
	
	public String getClassName() {
		return this.className;
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
