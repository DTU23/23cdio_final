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
	
	@Test(expected = ValidationException.class)
	public void testIsNotPositiveInteger() throws ValidationException {
		Validation.isPositiveInteger("-7");
	}

	@Test
	public void testIsOnlyLetters() throws ValidationException {
		Validation.isOnlyLetters("egrtjtyERHRT");
	}

	@Test(expected = ValidationException.class)
	public void testIsNotOnlyLetters() throws ValidationException {
		Validation.isOnlyLetters("egrtj345RHRT");
	}

	@Test
	public void testIsValidID() throws ValidationException {
		Validation.isValidID("6755");
	}

	@Test(expected = ValidationException.class)
	public void testIsInvalidID() throws ValidationException {
		Validation.isValidID("-1");
	}

	@Test
	public void testIsValidUserName() throws ValidationException {
		Validation.isValidUserName("Peter Madsen");
	}

	@Test(expected = ValidationException.class)
	public void testIsInvalidUserName() throws ValidationException {
		Validation.isValidUserName("P3t3r M4ds3n");
	}

	@Test
	public void testIsValidInitials() throws ValidationException {
		Validation.isValidInitials("PM");
	}

	@Test(expected = ValidationException.class)
	public void testIsInvalidInitials() throws ValidationException {
		Validation.isValidInitials("C3PO");
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
		Validation.isValidPassword("dfgE*%34RET");
	}

	@Test(expected = ValidationException.class)
	public void testIsInvalidPassword() throws ValidationException {
		Validation.isValidPassword("123");
	}

	@Test
	public void testIsValidRole() throws ValidationException {
		Validation.isValidRole("foreman");
	}

	@Test(expected = ValidationException.class)
	public void testIsInvalidRole() throws ValidationException {
		Validation.isValidRole("hacker");
	}

}
