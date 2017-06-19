package dk.dtu.model.connector;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.DataInsertException;
import dk.dtu.model.exceptions.dal.DataResetException;

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

		if(Constant.useInitialPoolSizeSettings)
			connectionPool.setInitialPoolSize(Constant.initialPoolSize);
		if(Constant.useMinPoolSizeSettings)
			connectionPool.setMinPoolSize(Constant.minPoolSize);
		if(Constant.useAcquireIncrementSettings)
			connectionPool.setAcquireIncrement(Constant.acquireIncrement);
		if(Constant.useMaxPoolSizeSettings)
			connectionPool.setMaxPoolSize(Constant.maxPoolSize);
		if(Constant.useMaxStatementsSettings)
			connectionPool.setMaxStatements(Constant.maxStatements);
	}

	public static synchronized DataSource getInstance() throws ConnectivityException {
		if (instance == null) {
			synchronized(DataSource.class) {
				if (instance == null) {
					try {
						instance = new DataSource();
					} catch (IOException | SQLException | PropertyVetoException e) {
						throw new ConnectivityException(e);
					}
				}
			}
		}
		return instance;
	}

	public Connection getConnection() throws ConnectivityException {
		try {
			return connectionPool.getConnection();
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		}	
	}

	public void resetData() throws ConnectivityException, DataResetException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = getInstance().getConnection();
			stm = conn.prepareStatement("CALL reset_data();");
			if(stm.executeUpdate() == 0) {
				throw new DataResetException("No rows affected");
			}
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}
	
	// methods below this line are for presentation
	
	public void insertUsers() throws ConnectivityException, DataInsertException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = getInstance().getConnection();
			stm = conn.prepareStatement("CALL insert_users();");
			if(stm.executeUpdate() == 0) {
				throw new DataInsertException("No rows affected");
			}
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	public void insertProduce() throws ConnectivityException, DataInsertException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = getInstance().getConnection();
			stm = conn.prepareStatement("CALL insert_produce();");
			if(stm.executeUpdate() == 0) {
				throw new DataInsertException("No rows affected");
			}
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	public void insertRecipe() throws ConnectivityException, DataInsertException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = getInstance().getConnection();
			stm = conn.prepareStatement("CALL insert_recipe();");
			if(stm.executeUpdate() == 0) {
				throw new DataInsertException("No rows affected");
			}
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	public void insertProduceBatch() throws ConnectivityException, DataInsertException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = getInstance().getConnection();
			stm = conn.prepareStatement("CALL insert_producebatch();");
			if(stm.executeUpdate() == 0) {
				throw new DataInsertException("No rows affected");
			}
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	public void insertProductBatch() throws ConnectivityException, DataInsertException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = getInstance().getConnection();
			stm = conn.prepareStatement("CALL insert_productbatch();");
			if(stm.executeUpdate() == 0) {
				throw new DataInsertException("No rows affected");
			}
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	public void insertProductBatchComp() throws ConnectivityException, DataInsertException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = getInstance().getConnection();
			stm = conn.prepareStatement("CALL insert_productbatchcomponents();");
			if(stm.executeUpdate() == 0) {
				throw new DataInsertException("No rows affected");
			}
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}
}