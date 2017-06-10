package dk.dtu.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.Connector;
import dk.dtu.model.dto.ProduceBatchDTO;
import dk.dtu.model.dto.StockDTO;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.interfaces.ProduceBatchDAO;

public class MySQLProduceBatchDAO implements ProduceBatchDAO {

	@Override
	public void createProduceBatch(int produce_id, double amount) throws DALException {
		if(Connector.getInstance().doUpdate("CALL create_produce_batch('"+produce_id+"','"+amount+"');") == 0)
		{
			throw new DALException("No rows affected!");
		}
	}
	
	@Override
	public ProduceBatchDTO readProduceBatch(int rbId) throws DALException {
		ResultSet rs = Connector.getInstance().doQuery("CALL read_produce_batch('"+rbId+"');");
		try {
			if(!rs.first()) {
				throw new DALException("Produce batch with id " + rbId + " does not exist");
			}
			return new ProduceBatchDTO(
					rs.getInt("rb_id"),
					rs.getInt("produce_id"),
					null,
					null,
					rs.getDouble("amount"));
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}

	@Override
	public void updateProduceBatch(int produce_id, double amount) throws DALException {
		if(Connector.getInstance().doUpdate("CALL update_produce_batch('"+produce_id+"','"+amount+"');") == 0) 
		{
			throw new DALException("No rows affected!");
		}
	}
	
	@Override
	public void deleteProduceBatch(int rbId) throws DALException {
		if(Connector.getInstance().doUpdate("CALL delete_produce_batch('"+rbId+"');") == 0) 
		{
			throw new DALException("No rows affected");
		}
	}

	@Override
	public List<ProduceBatchDTO> getProduceBatchList() throws DALException {
		List<ProduceBatchDTO> list = new ArrayList<ProduceBatchDTO>();
		ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM produce_batch_list;");
		try 
		{
			while (rs.next()) 
			{
				list.add(new ProduceBatchDTO(
						rs.getInt("rb_id"),
						0,
						rs.getString("produce_name"),
						rs.getString("supplier"),
						rs.getDouble("amount")));
			}
			return list;
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}
	
	@Override
	public ProduceBatchDTO getProduceBatchWithProduceName(int rbId) throws DALException {
		ResultSet rs = Connector.getInstance().doQuery("CALL read_produce_batch_list('"+rbId+"');");
		try {
			if(!rs.first()) {
				throw new DALException("Produce batch with id " + rbId + " does not exist");
			}
			return new ProduceBatchDTO(
					rs.getInt("rb_id"),
					0,
					rs.getString("produce_name"),
					rs.getString("supplier"),
					rs.getDouble("amount")
			);
		} catch (SQLException e){
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}
	
	@Override
	public List<StockDTO> getProduceInStock() throws DALException {
		List<StockDTO> list = new ArrayList<StockDTO>();
		ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM produce_in_stock;");
		try 
		{
			while (rs.next()) 
			{
				list.add(new StockDTO(
						rs.getString("produce_name"),
						rs.getDouble("current_stock")));
			}
			return list;
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}
}