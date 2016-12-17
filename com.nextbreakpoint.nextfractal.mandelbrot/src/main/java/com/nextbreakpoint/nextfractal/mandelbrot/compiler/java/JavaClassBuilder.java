/*
 * NextFractal 2.0.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2017 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot.compiler.java;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError;

import java.util.List;

public class JavaClassBuilder<T> implements CompilerBuilder<T> {
	private Class<T> clazz;
	private List<CompilerError> errors;
	
	public JavaClassBuilder(Class<T> clazz, List<CompilerError> errors) {
		this.clazz = clazz;
		this.errors = errors;
	}
	
	public T build() throws InstantiationException, IllegalAccessException {
		if (clazz == null) {
			return null;
		}
		return clazz.newInstance();
	}

	public List<CompilerError> getErrors() {
		return errors;
	}
}
