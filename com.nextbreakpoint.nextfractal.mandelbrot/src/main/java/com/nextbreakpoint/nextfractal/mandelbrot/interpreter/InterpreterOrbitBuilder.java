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
package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.core.Error;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerSourceException;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOrbit;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTException;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbit;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbitTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InterpreterOrbitBuilder implements CompilerBuilder<Orbit> {
	private ASTFractal astFractal;
	private String source;
	private List<Error> errors;
	
	public InterpreterOrbitBuilder(ASTFractal astFractal, String source, List<Error> errors) {
		this.astFractal = astFractal;
		this.source = source;
		this.errors = errors;
	}
	
	public Orbit build() throws InstantiationException, IllegalAccessException, CompilerSourceException {
		try {
			ExpressionContext context = new ExpressionContext();
			ASTOrbit astOrbit = astFractal.getOrbit();
			double ar = astOrbit.getRegion().getA().r();
			double ai = astOrbit.getRegion().getA().i();
			double br = astOrbit.getRegion().getB().r();
			double bi = astOrbit.getRegion().getB().i();
			List<CompilerVariable> orbitVars = new ArrayList<>();
			for (CompilerVariable var : astFractal.getOrbitVariables()) {
				orbitVars.add(var.copy());
			}
			List<CompilerVariable> stateVars = new ArrayList<>();
			for (CompilerVariable var : astFractal.getStateVariables()) {
				stateVars.add(var.copy());
			}
			Map<String, CompilerVariable> vars = new HashMap<>();
			for (Iterator<CompilerVariable> s = astFractal.getStateVariables().iterator(); s.hasNext();) {
				CompilerVariable var = s.next();
				vars.put(var.getName(), var);
			}
			for (Iterator<CompilerVariable> s = astFractal.getOrbitVariables().iterator(); s.hasNext();) {
				CompilerVariable var = s.next();
				vars.put(var.getName(), var);
			}
			Map<String, CompilerVariable> newScope = new HashMap<>(vars);
			InterpreterASTCompiler compiler = new InterpreterASTCompiler(context, newScope);
			CompiledOrbit orbit = new CompiledOrbit(orbitVars, stateVars, astOrbit.getLocation());
			orbit.setRegion(new Number[] { new Number(ar, ai), new Number(br, bi) });
			List<CompiledStatement> beginStatements = new ArrayList<>();
			List<CompiledStatement> loopStatements = new ArrayList<>();
			List<CompiledStatement> endStatements = new ArrayList<>();
			List<CompiledTrap> traps = new ArrayList<>();
			if (astOrbit.getBegin() != null) {
				for (ASTStatement astStatement : astOrbit.getBegin().getStatements()) {
					beginStatements.add(astStatement.compile(compiler));
				}
			}
			if (astOrbit.getLoop() != null) {
				for (ASTStatement astStatement : astOrbit.getLoop().getStatements()) {
					loopStatements.add(astStatement.compile(compiler));
				}
				orbit.setLoopCondition(astOrbit.getLoop().getExpression().compile(compiler));
				orbit.setLoopBegin(astOrbit.getLoop().getBegin());
				orbit.setLoopEnd(astOrbit.getLoop().getEnd());
			}
			if (astOrbit.getEnd() != null) {
				for (ASTStatement astStatement : astOrbit.getEnd().getStatements()) {
					endStatements.add(astStatement.compile(compiler));
				}
			}
			if (astOrbit.getTraps() != null) {
				for (ASTOrbitTrap astTrap : astOrbit.getTraps()) {
					traps.add(astTrap.compile(compiler));
				}
			}
			orbit.setBeginStatements(beginStatements);
			orbit.setLoopStatements(loopStatements);
			orbit.setEndStatements(endStatements);
			orbit.setTraps(traps);
			return new InterpreterOrbit(orbit, context);
		} catch (ASTException e) {
			errors.add(new CompilerError(Error.ErrorType.SCRIPT_COMPILER, e.getLocation().getLine(), e.getLocation().getCharPositionInLine(), e.getLocation().getStartIndex(), e.getLocation().getStopIndex() - e.getLocation().getStartIndex(), e.getMessage()));
			throw new CompilerSourceException("Cannot build orbit", errors);
		} catch (Exception e) {
			errors.add(new CompilerError(Error.ErrorType.SCRIPT_COMPILER, 0, 0, 0, 0, e.getMessage()));
			throw new CompilerSourceException("Cannot build orbit", errors);
		}
	}

	public List<Error> getErrors() {
		return errors;
	}
}
