package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

public class CompilerReport {
	private String ast;
	private String source;
	private Object object;

	public CompilerReport(String ast, String source, Object object) {
		this.ast = ast;
		this.source = source;
		this.object = object;
	}

	public String getAst() {
		return ast;
	}

	public String getSource() {
		return source;
	}

	public Object getObject() {
		return object;
	}
}
