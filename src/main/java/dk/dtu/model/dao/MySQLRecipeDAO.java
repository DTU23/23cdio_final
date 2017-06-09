package dk.dtu.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.Connector;
import dk.dtu.model.dto.RecipeDTO;
import dk.dtu.model.dto.RecipeListDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.RecipeDAO;

public class MySQLRecipeDAO implements RecipeDAO {

	@Override
	public void createRecipe(String recipeName) throws DALException {
		if(Connector.getInstance().doUpdate("CALL create_recipe('"+recipeName+"');") == 0)
		{
			throw new DALException("No rows affected!");
		}
	}
	
	@Override
	public RecipeDTO readRecipe(int recipeId) throws DALException {
		ResultSet rs = Connector.getInstance().doQuery("CALL read_recipe('"+recipeId+"');");
		try
		{
			if (!rs.first()) {
				throw new DALException("Recipe with id " + recipeId + " does not exist");
			}
			return new RecipeDTO(
					rs.getInt("recipe_id"),
					rs.getString("recipe_name"));
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}
	
	@Override
	public void updateRecipe(RecipeDTO recipe) throws DALException {
		if(Connector.getInstance().doUpdate("CALL update_recipe('" 
				+ recipe.getRecipeId() + "','"
				+ recipe.getRecipeName() + "');" ) == 0)
		{
			throw new DALException("No rows affected");
		}
	}
	
	@Override
	public void deleteRecipe(int recipeId) throws DALException {
		if(Connector.getInstance().doUpdate("CALL delete_recipe('" + recipeId + "');") == 0)
		{
			throw new DALException("No rows affected!");
		}
	}

	@Override
	public List<RecipeDTO> getRecipeList() throws DALException {
		List<RecipeDTO> list = new ArrayList<RecipeDTO>();
		ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM recipe;");
		try 
		{
			while (rs.next())
			{
				list.add(new RecipeDTO(
						rs.getInt("recipe_id"),
						rs.getString("recipe_name")));
			}
			return list;
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}
	
	@Override
	public List<RecipeListDTO> getRecipeDetailsByID(int recipeID) throws DALException {
		List<RecipeListDTO> list = new ArrayList<RecipeListDTO>();
		ResultSet rs = Connector.getInstance().doQuery("CALL get_recipe_details_by_id("+recipeID+");");
		try 
		{
			while(rs.next()) 
			{
				list.add(new RecipeListDTO(rs.getInt("recipe_id"),
						rs.getString("recipe_name"),
						rs.getInt("produce_id"),
						rs.getString("produce_name"),
						rs.getDouble("nom_netto"),
						rs.getDouble("tolerance")));
			}
			return list;
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
			Connector.getInstance().closeResources();
		}
	}
	
	

}
