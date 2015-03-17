package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import com.nextbreakpoint.nextfractal.core.session.SessionError;

public class CompilerError implements SessionError {
	private ErrorType type;
	private long line;
	private long charPositionInLine;
	private long index;
	private long length;
	private String message;

	public CompilerError(ErrorType type, long line, long charPositionInLine, long index, long length, String message) {
		this.type = type;
		this.line = line;
		this.index = index;
		this.charPositionInLine = charPositionInLine;
		this.message = message;
	}

	public ErrorType getType() {
		return type;
	}

	public long getLine() {
		return line;
	}

	public long getCharPositionInLine() {
		return charPositionInLine;
	}

	public String getMessage() {
		return message;
	}
	
	public long getLength() {
		return length;
	}
	
	public long getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return "[" + line + ":" + charPositionInLine + ":" + index + ":" + length + "] " + message;
	}
}
