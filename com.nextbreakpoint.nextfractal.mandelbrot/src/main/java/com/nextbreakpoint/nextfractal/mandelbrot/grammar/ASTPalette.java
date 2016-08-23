/*
 * NextFractal 1.2.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledPalette;

public class ASTPalette extends ASTObject {
	private String name;
	private List<ASTPaletteElement> elements = new ArrayList<>(); 

	public ASTPalette(Token location, String name) {
		super(location);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<ASTPaletteElement> getElements() {
		return elements;
	}

	public void addElements(ASTPaletteElement element) {
		elements.add(element);
	}

	@Override
	public String toString() {
		StringBuilder driver = new StringBuilder();
		driver.append("name = ");
		driver.append(name);
		driver.append(",elements = [");
		for (int i = 0; i < elements.size(); i++) {
			ASTPaletteElement statement = elements.get(i);
			driver.append("{");
			driver.append(statement);
			driver.append("}");
			if (i < elements.size() - 1) {
				driver.append(",");
			}
		}
		driver.append("]");
		return driver.toString();
	}

	public CompiledPalette compile(ASTExpressionCompiler compiler) {
		return compiler.compile(this);
	}
}