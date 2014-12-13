package com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.Token;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.compiler.CompilerVariable;

public class ASTFractal extends ASTObject {
	private Map<String, CompilerVariable> vars = new HashMap<>();
	private List<String> variables = new ArrayList<>();
	private ASTOrbit orbit;
	private ASTColor color;

	public ASTFractal(Token location) {
		super(location);
		registerVariable("x", false, false, location);
		registerVariable("w", false, false, location);
		registerVariable("z", false, false, location);
		registerVariable("n", false, false, location);
		registerVariable("c", false, false, location);
	}

	public void registerStateVariable(String varName, Token location) {
		if (vars.get(varName) == null) {
			throw new RuntimeException("CompilerVariable not defined: " + location.getText() + " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]");
		}
		variables.add(varName);
	}

	public List<String> getVariables() {
		return variables;
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
		color.setVariables(variables);
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

	public void registerVariable(String name, boolean real, boolean create, Token location) {
		CompilerVariable var = vars.get(name);
		if (var == null) {
			var = new CompilerVariable(name, real, create);
			vars.put(var.getName(), var);
		} else if (!real && var.isReal()) {
			throw new RuntimeException("Expression not assignable: " + location.getText() + " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]");
		}
	}

	public CompilerVariable getVariable(String name, Token location) {
		CompilerVariable var = vars.get(name);
		if (var == null) {
			throw new RuntimeException("CompilerVariable not defined: " + location.getText() + " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]");
		}
		return var;
	}

	public CompilerVariable getVar(String name) {
		return vars.get(name);
	}
	
	public Collection<CompilerVariable> getVars() {
		return vars.values();
	}
}