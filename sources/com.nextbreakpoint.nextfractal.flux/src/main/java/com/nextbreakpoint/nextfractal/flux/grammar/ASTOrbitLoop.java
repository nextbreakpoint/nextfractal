package com.nextbreakpoint.nextfractal.flux.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

public class ASTOrbitLoop extends ASTObject {
	private int begin;
	private int end;
	private List<ASTStatement> statements = new ArrayList<>(); 

	public ASTOrbitLoop(Token location, int begin, int end) {
		super(location);
		this.begin = begin;
		this.end = end;
	}

	public ASTOrbitLoop(Token location, String begin, String end) {
		super(location);
		this.begin = Integer.parseInt(begin);
		this.end = Integer.parseInt(end);
	}

	public List<ASTStatement> getStatements() {
		return statements;
	}

	public void addStatement(ASTStatement statement) {
		statements.add(statement);
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
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