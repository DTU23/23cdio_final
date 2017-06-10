package dk.dtu.model.connector;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dk.dtu.model.exceptions.DALException;

public final class Connector {
	
	public static Connection getConnection(String server, int port, String database, String username, String password) throws DALException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return (Connection) DriverManager.getConnection("jdbc:mysql://"+server+":"+port+"/"+database+"?verifyServerCertificate=false&useSSL=true", username, password);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			throw new DALException(e);
		}	
	}
	
	public static Connection getConnection() throws DALException {
		return getConnection(Constant.server, Constant.port, Constant.database, Constant.username, Constant.password);	
	}
	
	public static int resetData() throws DALException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement("CALL reset_data();");
			return statement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			try { if (statement != null) statement.close(); } catch (SQLException e) {};
			try { if (connection != null) connection.close(); } catch (SQLException e) {};
		}
	}
	
	private static Connector instance;
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	private Connector() {}

	public static synchronized Connector getInstance() throws DALException{
		if (instance == null) {
			synchronized(Connector.class) {
				if (instance == null) {
					instance = new Connector();
					try {
						Class.forName("com.mysql.jdbc.Driver").newInstance();
					} catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
						throw new DALException(e);
					}
				}
			}
		}
		return instance;
	}

	public void connectToDatabase(String server, int port, String database, String username, String password) throws DALException {
		try {
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://"+server+":"+port+"/"+database+"?verifyServerCertificate=false&useSSL=true", username, password);
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	public void connectToDatabase() throws DALException {
		connectToDatabase(Constant.server, Constant.port, Constant.database, Constant.username, Constant.password);
	}

	public ResultSet doQuery(String cmd) throws DALException {
		connectToDatabase();
		try {
			statement = connection.prepareStatement(cmd);
			resultSet = statement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	public int doUpdate(String cmd) throws DALException {
		connectToDatabase();
		try {
			statement = connection.prepareStatement(cmd);
			return statement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			closeResources();
		}
	}

	public void closeResources() {
		try { if (resultSet != null) resultSet.close(); } catch (SQLException e) {};
		try { if (statement != null) statement.close(); } catch (SQLException e) {};
		try { if (connection != null) connection.close(); } catch (SQLException e) {};
	}
}