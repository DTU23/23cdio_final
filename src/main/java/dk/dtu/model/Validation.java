package dk.dtu.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

	/**
	 * 
	 * @param input
	 * @return true if the input i a positive integer
	 */
	public static void isPositiveInteger(String input) throws ValidationException {
		try {
			long i = Long.parseLong(input);
			if (i <= 0) {
				throw new ValidationException("Input is not a positive number");
			}
		} catch (NumberFormatException e) {
			throw new ValidationException("Input is not an integer.");
		}
	}

	public static void isOnlyLetters(String input) throws ValidationException {
		if(!input.matches("[a-zA-Z]+"))
			throw new ValidationException("Input contains non-letter characters.");
	}

	/**
	 * 
	 * @param ID
	 * @param password
	 * @return
	 * @throws ValidationException
	 */
	public static void authenticateUser(String ID, String password) throws ValidationException {
		//TODO
	}

	/**
	 * Method to validate if an ID is a valid choice.
	 * @param ID
	 * @return true if the ID is valid
	 */
	public static void isValidID(String ID) throws ValidationException {
		isPositiveInteger(ID);
		int i = Integer.parseInt(ID);
		if(i > 99999999) {
			throw new ValidationException("ID out of bounds.");
		}
	}

	/**
	 * Method to validate if a user name is a valid choice.
	 * @param userName
	 * @return true if the user name is valid
	 */
	public static void isValidUserName(String userName) throws ValidationException {
		isOnlyLetters(userName);
		if(userName.length() < 2) {
			throw new ValidationException("User name too short.");
		} else if (userName.length() > 20) {
			throw new ValidationException("User name too long.");
		}
	}

	/**
	 * Method to validate if a set of initials is a valid choice.
	 * @param initials
	 * @return true if the initials are valid
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
	 * @return true if the cpr is valid
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
			if (cprDate.compareTo(currentDate) < 0) {
				throw new ValidationException("The given date is in the future.");
			}
			// check if full cpr is valid
			int CprProductSum = 0;
			int[] multiplyBy = {4, 3, 2, 7, 6, 5, 4, 3, 2, 1};
			for (int j = 0; j < cpr.length(); j++) {
				CprProductSum += Integer.parseInt(cpr.substring(j, j+1)) * multiplyBy[j];
			}
			if (CprProductSum % 11 != 0) {
				throw new ValidationException("Invalid cpr number.");
			}
		}
	}

	/**
	 * Method can validate if a chosen password is allowed or not, based on the following requirements:
	 * minimum 2 symbols
	 * minimum 2 upper case characters
	 * minimum 2 lower case characters
	 * (username not present in password string)
	 * @param password
	 * @return true if the password is valid.
	 */
	public static boolean isValidPassword(String password) throws ValidationException {
		//(?!.*" + hashMap.get("userName").toString()+")
		Pattern p = Pattern.compile("^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$");
		Matcher m = p.matcher(password);
		return m.matches();
	}

	/**
	 * Method to validate if a role is a valid choice.
	 * @param role
	 * @return true if the role is valid
	 */
	public static void isValidRole(String role) throws ValidationException {
		ArrayList<String> validRoles = new ArrayList<>();
		validRoles.add("pharmacist");
		validRoles.add("foreman");
		validRoles.add("operator");
		validRoles.add("none");
		role = role.toLowerCase();
		// if its a valid role
		if (!validRoles.contains(role)) {
			throw new ValidationException("Not a valid role");
		}
	}
}
