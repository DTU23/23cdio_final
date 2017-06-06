package test.java.dk.dtu.model.data.dao;

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
import main.java.dk.dtu.model.dao.MySQLProduceBatchDAO;
import main.java.dk.dtu.model.dao.MySQLProduceDAO;
import main.java.dk.dtu.model.dto.ProduceBatchDTO;
import main.java.dk.dtu.model.dto.ProduceDTO;
import main.java.dk.dtu.model.interfaces.DALException;

/**
 * This JUnit class tests the MySQLProduceBatchDAO class.
 * @author Frederik Værnegaard
 * 
 */

public class MySQLProduceBatchDAOTest {

	MySQLProduceBatchDAO produceBatch;

	@Before
	public void setUp() throws Exception {
		new Connector();
		produceBatch = new MySQLProduceBatchDAO();
	}

	@After
	public void tearDown() throws Exception{
		Connector.resetData();
		produceBatch = null;
	}

	/**
	 * Positive test. Get any produce batch by ID.
	 */
	@Test
	public void testGetProduceBatchByID() throws Exception {
		ProduceBatchDTO expected = new ProduceBatchDTO(1, null, "dej", "Wawelka", 1000);
		ProduceBatchDTO actual = null;
		// Get produce batch from DB specified by ID
		actual = produceBatch.readProduceBatch(1);
		assertThat(actual.toString(), is(expected.toString()));
	}

	/**
	 * Negative test. Try to get produce batch by ID that doesn't exist.
	 */
	@Test
	public void testGetProduceBatchByIDThatDoesntExist() throws Exception{
		ProduceBatchDTO actual = null;
		String error = null;

		try {
			actual = produceBatch.readProduceBatch(17);
		} catch (DALException e) {
			// e.printStackTrace();
			error = e.getMessage();
		}

		assertThat(actual, nullValue());
		assertThat(error, notNullValue());
	}

	/**
	 * Positive test. Get the produce batch list.
	 */
	@Test
	public void testGetProduceBatchList() throws Exception{
		List<ProduceBatchDTO> pbList = null;
		// Equal to the element in second row of the view produce_batch_list
		ProduceBatchDTO produceBatchDTO = new ProduceBatchDTO(2, null, "tomat", "Knoor", 300);

		pbList = produceBatch.getProduceBatchList();

		System.out.println("testGetProduceBatchList(): ");
		// Print out the produce batch list to console
		for(int i = 0; i < pbList.size(); i++)
			System.out.println(pbList.get(i));
		System.out.println();

		assertThat(pbList, notNullValue());
		assertThat(pbList.get(1).toString(), is(produceBatchDTO.toString()));
	}

	/**
	 * Positive test. Create a produce batch.
	 */
	@Test
	public void testCreateProduceBatch() throws Exception {
		// Get an already created produce
		MySQLProduceDAO produce = new MySQLProduceDAO();
		ProduceDTO produceDTO = produce.readProduce(4);
		// Define amount of produce
		double amount = 500;

		ProduceBatchDTO actual = null;
		ProduceBatchDTO expected = new ProduceBatchDTO(8, null, "tomat", "Franz", amount);

		produceBatch.createProduceBatch(produceDTO.getProduceId(), amount);
		actual = produceBatch.readProduceBatch(8);

		assertThat(actual.toString(), is(expected.toString()));
	}

	/**
	 * Positive test. Update a produce batch.
	 */
	@Test
	public void testUpdateProduceBatch() throws Exception {
		ProduceBatchDTO pbDTO = new ProduceBatchDTO(3, null, "tomat", "Veaubais", 300);
		ProduceBatchDTO actual = null;
		double amount = 600;

		produceBatch.updateProduceBatch(pbDTO.getRbId(), amount);
		actual = produceBatch.readProduceBatch(3);

		assertThat(actual.toString(), is(not(pbDTO.toString())));
		assertThat(actual.getAmount(), is(amount));
	}

	/**
	 * Negative test. Update a produce batch that doesn't exist.
	 */
	@Test
	public void testUpdateProduceBatchThatDoesntExist() throws Exception{
		ProduceBatchDTO pbDTO = new ProduceBatchDTO(12, null, "ketchup", "Heinz", 200);
		ProduceBatchDTO actual = null;
		String error = null;
		double amount = 400;

		// We expect this won't work, and therefore we try-catch a DALException
		try {
			produceBatch.updateProduceBatch(pbDTO.getRbId(), amount);
			actual = produceBatch.readProduceBatch(12);
		} catch (DALException e) {
			// e.printStackTrace();
			error = e.getMessage();
		}

		assertThat(error, notNullValue());
		assertThat(actual, nullValue());
	}

}