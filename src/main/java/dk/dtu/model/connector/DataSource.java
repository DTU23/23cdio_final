package dk.dtu.model.connector;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import dk.dtu.model.exceptions.DALException;

public final class DataSource {
	
	private static DataSource instance;
	private ComboPooledDataSource connectionPool;
	
	private DataSource() throws IOException, SQLException, PropertyVetoException {
		this(Constant.server, Constant.database, Constant.username, Constant.password);
	}
	
	private DataSource(String server, String database, String username, String password) throws IOException, SQLException, PropertyVetoException {
		connectionPool = new ComboPooledDataSource();
		connectionPool.setDriverClass("com.mysql.jdbc.Driver"); //loads the jdbc driver
		connectionPool.setJdbcUrl("jdbc:mysql://"+server+"/"+database);
		connectionPool.setUser(username);
		connectionPool.setPassword(password);

        if(Constant.usePoolSettings) {
		connectionPool.setMinPoolSize(Constant.minPoolSize);
		connectionPool.setAcquireIncrement(Constant.acquireIncrement);
		connectionPool.setMaxPoolSize(Constant.maxPoolSize);
		connectionPool.setMaxStatements(Constant.maxStatements);
        }
	}

	public static synchronized DataSource getInstance() throws DALException {
		if (instance == null) {
			synchronized(DataSource.class) {
				if (instance == null) {
					try {
						instance = new DataSource();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (PropertyVetoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return instance;
	}
	
	public Connection getConnection() throws DALException, SQLException {
		return connectionPool.getConnection();	
	}
	
	public int resetData() throws DALException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getInstance().getConnection();
			statement = connection.prepareStatement("CALL reset_data();");
			return statement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			try { if (statement != null) statement.close(); } catch (SQLException e) {};
			try { if (connection != null) connection.close(); } catch (SQLException e) {};
		}
	}
}