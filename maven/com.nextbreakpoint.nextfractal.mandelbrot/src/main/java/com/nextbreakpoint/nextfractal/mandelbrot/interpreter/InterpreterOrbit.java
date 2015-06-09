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
import java.util.List;
import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledTrapOp;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Palette;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;

public class InterpreterOrbit extends Orbit implements InterpreterContext {
	private InterpreterCompiledOrbit orbit;
	private ExpressionContext context;
	private CompilerVariable[] orbitVars;
	private CompilerVariable[] stateVars;
	private Map<String, Trap> traps = new HashMap<>();
	private Map<String, Palette> palettes = new HashMap<>();

	public InterpreterOrbit(InterpreterCompiledOrbit orbit, ExpressionContext context) {
		this.orbit = orbit;
		this.context = context;
	}

	public void init() {
		setInitialRegion(orbit.getRegion()[0], orbit.getRegion()[1]);
		orbitVars = new CompilerVariable[orbit.getOrbitVariables().size()];
		stateVars = new CompilerVariable[orbit.getStateVariables().size()];
		orbit.getOrbitVariables().toArray(orbitVars);
		orbit.getStateVariables().toArray(stateVars);
		addVariable(x);
		addVariable(n);
		for (int i = 0; i < stateVars.length; i++) {
			if (stateVars[i].isCreate()) {
				if (stateVars[i].isReal()) {
					addVariable(stateVars[i].getRealValue());
				} else {
					addVariable(stateVars[i].getValue());
				}
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
		n = orbit.getLoopBegin();
		if (states != null) {
			saveState(states);
		}
		Map<String, CompilerVariable> scope = new HashMap<>();
		for (int i = 0; i < stateVars.length; i++) {
			scope.put(stateVars[i].getName(), stateVars[i]);
		}
		for (int i = 0; i < orbitVars.length; i++) {
			scope.put(orbitVars[i].getName(), orbitVars[i]);
		}
		try {
			for (CompiledStatement statement : orbit.getBeginStatements()) {
				statement.evaluate(this, scope);
			} 
		} catch (RuntimeException e) {
		}
		boolean stop = false;
		for (int i = orbit.getLoopBegin() + 1; i <= orbit.getLoopEnd(); i++) {
			for (CompiledStatement statement : orbit.getLoopStatements()) {
				stop = statement.evaluate(this, scope);
			} 
			if (stop || orbit.getLoopCondition().evaluate(this, scope)) {
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
		setVariable(0, x);
		setVariable(1, n);
		for (int i = 0; i < stateVars.length; i++) {
			if (stateVars[i].isCreate()) {
				if (stateVars[i].isReal()) {
					setVariable(i, stateVars[i].getRealValue());
				} else {
					setVariable(i, stateVars[i].getValue());
				}
			}
		}
	}

	private void saveState(List<Number[]> states) {
		Number[] state = new Number[stateVars.length];
		for (int k = 0; k < stateVars.length; k++) {
			state[k] = new Number(stateVars[k].getValue());
		}
		states.add(state);
	}

	protected MutableNumber[] createNumbers() {
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
