package com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

public class ASTRule extends ASTObject {
	private ASTRuleExpression ruleExp;
	private ASTColorExpression colorExp;
	private float opacity;

	public ASTRule(Token location, float opacity, ASTRuleExpression ruleExp, ASTColorExpression colorExp) {
		super(location);
		this.opacity = opacity;
		this.ruleExp = ruleExp;
		this.colorExp = colorExp;
	}

	public ASTRuleExpression getRuleExp() {
		return ruleExp;
	}

	public ASTColorExpression getColorExp() {
		return colorExp;
	}

	public float getOpacity() {
		return opacity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("opacity = ");
		builder.append(opacity);
		builder.append(",ruleExp = {");
		builder.append(ruleExp);
		builder.append("},colorExp = {");
		builder.append(colorExp);
		builder.append("}");
		return builder.toString();
	}
}