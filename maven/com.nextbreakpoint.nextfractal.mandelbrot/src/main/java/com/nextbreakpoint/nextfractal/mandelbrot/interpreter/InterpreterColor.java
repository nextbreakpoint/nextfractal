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
import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledPaletteElement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledRule;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Palette;
import com.nextbreakpoint.nextfractal.mandelbrot.core.PaletteElement;
import com.nextbreakpoint.nextfractal.mandelbrot.core.PaletteExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;

public class InterpreterColor extends Color implements InterpreterContext {
	private InterpreterCompiledColor color;
	private ExpressionContext context;
	private Map<String, Trap> traps = new HashMap<>();
	private Map<String, Palette> palettes = new HashMap<>();

	public InterpreterColor(InterpreterCompiledColor color, ExpressionContext context) {
		this.color = color;
		this.context = context;
		initializeStack();
	}

	public void init() {
		for (CompiledPalette cPalette : color.getPalettes()) {
			Palette palette = new Palette();
			Map<String, CompilerVariable> scope = new HashMap<>();
			for (Iterator<CompilerVariable> s = color.getStateVariables().iterator(); s.hasNext();) {
				CompilerVariable var = s.next();
				scope.put(var.getName(), var.copy());
			}
			for (Iterator<CompilerVariable> s = color.getColorVariables().iterator(); s.hasNext();) {
				CompilerVariable var = s.next();
				scope.put(var.getName(), var.copy());
			}
			for (CompiledPaletteElement cElement : cPalette.getElements()) {
				PaletteExpression expression = null;
				if (cElement.getExp() != null) {
					expression = (start, end, step) -> {
						scope.put("start", new CompilerVariable("start", true, false, start));
						scope.put("end", new CompilerVariable("end", true, false, end));
						scope.put("step", new CompilerVariable("step", true, false, step));
						return cElement.getExp().evaluateReal(InterpreterColor.this, scope);
					};
				} else {
					expression = (start, end, step) -> {
						return (step - start) / (end - start); 
					};
				}
				PaletteElement element = new PaletteElement(cElement.getBeginColor(), cElement.getEndColor(), cElement.getSteps(), expression);
				palette.add(element);
			}
			palettes.put(cPalette.getName(), palette);
		}
	}

	public void render() {
		Map<String, CompilerVariable> scope = new HashMap<>();
		for (Iterator<CompilerVariable> s = color.getStateVariables().iterator(); s.hasNext();) {
			CompilerVariable var = s.next();
			scope.put(var.getName(), var.copy());
		}
		for (Iterator<CompilerVariable> s = color.getColorVariables().iterator(); s.hasNext();) {
			CompilerVariable var = s.next();
			scope.put(var.getName(), var.copy());
		}
		updateStateVars(scope);
		setColor(color.getBackgroundColor());
		for (CompiledStatement statement : color.getInitStatements()) {
			statement.evaluate(this, scope);
		} 
		for (CompiledRule rule : color.getRules()) {
			if ((rule.getRuleCondition().evaluate(this, scope))) {
				addColor(rule.getOpacity(), rule.getColorExp().evaluate(this, scope));
			}
		}
	}

	public void updateStateVars(Map<String, CompilerVariable> scope) {
		int i = 0;
		for (Iterator<CompilerVariable> s = scope.values().iterator(); s.hasNext();) {
			CompilerVariable var = s.next();
			var.setValue(this.scope.getVariable(i));
			i++;
		}
	}

	protected MutableNumber[] createNumbers() {
		if (context == null) {
			return null;
		}
		return new MutableNumber[context.getNumberCount()];
	}

	@Override
	public Trap getTrap(String name) {
		return traps.get(name);
	}

	@Override
	public Palette getPalette(String name) {
		return palettes.get(name);
	}
}
