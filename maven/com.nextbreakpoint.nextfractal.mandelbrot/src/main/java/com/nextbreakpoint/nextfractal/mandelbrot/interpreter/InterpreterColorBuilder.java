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

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledColor;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledRule;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTColor;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTRule;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTStatement;

public class InterpreterColorBuilder implements CompilerBuilder<Color> {
	private ASTFractal astFractal;
	private List<CompilerError> errors;

	public InterpreterColorBuilder(ASTFractal astFractal, List<CompilerError> errors) {
		this.astFractal = astFractal;
		this.errors = errors;
	}
	
	public Color build() throws InstantiationException, IllegalAccessException {
		ExpressionContext context = new ExpressionContext();
		InterpreterASTCompiler compiler = new InterpreterASTCompiler(context);  
		ASTColor astColor = astFractal.getColor();
		List<CompilerVariable> colorVars = new ArrayList<>();
		for (CompilerVariable var : astFractal.getColorVariables()) {
			colorVars.add(var.copy());
		}
		List<CompilerVariable> stateVars = new ArrayList<>();
		for (CompilerVariable var : astFractal.getStateVariables()) {
			stateVars.add(var.copy());
		}
		CompiledColor color = new CompiledColor(colorVars, stateVars);
		color.setBackgroundColor(astColor.getArgb().getComponents());
		List<CompiledRule> rules = new ArrayList<>();
		for (ASTRule astRule : astColor.getRules()) {
			CompiledRule rule = new CompiledRule();
			rule.setRuleCondition(astRule.getRuleExp().compile(compiler));
			rule.setColorExp(astRule.getColorExp().compile(compiler));
			rule.setOpacity(astRule.getOpacity());
			rules.add(rule);
		}
		List<CompiledPalette> palettes = new ArrayList<>();
		if (astColor.getPalettes() != null) {
			for (ASTPalette astPalette : astColor.getPalettes()) {
				palettes.add(astPalette.compile(compiler));
			}
		}
		List<CompiledStatement> statements = new ArrayList<>();
		if (astColor.getInit() != null) {
			for (ASTStatement statement : astColor.getInit().getStatements()) {
				statements.add(statement.compile(compiler));
			}
		}
		color.setInitStatements(statements);
		color.setPalettes(palettes);
		color.setRules(rules);
		return new InterpreterColor(color, context);
	}

	public List<CompilerError> getErrors() {
		return errors;
	}
}
