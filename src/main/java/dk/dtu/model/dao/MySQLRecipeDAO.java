package dk.dtu.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.DataSource;
import dk.dtu.model.dto.RecipeDTO;
import dk.dtu.model.dto.RecipeListDTO;
import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;
import dk.dtu.model.exceptions.dal.NotFoundException;
import dk.dtu.model.interfaces.RecipeDAO;

public class MySQLRecipeDAO implements RecipeDAO {

	@Override
	public void createRecipe(String recipeName) throws ConnectivityException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL create_recipe(?);");
			stm.setString(1, recipeName);
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
	public RecipeDTO readRecipe(int recipeId) throws ConnectivityException, NotFoundException {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL read_recipe(?);");
			stm.setInt(1, recipeId);
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("Recipe with id " + recipeId + " does not exist");
			}
			return new RecipeDTO(
					rs.getInt("recipe_id"),
					rs.getString("recipe_name"));
		} catch (SQLException e) {
			throw new ConnectivityException(e);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
			try { if (stm != null) stm.close(); } catch (SQLException e) {};
			try { if (conn != null) conn.close(); } catch (SQLException e) {};
		}
	}
	
	@Override
	public void updateRecipe(RecipeDTO recipe) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL update_recipe(?,?);");
			stm.setInt(1, recipe.getRecipeId());
			stm.setString(2, recipe.getRecipeName());
			if(stm.executeUpdate() == 0) {
				throw new NotFoundException("Recipe with id " + recipe.getRecipeId() + " does not exist");
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
	public void deleteRecipe(int recipeId) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL delete_recipe(?);");
			stm.setInt(1, recipeId);
			if(stm.executeUpdate() == 0) {
				throw new NotFoundException("Recipe with id " + recipeId + " does not exist");
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
	public List<RecipeDTO> getRecipeList() throws ConnectivityException, NotFoundException {
		List<RecipeDTO> list = new ArrayList<RecipeDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("SELECT * FROM recipe;");
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("No recipes found");
			}
			do {
				list.add(new RecipeDTO(
						rs.getInt("recipe_id"),
						rs.getString("recipe_name")));
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
	public List<RecipeListDTO> getRecipeDetailsByID(int recipeID) throws ConnectivityException, NotFoundException {
		List<RecipeListDTO> list = new ArrayList<RecipeListDTO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try	{
			conn = DataSource.getInstance().getConnection();
			stm = conn.prepareStatement("CALL get_recipe_details_by_id(?);");
			stm.setInt(1, recipeID);
			rs = stm.executeQuery();
			if (!rs.first()) {
				throw new NotFoundException("Recipe with id " + recipeID + " does not exist");
			}
			do {
				list.add(new RecipeListDTO(
						rs.getInt("recipe_id"),
						rs.getString("recipe_name"),
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
