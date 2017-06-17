package dk.dtu.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.DataSource;
import dk.dtu.model.dto.RecipeCompDTO;
import dk.dtu.model.dto.RecipeListDTO;
import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;
import dk.dtu.model.exceptions.dal.NotFoundException;
import dk.dtu.model.interfaces.RecipeCompDAO;

public class MySQLRecipeCompDAO implements RecipeCompDAO {

	@Override
	public void createRecipeComp(RecipeCompDTO recipeComponent) throws ConnectivityException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL create_recipe_component(?,?,?,?);");
			stm.setInt(1, recipeComponent.getRecipeId());
			stm.setInt(2, recipeComponent.getProduceId());
			stm.setDouble(3, recipeComponent.getNomNetto());
			stm.setDouble(4, recipeComponent.getTolerance());
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
	public RecipeCompDTO readRecipeComp(int recipeId, int produceId) throws ConnectivityException, NotFoundException {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL read_recipe_component(?,?);");
			stm.setInt(1, recipeId);
			stm.setInt(2, produceId);
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("Recipe component with recipe id " + recipeId + " and produce id " + produceId + " does not exist");
			}
			return new RecipeCompDTO(
					rs.getInt("recipe_id"),
					rs.getInt("produce_id"),
					rs.getDouble("nom_netto"),
					rs.getDouble("tolerance"));
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public void updateRecipeComp(RecipeCompDTO recipeComponent) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL update_recipe_component(?,?,?,?);");
			stm.setInt(1, recipeComponent.getRecipeId());
			stm.setInt(2, recipeComponent.getProduceId());
			stm.setDouble(3, recipeComponent.getNomNetto());
			stm.setDouble(4, recipeComponent.getTolerance());
			if(stm.executeUpdate() == 0) {
				throw new NotFoundException("Recipe component with recipe id " + recipeComponent.getRecipeId() + " and produce id " + recipeComponent.getProduceId() + " does not exist");
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
	public void deleteRecipeComp(int recipeId, int produceId) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL delete_recipe_component(?,?);");
			stm.setInt(1, recipeId);
			stm.setInt(2, produceId);
			if(stm.executeUpdate() == 0) {
				throw new NotFoundException("Recipe component with recipe id " + recipeId + " and produce id " + produceId + " does not exist");
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
	public List<RecipeCompDTO> getRecipeCompList() throws ConnectivityException, NotFoundException {
		List<RecipeCompDTO> list = new ArrayList<RecipeCompDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("SELECT * FROM recipecomponent;");
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("No recipe components found");
			}
			do {
				list.add(new RecipeCompDTO(
						rs.getInt("recipe_id"),
						rs.getInt("produce_id"),
						rs.getDouble("nom_netto"),
						rs.getDouble("tolerance")));
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
	public List<RecipeListDTO> getRecipeCompByRecipeId(int recipeId) throws ConnectivityException, NotFoundException {
		List<RecipeListDTO> list = new ArrayList<RecipeListDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL get_recipe_comp_by_recipe_id(?);");
			stm.setInt(1, recipeId);
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("Recipe with id " + recipeId + " does not exist");
			}
			do {
				list.add(new RecipeListDTO(
						rs.getInt("recipe_id"),
						null,
						rs.getInt("produce_id"),
						rs.getString("produce_name"),
						rs.getDouble("nom_netto"),
						rs.getDouble("tolerance")));
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