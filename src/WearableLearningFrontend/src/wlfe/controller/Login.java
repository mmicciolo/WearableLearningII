package wlfe.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import wlfe.common.Common;
import wlfe.common.MySQLAccessor;
import wlfe.model.Teacher;

public class Login {
	
	private String email;
	private String password;
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String validateUsernamePassword() {
		if(validateMySQL()) {
			createHttpSession();
			return "success";
		}
		return "failure";
	}
	
	public boolean validateMySQL() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				PreparedStatement preparedStatement = accessor.GetConnection().prepareStatement("SELECT email, password from teacher where email = ? and password = ?");
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, password);
				ResultSet results = preparedStatement.executeQuery();
				
				if(results.next()) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return false;
		}
		return false;
	}
	
	public void createHttpSession() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				Statement statement = accessor.GetConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM teacher WHERE email=" + "'" + email + "'");
				resultSet.next();
				Common.getSession().setAttribute("teacher", new Teacher(resultSet.getInt("teacherId"), resultSet.getString("email"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("school")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String logout() {
		Common.getSession().invalidate();
		return "Login.xhtml";
	}
}
