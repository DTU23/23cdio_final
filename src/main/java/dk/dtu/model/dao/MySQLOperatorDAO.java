package dk.dtu.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.Connector;
import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.dto.OperatorNoPWDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.OperatorDAO;

public class MySQLOperatorDAO implements OperatorDAO {

	@Override
	public void createOperator(OperatorDTO opr) throws DALException {
		if(Connector.getInstance().doUpdate("CALL create_operator('" 
				+ opr.getOprId() + "','" 
				+ opr.getOprName() + "','" 
				+ opr.getIni() + "','" 
				+ opr.getCpr() + "','" 
				+ opr.getPassword() + "'," 
				+ opr.isAdmin() + ",'" 
				+ opr.getRole() + "');" ) == 0)
		{
			throw new DALException("No rows affected");
		}
	}

	@Override
	public OperatorDTO readOperator(int oprId) throws DALException {
		ResultSet rs = Connector.getInstance().doQuery("CALL read_operator(" + oprId + ");");
		try	
		{
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
			Connector.getInstance().closeResources();
		}
	}

	@Override
	public void updateOperator(OperatorDTO opr) throws DALException {
		if(Connector.getInstance().doUpdate("CALL update_operator('" 
				+ opr.getOprId() + "','"
				+ opr.getOprName() + "','" 
				+ opr.getIni() + "','" 
				+ opr.getCpr() + "','" 
				+ opr.getPassword() + "'," 
				+ opr.isAdmin() + ",'" 
				+ opr.getRole() + "');" ) == 0)
		{
			throw new DALException("No rows affected");
		}
	}

	@Override
	public void deleteOperator(int oprId) throws DALException {
		if(Connector.getInstance().doUpdate("CALL delete_operator(" + oprId + ");") == 0) 
		{
			throw new DALException("No rows affected");
		}
	}

	@Override
	public List<OperatorNoPWDTO> getOperatorList() throws DALException {
		List<OperatorNoPWDTO> list = new ArrayList<OperatorNoPWDTO>();
		ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM operator_list;");
		try
		{
			while (rs.next()) 
			{
				list.add(new OperatorNoPWDTO(
						rs.getInt("opr_id"),
						rs.getString("opr_name"),
						rs.getString("ini"),
						rs.getString("cpr"),
						rs.getBoolean("admin"),
						rs.getString("role")));
			}
			return list;
		}
		catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}
}