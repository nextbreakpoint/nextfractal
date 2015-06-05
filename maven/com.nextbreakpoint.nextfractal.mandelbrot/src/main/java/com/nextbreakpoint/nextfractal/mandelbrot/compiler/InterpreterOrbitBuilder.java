/*
 * NextFractal 1.0.5
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.util.List;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;

public class InterpreterOrbitBuilder implements CompilerBuilder<Orbit> {
	private ASTFractal astFractal;
	private ExpressionContext context;
	private List<CompilerError> errors;
	
	public InterpreterOrbitBuilder(ASTFractal astFractal, ExpressionContext context, List<CompilerError> errors) {
		this.astFractal = astFractal;
		this.context = context;
		this.errors = errors;
	}
	
	public Orbit build() throws InstantiationException, IllegalAccessException {
		return new InterpreterOrbit(astFractal, context);
	}

	public List<CompilerError> getErrors() {
		return errors;
	}
}
