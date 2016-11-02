package wlfe.common;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	protected void MySQLSetGet(boolean set, PreparedStatement preparedStatment, String[] returns, ResultSet results, Map<String, DataTableColumn> queryData, Object modelData, int count) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException, SQLException {
		for(Entry<String, DataTableColumn> entry : queryData.entrySet()) {
			Object value = null;
			Object resultValue = null;
			if((!entry.getKey().equals("")) && (!entry.getKey().equals(returns[0]))) {
				value =  new PropertyDescriptor(entry.getKey(), modelData.getClass()).getReadMethod().invoke(modelData);
				if(value.getClass().getName().equals("java.lang.String")) {
					if(set) {
						preparedStatment.setString(count, entry.getValue().getProperty());
						count++;
					} else {
						resultValue = results.getString(entry.getKey());
						value = new PropertyDescriptor(entry.getKey(), modelData.getClass()).getWriteMethod().invoke(modelData, resultValue);
					}
				} else if(value.getClass().getName().equals("java.lang.Integer")) {
					if(set) {
						preparedStatment.setInt(count, Integer.parseInt(entry.getValue().getProperty()));
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
	
	protected void createMySQLEntry(PreparedStatement preparedStatment, Map<String, DataTableColumn> queryData, Object modelData, String returnId[]) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException, SQLException {
		MySQLSetGet(true, preparedStatment, returnId, null, queryData, modelData, 2);
		preparedStatment.executeUpdate();
		ResultSet rs = preparedStatment.getGeneratedKeys();
		int newId = 0;
		if(rs.next()) { newId = (int)rs.getLong(1); }
		queryData.get(returnId[0]).setProperty(String.valueOf(newId));
		rs.close();
		preparedStatment.close();
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
