package com.nextbreakpoint.nextfractal.flux.mandelbrot.compiler;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.Fractal;

public class CompilerReport {
	private String ast;
	private String source;
	private Fractal fractal;

	public CompilerReport(String ast, String source, Fractal fractal) {
		this.ast = ast;
		this.source = source;
		this.fractal = fractal;
	}

	public String getAst() {
		return ast;
	}

	public String getSource() {
		return source;
	}

	public Fractal getFractal() {
		return fractal;
	}
}
