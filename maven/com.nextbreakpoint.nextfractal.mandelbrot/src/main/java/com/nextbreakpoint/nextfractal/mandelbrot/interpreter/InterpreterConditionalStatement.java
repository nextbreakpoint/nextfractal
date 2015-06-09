package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;

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
	public boolean evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		boolean stop = false; 
		if (condition.evaluate(context, scope)) {
			Map<String, CompilerVariable> newScope = new HashMap<String, CompilerVariable>(scope);
			for (CompiledStatement statement : thenStatements) {
				stop = statement.evaluate(context, newScope);
				if (stop) {
					break;
				}
			}
		} else {
			Map<String, CompilerVariable> newScope = new HashMap<String, CompilerVariable>(scope);
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
