package dk.dtu.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ValidationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsPositiveInteger() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsOnlyLetters() {
		fail("Not yet implemented");
	}

	@Test
	public void testAuthenticateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsValidID() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsValidUserName() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsValidInitials() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsValidCpr() throws ValidationException {
		Validation.isValidCpr("1208871443");
	}

	@Test
	public void testIsInvalidCprDay() throws ValidationException {
		Validation.isValidCpr("3208871443");
	}
	
	@Test(expected = ValidationException.class)
	public void testIsInvalidCprMonth() throws ValidationException {
		Validation.isValidCpr("1213871443");
	}
	
	@Test(expected = ValidationException.class)
	public void testIsInvalidCprFutureDate() throws ValidationException {
		Validation.isValidCpr("2902401443");
	}
	
	@Test(expected = ValidationException.class)
	public void testIsInvalidCpr() throws ValidationException {
		Validation.isValidCpr("1208871443");
	}
	
	@Test
	public void testIsValidPassword() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsValidRole() {
		fail("Not yet implemented");
	}

}
