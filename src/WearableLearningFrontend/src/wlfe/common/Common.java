package wlfe.common;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Common {
	
	public static void SuccessMessage() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Sucessfully Deleted");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public static void ErrorMessage() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error has occured");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
