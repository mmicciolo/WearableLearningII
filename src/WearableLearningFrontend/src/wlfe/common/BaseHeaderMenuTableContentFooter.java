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
		
	public void createPressed() {
		
	}
	
	public void editPressed() {
		if(selectedObject != null) {
			for(Entry<String, DataTableColumn> entry : fields.entrySet()) {
				if(!entry.getKey().equals("")) {
					try {
						Object value =  new PropertyDescriptor(entry.getKey(), selectedObject.getClass()).getReadMethod().invoke(selectedObject);
						entry.getValue().setProperty(String.valueOf(value));
						RequestContext.getCurrentInstance().execute("PF('EditClass').show();");
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void editConfirmPressed() {
		
	}
	
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

	public void clear() {
		for(Entry<String, DataTableColumn> entry : fields.entrySet()) {
			entry.getValue().setProperty("");
		}
	}
	
	public List<String> fillDropDown(String query) {
		return null;
	}
	
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
	
	public void setColumns(List<DataTableColumn> columns) {
		this.columns= columns;
	}
	
	public void setFields(Map<String, DataTableColumn> fields) {
		this.fields = fields;
	}
	
	public void setTableObjects(List<T> tableObjects) {
		this.tableObjects = tableObjects;
	}
	
	public void setSelectedObject(T selectedObject) {
		this.selectedObject = selectedObject;
	}
		
	public List<DataTableColumn> getColumns() {
		return this.columns;
	}
	
	public Map<String, DataTableColumn> getFields() {
		return this.fields;
	}
	
	public List<T> getTableObjects() {
		return this.tableObjects;
	}
	
	public T getSelectedObject() {
		return this.selectedObject;
	}
}
