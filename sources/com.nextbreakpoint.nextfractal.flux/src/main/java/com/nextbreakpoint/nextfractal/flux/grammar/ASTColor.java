package com.nextbreakpoint.nextfractal.flux.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

public class ASTColor extends ASTObjectImpl {
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

	public void addPalette(ASTPalette palette) {
		palettes.add(palette);
	}
	
	public List<ASTRule> getRules() {
		return rules;
	}

	public void addRule(ASTRule rule) {
		rules.add(rule);
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
		builder.append(",palettes = [");
		for (int i = 0; i < palettes.size(); i++) {
			ASTPalette palette = palettes.get(i);
			builder.append("{");
			builder.append(palette);
			builder.append("}");
			if (i < palettes.size() - 1) {
				builder.append(",");
			}
		}
		builder.append("]");
		builder.append(",rules = [");
		for (int i = 0; i < rules.size(); i++) {
			ASTRule rule = rules.get(i);
			builder.append("{");
			builder.append(rule);
			builder.append("}");
			if (i < rules.size() - 1) {
				builder.append(",");
			}
		}
		builder.append("]");
		return builder.toString();
	}
}