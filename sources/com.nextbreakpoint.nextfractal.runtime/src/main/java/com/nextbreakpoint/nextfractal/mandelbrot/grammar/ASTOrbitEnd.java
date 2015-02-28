package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

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
		builder.append("statements = [");
		for (int i = 0; i < statements.size(); i++) {
			ASTStatement statement = statements.get(i);
			builder.append("{");
			builder.append(statement);
			builder.append("}");
			if (i < statements.size() - 1) {
				builder.append(",");
			}
		}
		builder.append("]");
		return builder.toString();
	}
}