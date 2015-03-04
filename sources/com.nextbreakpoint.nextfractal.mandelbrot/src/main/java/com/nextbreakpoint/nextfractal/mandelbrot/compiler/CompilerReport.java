package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;

public class CompilerReport {
	private ASTFractal ast;
	private String orbitSource;
	private String colorSource;

	public CompilerReport(ASTFractal ast, String orbitSource, String colorSource) {
		this.ast = ast;
		this.orbitSource = orbitSource;
		this.colorSource = colorSource;
	}

	public ASTFractal getAST() {
		return ast;
	}

	public String getOrbitSource() {
		return orbitSource;
	}

	public String getColorSource() {
		return colorSource;
	}
}
