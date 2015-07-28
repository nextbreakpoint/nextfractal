/*
 * NextFractal 1.1.2
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
package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.Token;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;

public class ASTFractal extends ASTObject {
	private Map<String, CompilerVariable> stateVars = new HashMap<>();
	private Map<String, CompilerVariable> colorVars = new HashMap<>();
	private Map<String, CompilerVariable> orbitVars = new HashMap<>();
	private ASTOrbit orbit;
	private ASTColor color;

	public ASTFractal(Token location) {
		super(location);
		registerOrbitVariable("x", false, false, location);
		registerOrbitVariable("w", false, false, location);
		registerOrbitVariable("n", true, false, location);
	}

	public ASTOrbit getOrbit() {
		return orbit;
	}
	
	public void setOrbit(ASTOrbit orbit) {
		this.orbit = orbit;
	}
	
	public ASTColor getColor() {
		return color;
	}
	
	public void setColor(ASTColor color) {
		this.color = color;
	}

	public void registerStateVariable(String varName, boolean real, Token location) {
		if (orbitVars.get(varName) == null) {
			registerOrbitVariable(varName, real, true, location);
		} else {
			if (orbitVars.get(varName).isReal() != real) {
				throw new ASTException("Variable already defined: " + location.getText(), location);
			}
		}
		if (stateVars.get(varName) == null) {
			stateVars.put(varName, orbitVars.get(varName));
		}
	}

	public void registerOrbitVariable(String name, boolean real, boolean create, Token location) {
		CompilerVariable var = orbitVars.get(name);
		if (var == null) {
			var = new CompilerVariable(name, real, create);
			orbitVars.put(var.getName(), var);
		}
	}

	public void registerColorVariable(String name, boolean real, boolean create, Token location) {
		CompilerVariable var = colorVars.get(name);
		if (var == null) {
			var = new CompilerVariable(name, real, create);
			colorVars.put(var.getName(), var);
		}
	}

	public CompilerVariable getOrbitVariable(String name, Token location) {
		CompilerVariable var = orbitVars.get(name);
		if (var == null) {
			throw new ASTException("Variable not defined: " + location.getText(), location);
		}
		return var;
	}

	public CompilerVariable getColorVariable(String name, Token location) {
		CompilerVariable var = colorVars.get(name);
		if (var == null) {
			var = orbitVars.get(name);
			if (var == null) {
				throw new ASTException("Variable not defined: " + location.getText(), location);
			}
		}
		return var;
	}

	public Collection<CompilerVariable> getStateVariables() {
		return stateVars.values();
	}

	public Collection<CompilerVariable> getOrbitVariables() {
		return orbitVars.values();
	}

	public Collection<CompilerVariable> getColorVariables() {
		return colorVars.values();
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("orbit = {");
		builder.append(orbit);
		builder.append("},color = {");
		builder.append(color);
		builder.append("}");
		return builder.toString();
	}
}
