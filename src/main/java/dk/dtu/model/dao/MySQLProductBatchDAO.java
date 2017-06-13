package dk.dtu.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.DataSource;
import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.dto.ProductBatchListDTO;
import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;
import dk.dtu.model.exceptions.dal.NotFoundException;
import dk.dtu.model.interfaces.ProductBatchDAO;

public class MySQLProductBatchDAO implements ProductBatchDAO {

	@Override
	public void createProductBatch(ProductBatchDTO producBatchDTO) throws ConnectivityException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL create_product_batch(?,?);");
			stm.setInt(1, producBatchDTO.getPbId());
			stm.setInt(2, producBatchDTO.getRecipeId());
			stm.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IntegrityConstraintViolationException(e);
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public ProductBatchDTO readProductBatch(int pbId) throws ConnectivityException, NotFoundException {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL read_product_batch(?);");
			stm.setInt(1, pbId);
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("Product batch with id " + pbId + " does not exist");
			}
			return new ProductBatchDTO(
					rs.getInt("pb_id"),
					rs.getInt("recipe_id"),
					rs.getInt("status"));
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public void updateProductBatch(ProductBatchDTO producBatchDTO) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL update_product_batch(?,?,?);");
			stm.setInt(1, producBatchDTO.getPbId());
			stm.setInt(2, producBatchDTO.getRecipeId());
			stm.setInt(3, producBatchDTO.getStatus());
			if(stm.executeUpdate() == 0) {
				throw new NotFoundException("Product batch with id " + producBatchDTO.getPbId() + " does not exist");
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IntegrityConstraintViolationException(e);
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public void deleteProductBatch(int pbId) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL delete_product_batch(?);");
			stm.setInt(1, pbId);
			if(stm.executeUpdate() == 0) {
				throw new NotFoundException("Product batch with id " + pbId + " does not exist");
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IntegrityConstraintViolationException(e);
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public List<ProductBatchListDTO> getProductBatchList() throws ConnectivityException, NotFoundException {
		List<ProductBatchListDTO> list = new ArrayList<ProductBatchListDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("SELECT * FROM product_batch_list;");
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("No product batches found");
			}
			do {
				list.add(new ProductBatchListDTO(
						rs.getInt("pb_id"),
						rs.getInt("recipe_id"),
						rs.getString("recipe_name"),
						rs.getInt("status")));
			} while (rs.next());
			return list;
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public ProductBatchListDTO getProductBatchListDetailsByPbId(int pbId) throws ConnectivityException, NotFoundException {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL get_product_batch_list_details_by_pb_id(?);");
			stm.setInt(1, pbId);
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("Product batch with id " + pbId + " does not exist");
			}
			return new ProductBatchListDTO(
					rs.getInt("pb_id"),
					rs.getInt("recipe_id"),
					rs.getString("recipe_name"),
					rs.getInt("status"));
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public List<ProductBatchCompOverviewDTO> getProductBatchDetailsByPbId(int pbId) throws ConnectivityException, NotFoundException {
		List<ProductBatchCompOverviewDTO> list = new ArrayList<ProductBatchCompOverviewDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL get_product_batch_details_by_pb_id(?);");
			stm.setInt(1, pbId);
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("Product batch with id " + pbId + " does not exist");
			}
			do {
				list.add(new ProductBatchCompOverviewDTO(
						rs.getInt("pb_id"),
						rs.getInt("rb_id"),
						rs.getInt("recipe_id"),
						rs.getString("recipe_name"),
						rs.getInt("status"),
						rs.getString("produce_name"),
						rs.getDouble("netto"),
						rs.getInt("opr_id")));
			} while (rs.next());
			return list;
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}
}