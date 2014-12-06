package com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

public class ASTPaletteElement extends ASTObject {
	private int beginIndex; 
	private int endIndex; 
	private ASTColorARGB beginColor; 
	private ASTColorARGB endColor; 
	private ASTExpression exp;
	
	public ASTPaletteElement(Token location, int beginIndex, int endIndex, ASTColorARGB beginColor, ASTColorARGB endColor, ASTExpression exp) {
		super(location);
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
		this.beginColor = beginColor;
		this.endColor = endColor;
		this.exp = exp;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("beginIndex = ");
		builder.append(beginIndex);
		builder.append(",endIndex = ");
		builder.append(endIndex);
		builder.append(",beginColor = ");
		builder.append(beginColor);
		builder.append(",endColor = ");
		builder.append(endColor);
		builder.append(",exp = {");
		builder.append(exp);
		builder.append("}");
		return builder.toString();
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public int getEndIndex() {
		return endIndex;
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
