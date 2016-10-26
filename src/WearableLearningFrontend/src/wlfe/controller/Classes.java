package wlfe.controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import wlfe.common.DataTableColumn;
import wlfe.common.MySQLAccessor;
import wlfe.model.ClassData;

public class Classes {
	
	private List<ClassData> classes = new ArrayList<ClassData>();;
	private List<DataTableColumn> columns = new ArrayList<DataTableColumn>();;
	
	private ClassData selectedClass;
	private int rowIndex;
	
	private String className;
	
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
					String className = results.getString("className");
					String teacher = "Teacher";
					int grade = results.getInt("grade");
					int year = results.getInt("year");
					String school = results.getString("school");
					classes.add(new ClassData(className, teacher, school, grade, year));
				}
				results.close();
				statement.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createPressed() {
		
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
	
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	
	public void setClassName(String className) {
		this.className = className;
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
	
	public int getRowIndex() {
		return this.rowIndex;
	}
	
	public String getClassName() {
		return this.className;
	}
}
