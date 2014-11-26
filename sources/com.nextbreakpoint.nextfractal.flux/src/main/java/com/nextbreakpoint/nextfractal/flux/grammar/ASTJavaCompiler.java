package com.nextbreakpoint.nextfractal.flux.grammar;


public class ASTJavaCompiler {
	private ASTFractal fractal;
	
	public ASTJavaCompiler(ASTFractal fractal) {
		this.fractal = fractal;
	}

	public String compile() {
		StringBuilder builder = new StringBuilder();
		return builder.toString();
	}
}	
