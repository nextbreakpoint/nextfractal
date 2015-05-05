/*
 * NextFractal 1.0.4
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
