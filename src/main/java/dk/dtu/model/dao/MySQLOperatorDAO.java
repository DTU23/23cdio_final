package main.java.dk.dtu.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.dk.dtu.model.connector.Connector;
import main.java.dk.dtu.model.dto.OperatorDTO;
import main.java.dk.dtu.model.dto.OperatorNoPWDTO;
import main.java.dk.dtu.model.interfaces.DALException;
import main.java.dk.dtu.model.interfaces.OperatorDAO;

public class MySQLOperatorDAO implements OperatorDAO {

	@Override
	public OperatorDTO getOperator(int oprId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM operator WHERE opr_id = " + oprId + ";");
		try
		{
			if (!rs.first()) throw new DALException("Operator with id " + oprId + " does not exist");
			return new OperatorDTO (rs.getInt("opr_id"), rs.getString("opr_name"), rs.getString("ini"), rs.getString("cpr"), rs.getString("password"), rs.getBoolean("admin"), rs.getString("role"));
		}
		catch (SQLException e) {throw new DALException(e); }
	}

	@Override
	public void createOperator(OperatorDTO opr) throws DALException {
		if(Connector.doUpdate("CALL create_operator(" + opr.getOprId() + ",'" + opr.getOprName() + "','" + opr.getIni() + "','" + 
				opr.getCpr() + "','" + opr.getPassword() + "'," + opr.getAdmin() + ",'" + opr.getRole() + "');") == 0)
		{
			throw new DALException("No rows affected");
		}
	}
	
	@Override
	public void deleteOperator(int oprId) throws DALException {
		Connector.doUpdate("CALL delete_operator(" + oprId + ";");
		{
			throw new DALException("No rows affected");
		}
	}

	@Override
	public void updateOperator(OperatorDTO opr) throws DALException {
		if(Connector.doUpdate("CALL update_operator(" + opr.getOprId() + ",'" + opr.getOprName() + "','" + opr.getIni() + "','" + 
				opr.getCpr() + "','" + opr.getPassword() + "'," + opr.getAdmin() + ",'" + opr.getRole() + "');") == 0)
		{
			throw new DALException("No rows affected");
		}
	}

	@Override
	public List<OperatorNoPWDTO> getOperatorList() throws DALException {
		List<OperatorNoPWDTO> list = new ArrayList<OperatorNoPWDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM operator_list;");
		try
		{
			while (rs.next()) 
			{
				list.add(new OperatorNoPWDTO(rs.getInt("opr_id"), rs.getString("opr_name"), rs.getString("ini"), rs.getString("cpr"), rs.getBoolean("admin"), rs.getString("role")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
}