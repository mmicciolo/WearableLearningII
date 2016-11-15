package wlfe.common;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;

/**
 * This is the base class for any UI page that includes a menu with a table and new, edit, delete button panel.
 * It is a generic so it can work with any data type class.
 * @author MatthewMicciolo
 *
 * @param <T> data type
 */
public class BaseHeaderMenuTableContentFooter<T> {
	
	protected List<DataTableColumn> columns = new ArrayList<DataTableColumn>();
	protected Map<String, DataTableColumn> fields = new LinkedHashMap<String, DataTableColumn>();
	protected List<T> tableObjects = new ArrayList<T>();
	protected T selectedObject;
	
	/**
	 * This function is called when the manage bean is first created.
	 * @author Matthew Micciolo
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
	
	/** Default method for createPressed()
	 * Override this to write your own code to be called when
	 * the create button is pressed
	 * @author Matthew Micciolo
	 */		
	public void createPressed() {
		
	}
	
	/** Default method for editPressed()
	 * Override this to write your own code to be called when
	 * the edit button is pressed. This is the edit button on the button panel
	 * in the browser
	 * @author Matthew Micciolo
	 */
	public void editPressed() {
		if(selectedObject != null) {
			for(Entry<String, DataTableColumn> entry : fields.entrySet()) {
				if(!entry.getKey().equals("")) {
					try {
						Object value =  new PropertyDescriptor(entry.getKey(), selectedObject.getClass()).getReadMethod().invoke(selectedObject);
						entry.getValue().setProperty(String.valueOf(value));
						RequestContext.getCurrentInstance().execute("PF('EditDialog').show();");
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/** Default method for editConfirmPressed()
	 * Override this to write your own code to be called when
	 * the edit confirm button is pressed. This is the edit confirm button
	 * in the edit dialog box.
	 * @author Matthew Micciolo
	 */
	public void editConfirmPressed() {
		
	}
	
	/** Default method for deletePressed()
	 * Override this to write your own code to be called when
	 * the delete button is pressed. Usually you dont have to override this
	 * but if you do just call super.deletePressed() at the end of your code.
	 * @author Matthew Micciolo
	 * @param query optional string to pass in
	 */
	public void deletePressed(String query) {
		MySQLAccessor accessor = MySQLAccessor.getInstance();
		if(accessor.Connect()) {
			try {
				PreparedStatement statement = accessor.GetConnection().prepareStatement(query);
				statement.executeUpdate();
				statement.close();
			} catch(Exception e) {
				e.printStackTrace();
				Common.ErrorMessage();
				accessor.Disconnect();
				return;
			}
			tableObjects.remove(selectedObject);
			RequestContext.getCurrentInstance().update("main:mainTable");
			Common.SuccessMessage();
			accessor.Disconnect();
		}
	}

	/**
	 * This method is called when the new class dialog is closed.
	 * It clears our all of the values so next time it is open it is
	 * reset.
	 * @author Matthew Micciolo
	 */
	public void clear() {
		for(Entry<String, DataTableColumn> entry : fields.entrySet()) {
			entry.getValue().setProperty("");
		}
	}
	
	
	/**
	 * Default method to fill any dropdown controls that may be
	 * used in the create or edit.
	 * @author Matthew Micciolo
	 * @param query identifier
	 * @return list of items to be displayed in the drop down
	 */
	public List<String> fillDropDown(String query) {
		return null;
	}
	
	/** This method peroforms all of the mysql operations required for the 
	 * items in queryData. The first arugment is a string or the name of the mysql
	 * column name and the second is a DataTableColumn or (string header, string property).
	 * This function simply uses the first argument string to get the value from the
	 * mysql table and put it in the property variable of the second argument. This function will also
	 * dynamically resolve the types of these variables to their appropriate types.
	 * @author Matthew Micciolo
	 * @param set setting or getting mysql data
	 * @param preparedStatement mysql query statement
	 * @param returns what id to return, ex class, {classId}
	 * @param results result set to use
	 * @param queryData mysql mappings
	 * @param modelData data type, ex ClassData
	 * @param count count in the preparedstatement to start at
	 */
	
	protected void MySQLSetGet(boolean set, PreparedStatement preparedStatement, String[] returns, ResultSet results, Map<String, DataTableColumn> queryData, Object modelData, int count) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException, SQLException {
		for(Entry<String, DataTableColumn> entry : queryData.entrySet()) {
			Object value = null;
			Object resultValue = null;
			if((!entry.getKey().equals("")) && (!entry.getKey().equals(returns[0]))) {
				value =  new PropertyDescriptor(entry.getKey(), modelData.getClass()).getReadMethod().invoke(modelData);
				if(value.getClass().getName().equals("java.lang.String")) {
					if(set) {
						preparedStatement.setString(count, entry.getValue().getProperty());
						count++;
					} else {
						resultValue = results.getString(entry.getKey());
						value = new PropertyDescriptor(entry.getKey(), modelData.getClass()).getWriteMethod().invoke(modelData, resultValue);
					}
				} else if(value.getClass().getName().equals("java.lang.Integer")) {
					if(set) {
						preparedStatement.setInt(count, Integer.parseInt(entry.getValue().getProperty()));
						count++;
					} else {
						resultValue = results.getInt(entry.getKey());
						value = new PropertyDescriptor(entry.getKey(), modelData.getClass()).getWriteMethod().invoke(modelData, resultValue);
					}
				}
			} else {
				if(!set) {
					DataTableColumn column = entry.getValue();
					value = new PropertyDescriptor(column.getProperty(), modelData.getClass()).getWriteMethod().invoke(modelData, column.getHeader());
				}
			}
		}
	}
	
	/**
	 * This function creates a new mysql entry into the database based off data input
	 * from a dialog. The data from the DataTableColumn.property of queryData is placed into
	 * the column name from DataTableColumn.header.
	 * @author Matthew Micciolo
	 * @param preparedStatement Mysql query statement
	 * @param queryData Mysql mapping
	 * @param modelData type, ex ClassData
	 * @param returnId id name of newly inserted record, ex {classId}
	 * @param count count to state the prepared statement at
	 */
	protected void createMySQLEntry(PreparedStatement preparedStatement, Map<String, DataTableColumn> queryData, Object modelData, String returnId[], int count) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException, SQLException {
		MySQLSetGet(true, preparedStatement, returnId, null, queryData, modelData, count);
		preparedStatement.executeUpdate();
		ResultSet rs = preparedStatement.getGeneratedKeys();
		int newId = 0;
		if(rs.next()) { newId = (int)rs.getLong(1); }
		queryData.get(returnId[0]).setProperty(String.valueOf(newId));
		rs.close();
		preparedStatement.close();
	}
	
	/**
	 * Sets the list of DataTableColumns (header, property)
	 * @author Matthew Micciolo
	 * @param columns list to set
	 */
	public void setColumns(List<DataTableColumn> columns) {
		this.columns= columns;
	}
	
	/**
	 * Sets the map fields mysql datacolumn name to datacolumn name, value
	 * @author Matthew Micciolo
	 * @param fields list of mappings
	 */
	public void setFields(Map<String, DataTableColumn> fields) {
		this.fields = fields;
	}
	
	/**
	 * Sets the table objects list. This list is a list of objects
	 * displayed in the table on the screen.
	 * @author Matthew Micciolo
	 * @param tableObjects list of objects displayed in table on screen
	 */
	public void setTableObjects(List<T> tableObjects) {
		this.tableObjects = tableObjects;
	}
	
	/**
	 * Sets the selected object in the table.
	 * @author Matthew Micciolo
	 * @param selectedObject object selected in table
	 */
	public void setSelectedObject(T selectedObject) {
		this.selectedObject = selectedObject;
	}
		
	/**
	 * See sets methods
	 * @author Matthew Micciolo
	 * @return list of columns
	 */
	public List<DataTableColumn> getColumns() {
		return this.columns;
	}
	
	/**
	 * See sets methods
	 * @author Matthew Micciolo
	 * @return map of fields
	 */
	public Map<String, DataTableColumn> getFields() {
		return this.fields;
	}
	
	/**
	 * See sets methods
	 * @author Matthew Micciolo
	 * @return list of table objects
	 */
	public List<T> getTableObjects() {
		return this.tableObjects;
	}
	
	/**
	 * See sets methods
	 * @author Matthew Micciolo
	 * @return selected table object
	 */
	public T getSelectedObject() {
		return this.selectedObject;
	}
}
