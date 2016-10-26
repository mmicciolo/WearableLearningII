package wlfe.common;

import java.sql.*;

public class MySQLAccessor {
	
	private static MySQLAccessor instance = null;
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost/wearablelearning";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	
	private Connection connection = null;
	private Statement statement = null;
	
	protected MySQLAccessor() {
		
	}
	
	public static MySQLAccessor getInstance() {
		if(instance == null) {
			instance = new MySQLAccessor();
		}
		return instance;
	}
	
	public Connection GetConnection() {
		return connection;
	}
	
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
	
	public boolean ExecuteStatement(String query) {
		try {
			statement = connection.createStatement();
			int rs = statement.executeUpdate(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

