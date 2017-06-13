package dk.dtu.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.DataSource;
import dk.dtu.model.dto.ProduceDTO;
import dk.dtu.model.dto.ProduceOverviewDTO;
import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;
import dk.dtu.model.exceptions.dal.NotFoundException;
import dk.dtu.model.interfaces.ProduceDAO;

public class MySQLProduceDAO implements ProduceDAO {

	@Override
	public void createProduce(ProduceDTO produce) throws ConnectivityException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL create_produce(?,?);");
			stm.setString(1, produce.getProduceName());
			stm.setString(2, produce.getSupplier());
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
	public ProduceDTO readProduce(int produceId) throws ConnectivityException, NotFoundException {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL read_produce(?);");
			stm.setInt(1, produceId);
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("Produce with id " + produceId + " does not exist");
			}
			return new ProduceDTO(
					rs.getInt("produce_id"),
					rs.getString("produce_name"),
					rs.getString("supplier"));
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public void updateProduce(ProduceDTO produce) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL update_produce(?,?,?);");
			stm.setInt(1, produce.getProduceId());
			stm.setString(2, produce.getProduceName());
			stm.setString(3, produce.getSupplier());
			if(stm.executeUpdate() == 0) {
				throw new NotFoundException("Produce with id " + produce.getProduceId() + " does not exist");
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
	public void deleteProduce(int produceId) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL delete_produce(?);");
			stm.setInt(1, produceId);
			if(stm.executeUpdate() == 0) {
				throw new NotFoundException("Produce with id " + produceId + " does not exist");
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
	public List<ProduceDTO> getProduceList() throws ConnectivityException, NotFoundException {
		List<ProduceDTO> list = new ArrayList<ProduceDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("SELECT * FROM produce;");
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("No produces found");
			}
			do {
				list.add(new ProduceDTO(
						rs.getInt("produce_id"),
						rs.getString("produce_name"),
						rs.getString("supplier")));
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
	public List<ProduceOverviewDTO> getProduceOverview() throws ConnectivityException, NotFoundException {
		List<ProduceOverviewDTO> list = new ArrayList<ProduceOverviewDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("SELECT * FROM produce_overview;");
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("No produces found");
			}	
			do {
				list.add(new ProduceOverviewDTO(
						rs.getInt("produce_id"),
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
}
