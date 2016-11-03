package wlfe.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.primefaces.context.RequestContext;

import wlfe.common.BaseHeaderMenuTableContentFooter;
import wlfe.common.Common;
import wlfe.common.DataTableColumn;
import wlfe.common.MySQLAccessor;
import wlfe.model.GameData;
import wlfe.model.StudentData;

public class Games extends BaseHeaderMenuTableContentFooter<GameData> {
	
	protected boolean initColumns() {
		if(columns.add(new DataTableColumn("Title", "title")) &&
		   columns.add(new DataTableColumn("Team Count", "teamCount")) &&
		   columns.add(new DataTableColumn("Players Per Team", "playersPerTeam"))) {
		   fields.put("gameId", new DataTableColumn("Game Id", ""));
		   fields.put("title", new DataTableColumn("Title", ""));
		   fields.put("teamCount", new DataTableColumn("Team Count", ""));
		   fields.put("playersPerTeam", new DataTableColumn("Players Per Team", ""));
		   return true;
		}
		return false;
	}
	
	public boolean initData() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				Statement statement = accessor.GetConnection().createStatement();
				ResultSet results = statement.executeQuery("SELECT * from games");
				while(results.next()) {
					GameData gameData = new GameData();
					String returnId[] = {""};
					MySQLSetGet(false, null, returnId, results, fields, gameData, 1);
					tableObjects.add(gameData);
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
			String className = "";
			try {
				String returnId[] = {"gameId"};
				PreparedStatement preparedStatement = accessor.GetConnection().prepareStatement("INSERT INTO games (title, teamCount, playersPerTeam) VALUES (?, ?, ?)", returnId);
				StudentData classData = new StudentData();
				createMySQLEntry(preparedStatement, fields, classData, returnId, 1);
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				Common.ErrorMessage();
			}
			tableObjects.add(new GameData(Integer.parseInt(fields.get("gameId").getProperty()), fields.get("title").getProperty(), Integer.parseInt(fields.get("teamCount").getProperty()), Integer.parseInt(fields.get("playersPerTeam").getProperty())));
			RequestContext.getCurrentInstance().update("main:mainTable");
			RequestContext.getCurrentInstance().execute("PF('NewClass').hide();");
			Common.SuccessMessage();
			accessor.Disconnect();
		}
	}
	
	public void editPressed() {
//		super.editPressed();
//		fields.get("classId").setProperty(getClassNameFromClassId(Integer.parseInt(fields.get("classId").getProperty())));
	}
	
	public void editConfirmPressed() {
//		MySQLAccessor accessor = MySQLAccessor.getInstance();
//		if(accessor.Connect()) {
//			try {
//				String returnId[] = {"studentId"};
//				PreparedStatement preparedStatement = accessor.GetConnection().prepareStatement("UPDATE student SET firstName = ?, lastName = ?, gender = ?, age = ?, classId = ? WHERE studentId=" + fields.get("studentId").getProperty(), returnId);
//				StudentData studentData = new StudentData();
//				fields.get("classId").setProperty(String.valueOf(getClassIdFromClassName(fields.get("classId").getProperty())));
//				createMySQLEntry(preparedStatement, fields, studentData, returnId, 1);
//			} catch (Exception e) {
//				e.printStackTrace();
//				Common.ErrorMessage();
//				accessor.Disconnect();
//				return;
//			}
//			RequestContext.getCurrentInstance().update("main:mainTable");
//			Common.SuccessMessage();
//			accessor.Disconnect();
//			selectedObject.setFirstName(fields.get("firstName").getProperty());
//			selectedObject.setLastName(fields.get("lastName").getProperty());
//			selectedObject.setGender(fields.get("gender").getProperty());
//			selectedObject.setAge(Integer.parseInt(fields.get("age").getProperty()));
//			selectedObject.setClassId(Integer.parseInt(fields.get("classId").getProperty()));
//			RequestContext.getCurrentInstance().update("main:mainTable");
//			RequestContext.getCurrentInstance().execute("PF('EditClass').hide();");
//		}
	}
	
	public void deletePressed(String query) {
//		super.deletePressed("DELETE FROM student WHERE studentId=" + selectedObject.getStudentId());
	}
}
