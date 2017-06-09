package dk.dtu.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.model.connector.Connector;
import dk.dtu.model.dto.RecipeCompDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.RecipeCompDAO;

public class MySQLRecipeCompDAO implements RecipeCompDAO {

	@Override
	public void createRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
		if(Connector.getInstance().doUpdate("CALL create_recipe_component('" 
				+ recipeComponent.getRecipeId() + "', '" 
				+ recipeComponent.getProduceId() + "', '" 
				+ recipeComponent.getNomNetto() + "', '" 
				+ recipeComponent.getTolerance() + "');") == 0)
		{
			throw new DALException("No rows affected!");
		}
	}

	@Override
	public RecipeCompDTO readRecipeComp(int recipeId, int produceId) throws DALException {
		ResultSet rs = Connector.getInstance().doQuery("CALL read_recipe_component('"+recipeId+"', '"+produceId+"');");
		try
		{
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
			Connector.getInstance().closeResources();
		}
	}

	@Override
	public void updateRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
		if(Connector.getInstance().doUpdate("CALL update_recipe_component('" 
				+ recipeComponent.getRecipeId() + "','"
				+ recipeComponent.getProduceId() + "','" 
				+ recipeComponent.getNomNetto() + "','" 
				+ recipeComponent.getTolerance() + "');" ) == 0)
		{
			throw new DALException("No rows affected");
		}
	}

	@Override
	public void deleteRecipeComp(int recipeId, int produceId) throws DALException {
		if(Connector.getInstance().doUpdate("CALL delete_recipe_component('"+recipeId+"', '"+produceId+"');") == 0)
		{
			throw new DALException("No rows affected");
		}
	}

	@Override
	public List<RecipeCompDTO> getRecipeCompList() throws DALException {
		List<RecipeCompDTO> list = new ArrayList<RecipeCompDTO>();
		ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM recipecomponent;");

		try {
			while (rs.next())
			{
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
			Connector.getInstance().closeResources();
		}
	}

	@Override
	public List<RecipeCompDTO> getRecipeCompByRecipeId(int recipeId) throws DALException {
		List<RecipeCompDTO> list = new ArrayList<RecipeCompDTO>();
		ResultSet rs = Connector.getInstance().doQuery("CALL get_recipe_comp_by_recipe_id('"+recipeId+"');");
		try {
			while (rs.next())
			{
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
			Connector.getInstance().closeResources();
		}
	}

}
