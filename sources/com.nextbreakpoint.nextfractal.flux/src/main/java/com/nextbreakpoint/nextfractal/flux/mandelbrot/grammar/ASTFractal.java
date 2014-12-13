package com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.Token;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.compiler.CompilerVariable;

public class ASTFractal extends ASTObject {
	private Map<String, CompilerVariable> vars = new HashMap<>();
	private ASTOrbit orbit;
	private ASTColor color;

	public ASTFractal(Token location) {
		super(location);
		registerVar("x", false, false, location);
		registerVar("w", false, false, location);
		registerVar("z", false, false, location);
		registerVar("n", false, false, location);
		registerVar("c", false, false, location);
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

	public void registerVar(String name, boolean real, boolean create, Token location) {
		CompilerVariable var = vars.get(name);
		if (var == null) {
			var = new CompilerVariable(name, real, create);
			vars.put(var.getName(), var);
		} else if (!real && var.isReal()) {
			throw new RuntimeException("Expression not assignable: " + location.getText() + " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]");
		}
	}

	public CompilerVariable getVar(String name, Token location) {
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