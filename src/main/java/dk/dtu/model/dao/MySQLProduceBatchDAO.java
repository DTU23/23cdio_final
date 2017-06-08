package dk.dtu.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.Connector;
import dk.dtu.model.dto.ProduceBatchDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.ProduceBatchDAO;

public class MySQLProduceBatchDAO implements ProduceBatchDAO {

	@Override
	public void createProduceBatch(int produce_id, double amount) throws DALException {
		if(Connector.doUpdate("CALL create_produce_batch('"+produce_id+"', '"+amount+"');") == 0)
		{
			throw new DALException("No rows affected!");
		}
	}
	
	@Override
	public ProduceBatchDTO readProduceBatch(int rbId) throws DALException {
		ResultSet rs = Connector.doQuery("CALL read_produce_batch('"+rbId+"');");
		try 
		{
			if(!rs.first()) throw new DALException("Produce batch with id " + rbId + " does not exist");
			return new ProduceBatchDTO(rs.getInt("rb_id"), rs.getInt("produce_id"),	null, null,	rs.getDouble("amount"));
		} catch (SQLException e) { throw new DALException(e); }
	}

	@Override
	public void updateProduceBatch(int produce_id, double amount) throws DALException {
		if(Connector.doUpdate("CALL update_produce_batch('"+produce_id+"', '"+amount+"');") == 0) 
		{
			throw new DALException("No rows affected!");
		}
	}
	
	@Override
	public void deleteProduceBatch(int rbId) throws DALException {
		if(Connector.doUpdate("CALL delete_produce_batch('"+rbId+"');") == 0) 
		{
			throw new DALException("No rows affected");
		}
	}
	
	@Override
	public ProduceBatchDTO getProduceBatchWithProduceName(int rbId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM produce_batch_list WHERE rb_id="+rbId+";");
		try
		{
			rs.next();
			return new ProduceBatchDTO(
					rs.getInt("rb_id"),
					null,
					rs.getString("produce_name"),
					rs.getString("supplier"),
					rs.getDouble("amount")
			);
		} catch (SQLException e){ throw new DALException(e); }
	}

	@Override
	public List<ProduceBatchDTO> getProduceBatchList() throws DALException {
		List<ProduceBatchDTO> list = new ArrayList<ProduceBatchDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM produce_batch_list;");
		try 
		{
			while (rs.next()) 
			{
				list.add(new ProduceBatchDTO(
						rs.getInt("rb_id"),
						null,
						rs.getString("produce_name"),
						rs.getString("supplier"),
						rs.getDouble("amount")
				));
			}
		} catch (SQLException e) { throw new DALException(e); }
		return list;
	}


}