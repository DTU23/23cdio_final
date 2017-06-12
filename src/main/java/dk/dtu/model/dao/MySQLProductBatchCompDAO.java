package dk.dtu.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.DataSource;
import dk.dtu.model.dto.ProductBatchCompDTO;
import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.ProductBatchCompSupplierDetailsDTO;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.interfaces.ProductBatchCompDAO;

public class MySQLProductBatchCompDAO implements ProductBatchCompDAO {

	@Override
	public void createProductBatchComp(ProductBatchCompDTO productBatchComponent) throws DALException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL create_product_batch_component(?,?,?,?,?)");
			stm.setInt(1, productBatchComponent.getPbId());
			stm.setInt(2, productBatchComponent.getRbId());
			stm.setDouble(3, productBatchComponent.getTara());
			stm.setDouble(4, productBatchComponent.getNetto());
			stm.setInt(5, productBatchComponent.getOprId());
			if(stm.executeUpdate() == 0) {
				throw new DALException("No rows affected");
			}
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public ProductBatchCompDTO readProductBatchComp(int pbId, int rbId) throws DALException {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL read_product_batch_component(?,?);");
			stm.setInt(1, pbId);
			stm.setInt(2, rbId);
			rs = stm.executeQuery();
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
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public void updateProductBatchComp(ProductBatchCompDTO productBatchComponent) throws DALException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL update_product_batch_component(?,?,?,?,?)");
			stm.setInt(1, productBatchComponent.getPbId());
			stm.setInt(2, productBatchComponent.getRbId());
			stm.setDouble(3, productBatchComponent.getTara());
			stm.setDouble(4, productBatchComponent.getNetto());
			stm.setInt(5, productBatchComponent.getOprId());
			if(stm.executeUpdate() == 0) {
				throw new DALException("No rows affected");
			}
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public void deleteProductBatchComp(int pbId, int rbId) throws DALException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL delete_product_batch_component(?,?)");
			stm.setInt(1, pbId);
			stm.setInt(2, rbId);
			if(stm.executeUpdate() == 0) {
				throw new DALException("No rows affected");
			}
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public List<ProductBatchCompDTO> getProductBatchCompList() throws DALException {
		List<ProductBatchCompDTO> list = new ArrayList<ProductBatchCompDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("SELECT * FROM productbatchcomponent;");
			rs = stm.executeQuery();
			while (rs.next()) {
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
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public List<ProductBatchCompOverviewDTO> getProductBatchCompOverview() throws DALException {
		List<ProductBatchCompOverviewDTO> list = new ArrayList<ProductBatchCompOverviewDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("SELECT * FROM product_batch_component_overview;");
			rs = stm.executeQuery();
			while (rs.next()) {
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
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public List<ProductBatchCompSupplierDetailsDTO> getProductBatchComponentSupplierDetailsByPbId(int pbId) throws DALException{
		List<ProductBatchCompSupplierDetailsDTO> list = new ArrayList<ProductBatchCompSupplierDetailsDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL get_product_batch_component_supplier_details_by_pb_id(?);");
			stm.setInt(1, pbId);
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new DALException("Product batch with pbId " + pbId + " not found!");
			}
			while (rs.next()) {
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
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}
}
