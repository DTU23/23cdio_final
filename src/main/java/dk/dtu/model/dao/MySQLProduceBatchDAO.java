package dk.dtu.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.DataSource;
import dk.dtu.model.dto.ProduceBatchDTO;
import dk.dtu.model.dto.StockDTO;
import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;
import dk.dtu.model.exceptions.dal.NotFoundException;
import dk.dtu.model.interfaces.ProduceBatchDAO;

public class MySQLProduceBatchDAO implements ProduceBatchDAO {

	@Override
	public void createProduceBatch(int produce_id, double amount) throws ConnectivityException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL create_produce_batch(?,?);");
			stm.setInt(1, produce_id);
			stm.setDouble(2, amount);
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IntegrityConstraintViolationException(e);
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}
	
	@Override
	public ProduceBatchDTO readProduceBatch(int rbId) throws ConnectivityException, NotFoundException {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL read_produce_batch(?);");
			stm.setInt(1, rbId);
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("Produce batch with id " + rbId + " does not exist");
			}
			return new ProduceBatchDTO(
					rs.getInt("rb_id"),
					rs.getInt("produce_id"),
					null,
					null,
					rs.getDouble("amount"));
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public void updateProduceBatch(int rbId, double amount) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL update_produce_batch(?,?);");
			stm.setInt(1, rbId);
			stm.setDouble(2, amount);
			if(stm.executeUpdate() == 0) {
				throw new NotFoundException("Produce batch with id " + rbId + " does not exist");
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IntegrityConstraintViolationException(e);
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}
	
	@Override
	public void deleteProduceBatch(int rbId) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL delete_produce_batch(?);");
			stm.setInt(1, rbId);
			if(stm.executeUpdate() == 0) {
				throw new NotFoundException("Produce batch with id " + rbId + " does not exist");
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IntegrityConstraintViolationException(e);
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public List<ProduceBatchDTO> getProduceBatchList() throws ConnectivityException, NotFoundException {
		List<ProduceBatchDTO> list = new ArrayList<ProduceBatchDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("SELECT * FROM produce_batch_list;");
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("No produce batches found");
			}
			do {
				list.add(new ProduceBatchDTO(
						rs.getInt("rb_id"),
						0,
						rs.getString("produce_name"),
						rs.getString("supplier"),
						rs.getDouble("amount")));
			} while (rs.next());
			return list;
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}
	
	@Override
	public ProduceBatchDTO getProduceBatchWithProduceName(int rbId) throws ConnectivityException, NotFoundException {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL read_produce_batch(?);");
			stm.setInt(1, rbId);
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("Produce batch with id " + rbId + " does not exist");
			}
			return new ProduceBatchDTO(
					rs.getInt("rb_id"),
					0,
					rs.getString("produce_name"),
					rs.getString("supplier"),
					rs.getDouble("amount"));
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}
	
	@Override
	public List<StockDTO> getProduceInStock() throws ConnectivityException, NotFoundException {
		List<StockDTO> list = new ArrayList<StockDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("SELECT * FROM produce_in_stock;");
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("No produces found");
			}
			do {
				list.add(new StockDTO(
						rs.getString("produce_name"),
						rs.getDouble("current_stock")));
			} while (rs.next());
			return list;
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}
}