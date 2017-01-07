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
package com.nextbreakpoint.nextfractal.contextfree.compiler;

import com.nextbreakpoint.nextfractal.core.Error;

import java.util.List;

public class CompilerClassException extends Exception {
	private static final long serialVersionUID = 1L;
	private List<Error> errors;
	private String source;

	public CompilerClassException(String message, String source, List<Error> errors) {
		super(message);
		this.source = source;
		this.errors = errors;
	}
	
	public List<Error> getErrors() {
		return errors;
	}

	public String getSource() {
		return source;
	}
}
