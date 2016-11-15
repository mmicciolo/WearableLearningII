package wlfe.common;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import wlfe.model.Teacher;

/**
 * A collection of common static methods for use throughout beans.
 * @author Matthew Micciolo
 *
 */
public class Common {
	
	/**
	 * Display success message. Must have growl setup in XHTML.
	 * Check HeaderMenuTableContentFooter tempalte XHTML for more info.
	 * @author Matthew Micciolo
	 */
	public static void SuccessMessage() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Sucessfully Deleted");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	/**
	 * Display error message Must have growl setup in XHTML.
	 * Check HeaderMenuTableContentFooter tempalte XHTML for more info.
	 * @author Matthew Micciolo
	 */
	public static void ErrorMessage() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error has occured");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	public static Teacher getTeacherForSession() {
		return (Teacher) Common.getSession().getAttribute("teacher");
	}
}
