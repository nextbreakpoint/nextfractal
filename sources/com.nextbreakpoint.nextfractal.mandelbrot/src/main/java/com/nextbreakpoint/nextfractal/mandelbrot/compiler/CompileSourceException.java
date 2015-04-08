package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.util.List;

public class CompileSourceException extends Exception {
	private static final long serialVersionUID = 1L;
	private List<CompilerError> errors;

	public CompileSourceException(String message, List<CompilerError> errors) {
		super(message);
		this.errors = errors;
	}
	
	public List<CompilerError> getErrors() {
		return errors;
	}
}
