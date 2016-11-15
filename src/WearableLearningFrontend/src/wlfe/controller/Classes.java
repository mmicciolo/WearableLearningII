package wlfe.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.primefaces.context.RequestContext;

import wlfe.common.BaseHeaderMenuTableContentFooter;
import wlfe.common.Common;
import wlfe.common.DataTableColumn;
import wlfe.common.MySQLAccessor;
import wlfe.model.ClassData;

public class Classes extends BaseHeaderMenuTableContentFooter<ClassData> {
	
	protected boolean initColumns() {
		if(columns.add(new DataTableColumn("Name", "className")) &&
		   columns.add(new DataTableColumn("Teacher", "teacher")) &&
		   columns.add(new DataTableColumn("School", "school")) &&
		   columns.add(new DataTableColumn("Grade", "grade")) &&
		   columns.add(new DataTableColumn("Year", "year"))) {
		   fields.put("classId", new DataTableColumn("Class Id", ""));
		   fields.put("className", new DataTableColumn("Class Name", ""));
		   fields.put("", new DataTableColumn("Teacher", "teacher"));
		   fields.put("school", new DataTableColumn("School", ""));
		   fields.put("grade", new DataTableColumn("Grade", ""));
		   fields.put("year", new DataTableColumn("Year", ""));
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
					MySQLSetGet(false, null, returnId, results, fields, classData, 1);
					tableObjects.add(classData);
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
	
	public void createPressed() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				String returnId[] = {"classId"};
				PreparedStatement preparedStatement = accessor.GetConnection().prepareStatement("INSERT INTO class (teacherId, className, school, grade, year) VALUES (?, ?, ?, ?, ?)", returnId);
				ClassData classData = new ClassData();
				preparedStatement.setInt(1, 1);
				createMySQLEntry(preparedStatement, fields, classData, returnId, 2);
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				Common.ErrorMessage();
			}
			tableObjects.add(new ClassData(Integer.parseInt(fields.get("classId").getProperty()), fields.get("className").getProperty(), "teacher", fields.get("school").getProperty(), Integer.parseInt(fields.get("grade").getProperty()), Integer.parseInt(fields.get("year").getProperty())));
			RequestContext.getCurrentInstance().update("main:mainTable");
			RequestContext.getCurrentInstance().execute("PF('NewDialog').hide();");
			Common.SuccessMessage();
			accessor.Disconnect();
		}
	}
	
	public void editPressed() {
		super.editPressed();
	}
	
	public void editConfirmPressed() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				String returnId[] = {"classId"};
				PreparedStatement preparedStatement = accessor.GetConnection().prepareStatement("UPDATE class SET className = ?, school = ?, grade = ?, year = ? WHERE classId=" + fields.get("classId").getProperty(), returnId);
				ClassData classData = new ClassData();
				createMySQLEntry(preparedStatement, fields, classData, returnId, 1);
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				Common.ErrorMessage();
				accessor.Disconnect();
				return;
			}
			RequestContext.getCurrentInstance().update("main:mainTable");
			Common.SuccessMessage();
			accessor.Disconnect();
			selectedObject.setClassName(fields.get("className").getProperty());
			selectedObject.setSchool(fields.get("school").getProperty());
			selectedObject.setGrade(Integer.parseInt(fields.get("grade").getProperty()));
			selectedObject.setYear(Integer.parseInt(fields.get("year").getProperty()));
			RequestContext.getCurrentInstance().update("main:mainTable");
			RequestContext.getCurrentInstance().execute("PF('EditDialog').hide();");
		}
	}
	
	public void deletePressed(String query) {
		super.deletePressed("DELETE FROM class WHERE classId=" + selectedObject.getClassId());
	}
}
