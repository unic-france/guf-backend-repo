package com.unic.fr.exception;

public class TechnicalException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TechnicalException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a {@code UsernameNotFoundException} with the specified message and root
	 * cause.
	 *
	 * @param msg the detail message.
	 * @param t root cause
	 */
	public TechnicalException(String msg, Throwable t) {
		super(msg, t);
	}

}
