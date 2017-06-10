package dk.dtu.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.Connector;
import dk.dtu.model.dto.ProductBatchCompDTO;
import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.ProductBatchCompSupplierDetailsDTO;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.interfaces.ProductBatchCompDAO;

public class MySQLProductBatchCompDAO implements ProductBatchCompDAO {

	@Override
	public void createProductBatchComp(ProductBatchCompDTO productBatchComponent) throws DALException {
		if(Connector.getInstance().doUpdate("CALL create_product_batch_component('" 
				+ productBatchComponent.getPbId() + "', '" 
				+ productBatchComponent.getRbId() + "', '" 
				+ productBatchComponent.getTara() + "', '" 
				+ productBatchComponent.getNetto() + "', '" 
				+ productBatchComponent.getOprId() + "');") == 0)
		{
			throw new DALException("No rows affected!");
		}
	}

	@Override
	public ProductBatchCompDTO readProductBatchComp(int pbId, int rbId) throws DALException {
		ResultSet rs = Connector.getInstance().doQuery("CALL read_product_batch_component('"+pbId+"', '"+rbId+"');");
		try 
		{
			if (!rs.first()) {
				throw new DALException("Product batch component with pbId " + pbId + " and rbId " + rbId + " not found!");
			}
			return new ProductBatchCompDTO (
					rs.getInt("pb_id"),
					rs.getInt("rb_id"),
					rs.getDouble("tara"),
					rs.getDouble("netto"),
					rs.getInt("opr_id"));
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}

	@Override
	public void updateProductBatchComp(ProductBatchCompDTO productBatchComponent) throws DALException {
		if(Connector.getInstance().doUpdate("CALL update_product_batch_component('" 
				+ productBatchComponent.getPbId() + "','" 
				+ productBatchComponent.getRbId() + "','" 
				+ productBatchComponent.getTara() + "','"
				+ productBatchComponent.getNetto() + "','" 
				+ productBatchComponent.getOprId() + "');") == 0)
		{
			throw new DALException("No rows affected");
		}
	}

	@Override
	public void deleteProductBatchComp(int pbId, int rbId) throws DALException {
		if(Connector.getInstance().doUpdate("CALL delete_product_batch_component(" + pbId + "," + rbId + ";") == 0)
		{
			throw new DALException("No rows affected");
		}
	}

	@Override
	public List<ProductBatchCompDTO> getProductBatchCompList() throws DALException {
		List<ProductBatchCompDTO> list = new ArrayList<ProductBatchCompDTO>();
		ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM productbatchcomponent;");
		try
		{
			while (rs.next())
			{
				list.add(new ProductBatchCompDTO(
						rs.getInt("pb_id"),
						rs.getInt("rb_id"),
						rs.getDouble("tara"),
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

	@Override
	public List<ProductBatchCompOverviewDTO> getProductBatchCompOverview() throws DALException {
		List<ProductBatchCompOverviewDTO> list = new ArrayList<ProductBatchCompOverviewDTO>();
		ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM product_batch_component_overview;");
		try
		{
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

	@Override
	public List<ProductBatchCompSupplierDetailsDTO> getProductBatchComponentSupplierDetailsByPbId(int pbId) throws DALException{
		List<ProductBatchCompSupplierDetailsDTO> list = new ArrayList<ProductBatchCompSupplierDetailsDTO>();
		ResultSet rs = Connector.getInstance().doQuery("CALL get_product_batch_component_supplier_details_by_pb_id("+pbId+");");
		try
		{
			if (!rs.first()) {
				throw new DALException("Product batch with pbId " + pbId + " not found!");
			}
			while (rs.next())
			{
				list.add(new ProductBatchCompSupplierDetailsDTO(
						rs.getInt("rb_id"),
						rs.getString("supplier"),
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
