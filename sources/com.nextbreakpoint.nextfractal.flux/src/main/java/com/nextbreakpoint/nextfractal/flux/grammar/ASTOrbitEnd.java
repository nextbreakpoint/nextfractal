package com.nextbreakpoint.nextfractal.flux.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

public class ASTOrbitEnd extends ASTObject {
	private List<ASTStatement> statements; 

	public ASTOrbitEnd(Token location) {
		super(location);
	}

	public List<ASTStatement> getStatements() {
		return statements;
	}

	public void addStatement(ASTStatement statement) {
		if (statements == null) {
			statements = new ArrayList<ASTStatement>();
		}
		statements.add(statement);
	}
}