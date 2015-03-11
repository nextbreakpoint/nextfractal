package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

public class ASTPaletteElement extends ASTObject {
	private ASTColorARGB beginColor; 
	private ASTColorARGB endColor; 
	private int steps; 
	private ASTExpression exp;
	
	public ASTPaletteElement(Token location, ASTColorARGB beginColor, ASTColorARGB endColor, int steps, ASTExpression exp) {
		super(location);
		this.beginColor = beginColor;
		this.endColor = endColor;
		this.steps = steps;
		this.exp = exp;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("beginColor = ");
		builder.append(beginColor);
		builder.append(",endColor = ");
		builder.append(endColor);
		builder.append(",steps = ");
		builder.append(steps);
		builder.append(",exp = {");
		builder.append(exp);
		builder.append("}");
		return builder.toString();
	}

	public int getSteps() {
		return steps;
	}

	public ASTColorARGB getBeginColor() {
		return beginColor;
	}

	public ASTColorARGB getEndColor() {
		return endColor;
	}

	public ASTExpression getExp() {
		return exp;
	}
}
