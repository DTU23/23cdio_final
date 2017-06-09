package dk.dtu.control;

public class AuthException extends Exception {
	private static final long serialVersionUID = -5325383575045241965L;
	public AuthException(String msg) { super(msg); }
	public AuthException(Exception e) { super(e); }
}
