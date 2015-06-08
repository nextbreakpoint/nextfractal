package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;

public class InterpreterConditionalStatement implements CompiledStatement {
	private CompiledCondition condition;
	private List<CompiledStatement> thenStatements;
	private List<CompiledStatement> elseStatements;
	
	public InterpreterConditionalStatement(CompiledCondition condition, List<CompiledStatement> thenStatements, List<CompiledStatement> elseStatements) {
		this.condition = condition;
		this.thenStatements = thenStatements;
		this.elseStatements = elseStatements;
	}

	@Override
	public void evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		if (condition.evaluate(context)) {
			for (CompiledStatement statement : thenStatements) {
				Map<String, CompilerVariable> newScope = new HashMap<String, CompilerVariable>(scope);
				statement.evaluate(context, newScope);
			}
		} else {
			for (CompiledStatement statement : elseStatements) {
				Map<String, CompilerVariable> newScope = new HashMap<String, CompilerVariable>(scope);
				statement.evaluate(context, newScope);
			}
		}
	}
}
