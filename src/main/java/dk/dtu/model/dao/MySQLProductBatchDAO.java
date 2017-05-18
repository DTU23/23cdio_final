package main.java.dk.dtu.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.dk.dtu.model.connector.Connector;
import main.java.dk.dtu.model.dto.ProductBatchDTO;
import main.java.dk.dtu.model.interfaces.DALException;
import main.java.dk.dtu.model.interfaces.ProductBatchDAO;

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
}
