package dk.dtu.model.connector;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dk.dtu.model.exceptions.DALException;

import java.sql.Connection;

public final class MySQLConnect {

	private static Connection connection;
	private static Statement statement;
	private static MySQLConnect instance;


	private MySQLConnect() {}

	public static synchronized MySQLConnect getDBConnection() throws DALException {
		if (instance == null) { instance = new MySQLConnect(); }
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://"+Constant.server+":"+Constant.port+"/"+Constant.database+"?verifyServerCertificate=false&useSSL=true", Constant.username, Constant.password);
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new DALException("Error trying to connect to database!");
		}

		return instance;
	}

	public static ResultSet doQuery(String query) throws DALException {
		getDBConnection();
		ResultSet rs;
		rs = null;
		try {
			statement = MySQLConnect.connection.createStatement();
			rs = statement.executeQuery(query);
			return rs;	
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DALException(e);
		}
		finally {	
			
			try {	
				if (rs != null)	 
				rs.close();
				if (statement != null)	 
					statement.close();		 
				if (connection != null)	 
					connection.close();		 
			} catch (SQLException e) {		 
				e.printStackTrace();		 
			} catch (Exception e) {		 
				e.printStackTrace();		 
			}		 
		}

	}

	public static int doUpdate(String query) throws DALException {
		getDBConnection();
		int result;
		try {
			statement = MySQLConnect.connection.createStatement();
			result = statement.executeUpdate(query);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DALException(e);
		}
		finally {			  
			try {	
				if (statement != null)	 
					statement.close();		 
				if (connection != null)		 
					connection.close();		 
			} catch (SQLException e) {		 
				e.printStackTrace();		 
			} catch (Exception e) {		 
				e.printStackTrace();		 
			}
		}

	}
	
}