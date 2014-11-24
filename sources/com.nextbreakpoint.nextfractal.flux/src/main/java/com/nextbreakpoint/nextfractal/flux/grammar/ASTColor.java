package com.nextbreakpoint.nextfractal.flux.grammar;

import java.util.List;

import org.antlr.v4.runtime.Token;

public class ASTColor extends ASTObject {
	private List<ASTPalette> palettes; 
	private List<ASTRule> rules; 
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
}