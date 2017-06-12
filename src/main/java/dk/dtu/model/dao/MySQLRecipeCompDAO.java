package dk.dtu.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.DataSource;
import dk.dtu.model.dto.RecipeCompDTO;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.interfaces.RecipeCompDAO;

public class MySQLRecipeCompDAO implements RecipeCompDAO {

	@Override
	public void createRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL create_recipe_component(?,?,?,?);");
			stm.setInt(1, recipeComponent.getRecipeId());
			stm.setInt(2, recipeComponent.getProduceId());
			stm.setDouble(3, recipeComponent.getNomNetto());
			stm.setDouble(4, recipeComponent.getTolerance());
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
	public RecipeCompDTO readRecipeComp(int recipeId, int produceId) throws DALException {
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
				throw new DALException("Recipe component with recipe id " + recipeId + " and produce id " + produceId + " does not exist");
			}
			return new RecipeCompDTO(
					rs.getInt("recipe_id"),
					rs.getInt("produce_id"),
					rs.getDouble("nom_netto"),
					rs.getDouble("tolerance"));
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}

	@Override
	public void updateRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
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
	public void deleteRecipeComp(int recipeId, int produceId) throws DALException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL delete_recipe_component(?,?);");
			stm.setInt(1, recipeId);
			stm.setInt(2, produceId);
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
	public List<RecipeCompDTO> getRecipeCompList() throws DALException {
		List<RecipeCompDTO> list = new ArrayList<RecipeCompDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("SELECT * FROM recipecomponent;");
			rs = stm.executeQuery();
			while (rs.next()) {
				list.add(new RecipeCompDTO(
						rs.getInt("recipe_id"),
						rs.getInt("produce_id"),
						rs.getDouble("nom_netto"),
						rs.getDouble("tolerance")));
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
	public List<RecipeCompDTO> getRecipeCompByRecipeId(int recipeId) throws DALException {
		List<RecipeCompDTO> list = new ArrayList<RecipeCompDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL get_recipe_comp_by_recipe_id(?);");
			stm.setInt(1, recipeId);
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new DALException("Recipe with id " + recipeId + " does not exist");
			}
			while (rs.next()) {
				list.add(new RecipeCompDTO(
						rs.getInt("recipe_id"),
						rs.getInt("produce_id"),
						rs.getDouble("nom_netto"),
						rs.getDouble("tolerance")));
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
