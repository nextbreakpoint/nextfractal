package com.nextbreakpoint.nextfractal.contextfree.parser;

public class ContextFreeParserException extends Exception {
	private static final long serialVersionUID = 1L;

	public ContextFreeParserException(String message) {
		super(message);
	}

	public ContextFreeParserException(Throwable cause) {
		super(cause);
	}

	public ContextFreeParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
