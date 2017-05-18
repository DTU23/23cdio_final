package test.java.dk.dtu.model.data.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.dk.dtu.model.connector.Connector;
import main.java.dk.dtu.model.dao.MySQLProduceDAO;
import main.java.dk.dtu.model.dto.ProduceDTO;
import main.java.dk.dtu.model.dto.ProduceOverviewDTO;
import main.java.dk.dtu.model.interfaces.DALException;

/**
 * This JUnit class tests the MySQLProduceDAO class.
 * @author Frederik Værnegaard
 * 
 */

public class MySQLProduceDAOTest {

	MySQLProduceDAO produce;

	@Before
	public void setUp() throws Exception {
		new Connector();
		produce = new MySQLProduceDAO();
	}

	@After
	public void tearDown() throws Exception{
		Connector.resetData();
		produce = null;
	}

	/**
	 * Positive test. Get any produce by ID.
	 */
	@Test
	public void testGetProduceByID() throws Exception {
		ProduceDTO actual = null;
		ProduceDTO expected = new ProduceDTO(2, "tomat", "Knoor");
		// Get produce by ID from DB
		actual = produce.getProduce(2);
		assertThat(actual.toString(), is(expected.toString()));
	}
	
	/**
	 * Negative test. Try to get produce with non-existing ID.
	 */
	@Test
	public void testGetProduceByIDThatDoesntExist() throws Exception {
		ProduceDTO actual = null;
		String error = null;
		
		// We expect this won't work and therefore throw a DALException
		try {
		// Get produce by non-existing ID from DB
		actual = produce.getProduce(10);
		} catch (DALException e) {
			// e.printStackTrace();
			error = e.getMessage();
		}
		
		assertThat(actual, nullValue());
		assertThat(error, notNullValue());
	}

	/**
	 * Positive test. Get all produces from the produce list.
	 */
	@Test
	public void testGetProduceList() throws Exception {
		List<ProduceDTO> produceList = null;
		// Last row in our table, produce
		ProduceDTO produceDTO = new ProduceDTO(7, "champignon", "Igloo Frostvarer");
		// Get whole table, produce, from DB
		produceList = produce.getProduceList();

		// Print out the produce list to console
		System.out.println("testGetProduceList(): ");
		for(int i = 0; i < produceList.size(); i++)
			System.out.println(produceList.get(i));
		System.out.println();
		
		assertThat(produceList, notNullValue());
		assertThat(produceList.get(6).toString(), is(produceDTO.toString()));
	}

	/**
	 * Positive test. Create a produce.
	 */
	@Test
	public void testCreateProduce() throws Exception {
		// The ID doesn't affect anything when creating, because it will be auto generated
		ProduceDTO expected = new ProduceDTO(8, "smør", "Kærgården"); 
		ProduceDTO actual = null;

		produce.createProduce(expected);
		actual = produce.getProduce(8);

		assertThat(actual.toString(), is(expected.toString()));
	}

	/**
	 * Positive test. Update produce
	 */
	@Test
	public void testUpdateProduce() throws Exception {
		ProduceDTO expected = new ProduceDTO(4, "tomat", "Kims");
		ProduceDTO actual = null;

		produce.updateProduce(expected);
		actual = produce.getProduce(4);
		
		assertThat(actual.toString(), is(expected.toString()));
	}

	/**
	 * Positive test. Get the view produce_overview
	 */
	@Test
	public void testGetProduceOverview() throws Exception {
		List<ProduceOverviewDTO> produceOverview = null;
		// First row in our view, produce_overview
		ProduceOverviewDTO produceOverviewDTO = new ProduceOverviewDTO(7, "champignon", 100);

		produceOverview = produce.getProduceOverview();

		// Print out the produce overview to console
		System.out.println("testGetProduceOverview(): ");
		for(int i = 0; i < produceOverview.size(); i++)
			System.out.println(produceOverview.get(i));
		System.out.println();
		
		assertThat(produceOverview, notNullValue());
		assertThat(produceOverview.get(0).toString(), is(produceOverviewDTO.toString()));
	}

}