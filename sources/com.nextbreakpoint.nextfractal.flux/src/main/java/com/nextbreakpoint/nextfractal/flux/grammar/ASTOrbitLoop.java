package com.nextbreakpoint.nextfractal.flux.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

class ASTOrbitLoop extends ASTObject {
	private int begin;
	private int end;
	private List<ASTStatement> statements; 

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
		if (statements == null) {
			statements = new ArrayList<ASTStatement>();
		}
		statements.add(statement);
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}
}