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

/**
 * 
 * This class is the implementation of ProduceDAO.
 * This class (data access object) accesses the data in the relation 'produce' from the database.
 * We are using the relation 'produce', the view 'produce_overview', the routine 'create_produce(TEXT, TEXT)' and 'update_produce_by_id(INT, TEXT, TEXT)'.
 * 
 */

public class MySQLProduceDAO implements ProduceDAO {


	/**
	 * Returns a specific produce as ProduceDTO (produce data transfer object) by the input-id from the database.
	 * Uses the relation 'produce' in the database.
	 * @param raavareId - the specific id of the produce in the database
	 * @return produce - returns the produce that was specified
	 */
	@Override
	public ProduceDTO getProduce(int raavareId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM produce WHERE "+raavareId+"=produce_id;");
		try{
			rs.next();
			return new ProduceDTO(rs.getInt("produce_id"), rs.getString("produce_name"), rs.getString("supplier"));
		} catch (SQLException e){ throw new DALException(e); }
	}

	/**
	 * Returns the relation 'produce' as a list of ProduceDTO's.
	 * Uses the relation 'produce' in the database.
	 * @return list - a list of ProduceDTO's
	 */
	@Override
	public List<ProduceDTO> getProduceList() throws DALException {
		List<ProduceDTO> list = new ArrayList<ProduceDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM produce;");
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
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	/**
	 * Creates a produce in the relation 'produce' in our database.
	 * Uses the routine 'create_produce(TEXT, TEXT)'.
	 * @param produce - a single produce wrapped as a ProduceDTO
	 */
	@Override
	public void createProduce(ProduceDTO produce) throws DALException {
		Connector.doQuery("CALL create_produce('" + produce.getProduceName() + "', '" + produce.getSupplier() + "');");
	}

	/**
	 * Updates the produce, which is found by the id, in the relation 'produce' in our database.
	 * The produceId will determine which produce is being overwritten/updated in the database. Produce name and supplier will be put in as the new values.
	 * Uses the routine 'update_produce_by_id(INT, TEXT, TEXT)'.
	 * @param produce - a single produce wrapped as a ProduceDTO
	 */
	@Override
	public void updateProduce(ProduceDTO produce) throws DALException {
		Connector.doQuery("CALL update_produce_by_id('"+produce.getProduceId()+"', '" + produce.getProduceName() + "', '" + produce.getSupplier() + "');");
	}

	/**
	 * Returns the view 'produce_overview' as a list of ProduceOverviewDTO's. 
	 * Uses the view 'produce_overview' in the database.
	 * @return list - a list of ProduceDTO's
	 */
	@Override
	public List<ProduceOverviewDTO> getProduceOverview() throws DALException{
		List<ProduceOverviewDTO> list = new ArrayList<ProduceOverviewDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM produce_overview;");
		try
		{
			while (rs.next())
			{
				list.add(new ProduceOverviewDTO(
						rs.getInt("produce_id"),
						rs.getString("produce_name"),
						rs.getDouble("amount")
				));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
}
