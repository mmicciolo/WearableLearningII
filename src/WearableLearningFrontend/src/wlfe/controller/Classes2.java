package wlfe.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import wlfe.common.BaseHeaderMenuTableContentFooter;
import wlfe.common.DataTableColumn;
import wlfe.common.MySQLAccessor;
import wlfe.model.ClassData;

public class Classes2 extends BaseHeaderMenuTableContentFooter {
	
	private List<ClassData> classes = new ArrayList<ClassData>();;
	private ClassData selectedClass;
	private int classId;
	
	protected boolean initColumns() {
		if(columns.add(new DataTableColumn("Name", "name")) &&
		   columns.add(new DataTableColumn("Teacher", "teacher")) &&
		   columns.add(new DataTableColumn("School", "school")) &&
		   columns.add(new DataTableColumn("Grade", "grade")) &&
		   columns.add(new DataTableColumn("Year", "year")) &&
		
		   inputTextNames.add(new DataTableColumn("Class Name", "")) &&
		   inputTextNames.add(new DataTableColumn("School", "")) &&
		   inputTextNames.add(new DataTableColumn("Grade", "")) &&
		   inputTextNames.add(new DataTableColumn("Year", ""))) { 
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
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error has occured");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return false;
			}
		}
		return false;
	}
	
	public void createPressed() {
		if(!DataTableColumn.getPropertyFromHeader("Class Name", inputTextNames).equals("") && !DataTableColumn.getPropertyFromHeader("School", inputTextNames).equals("") && !DataTableColumn.getPropertyFromHeader("Grade", inputTextNames).equals("") && !DataTableColumn.getPropertyFromHeader("Year", inputTextNames).equals("")) {
			MySQLAccessor accessor = MySQLAccessor.getInstance();
			int newId = -1;
			if(accessor.Connect()) {
				try {
					String key[] = {"classId"};
					PreparedStatement statement = accessor.GetConnection().prepareStatement("INSERT INTO class (teacherId, className, grade, school, year) VALUES (?, ?, ?, ?, ?)", key);
					//statement.setInt(1, Teacher.getInstance().GetID());
					statement.setInt(1, 1);
					statement.setString(2, DataTableColumn.getPropertyFromHeader("Class Name", inputTextNames));
					statement.setInt(3, Integer.parseInt(DataTableColumn.getPropertyFromHeader("Grade", inputTextNames)));
					statement.setString(4, DataTableColumn.getPropertyFromHeader("School", inputTextNames));
					statement.setInt(5, Integer.parseInt(DataTableColumn.getPropertyFromHeader("Year", inputTextNames)));
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
				classes.add(new ClassData(newId, DataTableColumn.getPropertyFromHeader("Class Name", inputTextNames), "Teacher", DataTableColumn.getPropertyFromHeader("School", inputTextNames), Integer.parseInt(DataTableColumn.getPropertyFromHeader("Grade", inputTextNames)), Integer.parseInt(DataTableColumn.getPropertyFromHeader("Year", inputTextNames))));
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
			DataTableColumn.setPropertyFromHeader("Class Name", selectedClass.getName(), inputTextNames);
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
			classes.get(classes.lastIndexOf(selectedClass)).setName(DataTableColumn.getPropertyFromHeader("Class Name", inputTextNames));
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
		DataTableColumn.setPropertyFromHeader("Class Name", "", inputTextNames);
		DataTableColumn.setPropertyFromHeader("School", "", inputTextNames);
		DataTableColumn.setPropertyFromHeader("Grade", "", inputTextNames);
		DataTableColumn.setPropertyFromHeader("Year", "", inputTextNames);
	}
	
	public void setClasses(List<ClassData> classes) {
		this.classes = classes;
	}
	
	public void setSelectedClass(ClassData selectedClass) {
		this.selectedClass = selectedClass;
	}
	
	public List<ClassData> getClasses() {
		return this.classes;
	}
	
	public ClassData getSelectedClass() {
		return this.selectedClass;
	}
}
