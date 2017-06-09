package dk.dtu.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dk.dtu.control.api.Role;

public class Validation {

	/**
	 * Method to validate if an input is a positive integer.
	 * @param input
	 * @throws ValidationException
	 */
	public static void isPositiveInteger(String input) throws ValidationException {
		try {
			long i = Long.parseLong(input);
			if (i <= 0) {
				throw new ValidationException("Input is not a positive integer");
			}
		} catch (NumberFormatException e) {
			throw new ValidationException("Input is not an integer");
		}
	}

	/**
	 * Method to validate if an input is a positive integer.
	 * @param input
	 * @throws ValidationException
	 */
	public static void isPositiveInteger(int input) throws ValidationException {
		if (input <= 0) {
			throw new ValidationException("Input is not a positive integer");
		}
	}

	/**
	 * Method to validate if an input is a positive number.
	 * @param input
	 * @throws ValidationException
	 */
	public static void isPositiveDouble(String input) throws ValidationException {
		try {
			double i = Double.parseDouble(input);
			if (i <= 0) {
				throw new ValidationException("Input is not a positive number");
			}
		} catch (NumberFormatException e) {
			throw new ValidationException("Input is not a double");
		}
	}

	/**
	 * Method to validate if an input is a positive number.
	 * @param input
	 * @throws ValidationException
	 */
	public static void isPositiveDouble(double input) throws ValidationException {
		if (input <= 0) {
			throw new ValidationException("Input is not a positive number");
		}
	}

	/**
	 * Method to validate if an input contains only letters.
	 * @param input
	 * @throws ValidationException
	 */
	public static void isOnlyLetters(String input) throws ValidationException {
		if(!input.matches("[a-zA-Z]+"))
			throw new ValidationException("Input contains non-letter characters.");
	}

	/**
	 * Method to validate if an ID is a valid choice.
	 * @param ID
	 * @throws ValidationException
	 */
	public static void isValidID(String ID) throws ValidationException {
		isPositiveInteger(ID);
		int i = Integer.parseInt(ID);
		if(i > 99999999) {
			throw new ValidationException("ID out of bounds.");
		}
	}
	
	/**
	 * Method to validate if an ID is a valid choice.
	 * @param ID
	 * @throws ValidationException
	 */
	public static void isValidID(int ID) throws ValidationException {
		isPositiveInteger(ID);
		if(ID > 99999999) {
			throw new ValidationException("ID out of bounds.");
		}
	}

	/**
	 * Method to validate if a user name is a valid choice.
	 * @param userName
	 * @throws ValidationException
	 */
	public static void isValidUserName(String userName) throws ValidationException {
		if(!userName.matches("[a-zA-Z ]+"))
			throw new ValidationException("Input contains non-letter characters.");
		if(userName.length() < 2) {
			throw new ValidationException("User name too short.");
		} else if (userName.length() > 20) {
			throw new ValidationException("User name too long.");
		}
	}

	/**
	 * Method to validate if a set of initials is a valid choice.
	 * @param initials
	 * @throws ValidationException
	 */
	public static void isValidInitials(String initials) throws ValidationException {
		isOnlyLetters(initials);
		if(initials.length() < 2) {
			throw new ValidationException("Too few initials.");
		} else if (initials.length() > 4) {
			throw new ValidationException("Too many initials.");
		}
	}

	/**
	 * Method to validate if a cpr is a valid.
	 * @param cpr
	 * @throws ValidationException
	 */
	public static void isValidCpr(String cpr) throws ValidationException {
		isPositiveInteger(cpr);
		// checks the length
		if(cpr.length() != 10) {
			throw new ValidationException("Not ten digits.");
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
			} catch (Exception e) {
				throw new ValidationException("Not valid date.");
			}
			// checks if date is in the future
			Calendar currentDate = new GregorianCalendar();
			if (cprDate.compareTo(currentDate) > 0) {
				throw new ValidationException("Invalid cpr number.");
			}
			// check if full cpr is valid
			int CprProductSum = 0;
			int[] multiplyBy = {4, 3, 2, 7, 6, 5, 4, 3, 2, 1};
			for (int j = 0; j < cpr.length(); j++) {
				CprProductSum += Integer.parseInt(cpr.substring(j, j+1)) * multiplyBy[j];
			}
			if (CprProductSum % 11 != 0) {
				if(year == 2000) {
					throw new ValidationException("Invalid cpr number.");
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
	public static void isValidPassword(String password) throws ValidationException {
		Pattern p = Pattern.compile("^(?=.*[A-Z].*[A-Z])(?=.*[0-9])(?=.*[a-z].*[a-z]).{7,}$");
		Matcher m = p.matcher(password);
		if(!m.matches()) {
			throw new ValidationException("Not a valid password");
		}
	}

	/**
	 * Method to validate if a role is a valid choice.
	 * @param role
	 * @throws ValidationException
	 */
	public static void isValidRole(String role) throws ValidationException {
		ArrayList<String> validRoles = new ArrayList<>();
		for (Role validrole : Role.values()) {
			validRoles.add(validrole.toString().toLowerCase());
		}
		// if its a valid role
		if (!validRoles.contains(role.toLowerCase())) {
			throw new ValidationException("Not a valid role");
		}
	}
}
