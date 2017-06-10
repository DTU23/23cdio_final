package dk.dtu.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.Connector;
import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.dto.ProductBatchListDTO;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.interfaces.ProductBatchDAO;

public class MySQLProductBatchDAO implements ProductBatchDAO {

	@Override
	public void createProductBatch(int recipe_id) throws DALException {
		if(Connector.getInstance().doUpdate("CALL create_product_batch('"+recipe_id+"');") == 0)
		{
			throw new DALException("No rows affected!");
		}
	}

	@Override
	public ProductBatchDTO readProductBatch(int pbId) throws DALException {
		ResultSet rs = Connector.getInstance().doQuery("CALL read_product_batch('"+pbId+"');");
		try {
			if (!rs.first()) {
				throw new DALException("Product batch with id " + pbId + " does not exist");
			}
			return new ProductBatchDTO(
					rs.getInt("pb_id"),
					rs.getInt("recipe_id"),
					rs.getInt("status"));
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}

	@Override
	public void updateProductBatch(ProductBatchDTO productbatch) throws DALException {
		if(Connector.getInstance().doUpdate("CALL update_product_batch('"
				+ productbatch.getPbId()+"','"
				+ productbatch.getRecipeId()+"','"
				+ productbatch.getStatus()+"');") == 0)
		{
			throw new DALException("No rows affected!");
		}
	}

	@Override
	public void deleteProductBatch(int pbId) throws DALException {
		if(Connector.getInstance().doUpdate("CALL delete_product_batch(" + pbId + ";") == 0)
		{
			throw new DALException("No rows affected");
		}
	}

	@Override
	public List<ProductBatchListDTO> getProductBatchList() throws DALException {
		List<ProductBatchListDTO> list = new ArrayList<ProductBatchListDTO>();
		ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM product_batch_list;");
		try
		{
			while (rs.next()) 
			{
				list.add(new ProductBatchListDTO(
						rs.getInt("pb_id"),
						rs.getInt("recipe_id"),
						rs.getString("recipe_name"),
						rs.getInt("status")));
			}
			return list;
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}

	@Override
	public ProductBatchListDTO getProductBatchListDetailsByPbId(int pbId) throws DALException {
		ResultSet rs = Connector.getInstance().doQuery("CALL get_product_batch_list_details_by_pb_id("+pbId+");");
		try {
			if(!rs.first()) {
				throw new DALException("Product batch with id " + pbId + " does not exist");
			}
			return new ProductBatchListDTO(
					rs.getInt("pb_id"),
					rs.getInt("recipe_id"),
					rs.getString("recipe_name"),
					rs.getInt("status"));
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}

	@Override
	public List<ProductBatchCompOverviewDTO> getProductBatchDetailsByPbId(int pbId) throws DALException {
		List<ProductBatchCompOverviewDTO> list = new ArrayList<ProductBatchCompOverviewDTO>();
		ResultSet rs = Connector.getInstance().doQuery("CALL get_product_batch_details_by_pb_id("+pbId+");");

		try {
			while (rs.next())
			{
				list.add(new ProductBatchCompOverviewDTO(
						rs.getInt("pb_id"),
						rs.getInt("rb_id"),
						rs.getInt("recipe_id"),
						rs.getString("recipe_name"),
						rs.getInt("status"),
						rs.getString("produce_name"),
						rs.getDouble("netto"),
						rs.getInt("opr_id")));
			}
			return list;
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}
}
