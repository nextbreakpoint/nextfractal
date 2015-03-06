package com.nextbreakpoint.nextfractal.core.session;

public class SessionException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public SessionException(final String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SessionException(final Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SessionException(final String message, final Throwable cause) {
		super(message, cause);
	}
}