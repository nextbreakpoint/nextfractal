package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTBuilder {
	public ASTBuilder() {
	}
	
	protected void warning(String message, Token location) {
		System.out.println("[" + location.getLine() + ":" + location.getCharPositionInLine() + "] : " + message);
	}
	
	protected void error(String message, Token location) {
		System.err.println("[" + location.getLine() + ":" + location.getCharPositionInLine() + "] : " + message);
	}
	
	public ASTStatement makeVariable(Token location, String name, ASTComplexExpression exp) {
		return new ASTStatement(location, name, exp);
	}

	public ASTComplexExpression makeFunction(Token location, String name, ASTComplexExpression[] args) {
		return new ASTComplexFunction(location, name, args);
	}
	
	public void setExpression(ASTComplexExpression exp) {
		
	}

	public void addBeginStatement(ASTStatement result) {
		// TODO Auto-generated method stub
		
	}

	public void addEndStatement(ASTStatement result) {
		// TODO Auto-generated method stub
		
	}

	public void addLoopStatement(ASTStatement result) {
		// TODO Auto-generated method stub
		
	}

	public void setColor(ASTColor color) {
		// TODO Auto-generated method stub
		
	}

	public void setOrbitBegin(ASTOrbitBegin orbitBegin) {
		// TODO Auto-generated method stub
		
	}

	public void setOrbitProjection(ASTOrbitProjection orbitProjection) {
		// TODO Auto-generated method stub
		
	}

	public void setOrbitEnd(ASTOrbitEnd orbitEnd) {
		// TODO Auto-generated method stub
		
	}

	public void addOrbitTrap(ASTOrbitTrap orbitTrap) {
		// TODO Auto-generated method stub
		
	}

	public void setOrbitLoop(ASTOrbitLoop orbitLoop) {
		// TODO Auto-generated method stub
		
	}

	public void setOrbitCondition(ASTOrbitCondition orbitCondition) {
		// TODO Auto-generated method stub
		
	}

	public void setOrbit(ASTOrbit orbit) {
		// TODO Auto-generated method stub
		
	}
}	
