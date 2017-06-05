package dk.dtu.model;

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
	public void testIsPositiveInteger() throws ValidationException {
		Validation.isPositiveInteger("7");
	}

	@Test
	public void testIsOnlyLetters() throws ValidationException {
		Validation.isOnlyLetters("egrtjtyERHRT");
	}

	@Test
	public void testAuthenticateUser() throws ValidationException {
		Validation.authenticateUser("admin", "root");
	}

	@Test
	public void testIsValidID() throws ValidationException {
		Validation.isValidID("6755");
	}

	@Test
	public void testIsValidUserName() throws ValidationException {
		Validation.isValidUserName("Peter Madsen");
	}

	@Test
	public void testIsValidInitials() throws ValidationException {
		Validation.isValidInitials("PM");
	}

	@Test
	public void testIsValidCpr() throws ValidationException {
		Validation.isValidCpr("1208871443");
	}

	@Test(expected = ValidationException.class)
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
		Validation.isValidCpr("1208871442");
	}

	@Test
	public void testIsValidPassword() throws ValidationException {
		Validation.isValidPassword("dfgERET");
	}

	@Test
	public void testIsValidRole() throws ValidationException {
		Validation.isValidRole("foreman");
	}

}
