package main.java.dk.dtu.control.api.v1;

public class AdaptorException extends Exception {
		private static final long serialVersionUID = -7306466308871895237L;
		public AdaptorException(String msg) { super(msg); }
		public AdaptorException(Exception e) { super(e); }	
}
