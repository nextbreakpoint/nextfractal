/*
 * NextFractal 1.1.0
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

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOrbit;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbit;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbitTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTStatement;

public class InterpreterOrbitBuilder implements CompilerBuilder<Orbit> {
	private ASTFractal astFractal;
	private List<CompilerError> errors;
	
	public InterpreterOrbitBuilder(ASTFractal astFractal, List<CompilerError> errors) {
		this.astFractal = astFractal;
		this.errors = errors;
	}
	
	public Orbit build() throws InstantiationException, IllegalAccessException {
		ExpressionContext context = new ExpressionContext();
		InterpreterASTCompiler compiler = new InterpreterASTCompiler(context);
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
		CompiledOrbit orbit = new CompiledOrbit(orbitVars, stateVars);
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
	}

	public List<CompilerError> getErrors() {
		return errors;
	}
}
