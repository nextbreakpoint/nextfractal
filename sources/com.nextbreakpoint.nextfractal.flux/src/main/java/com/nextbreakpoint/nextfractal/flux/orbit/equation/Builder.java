package com.nextbreakpoint.nextfractal.flux.orbit.equation;

import org.antlr.v4.runtime.Token;

public class Builder {
	public Builder() {
	}
	
	protected void warning(String message, Token location) {
		System.out.println("[" + location.getLine() + ":" + location.getCharPositionInLine() + "] : " + message);
	}
	
	protected void error(String message, Token location) {
		System.err.println("[" + location.getLine() + ":" + location.getCharPositionInLine() + "] : " + message);
	}
	
	public ASTExpression makeVariable(Token location, String name) {
		return new ASTVariable(location, 0, name);
	}

	public ASTExpression makeFunction(Token location, String name, ASTExpression args) {
		return new ASTFunction(location, name, args);
	}
	
	public void setExpression(ASTExpression exp) {
		
	}
}	
