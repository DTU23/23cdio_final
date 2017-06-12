package dk.dtu.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dk.dtu.control.api.Role;
import dk.dtu.model.exceptions.ValidationException;
import dk.dtu.model.exceptions.validation.PositiveDoubleValidationException;
import dk.dtu.model.exceptions.validation.PositiveIntegerValidationException;
import dk.dtu.model.exceptions.validation.InvalidCprException;
import dk.dtu.model.exceptions.validation.InvalidIDException;
import dk.dtu.model.exceptions.validation.InvalidInitialsException;
import dk.dtu.model.exceptions.validation.InvalidNameException;
import dk.dtu.model.exceptions.validation.InvalidPasswordException;
import dk.dtu.model.exceptions.validation.InvalidRoleException;
import dk.dtu.model.exceptions.validation.InvalidStatusException;
import dk.dtu.model.exceptions.validation.NotLettersException;

public class Validation {

	/**
	 * Method to validate if an input is a positive integer.
	 * @param input
	 * @throws ValidationException
	 */
	public static void isPositiveInteger(String input) throws PositiveIntegerValidationException {
		try {
			long i = Long.parseLong(input);
			if (i <= 0) {
				throw new PositiveIntegerValidationException("Input is not a positive integer");
			}
		} catch (NumberFormatException e) {
			throw new PositiveIntegerValidationException("Input is not an integer");
		}
	}

	/**
	 * Method to validate if an input is a positive integer.
	 * @param input
	 * @throws ValidationException
	 */
	public static void isPositiveInteger(int input) throws PositiveIntegerValidationException {
		if (input <= 0) {
			throw new PositiveIntegerValidationException("Input is not a positive integer");
		}
	}

	/**
	 * Method to validate if an input is a positive number.
	 * @param input
	 * @throws ValidationException
	 */
	public static void isPositiveDouble(String input) throws PositiveDoubleValidationException {
		try {
			double i = Double.parseDouble(input);
			if (i <= 0) {
				throw new PositiveDoubleValidationException("Input is not a positive number");
			}
		} catch (NumberFormatException e) {
			throw new PositiveDoubleValidationException("Input is not a double");
		}
	}

	/**
	 * Method to validate if an input is a positive number.
	 * @param input
	 * @throws ValidationException
	 */
	public static void isPositiveDouble(double input) throws PositiveDoubleValidationException {
		if (input <= 0) {
			throw new PositiveDoubleValidationException("Input is not a positive number");
		}
	}

	/**
	 * Method to validate if an input contains only letters.
	 * @param input
	 * @throws ValidationException
	 */
	public static void isOnlyLetters(String input) throws NotLettersException {
		if(!input.matches("[a-zA-Z]+"))
			throw new NotLettersException("Input contains non-letter characters");
	}

	/**
	 * Method to validate if an ID is a valid choice.
	 * @param ID
	 * @throws ValidationException
	 */
	public static void isValidID(String ID) throws InvalidIDException {
		try {
			isPositiveInteger(ID);
		} catch (PositiveIntegerValidationException e) {
			throw new InvalidIDException(e);
		}
		int i = Integer.parseInt(ID);
		if(i > 99999999) {
			throw new InvalidIDException("ID out of bounds");
		}
	}
	
	/**
	 * Method to validate if an ID is a valid choice.
	 * @param ID
	 * @throws ValidationException
	 */
	public static void isValidID(int ID) throws InvalidIDException {
		try {
			isPositiveInteger(ID);
		} catch (PositiveIntegerValidationException e) {
			throw new InvalidIDException(e);
		}
		if(ID > 99999999) {
			throw new InvalidIDException("ID out of bounds");
		}
	}

	/**
	 * Method to validate if a user name is a valid choice.
	 * @param name
	 * @throws ValidationException
	 */
	public static void isValidName(String name) throws InvalidNameException {
		if(!name.matches("[a-zA-Z ]+"))
			throw new InvalidNameException("Input contains non-letter characters");
		if(name.length() < 2) {
			throw new InvalidNameException("Name too short");
		} else if (name.length() > 20) {
			throw new InvalidNameException("Name too long");
		}
	}

	/**
	 * Method to validate if a set of initials is a valid choice.
	 * @param initials
	 * @throws ValidationException
	 */
	public static void isValidInitials(String initials) throws InvalidInitialsException {
		try {
			isOnlyLetters(initials);
		} catch (NotLettersException e) {
			throw new InvalidInitialsException(e);
		}
		if(initials.length() < 2) {
			throw new InvalidInitialsException("Too few initials");
		} else if (initials.length() > 4) {
			throw new InvalidInitialsException("Too many initials");
		}
	}

	/**
	 * Method to validate if a cpr is a valid.
	 * @param cpr
	 * @throws ValidationException
	 */
	public static void isValidCpr(String cpr) throws InvalidCprException {
		try {
			isPositiveInteger(cpr);
		} catch (PositiveIntegerValidationException e) {
			throw new InvalidCprException(e);
		}
		// checks the length
		if(cpr.length() != 10) {
			throw new InvalidCprException("Not ten digits");
		}
		// checks for valid date
		for (int i = 1900; i < 2100; i += 100) {
			int year = i + Integer.parseInt(cpr.substring(4, 6));
			int month = Integer.parseInt(cpr.substring(2, 4));
			int day = Integer.parseInt(cpr.substring(0, 2));
			Calendar cprDate = new GregorianCalendar(year, month-1, day);
			cprDate.setLenient(false);
			try {
				cprDate.getTime();
			} catch (Exception e1) {
				throw new InvalidCprException("Not a valid date");
			}
			// checks if date is in the future
			Calendar currentDate = new GregorianCalendar();
			if (cprDate.compareTo(currentDate) > 0) {
				throw new InvalidCprException("Invalid cpr number");
			}
			// check if full cpr is valid
			int CprProductSum = 0;
			int[] multiplyBy = {4, 3, 2, 7, 6, 5, 4, 3, 2, 1};
			for (int j = 0; j < cpr.length(); j++) {
				CprProductSum += Integer.parseInt(cpr.substring(j, j+1)) * multiplyBy[j];
			}
			if (CprProductSum % 11 != 0) {
				if(year == 2000) {
					throw new InvalidCprException("Invalid cpr number");
				}
			} else {
				break;
			}
		}
	}

	/**
	 * Method can validate if a chosen password is allowed or not, based on the following requirements:
	 * minimum 2 upper case characters
	 * minimum 2 lower case characters
	 * minimum 1 number
	 * @param password
	 * @throws ValidationException
	 */
	public static void isValidPassword(String password) throws InvalidPasswordException {
		Pattern p = Pattern.compile("^(?=.*[A-Z].*[A-Z])(?=.*[0-9])(?=.*[a-z].*[a-z]).{7,}$");
		Matcher m = p.matcher(password);
		if(!m.matches()) {
			throw new InvalidPasswordException("Not a valid password");
		}
	}

	/**
	 * Method to validate if a role is a valid choice.
	 * @param role
	 * @throws ValidationException
	 */
	public static void isValidRole(String role) throws InvalidRoleException {
		ArrayList<String> validRoles = new ArrayList<>();
		for (Role validrole : Role.values()) {
			validRoles.add(validrole.toString().toLowerCase());
		}
		// if its a valid role
		if (!validRoles.contains(role.toLowerCase())) {
			throw new InvalidRoleException("Not a valid role");
		}
	}

	public static void isValidStatus(int status) throws InvalidStatusException {
		try {
			isPositiveInteger(status);
		} catch (PositiveIntegerValidationException e) {
			throw new InvalidStatusException(e);
		}
		if(status > 2) {
			throw new InvalidStatusException("Not a valid status");
		}
	}
}
