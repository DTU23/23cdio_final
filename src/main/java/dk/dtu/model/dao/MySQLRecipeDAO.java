package main.java.dk.dtu.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.dk.dtu.model.connector.Connector;
import main.java.dk.dtu.model.dto.RecipeDTO;
import main.java.dk.dtu.model.interfaces.DALException;
import main.java.dk.dtu.model.interfaces.RecipeDAO;

public class MySQLRecipeDAO implements RecipeDAO {

	@Override
	public RecipeDTO getRecipe(int receptId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM recipe WHERE recipe_id = " + receptId + ";");
		try {
			if (!rs.first())
				throw new DALException("Recipe with id " + receptId + " does not exist");
			return new RecipeDTO(rs.getInt("recipe_id"), rs.getString("recipe_name"));
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public List<RecipeDTO> getRecipeList() throws DALException {
		List<RecipeDTO> list = new ArrayList<RecipeDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM recipe;");
		try {
			while (rs.next()) {
				list.add(new RecipeDTO(rs.getInt("recipe_id"), rs.getString("recipe_name")));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public void createRecipe(RecipeDTO recept) throws DALException {
		Connector.doUpdate("CALL create_recipe(" + recept.getRecipeName() + ");");
	}

}