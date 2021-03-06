package dk.dtu.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.DataSource;
import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.dto.OperatorNoPWDTO;
import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;
import dk.dtu.model.exceptions.dal.NotFoundException;
import dk.dtu.model.interfaces.OperatorDAO;

public class MySQLOperatorDAO implements OperatorDAO {

	@Override
	public void createOperator(OperatorDTO opr) throws ConnectivityException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL create_operator(?,?,?,?,?,?,?)");
			stm.setInt(1, opr.getOprId());
			stm.setString(2, opr.getOprName());
			stm.setString(3, opr.getIni());
			stm.setString(4, opr.getCpr());
			stm.setString(5, opr.getPassword());
			stm.setBoolean(6, opr.isAdmin());
			stm.setString(7, opr.getRole());
			stm.executeUpdate();
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
	public OperatorDTO readOperator(int oprId) throws ConnectivityException, NotFoundException {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL read_operator(?);");
			stm.setInt(1, oprId);
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("Operator with id " + oprId + " does not exist");
			}
			return new OperatorDTO(
					rs.getInt("opr_id"),
					rs.getString("opr_name"),
					rs.getString("ini"),
					rs.getString("cpr"),
					rs.getString("password"),
					rs.getBoolean("admin"),
					rs.getString("role"));
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public OperatorNoPWDTO readOperatorNoPW(int oprId) throws ConnectivityException, NotFoundException {
		OperatorDTO opr = readOperator(oprId);
		return new OperatorNoPWDTO(opr);
	}

	@Override
	public void updateOperator(OperatorDTO opr) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL update_operator(?,?,?,?,?,?,?)");
			stm.setInt(1, opr.getOprId());
			stm.setString(2, opr.getOprName());
			stm.setString(3, opr.getIni());
			stm.setString(4, opr.getCpr());
			stm.setString(5, opr.getPassword());
			stm.setBoolean(6, opr.isAdmin());
			stm.setString(7, opr.getRole());
			if(stm.executeUpdate() == 0) {
				throw new NotFoundException("Operator with id " + opr.getOprId() + " does not exist");
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
	public void deleteOperator(int oprId) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL delete_operator(?);");
			stm.setInt(1, oprId);
			if(stm.executeUpdate() == 0) {
				throw new NotFoundException("Operator with id " + oprId + " does not exist");
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
	public List<OperatorNoPWDTO> getOperatorList() throws ConnectivityException, NotFoundException {
		List<OperatorNoPWDTO> list = new ArrayList<OperatorNoPWDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("SELECT * FROM operator_list;");
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("No operators found");
			}
			do {
				list.add(new OperatorNoPWDTO(
						rs.getInt("opr_id"),
						rs.getString("opr_name"),
						rs.getString("ini"),
						rs.getString("cpr"),
						rs.getBoolean("admin"),
						rs.getString("role")));
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