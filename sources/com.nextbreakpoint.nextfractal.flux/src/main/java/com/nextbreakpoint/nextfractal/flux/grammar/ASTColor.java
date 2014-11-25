package com.nextbreakpoint.nextfractal.flux.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

public class ASTColor extends ASTObject {
	private List<ASTPalette> palettes = new ArrayList<>(); 
	private List<ASTRule> rules = new ArrayList<>(); 
	private ASTColorARGB argb; 

	public ASTColor(Token location, ASTColorARGB argb) {
		super(location);
		this.argb = argb;
	}

	public List<ASTPalette> getPalettes() {
		return palettes;
	}

	public void setPalettes(List<ASTPalette> palettes) {
		this.palettes = palettes;
	}

	public List<ASTRule> getRules() {
		return rules;
	}

	public void setRules(List<ASTRule> rules) {
		this.rules = rules;
	}

	public ASTColorARGB getArgb() {
		return argb;
	}

	public void setArgb(ASTColorARGB argb) {
		this.argb = argb;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("argb = ");
		builder.append(argb);
		builder.append("\n");
		for (ASTPalette palette : palettes) {
			builder.append("palette = ");
			builder.append(palette);
			builder.append("\n");
		}
		for (ASTRule rule : rules) {
			builder.append("rule = ");
			builder.append(rule);
			builder.append("\n");
		}
		return builder.toString();
	}
}