package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.util.List;

import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;

public class CompilerReport {
	private ASTFractal ast;
	private String orbitSource;
	private String colorSource;
	private List<CompilerError> errors;

	public CompilerReport(ASTFractal ast, String orbitSource, String colorSource, List<CompilerError> errors) {
		this.ast = ast;
		this.orbitSource = orbitSource;
		this.colorSource = colorSource;
		this.errors = errors;
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

	public List<CompilerError> getErrors() {
		return errors;
	}
}
