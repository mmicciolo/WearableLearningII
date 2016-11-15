package wlfe.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.context.RequestContext;

import wlfe.common.BaseHeaderMenuTableContentFooter;
import wlfe.common.Common;
import wlfe.common.DataTableColumn;
import wlfe.common.MySQLAccessor;
import wlfe.model.StudentData;

public class Students extends BaseHeaderMenuTableContentFooter<StudentData> {
	
	protected boolean initColumns() {
		if(columns.add(new DataTableColumn("First Name", "firstName")) &&
		   columns.add(new DataTableColumn("Last Name", "lastName")) &&
		   columns.add(new DataTableColumn("Gender", "gender")) &&
		   columns.add(new DataTableColumn("Age", "age")) &&
		   columns.add(new DataTableColumn("Class Name", "className"))) {
		   fields.put("studentId", new DataTableColumn("Student Id", ""));
		   fields.put("firstName", new DataTableColumn("First Name", ""));
		   fields.put("lastName", new DataTableColumn("Last Name", ""));
		   fields.put("gender", new DataTableColumn("Gender", ""));
		   fields.put("age", new DataTableColumn("Age", ""));
		   fields.put("classId", new DataTableColumn("Class", ""));
		   return true;
		}
		return false;
	}
	
	public boolean initData() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				Statement statement = accessor.GetConnection().createStatement();
				ResultSet results = statement.executeQuery("SELECT * from student");
				while(results.next()) {
					StudentData studentData = new StudentData();
					String returnId[] = {""};
					MySQLSetGet(false, null, returnId, results, fields, studentData, 1);
					studentData.setClassName(getClassNameFromClassId(studentData.getClassId()));
					tableObjects.add(studentData);
				}
				results.close();
				statement.close();
				accessor.Disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				Common.ErrorMessage();
				accessor.Disconnect();
				return false;
			}
			return true;
		}
		return false;
	}
	
	private String getClassNameFromClassId(int classId) {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		String name = "";
		if(accessor.Connect()) {
			try {
				Statement classIdStatement = accessor.GetConnection().createStatement();
				ResultSet classIdResults = classIdStatement.executeQuery("SELECT className FROM class WHERE classId=" + classId + " AND teacherId=" + Common.getTeacherForSession().getTeacherId());
				classIdResults.next();
				name = classIdResults.getString("className");
				classIdResults.close();
				classIdStatement.close();
				return name;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	private int getClassIdFromClassName(String className) {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		int id = 0;
		if(accessor.Connect()) {
			try {
				Statement classIdStatement = accessor.GetConnection().createStatement();
				ResultSet classIdResults = classIdStatement.executeQuery("SELECT classId FROM class WHERE className=" + "'" + className + "'" + " AND teacherId=" + Common.getTeacherForSession().getTeacherId());
				classIdResults.next();
				id = classIdResults.getInt("classId");
				classIdResults.close();
				classIdStatement.close();
				return id;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public void createPressed() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			String className = "";
			try {
				Statement classId = accessor.GetConnection().createStatement();
				ResultSet result = classId.executeQuery("SELECT classId FROM class WHERE className=" + "'" + fields.get("classId").getProperty() + "'");
				result.next();
				className = fields.get("classId").getProperty();
				fields.get("classId").setProperty(String.valueOf(result.getInt("classId")));
				String returnId[] = {"studentId"};
				PreparedStatement preparedStatement = accessor.GetConnection().prepareStatement("INSERT INTO student (firstName, lastName, gender, age, classId) VALUES (?, ?, ?, ?, ?)", returnId);
				StudentData classData = new StudentData();
				createMySQLEntry(preparedStatement, fields, classData, returnId, 1);
				result.close();
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				Common.ErrorMessage();
			}
			tableObjects.add(new StudentData(Integer.parseInt(fields.get("studentId").getProperty()), fields.get("firstName").getProperty(), fields.get("lastName").getProperty(), fields.get("gender").getProperty(), Integer.parseInt(fields.get("age").getProperty()), Integer.parseInt(fields.get("classId").getProperty()), className));
			RequestContext.getCurrentInstance().update("main:mainTable");
			RequestContext.getCurrentInstance().execute("PF('NewDialog').hide();");
			Common.SuccessMessage();
			accessor.Disconnect();
		}
	}
	
	public void editPressed() {
		super.editPressed();
		fields.get("classId").setProperty(getClassNameFromClassId(Integer.parseInt(fields.get("classId").getProperty())));
	}
	
	public void editConfirmPressed() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				String returnId[] = {"studentId"};
				PreparedStatement preparedStatement = accessor.GetConnection().prepareStatement("UPDATE student SET firstName = ?, lastName = ?, gender = ?, age = ?, classId = ? WHERE studentId=" + fields.get("studentId").getProperty(), returnId);
				StudentData studentData = new StudentData();
				fields.get("classId").setProperty(String.valueOf(getClassIdFromClassName(fields.get("classId").getProperty())));
				createMySQLEntry(preparedStatement, fields, studentData, returnId, 1);
			} catch (Exception e) {
				e.printStackTrace();
				Common.ErrorMessage();
				accessor.Disconnect();
				return;
			}
			RequestContext.getCurrentInstance().update("main:mainTable");
			Common.SuccessMessage();
			accessor.Disconnect();
			selectedObject.setFirstName(fields.get("firstName").getProperty());
			selectedObject.setLastName(fields.get("lastName").getProperty());
			selectedObject.setGender(fields.get("gender").getProperty());
			selectedObject.setAge(Integer.parseInt(fields.get("age").getProperty()));
			selectedObject.setClassId(Integer.parseInt(fields.get("classId").getProperty()));
			RequestContext.getCurrentInstance().update("main:mainTable");
			RequestContext.getCurrentInstance().execute("PF('EditDialog').hide();");
		}
	}
	
	public void deletePressed(String query) {
		super.deletePressed("DELETE FROM student WHERE studentId=" + selectedObject.getStudentId());
	}
	
	public List<String> fillDropDown(String key) {
		List<String> list = new ArrayList<String>();
		if(key.equals("gender")) {
			list.add("Male");
			list.add("Female");
			return list;
		} else if(key.equals("classId")) {
			MySQLAccessor accessor = MySQLAccessor.getInstance();
			if(accessor.Connect()) {
				try {
					Statement classes = accessor.GetConnection().createStatement();
					ResultSet set = classes.executeQuery("SELECT * FROM class WHERE teacherId=" + Common.getTeacherForSession().getTeacherId());
					while(set.next()) {
						list.add(set.getString("className"));
					}
					set.close();
					classes.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;
		} else {
			return null;
		}	
	}
}
