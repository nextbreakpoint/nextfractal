package com.nextbreakpoint.nextfractal.flux;

import java.util.HashMap;
import java.util.Map;

public abstract class Fractal {
	private Map<String, Variable> variables = new HashMap<>();
	private String sourceCode;

	public Fractal() {
	}

	public abstract Number compute(Number z, Number w);
	
	protected Number setVar(String name, int r) {
		return setVar(name, new Number(r));
	}
	
	protected Number setVar(String name, double r) {
		return setVar(name, new Number(r));
	}
	
	protected Number setVar(String name, double r, double i) {
		return setVar(name, new Number(r, i));
	}
	
	protected Number setVar(String name, Number x) {
		Variable var = variables.get(name);
		if (var == null) {
			var = new Variable(name);
			variables.put(name, var);
		}
		var.set(x);
		return var;
	}
	
	public Number getVar(String name) {
		Variable var = variables.get(name);
		if (var == null) {
			throw new RuntimeException("Variable not defined");
		}
		return var;
	}
	
	protected Number opAdd(Number a, Number b) {
		return new Number(a.r() + b.r(), a.i() + b.i());
	}
	
	protected Number opSub(Number a, Number b) {
		return new Number(a.r() - b.r(), a.i() - b.i());
	}
	
	protected Number opMul(Number a, Number b) {
		return new Number(a.r() * b.r() - a.i() * b.i(), a.r() * b.i() + a.r() * b.i());
	}
	
	protected Number opAdd(Number a, double b) {
		return new Number(a.r() + b, a.i());
	}
	
	protected Number opSub(Number a, double b) {
		return new Number(a.r() - b, a.i());
	}
	
	protected Number opMul(Number a, double b) {
		return new Number(a.r() * b, a.i() * b);
	}

	protected Number opDiv(Number a, double b) {
		return new Number(a.r() / b, a.i() / b);
	}
	
	protected Number opAdd(double a, Number b) {
		return new Number(a + b.r(), +b.i());
	}
	
	protected Number opSub(double a, Number b) {
		return new Number(a - b.r(), -b.i());
	}
	
	protected Number opMul(double a, Number b) {
		return new Number(a * b.r(), a * b.i());
	}

	protected Number opNeg(Number a) {
		return new Number(-a.r(), -a.i());
	}
	
	protected Number opPos(Number a) {
		return new Number(+a.r(), +a.i());
	}
	
	protected double opAddReal(double a, double b) {
		return a + b;
	}
	
	protected double opSubReal(double a, double b) {
		return a - b;
	}
	
	protected double opMulReal(double a, double b) {
		return a * b;
	}
	
	protected double opDivReal(double a, double b) {
		return a / b;
	}
	
	protected double opPowReal(double a, double b) {
		return Math.pow(a, b);
	}
	
	protected double opNegReal(double a) {
		return -a;
	}
	
	protected double opPosReal(double a) {
		return +a;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
}
