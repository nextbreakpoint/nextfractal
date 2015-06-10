package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTException;

public class CompiledAssignStatement implements CompiledStatement {
	private String name;
	private CompiledExpression exp;
	
	public CompiledAssignStatement(String name, CompiledExpression exp) {
		this.name = name;
		this.exp = exp;
	}

	@Override
	public boolean evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		CompilerVariable var = scope.get(name);
		if (var != null) {
			if (exp.isReal()) {
				var.setValue(exp.evaluateReal(context, scope));
			} else if (!var.isReal()) {
				var.setValue(exp.evaluate(context, scope));
			} else {
				throw new ASTException("Cannot assign expression", null);
			}
		} else {
			var = new CompilerVariable(name, exp.isReal(), false);
			scope.put(name, var);
			if (exp.isReal()) {
				var.setValue(exp.evaluateReal(context, scope));
			} else {
				var.setValue(exp.evaluate(context, scope));
			}
		}
		return false;
	}
}
