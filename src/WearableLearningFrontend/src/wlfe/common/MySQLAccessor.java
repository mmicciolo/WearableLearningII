package wlfe.common;

import java.sql.*;

/**
 * This class provides functions to interface with mysql databases.
 * It is a basic singleton wrapper style class.
 * @author Matthew Micciolo
 *
 */
public class MySQLAccessor {
	
	private static MySQLAccessor instance = null;
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://mmicciolo.tk/wearablelearning";
	private static final String USERNAME = "wlfe";
	private static final String PASSWORD = "matthew";
	
	private Connection connection = null;
	private Statement statement = null;
	
	/**
	 * Default Constructor
	 * @author Matthew Micciolo
	 */
	protected MySQLAccessor() {
		
	}
	
	/**
	 * Check if the instance is null. If it is, create a new one, else
	 * return the instance.
	 * @return singleton instance
	 */
	public static MySQLAccessor getInstance() {
		if(instance == null) {
			instance = new MySQLAccessor();
		}
		return instance;
	}
	
	/**
	 * Get mysql connection
	 * @author Matthew Micciolo
	 * @return mysql connection
	 */
	public Connection GetConnection() {
		return connection;
	}
	
	/**
	 * Attempt to connect to mysql database using the database name, and username and password credentials
	 * @author Matthew Micciolo
	 * @return true if successful
	 */
	public boolean Connect() {
		if(connection == null) {
			try {
				Class.forName(JDBC_DRIVER);
				connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
				if(connection != null) {
					return true;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		} else {
			return true;
		}	
	}
	
	/**
	 * Disconnect from mysql database
	 * @return true if successful
	 */
	public boolean Disconnect() {
		if(connection != null) {
			try {
				connection.close();
				connection = null;
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Execute a mysql statement
	 * @param query statement query
	 * @return true if successful
	 */
	public boolean ExecuteStatement(String query) {
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

