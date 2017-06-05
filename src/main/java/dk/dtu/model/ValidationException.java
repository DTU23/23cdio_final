package dk.dtu.model;

public class ValidationException extends Exception {
	private static final long serialVersionUID = 670332140423964441L;
	public ValidationException(String msg) { super(msg); }
	public ValidationException(Exception e) { super(e); }
}
