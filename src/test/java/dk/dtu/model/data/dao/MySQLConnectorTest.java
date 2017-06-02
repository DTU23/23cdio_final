package dk.dtu.model.data.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.dtu.model.connector.Connector;

public class MySQLConnectorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConnector() {
		String errorMsg = null;
		try { new Connector(); } 
		catch (InstantiationException e) { errorMsg = e.getMessage(); }
		catch (IllegalAccessException e) { errorMsg = e.getMessage(); }
		catch (ClassNotFoundException e) { errorMsg = e.getMessage(); }
		catch (SQLException e) { errorMsg = e.getMessage(); }
		assertThat(errorMsg, is(equalTo(null)));
	}
}
