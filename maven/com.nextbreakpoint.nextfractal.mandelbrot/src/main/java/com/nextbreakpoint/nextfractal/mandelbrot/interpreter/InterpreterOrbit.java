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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledTrapOp;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Palette;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;

public class InterpreterOrbit extends Orbit implements InterpreterContext {
	private InterpreterCompiledOrbit orbit;
	private ExpressionContext context;
	private Map<String, Trap> traps = new HashMap<>();
	private Map<String, Palette> palettes = new HashMap<>();

	public InterpreterOrbit(InterpreterCompiledOrbit orbit, ExpressionContext context) {
		this.orbit = orbit;
		this.context = context;
		initializeStack();
	}

	public void init() {
		setInitialRegion(orbit.getRegion()[0], orbit.getRegion()[1]);
		for (Iterator<CompilerVariable> s = orbit.getStateVariables().iterator(); s.hasNext();) {
			CompilerVariable var = s.next();
			if (var.isReal()) {
				addVariable(var.getRealValue());
			} else {
				addVariable(var.getValue());
			}
		}
		for (CompiledTrap cTrap : orbit.getTraps()) {
			Trap trap = new Trap(cTrap.getCenter());
			for (CompiledTrapOp cTrapOp : cTrap.getOperators()) {
				cTrapOp.evaluate(trap);
			}
			traps.put(cTrap.getName(), trap);
		}
	}

	public void render(List<Number[]> states) {
		Map<String, CompilerVariable> scope = new HashMap<>();
		for (Iterator<CompilerVariable> s = orbit.getStateVariables().iterator(); s.hasNext();) {
			CompilerVariable var = s.next();
			scope.put(var.getName(), var.copy());
		}
		for (Iterator<CompilerVariable> s = orbit.getOrbitVariables().iterator(); s.hasNext();) {
			CompilerVariable var = s.next();
			scope.put(var.getName(), var.copy());
		}
		n = orbit.getLoopBegin();
		ensureVariable(scope, "n", n);
		ensureVariable(scope, "x", x);
		ensureVariable(scope, "w", w);
		if (states != null) {
			saveState(states);
		}
		try {
			for (CompiledStatement statement : orbit.getBeginStatements()) {
				statement.evaluate(this, scope);
			} 
		} catch (RuntimeException e) {
		}
		boolean stop = false;
		Map<String, CompilerVariable> newScope = new HashMap<>(scope);
		for (int i = orbit.getLoopBegin() + 1; i <= orbit.getLoopEnd(); i++) {
			for (CompiledStatement statement : orbit.getLoopStatements()) {
				stop = statement.evaluate(this, newScope);
				if (stop) {
					break;
				}
			} 
			if (stop || orbit.getLoopCondition().evaluate(this, newScope)) {
				n = i;
				break;
			}
			if (states != null) {
				saveState(states);
			}
		}
		for (CompiledStatement statement : orbit.getEndStatements()) {
			statement.evaluate(this, scope);
		} 
		if (states != null) {
			saveState(states);
		}
		ensureVariable(scope, "n", n);
		int i = 0;
		for (Iterator<CompilerVariable> s = orbit.getStateVariables().iterator(); s.hasNext();) {
			CompilerVariable var = s.next();
			if (var.isReal()) {
				setVariable(i, scope.get(var.getName()).getRealValue());
			} else {
				setVariable(i, scope.get(var.getName()).getValue());
			}
			i++;
		}
	}

	private void ensureVariable(Map<String, CompilerVariable> scope, String name, double value) {
		CompilerVariable var = scope.get(name);
		if (var == null) {
			var = new CompilerVariable(name, true, true);
			scope.put(name, var);
		}
		var.setValue(value);
	}

	private void ensureVariable(Map<String, CompilerVariable> scope, String name, Number value) {
		CompilerVariable var = scope.get(name);
		if (var == null) {
			var = new CompilerVariable(name, true, true);
			scope.put(name, var);
		}
		var.setValue(value);
	}

	private void saveState(List<Number[]> states) {
		MutableNumber[] state = new MutableNumber[scope.stateSize()];
		for (int k = 0; k < state.length; k++) {
			state[k] = new MutableNumber();
		}
		scope.getState(state);
		states.add(state);
	}

	protected MutableNumber[] createNumbers() {
		if (context == null) {
			return null;
		}
		return new MutableNumber[context.getNumberCount()];
	}

	@Override
	public Palette getPalette(String name) {
		return palettes.get(name);
	}

	@Override
	public Trap getTrap(String name) {
		return traps.get(name);
	}
}
