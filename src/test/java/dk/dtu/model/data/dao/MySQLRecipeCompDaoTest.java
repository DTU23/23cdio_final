package test.java.dk.dtu.model.data.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.dk.dtu.model.connector.Connector;
import main.java.dk.dtu.model.dao.MySQLRecipeCompDAO;
import main.java.dk.dtu.model.dto.RecipeCompDTO;
import main.java.dk.dtu.model.interfaces.DALException;

public class MySQLRecipeCompDaoTest {

	MySQLRecipeCompDAO recipeComp;

	@Before
	public void setUp() throws Exception {
		new Connector();
		Connector.resetData();
		recipeComp = new MySQLRecipeCompDAO();
	}

	@After
	public void tearDown() throws Exception {
		Connector.resetData();
		recipeComp = null;
	}

/**
 * positive get recipe comp test
 */
	
	@Test
	public void positiveGetRecipeComp() {
		RecipeCompDTO actual = null;
		RecipeCompDTO expected = new RecipeCompDTO(1, 1, 10, 0.1);
		try {
			actual = recipeComp.getRecipeComp(1, 1);
		} catch (DALException e) { System.out.println(e.getMessage()); }
		assertThat(expected.toString(), is(equalTo(actual.toString())));
	}
	
	/**
	 * negative get recipe comp test
	 */
	
	@Test
	public void getRecipeCompByIDThatDoesntExist() {
		String errorMsg = null;
		try {
			recipeComp.getRecipeComp(4,1);
		} catch (DALException e) { errorMsg = e.getMessage(); }
		assertThat(errorMsg, is(equalTo("Recipecomponent with recipeid 4 and produceid 1 does not exist")));
	}

	/**
	 * positive get recipe comp list with ID 
	 */
	
	@Test
	public void positiveGetListRecipeCompWithID() {
		List<RecipeCompDTO> actual = null;
		try {
			actual = recipeComp.getRecipeCompList(1);
			
		} catch (DALException e) { System.out.println(e.getMessage()); }
		assertThat(actual.get(1).getRecipeId(), not(nullValue()));
		assertThat(actual.get(1).getProduceId(), not(nullValue()));
		assertThat(actual.get(1).getNomNetto(), not(nullValue()));
		assertThat(actual.get(1).getTolerance(), not(nullValue()));
	}

	/**
	 * positive get list recipe comp without ID test 
	 */
	
	@Test
	public void positiveGetListRecipeComp() {
		List<RecipeCompDTO> actual = null;
		try {
			actual = recipeComp.getRecipeCompList();
		} catch (DALException e) { System.out.println(e.getMessage()); }
		assertThat(actual.get(0).getRecipeId(), notNullValue());
		assertThat(actual.get(0).getProduceId(), notNullValue());
		assertThat(actual.get(0).getNomNetto(), notNullValue());
		assertThat(actual.get(0).getTolerance(), notNullValue());
	}

	/**
	 * Positive create recipe comp test
	 */
	
	@Test
	public void positiveCreateRecipeComp() {
		RecipeCompDTO expected = new RecipeCompDTO(3, 2, 1.5, 0.1);
		RecipeCompDTO actual = null;
		try {
			recipeComp.createRecipeComp(expected);
			actual = recipeComp.getRecipeComp(3, 2);
		} catch (DALException e) { System.out.println(e.getMessage()); }
		assertThat(expected.toString(), is(equalTo(actual.toString())));
	}
	
	/**
	 * get recipe comp with invalid input
	 */
	
	@Test (expected = DALException.class)
	public void getRecipeCompWithInvalidID() throws Exception{
		recipeComp.getRecipeComp(0, 1);
	}
	
}
