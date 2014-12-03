package com.nextbreakpoint.nextfractal.flux.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

public class ASTOrbitLoop extends ASTObject {
	private int begin;
	private int end;
	private ASTConditionExpression expression;
	private List<ASTStatement> statements = new ArrayList<>(); 

	public ASTOrbitLoop(Token location, int begin, int end, ASTConditionExpression expression) {
		super(location);
		this.begin = begin;
		this.end = end;
		this.expression = expression;
	}

	public ASTConditionExpression getExpression() {
		return expression;
	}

	public void setExpression(ASTConditionExpression expression) {
		this.expression = expression;
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
		builder.append("begin = ");
		builder.append(begin);
		builder.append(",");
		builder.append("end = ");
		builder.append(end);
		builder.append(",");
		builder.append("expression = [");
		builder.append(expression);
		builder.append("],");
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