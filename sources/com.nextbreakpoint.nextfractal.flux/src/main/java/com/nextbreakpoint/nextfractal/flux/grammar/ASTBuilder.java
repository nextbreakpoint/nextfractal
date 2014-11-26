package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTBuilder {
	private ASTOrbit orbit;
	private ASTColor color;
	
	public ASTBuilder() {
	}
	
	protected void warning(String message, Token location) {
		System.out.println("[" + location.getLine() + ":" + location.getCharPositionInLine() + "] : " + message);
	}
	
	protected void error(String message, Token location) {
		System.err.println("[" + location.getLine() + ":" + location.getCharPositionInLine() + "] : " + message);
	}
	
	public void setOrbit(ASTOrbit orbit) {
		this.orbit = orbit;
	}

	public void setOrbitBegin(ASTOrbitBegin orbitBegin) {
		orbit.setBegin(orbitBegin);
	}

	public void setOrbitEnd(ASTOrbitEnd orbitEnd) {
		orbit.setEnd(orbitEnd);
	}

	public void setOrbitLoop(ASTOrbitLoop orbitLoop) {
		orbit.setLoop(orbitLoop);
	}
	
	public void setOrbitProjection(ASTOrbitProjection orbitProjection) {
		orbit.setProjection(orbitProjection);
	}

	public void setOrbitCondition(ASTOrbitCondition orbitCondition) {
		orbit.setCondition(orbitCondition);
	}

	public void addOrbitTrap(ASTOrbitTrap orbitTrap) {
		orbit.addTrap(orbitTrap);
	}

	public void addBeginStatement(ASTStatement statement) {
		orbit.getBegin().addStatement(statement);
	}

	public void addEndStatement(ASTStatement statement) {
		orbit.getEnd().addStatement(statement);
	}

	public void addLoopStatement(ASTStatement statement) {
		orbit.getLoop().addStatement(statement);
	}

	public void addOrbitTrapOp(ASTOrbitTrapOp orbitTrapOp) {
		if (orbit.getTraps().size() > 0) {
			ASTOrbitTrap trap = orbit.getTraps().get(orbit.getTraps().size() - 1);
			trap.addOperator(orbitTrapOp);
		}
	}

	public void addPalette(ASTPalette palette) {
		color.addPalette(palette);
	}
	
	public void addRule(ASTRule rule) {
		color.addRule(rule);
	}

	public void addPaletteElement(ASTPaletteElement element) {
		if (color.getPalettes().size() > 0) {
			ASTPalette palette = color.getPalettes().get(color.getPalettes().size() - 1);
			palette.addElements(element);
		}
	}

	public void setColor(ASTColor color) {
		this.color = color;
	}

	public ASTOrbit getOrbit() {
		return orbit;
	}

	public ASTColor getColor() {
		return color;
	}

	public double parseDouble(String text) {
		return Double.parseDouble(text);
	}

	public float parseFloat(String text) {
		return Float.parseFloat(text);
	}

	public int parseInt(String text) {
		return Integer.parseInt(text);
	}

	public int parseInt(String text, int base) {
		return Integer.parseInt(text, base);
	}
}	
