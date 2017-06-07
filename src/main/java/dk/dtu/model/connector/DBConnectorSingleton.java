package dk.dtu.model.connector;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import dk.dtu.model.interfaces.DALException;

public class DBConnectorSingleton {

	private static DBConnectorSingleton instance = null;
	private static Connection connection;
	private static Statement stm;

	private DBConnectorSingleton() {
	}

	public static synchronized DBConnectorSingleton getDBConnectionInstance() {
		if (instance == null) {
			instance = new DBConnectorSingleton();
		}

		try {
			if(connection.isClosed() || !connection.isValid(1)) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = (Connection) DriverManager.getConnection("jdbc:mysql://"
						+ Constant.server + ":"
						+ Constant.port + "/"
						+ Constant.database
						+ "?verifyServerCertificate=false&useSSL=true",
						Constant.username,
						Constant.password
						);
				
				stm = (Statement) connection.createStatement();
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		return instance;
	}

	public static ResultSet doQuery(String cmd) throws DALException	{
		try { 
			getDBConnectionInstance();
			return stm.executeQuery(cmd); 
		} catch (SQLException e) { throw new DALException(e); }
	}

	public static int doUpdate(String cmd) throws DALException {
		try { 
			getDBConnectionInstance();
			return stm.executeUpdate(cmd);
		} catch (SQLException e) { throw new DALException(e); }
	}

	public static int resetData() throws DALException {
		try {
			getDBConnectionInstance();
			return stm.executeUpdate("CALL reset_data();");
		} catch (SQLException e) { throw new DALException(e); }
	}

}
