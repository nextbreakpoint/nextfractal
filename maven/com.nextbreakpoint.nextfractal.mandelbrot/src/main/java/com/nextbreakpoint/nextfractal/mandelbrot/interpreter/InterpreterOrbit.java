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

import static com.nextbreakpoint.nextfractal.mandelbrot.core.Expression.*;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbit;

public class InterpreterOrbit extends Orbit {
	private ASTFractal astFractal;
	private ExpressionContext context;
	private CompilerVariable[] orbitVars;
	private CompilerVariable[] stateVars;

	public InterpreterOrbit(ASTFractal astFractal, ExpressionContext context) {
		this.astFractal = astFractal;
		this.context = context;
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		try {
			engine.eval("print('Welocme to java worldddd')");
		} catch (ScriptException ex) {
			// TODO
		}
	}

	public void init() {
		ASTOrbit astOrbit = astFractal.getOrbit();
		double ar = astOrbit.getRegion().getA().r();
		double ai = astOrbit.getRegion().getA().i();
		double br = astOrbit.getRegion().getB().r();
		double bi = astOrbit.getRegion().getB().i();
		setInitialRegion(number(ar, ai), number(br, bi));
		orbitVars = new CompilerVariable[astFractal.getOrbitVariables().size()];
		stateVars = new CompilerVariable[astFractal.getStateVariables().size()];
		astFractal.getOrbitVariables().toArray(orbitVars);
		astFractal.getStateVariables().toArray(stateVars);
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
	}

	public void render(List<Number[]> states) {
		ASTOrbit astOrbit = astFractal.getOrbit();
		n = astOrbit.getLoop().getBegin();
		if (states != null) {
			saveState(states);
		}
		ExpressionContext context = new ExpressionContext();
		for (int i = astOrbit.getLoop().getBegin() + 1; i <= astOrbit.getLoop().getEnd(); i++) {
//			for (ASTStatement statement : astOrbit.getLoop().getStatements()) {
//				statement.evaluate(context, scope);
//			}
			x.set(opAdd(getNumber(0), opMul(getNumber(1), x, x), w));
			if ((funcMod2(x) > 4.0)) {
				n = i;
				break;
			}
			if (states != null) {
				saveState(states);
			}
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
}
