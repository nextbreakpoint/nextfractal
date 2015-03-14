package com.nextbreakpoint.nextfractal.core.session;

public interface SessionError {
	public ErrorType getType();

	public long getLine();

	public long getCharPositionInLine();

	public String getMessage();
	
	public long getLength();

	public static enum ErrorType {
		LEXER, PARSER, JAVA_COMPILER;
	}
}
