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

import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTColor;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;

public class InterpreterColor extends Color {
	private ASTFractal astFractal;
	private ExpressionContext context;

	public InterpreterColor(ASTFractal astFractal, ExpressionContext context) {
		this.astFractal = astFractal;
		this.context = context;
	}

	public void init() {
	}

	public void render() {
		ASTColor astColor = astFractal.getColor();
		final MutableNumber x = getVariable(0);
		double n = getRealVariable(1);
		setColor(color(1.0, 0.0, 0.0, 0.0));
		if ((n == 0.0)) {
			addColor(1.0, color(1.0, 0.0, 0.0, 0.0));
		}
		if ((n > 0.0)) {
			addColor(1.0, color(1.0, 1.0, 1.0, 1.0));
		}
	}

	protected MutableNumber[] createNumbers() {
		return new MutableNumber[context.getNumberCount()];
	}
}
