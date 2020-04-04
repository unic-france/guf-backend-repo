package com.unic.fr.exception;

public class AssignmentNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AssignmentNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a {@code UsernameNotFoundException} with the specified message and root
	 * cause.
	 *
	 * @param msg the detail message.
	 * @param t root cause
	 */
	public AssignmentNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}

}
