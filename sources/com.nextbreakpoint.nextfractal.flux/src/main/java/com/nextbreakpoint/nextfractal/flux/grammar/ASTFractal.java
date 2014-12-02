package com.nextbreakpoint.nextfractal.flux.grammar;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.Token;

import com.nextbreakpoint.nextfractal.flux.Variable;

public class ASTFractal extends ASTObject {
	private Map<String, Variable> variables = new HashMap<>();
	private ASTOrbit orbit;
	private ASTColor color;

	public ASTFractal(Token location) {
		super(location);
	}
	
	public ASTOrbit getOrbit() {
		return orbit;
	}
	
	public void setOrbit(ASTOrbit orbit) {
		this.orbit = orbit;
	}
	
	public ASTColor getColor() {
		return color;
	}
	
	public void setColor(ASTColor color) {
		this.color = color;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("orbit = {");
		builder.append(orbit);
		builder.append("},color = {");
		builder.append(color);
		builder.append("}");
		return builder.toString();
	}

	public void registerVariable(String name, boolean real, Token location) {
		Variable var = variables.get(name);
		if (var == null) {
			var = new Variable(name, real);
			variables.put(var.getName(), var);
		} else if (!real && var.isReal()) {
			throw new RuntimeException("Expression not assignable: " + location.getText() + " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]");
		}
	}

	public Variable getVariable(String name, Token location) {
		Variable var = variables.get(name);
		if (var == null) {
			throw new RuntimeException("Variable not defined: " + location.getText() + " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]");
		}
		return var;
	}

	public Collection<Variable> getVariables() {
		return variables.values();
	}
}