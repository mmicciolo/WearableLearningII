package wlfe.controller;

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
		if(email.equals("mmicciolo@wpi.edu") && password.equals("matthew")) {
			return "success";
		} else {
			return "failure";
		}
	}
}
