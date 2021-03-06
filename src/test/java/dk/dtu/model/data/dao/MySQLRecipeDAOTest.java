package dk.dtu.model.data.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.dtu.model.connector.DataSource;
import dk.dtu.model.dao.MySQLRecipeDAO;
import dk.dtu.model.dto.RecipeDTO;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;

public class MySQLRecipeDAOTest {

	MySQLRecipeDAO recipe;

	@Before
	public void setUp() throws Exception {
		DataSource.getInstance().resetData();
		recipe = new MySQLRecipeDAO();
	}

	@After
	public void tearDown() throws Exception {
		DataSource.getInstance().resetData();
		recipe = null;
	}

	/**
	 * Positive get Recipe test
	 */

	@Test
	public void positiveGetRecipe() {
		RecipeDTO actual = null;
		RecipeDTO expected = new RecipeDTO(3, "capricciosa");
		try {
			actual = recipe.readRecipe(3);	
		} catch (DALException e) {	System.out.println(e.getMessage());  }
		assertThat(expected.toString(), is(equalTo(actual.toString())));
	}

	/**
	 * Negative get Recipe test
	 */

	@Test
	public void getRecipeByIDThatDoesntExist() {
		String errorMsg = null;
		try {
			recipe.readRecipe(5);
		} catch (DALException e) {	errorMsg = e.getMessage();	}
		assertThat(errorMsg, is(equalTo("Recipe with id 5 does not exist")));

	}

	/**
	 * Positive get recipe list test
	 */

	@Test
	public void positiveGetListRecipe() {
		List<RecipeDTO> actual = null;
		try {
			actual = recipe.getRecipeList();
		} catch (DALException e) {	System.out.println(e.getMessage());}
		assertThat(actual.get(0).getRecipeId(), notNullValue());
		assertThat(actual.get(0).getRecipeName(), notNullValue());

	}

	/**
	 * Positive create recipe test
	 */
	@Test
	public void positiveCreateRecipe() {
		RecipeDTO expected = new RecipeDTO(4, "salami");
		RecipeDTO actual = null;
		try {
			recipe.createRecipe(expected);
			actual = recipe.readRecipe(4);
		} catch (DALException e) {	System.out.println(e.getMessage());}
		assertThat(expected.toString(), is(equalTo(actual.toString())));
	}

	/**
	 * negative test for create recipe. 
	 * Auto-generates an ID so cant create on existing. 
	 * @throws IntegrityConstraintViolationException 
	 * @throws ConnectivityException 
	 */
	@Test(expected = IntegrityConstraintViolationException.class)
	public void createRecipeOnExistingID() throws ConnectivityException, IntegrityConstraintViolationException {
		recipe.createRecipe(new RecipeDTO(3, "parmaskinke"));
	}

	/**
	 * get recipe with invalid input
	 */

	@Test
	public void getRecipeWithInvalidID() {
		String errorMsg = null;
		try {
			recipe.readRecipe(0);
		} catch (DALException e) { errorMsg = e.getMessage(); }
		assertThat(errorMsg, is(equalTo("Recipe with id 0 does not exist")));
	}

}