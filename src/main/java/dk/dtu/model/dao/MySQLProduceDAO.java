package dk.dtu.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.Connector;
import dk.dtu.model.dto.ProduceDTO;
import dk.dtu.model.dto.ProduceOverviewDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.ProduceDAO;

public class MySQLProduceDAO implements ProduceDAO {

	@Override
	public void createProduce(ProduceDTO produce) throws DALException {
		if(Connector.getInstance().doUpdate("CALL create_produce('"
				+ produce.getProduceName() + "','"
				+ produce.getSupplier() + "');") == 0) 
		{
			throw new DALException("No rows affected!");
		}
	}

	@Override
	public ProduceDTO readProduce(int produceId) throws DALException {
		ResultSet rs = Connector.getInstance().doQuery("CALL read_produce('" + produceId + "');");
		try {
			if (!rs.first()) {
				throw new DALException("Produce with id " + produceId + " does not exist");
			}
			return new ProduceDTO(
					rs.getInt("produce_id"),
					rs.getString("produce_name"),
					rs.getString("supplier"));
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}

	@Override
	public void updateProduce(ProduceDTO produce) throws DALException {
		if(Connector.getInstance().doUpdate("CALL update_produce('"
				+ produce.getProduceId() + "','"
				+ produce.getProduceName() + "','"
				+ produce.getSupplier() + "');") == 0) 
		{
			throw new DALException("No rows affected!");
		}
	}

	@Override
	public void deleteProduce(int raavareId) throws DALException {
		if(Connector.getInstance().doUpdate("CALL delete_produce(" + raavareId + ");") == 0)
		{
			throw new DALException("No rows affected");
		}
	}

	@Override
	public List<ProduceDTO> getProduceList() throws DALException {
		List<ProduceDTO> list = new ArrayList<ProduceDTO>();
		ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM produce;");
		try
		{
			while (rs.next())
			{
				list.add(new ProduceDTO(
						rs.getInt("produce_id"),
						rs.getString("produce_name"),
						rs.getString("supplier")
						));
			}
			return list;
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}

	@Override
	public List<ProduceOverviewDTO> getProduceOverview() throws DALException{
		List<ProduceOverviewDTO> list = new ArrayList<ProduceOverviewDTO>();
		ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM produce_overview;");
		try
		{
			while (rs.next())
			{
				list.add(new ProduceOverviewDTO(
						rs.getInt("produce_id"),
						rs.getString("produce_name"),
						rs.getString("supplier"),
						rs.getDouble("amount")
						));
			}
			return list;
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}
}
