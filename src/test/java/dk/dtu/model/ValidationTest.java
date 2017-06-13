package dk.dtu.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.dtu.model.exceptions.validation.InvalidCprException;
import dk.dtu.model.exceptions.validation.InvalidIDException;
import dk.dtu.model.exceptions.validation.InvalidInitialsException;
import dk.dtu.model.exceptions.validation.InvalidNameException;
import dk.dtu.model.exceptions.validation.InvalidPasswordException;
import dk.dtu.model.exceptions.validation.InvalidRoleException;
import dk.dtu.model.exceptions.validation.NotLettersException;
import dk.dtu.model.exceptions.validation.PositiveIntegerValidationException;

public class ValidationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsPositiveInteger() throws PositiveIntegerValidationException {
		Validation.isPositiveInteger("7");
	}
	
	@Test(expected = PositiveIntegerValidationException.class)
	public void testIsNotPositiveInteger() throws PositiveIntegerValidationException {
		Validation.isPositiveInteger("-7");
	}

	@Test
	public void testIsOnlyLetters() throws NotLettersException {
		Validation.isOnlyLetters("egrtjtyERHRT");
	}

	@Test(expected = NotLettersException.class)
	public void testIsNotOnlyLetters() throws NotLettersException {
		Validation.isOnlyLetters("egrtj345RHRT");
	}

	@Test
	public void testIsValidID() throws InvalidIDException {
		Validation.isValidID("6755");
	}

	@Test(expected = InvalidIDException.class)
	public void testIsInvalidID() throws InvalidIDException {
		Validation.isValidID("-1");
	}

	@Test
	public void testIsValidUserName() throws InvalidNameException {
		Validation.isValidName("Peter Madsen");
	}

	@Test(expected = InvalidNameException.class)
	public void testIsInvalidUserName() throws InvalidNameException {
		Validation.isValidName("P3t3r M4ds3n");
	}

	@Test
	public void testIsValidInitials() throws InvalidInitialsException {
		Validation.isValidInitials("PM");
	}

	@Test(expected = InvalidInitialsException.class)
	public void testIsInvalidInitials() throws InvalidInitialsException {
		Validation.isValidInitials("C3PO");
	}

	@Test
	public void testIsValidCpr() throws InvalidCprException {
		Validation.isValidCpr("1208871443");
	}

	@Test(expected = InvalidCprException.class)
	public void testIsInvalidCprDay() throws InvalidCprException {
		Validation.isValidCpr("3208871443");
	}

	@Test(expected = InvalidCprException.class)
	public void testIsInvalidCprMonth() throws InvalidCprException {
		Validation.isValidCpr("1213871443");
	}

	@Test(expected = InvalidCprException.class)
	public void testIsInvalidCprFutureDate() throws InvalidCprException {
		Validation.isValidCpr("2902401443");
	}

	@Test(expected = InvalidCprException.class)
	public void testIsInvalidCpr() throws InvalidCprException {
		Validation.isValidCpr("1208871442");
	}

	@Test
	public void testIsValidPassword() throws InvalidPasswordException {
		Validation.isValidPassword("dfgE*%34RET");
	}

	@Test(expected = InvalidPasswordException.class)
	public void testIsInvalidPassword() throws InvalidPasswordException {
		Validation.isValidPassword("123");
	}

	@Test
	public void testIsValidRole() throws InvalidRoleException {
		Validation.isValidRole("foreman");
	}

	@Test(expected = InvalidRoleException.class)
	public void testIsInvalidRole() throws InvalidRoleException {
		Validation.isValidRole("hacker");
	}

	@Test
	public void testIsValidSupplierName() throws InvalidNameException {
		Validation.isValidSupplierName("Ost og skinke A/S");
	}

	@Test(expected = InvalidNameException.class)
	public void testIsInvalidSupplierName() throws InvalidNameException {
		Validation.isValidSupplierName("0st 0g sk1nk3 A/S");
	}
}
