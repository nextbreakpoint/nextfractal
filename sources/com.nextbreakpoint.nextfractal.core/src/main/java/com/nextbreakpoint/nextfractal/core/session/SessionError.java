package com.nextbreakpoint.nextfractal.core.session;

public interface SessionError {
	public ErrorType getType();

	public long getLine();

	public long getCharPositionInLine();

	public String getMessage();
	
	public long getIndex();
	
	public long getLength();

	public static enum ErrorType {
		M_COMPILER, JAVA_COMPILER;
	}
}
