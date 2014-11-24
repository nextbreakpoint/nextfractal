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
	
	public void setColor(ASTColor color) {
		this.color = color;
	}

	public ASTOrbit getOrbit() {
		return orbit;
	}

	public ASTColor getColor() {
		return color;
	}
}	
