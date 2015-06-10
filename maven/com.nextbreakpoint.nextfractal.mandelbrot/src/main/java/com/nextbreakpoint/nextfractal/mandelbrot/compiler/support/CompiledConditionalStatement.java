package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;

public class CompiledConditionalStatement implements CompiledStatement {
	private CompiledCondition condition;
	private List<CompiledStatement> thenStatements;
	private List<CompiledStatement> elseStatements;
	private Map<String, CompilerVariable> newScope;
	
	public CompiledConditionalStatement(CompiledCondition condition, List<CompiledStatement> thenStatements, List<CompiledStatement> elseStatements) {
		this.condition = condition;
		this.thenStatements = thenStatements;
		this.elseStatements = elseStatements;
	}

	@Override
	public boolean evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		boolean stop = false;
		if (newScope == null) {
			newScope = new HashMap<String, CompilerVariable>(scope);
		}
		if (condition.evaluate(context, scope)) {
			for (CompiledStatement statement : thenStatements) {
				stop = statement.evaluate(context, newScope);
				if (stop) {
					break;
				}
			}
		} else {
			for (CompiledStatement statement : elseStatements) {
				stop = statement.evaluate(context, newScope);
				if (stop) {
					break;
				}
			}
		}
		return stop;
	}
}
