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
package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;

public class InterpreterClassCompiler {
	public InterpreterClassCompiler() {
	}
	
	public CompilerBuilder<Orbit> compileOrbit(CompilerReport report) throws ClassNotFoundException, IOException {
		List<CompilerError> errors = new ArrayList<>();
		return new InterpreterOrbitBuilder(report.getAST(), report.getOrbitContext(), errors);
	}

	public CompilerBuilder<Color> compileColor(CompilerReport report) throws ClassNotFoundException, IOException {
		List<CompilerError> errors = new ArrayList<>();
		return new InterpreterColorBuilder(report.getAST(), report.getColorContext(), errors);
	}
}	
