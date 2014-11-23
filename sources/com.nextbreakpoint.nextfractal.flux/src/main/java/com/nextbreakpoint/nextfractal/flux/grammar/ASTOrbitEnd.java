package com.nextbreakpoint.nextfractal.flux.grammar;

import java.util.List;

import org.antlr.v4.runtime.Token;

class ASTOrbitEnd extends ASTObject {
	private List<ASTStatement> statements; 

	public ASTOrbitEnd(Token location) {
		super(location);
	}

	public List<ASTStatement> getStatements() {
		return statements;
	}
}