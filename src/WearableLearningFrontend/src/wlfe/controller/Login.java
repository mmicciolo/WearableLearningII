package wlfe.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.primefaces.context.RequestContext;

import wlfe.common.Common;
import wlfe.common.MySQLAccessor;
import wlfe.model.Teacher;

public class Login {
	
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String school;
	
	public String validateUsernamePassword() {
		FacesContext context = FacesContext.getCurrentInstance();
		if(validateMySQL()) {
			createHttpSession();
			Teacher teacher = (Teacher) Common.getSession().getAttribute("teacher");
			context.addMessage(null, new FacesMessage("Successful", "Logged in as " + teacher.getFirstName()));
			return "success";
		}
		context.addMessage(null, new FacesMessage("Failure", "Incorrect email or password"));
		return "failure";
	}
	
	public boolean validateMySQL() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				PreparedStatement preparedStatement = accessor.GetConnection().prepareStatement("SELECT email, password from teacher where email = ? and password = ?");
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, calculateMD5(password));
				ResultSet results = preparedStatement.executeQuery();
				
				if(results.next()) {
					results.close();
					preparedStatement.close();
					accessor.Disconnect();
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				accessor.Disconnect();
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
				resultSet.close();
				statement.close();
				accessor.Disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				accessor.Disconnect();
			}
		}
	}
	
	public String logout() {
		Common.getSession().invalidate();
		return "Login.xhtml";
	}
	
	public void signUp() {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				PreparedStatement preparedStatement = accessor.GetConnection().prepareStatement("INSERT INTO teacher (email, password, firstName, lastName, school) VALUES (?, ?, ?, ?, ?)");
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, calculateMD5(password));
				preparedStatement.setString(3, firstName);
				preparedStatement.setString(4, lastName);
				preparedStatement.setString(5, school);
				preparedStatement.executeUpdate();
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				accessor.Disconnect();
			}
			RequestContext.getCurrentInstance().execute("PF('SignUpDialog').hide();");
			Common.SuccessMessage();
			accessor.Disconnect();
		}
	}
	
	public void clear() {
		email = "";
		password = "";
		firstName = "";
		lastName = "";
		school = "";
	}
	
	private String calculateMD5(String toSum) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			return (new HexBinaryAdapter()).marshal(md5.digest(toSum.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setSchool(String school) {
		this.school = school;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getSchool() {
		return this.school;
	}
}
