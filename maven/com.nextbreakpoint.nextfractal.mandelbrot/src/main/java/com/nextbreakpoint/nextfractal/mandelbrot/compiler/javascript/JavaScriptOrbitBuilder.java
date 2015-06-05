package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import java.util.List;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;

public class JavaScriptOrbitBuilder implements CompilerBuilder<Orbit> {
	private ASTFractal astFractal;
	private ExpressionContext context;
	private String source;
	private List<CompilerError> errors;
	
	public JavaScriptOrbitBuilder(ASTFractal astFractal, ExpressionContext context, String source, List<CompilerError> errors) {
		this.astFractal = astFractal;
		this.context = context;
		this.source = source;
		this.errors = errors;
	}

	@Override
	public Orbit build() throws InstantiationException, IllegalAccessException {
		return new JavaScriptOrbit(astFractal, context, source);
	}

	@Override
	public List<CompilerError> getErrors() {
		return errors;
	}
}
