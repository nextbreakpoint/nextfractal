package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.util.List;

public class CompilerBuilder<T> {
	private Class<T> clazz;
	private List<CompilerError> errors;
	
	public CompilerBuilder(Class<T> clazz, List<CompilerError> errors) {
		this.clazz = clazz;
		this.errors = errors;
	}
	
	public T build() throws InstantiationException, IllegalAccessException {
		return clazz.newInstance();
	}

	public List<CompilerError> getErrors() {
		return errors;
	}
}
