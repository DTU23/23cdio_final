package dk.dtu.model.connector;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dk.dtu.model.interfaces.DALException;

public final class Connector
{
	private static Connector instance;
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	private Connector() {}

	public static synchronized Connector getInstance() {
		if (instance == null) {
			synchronized(Connector.class) {
				if (instance == null) {
					instance = new Connector();
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

	public int resetData() throws DALException {
		connectToDatabase();
		try {
			statement = connection.prepareStatement("CALL reset_data();");
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