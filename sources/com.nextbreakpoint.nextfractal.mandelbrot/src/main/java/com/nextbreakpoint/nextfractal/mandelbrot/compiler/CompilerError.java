package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

public class CompilerError {
	private long line;
	private long charPositionInLine;
	private long length;
	private String message;

	public CompilerError(long line, long charPositionInLine, long length, String message) {
		this.line = line;
		this.charPositionInLine = charPositionInLine;
		this.message = message;
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

	@Override
	public String toString() {
		return "[" + line + ":" + charPositionInLine + ":" + length + "] " + message;
	}
}
