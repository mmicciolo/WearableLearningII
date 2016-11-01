package wlfe.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

public class BaseHeaderMenuTableContentFooter {
	
	protected List<DataTableColumn> columns = new ArrayList<DataTableColumn>();;
	protected List<DataTableColumn> inputTextNames = new ArrayList<DataTableColumn>();
	
	/**
	 * This function is called when the manage bean is first created.
	 */
	@PostConstruct
	public void init() {
		if(initColumns() && initData()) {
			
		} else {
			
		}
	}
	
	/**
	 * This function is called to initialize the column names using
	 * the DataTableColumn class. The DataTableColumn takes a header
	 * for the graphical display name and a property for the java
	 * managed bean property name. Override this in your implementation
	 * to init the UI.
	 * @author Matthew Micciolo
	 * @return true if successful
	 */	
	protected boolean initColumns() {
		return false;
	}
	
	/**
	 * This function is called to initialize the data that is to be 
	 * displayed in the table when the page first loads. This could include
	 * fetching data from a database, etc. Override this in your implementation
	 * to init the data.
	 * @author Matthew Micciolo
	 * @return true if successful
	 */	
	protected boolean initData() {
		return false;
	}
	
	public void createPressed() {
		
	}
	
	public void editPressed() {
		
	}
	
	public void editConfirmPressed() {
		
	}
	
	public void deletePressed() {
		
	}
	
	public void clear() {
		
	}
	
	public void setColumns(List<DataTableColumn> columns) {
		this.columns= columns;
	}
	
	public void setInputTextNames(List<DataTableColumn> inputTextNames) {
		this.inputTextNames = inputTextNames;
	}
	
	public List<DataTableColumn> getColumns() {
		return this.columns;
	}
	
	public List<DataTableColumn> getInputTextNames() {
		return this.inputTextNames;
	}
}
