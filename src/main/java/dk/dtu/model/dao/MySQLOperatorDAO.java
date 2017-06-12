package dk.dtu.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.Connector;
import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.dto.OperatorNoPWDTO;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.interfaces.OperatorDAO;

public class MySQLOperatorDAO implements OperatorDAO {

	@Override
	public void createOperator(OperatorDTO opr) throws DALException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = Connector.getConnection();
			stm = conn.prepareStatement("CALL create_operator(?,?,?,?,?,?,?)");
			stm.setInt(1, opr.getOprId());
			stm.setString(2, opr.getOprName());
			stm.setString(3, opr.getIni());
			stm.setString(4, opr.getCpr());
			stm.setString(5, opr.getPassword());
			stm.setBoolean(6, opr.isAdmin());
			stm.setString(7, opr.getRole());
			if(stm.executeUpdate() == 0) {
				throw new DALException("No rows affected");
			}
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public OperatorDTO readOperator(int oprId) throws DALException {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = Connector.getConnection();
			stm = conn.prepareStatement("CALL read_operator(?);");
			stm.setInt(1, oprId);
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new DALException("Operator with id " + oprId + " does not exist");
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
			throw new DALException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public OperatorNoPWDTO readOperatorNoPW(int oprId) throws DALException{
		OperatorDTO opr = readOperator(oprId);
		return new OperatorNoPWDTO(opr);
	}

	@Override
	public void updateOperator(OperatorDTO opr) throws DALException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = Connector.getConnection();
			stm = conn.prepareStatement("CALL update_operator(?,?,?,?,?,?,?)");
			stm.setInt(1, opr.getOprId());
			stm.setString(2, opr.getOprName());
			stm.setString(3, opr.getIni());
			stm.setString(4, opr.getCpr());
			stm.setString(5, opr.getPassword());
			stm.setBoolean(6, opr.isAdmin());
			stm.setString(7, opr.getRole());
			if(stm.executeUpdate() == 0) {
				throw new DALException("No rows affected");
			}
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public void deleteOperator(int oprId) throws DALException {
		Connection conn = null;
		PreparedStatement stm = null;
		try	{
			conn = Connector.getConnection();
			stm = conn.prepareStatement("CALL delete_operator(?);");
			stm.setInt(1, oprId);
			if(stm.executeUpdate() == 0) {
				throw new DALException("No rows affected");
			}
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
		if(Connector.getInstance().doUpdate("CALL delete_operator(" + oprId + ");") == 0) 
		{
			throw new DALException("No rows affected");
		}
	}

	@Override
	public List<OperatorNoPWDTO> getOperatorList() throws DALException {
		List<OperatorNoPWDTO> list = new ArrayList<OperatorNoPWDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = Connector.getConnection();
			stm = conn.prepareStatement("SELECT * FROM operator_list;");
			rs = stm.executeQuery();
			while (rs.next()) {
				list.add(new OperatorNoPWDTO(
						rs.getInt("opr_id"),
						rs.getString("opr_name"),
						rs.getString("ini"),
						rs.getString("cpr"),
						rs.getBoolean("admin"),
						rs.getString("role")));
			}
			return list;
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}
}