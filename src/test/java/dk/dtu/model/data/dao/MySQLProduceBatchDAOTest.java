package dk.dtu.model.data.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.dtu.model.connector.DataSource;
import dk.dtu.model.dao.MySQLProduceBatchDAO;
import dk.dtu.model.dao.MySQLProduceDAO;
import dk.dtu.model.dto.ProduceBatchDTO;
import dk.dtu.model.dto.ProduceDTO;
import dk.dtu.model.exceptions.DALException;

/**
 * This JUnit class tests the MySQLProduceBatchDAO class.
 * @author Frederik Værnegaard
 * 
 */

public class MySQLProduceBatchDAOTest {

	MySQLProduceBatchDAO produceBatch;

	@Before
	public void setUp() throws Exception {
		DataSource.getInstance().resetData();
		produceBatch = new MySQLProduceBatchDAO();
	}

	@After
	public void tearDown() throws Exception{
		DataSource.getInstance().resetData();
		produceBatch = null;
	}

	/**
	 * Positive test. Get any produce batch by ID.
	 */
	@Test
	public void testGetProduceBatchByID() throws Exception {
		ProduceBatchDTO expected = new ProduceBatchDTO(1, 1, null, null, 1000);
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
		ProduceBatchDTO produceBatchDTO = new ProduceBatchDTO(2, 0, "tomat", "Knoor", 300);

		pbList = produceBatch.getProduceBatchList();

		System.out.println("testGetProduceBatchList(): ");
		// Print out the produce batch list to console
		for(int i = 0; i < pbList.size(); i++)
			System.out.println(pbList.get(i));
		System.out.println();

		assertThat(pbList, notNullValue());
		assertThat(pbList.get(5).toString(), is(produceBatchDTO.toString()));
	}

	/**
	 * Positive test. Create a produce batch.
	 */
	@Test
	public void testCreateProduceBatch() throws Exception {
		// Get an already created produce
		MySQLProduceDAO produce = new MySQLProduceDAO();
		ProduceDTO produceDTO = produce.readProduce(4);
		int ListLengthPre = produceBatch.getProduceBatchList().size();
		// Define amount of produce
		ProduceBatchDTO produceBatchDTO = new ProduceBatchDTO(8, produceDTO.getProduceId(), null, null, 500);

		produceBatch.createProduceBatch(produceBatchDTO);
		int ListLengthPost = produceBatch.getProduceBatchList().size();

		assertThat(ListLengthPost, is(not(ListLengthPre)));
	}

	/**
	 * Positive test. Update a produce batch.
	 */
	@Test
	public void testUpdateProduceBatch() throws Exception {
		ProduceBatchDTO prePbDTO = null;
		ProduceBatchDTO expectedPbDTO = new ProduceBatchDTO(3, 3, null, null, 600);
		ProduceBatchDTO actual = null;

		prePbDTO = produceBatch.readProduceBatch(3);
		produceBatch.updateProduceBatch(expectedPbDTO);
		actual = produceBatch.readProduceBatch(3);

		assertThat(actual.toString(), is(not(prePbDTO.toString())));
		assertThat(actual.toString(), is(expectedPbDTO.toString()));
	}

	/**
	 * Negative test. Update a produce batch that doesn't exist.
	 */
	@Test
	public void testUpdateProduceBatchThatDoesntExist() throws Exception{
		ProduceBatchDTO pbDTO = new ProduceBatchDTO(12, 0, "ketchup", "Heinz", 400);
		ProduceBatchDTO actual = null;
		String error = null;

		// We expect this won't work, and therefore we try-catch a DALException
		try {
			produceBatch.updateProduceBatch(pbDTO);
			actual = produceBatch.readProduceBatch(12);
		} catch (DALException e) {
			// e.printStackTrace();
			error = e.getMessage();
		}

		assertThat(error, notNullValue());
		assertThat(actual, nullValue());
	}

}