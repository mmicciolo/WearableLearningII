package wlfe.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import wlfe.common.BaseHeaderMenuTableContentFooter;
import wlfe.common.Common;
import wlfe.common.DataTableColumn;
import wlfe.common.MySQLAccessor;
import wlfe.model.ClassData;

public class Classes2 extends BaseHeaderMenuTableContentFooter {
	
	private List<ClassData> classes = new ArrayList<ClassData>();
	private Map<String, DataTableColumn> classes2 = new LinkedHashMap<String, DataTableColumn>();
	private ClassData selectedClass;
	private int classId;
	
	protected boolean initColumns() {
		if(columns.add(new DataTableColumn("Name", "className")) &&
		   columns.add(new DataTableColumn("Teacher", "teacher")) &&
		   columns.add(new DataTableColumn("School", "school")) &&
		   columns.add(new DataTableColumn("Grade", "grade")) &&
		   columns.add(new DataTableColumn("Year", "year")) &&
		
		   inputTextNames.add(new DataTableColumn("Class Name", "")) &&
		   inputTextNames.add(new DataTableColumn("School", "")) &&
		   inputTextNames.add(new DataTableColumn("Grade", "")) &&
		   inputTextNames.add(new DataTableColumn("Year", ""))) { 
		   classes2.put("classId", new DataTableColumn("Class Id", ""));
		   classes2.put("className", new DataTableColumn("Class Name", ""));
		   classes2.put("", new DataTableColumn("Teacher", "teacher"));
		   classes2.put("school", new DataTableColumn("School", ""));
		   classes2.put("grade", new DataTableColumn("Grade", ""));
		   classes2.put("year", new DataTableColumn("Year", ""));
		   return true;
		}
		return false;
	}
			
	public boolean initData() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				Statement statement = accessor.GetConnection().createStatement();
				ResultSet results = statement.executeQuery("SELECT * from class where teacherId=1");
				while(results.next()) {
					ClassData classData = new ClassData();
					String returnId[] = {""};
					MySQLSetGet(false, null, returnId, results, classes2, classData, 1);
					classes.add(classData);
				}
				results.close();
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
				Common.ErrorMessage();
				return false;
			}
			return true;
		}
		return false;
	}
	
	public void createPressed() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				String returnId[] = {"classId"};
				PreparedStatement preparedStatment = accessor.GetConnection().prepareStatement("INSERT INTO class (teacherId, className, school, grade, year) VALUES (?, ?, ?, ?, ?)", returnId);
				ClassData classData = new ClassData();
				preparedStatment.setInt(1, 1);
				createMySQLEntry(preparedStatment, classes2, classData, returnId);
			} catch (Exception e) {
				e.printStackTrace();
				Common.ErrorMessage();
			}
			classes.add(new ClassData(Integer.parseInt(classes2.get("classId").getProperty()), classes2.get("className").getProperty(), "teacher", classes2.get("school").getProperty(), Integer.parseInt(classes2.get("grade").getProperty()), Integer.parseInt(classes2.get("year").getProperty())));
			RequestContext.getCurrentInstance().update("main:mainTable");
			RequestContext.getCurrentInstance().execute("PF('NewClass').hide();");
			Common.SuccessMessage();
		}
	}
	
	public void editPressed() {
		if(selectedClass != null) {
			classId = selectedClass.getClassId();
			DataTableColumn.setPropertyFromHeader("Class Name", selectedClass.getClassName(), inputTextNames);
			DataTableColumn.setPropertyFromHeader("School", selectedClass.getSchool(), inputTextNames);
			DataTableColumn.setPropertyFromHeader("Grade", String.valueOf(selectedClass.getGrade()), inputTextNames);
			DataTableColumn.setPropertyFromHeader("Year", String.valueOf(selectedClass.getYear()), inputTextNames);
			RequestContext.getCurrentInstance().execute("PF('EditClass').show();");
		}
	}
	
	public void editConfirmPressed() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				PreparedStatement statement = accessor.GetConnection().prepareStatement("UPDATE class SET className = ?, school = ?, grade = ?, year = ? WHERE classId=" + classId);
				statement.setString(1, DataTableColumn.getPropertyFromHeader("Class Name", inputTextNames));
				statement.setString(2, DataTableColumn.getPropertyFromHeader("School", inputTextNames));
				statement.setInt(3, Integer.parseInt(DataTableColumn.getPropertyFromHeader("Grade", inputTextNames)));
				statement.setInt(4, Integer.parseInt(DataTableColumn.getPropertyFromHeader("Year", inputTextNames)));
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
			classes.get(classes.lastIndexOf(selectedClass)).setClassName(DataTableColumn.getPropertyFromHeader("Class Name", inputTextNames));
			classes.get(classes.lastIndexOf(selectedClass)).setSchool(DataTableColumn.getPropertyFromHeader("School", inputTextNames));
			classes.get(classes.lastIndexOf(selectedClass)).setGrade(Integer.parseInt(DataTableColumn.getPropertyFromHeader("Grade", inputTextNames)));
			classes.get(classes.lastIndexOf(selectedClass)).setYear(Integer.parseInt(DataTableColumn.getPropertyFromHeader("Year", inputTextNames)));
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
				Common.ErrorMessage();
				return;
			}
			classes.remove(selectedClass);
			RequestContext.getCurrentInstance().update("main:mainTable");
			Common.SuccessMessage();
		}
	}
	
	public void clear() {
		classes2.get("className").setProperty("");
		classes2.get("school").setProperty("");
		classes2.get("grade").setProperty("");
		classes2.get("year").setProperty("");
	}
	
	public void setClasses(List<ClassData> classes) {
		this.classes = classes;
	}
	
	public void setClasses2(Map<String, DataTableColumn> classes) {
		this.classes2 = classes;
	}
	
	public void setSelectedClass(ClassData selectedClass) {
		this.selectedClass = selectedClass;
	}
	
	public List<ClassData> getClasses() {
		return this.classes;
	}
	
	public Map<String, DataTableColumn> getClasses2() {
		return this.classes2;
	}
	
	public ClassData getSelectedClass() {
		return this.selectedClass;
	}
}
