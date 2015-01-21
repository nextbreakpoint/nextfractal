package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

public class CompilerBuilder<T> {
	private Class<T> clazz;
	
	public CompilerBuilder(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public T build() throws InstantiationException, IllegalAccessException {
		return clazz.newInstance();
	}
}
