package dk.dtu.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.Connector;
import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.ProductBatchDAO;

public class MySQLProductBatchDAO implements ProductBatchDAO {

	@Override
	public ProductBatchDTO getProductBatch(int pbId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM productbatch WHERE pb_id = " + pbId + ";");
		try {
			if (!rs.first()) throw new DALException("Product batch with id " + pbId + " does not exist");
			return new ProductBatchDTO(rs.getInt("pb_id"), rs.getInt("status"), rs.getInt("recipe_id"));
		}
		catch (SQLException e) {throw new DALException(e); }
	}

	@Override
	public List<ProductBatchDTO> getProductBatchList() throws DALException {
		List<ProductBatchDTO> list = new ArrayList<ProductBatchDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM productbatch;");
		try
		{
			while (rs.next()) 
			{
				list.add(new ProductBatchDTO(rs.getInt("pb_id"), rs.getInt("status"), rs.getInt("recipe_id")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createProductBatch(int recipe_id) throws DALException {
		if(recipe_id < 0){
			throw new DALException("invalid recipe id");
		}
		Connector.doUpdate("CALL create_product_batch_from_recipe_id(" + recipe_id + ");");
	}

	@Override
	public void updateProductBatchStatus(ProductBatchDTO productbatch) throws DALException {
		if(productbatch.getStatus() == 0 || productbatch.getStatus() == 1 || productbatch.getStatus() == 2){
			Connector.doUpdate("CALL update_product_batch_status(" + productbatch.getPbId() + " , "+productbatch.getStatus()+");");
		}else{
			throw new DALException("Invalid Status provided!");
		}
	}

	public boolean exists(int pbId) throws DALException{
		ResultSet rs = Connector.doQuery("SELECT * FROM productbatch WHERE pb_id = " + pbId + ";");
		try {
			return rs.first();
		}
		catch (SQLException e) {
			return false;
		}
	}
	
	@Override
	public List<ProductBatchCompOverviewDTO> getProductBatchDetailsByPbId(int productBatchID) throws DALException {
		List<ProductBatchCompOverviewDTO> list = new ArrayList<ProductBatchCompOverviewDTO>();
		ResultSet rs = Connector.doQuery("CALL get_product_batch_details_by_pb_id("+productBatchID+");");
		
		try {
			while (rs.next())
			{
				list.add(new ProductBatchCompOverviewDTO(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getInt("recipe_id"), rs.getString("recipe_name"), rs.getInt("status"), rs.getString("produce_name"), rs.getDouble("netto"), rs.getInt("opr_id")));
			}
		} catch (SQLException e) {	throw new DALException(e); }
		
		return list;
	}
}
