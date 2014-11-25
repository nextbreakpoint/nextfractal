package com.nextbreakpoint.nextfractal.flux.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

public class ASTOrbitEnd extends ASTObject {
	private List<ASTStatement> statements = new ArrayList<>(); 

	public ASTOrbitEnd(Token location) {
		super(location);
	}

	public List<ASTStatement> getStatements() {
		return statements;
	}

	public void addStatement(ASTStatement statement) {
		statements.add(statement);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (ASTStatement statement : statements) {
			builder.append("statement = ");
			builder.append(statement);
			builder.append("\n");
		}
		return builder.toString();
	}
}