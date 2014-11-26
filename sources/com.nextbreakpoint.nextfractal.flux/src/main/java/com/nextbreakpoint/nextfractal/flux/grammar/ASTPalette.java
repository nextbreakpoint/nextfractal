package com.nextbreakpoint.nextfractal.flux.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

public class ASTPalette extends ASTObject {
	private String name;
	private int length;
	private List<ASTPaletteElement> elements = new ArrayList<>(); 

	public ASTPalette(Token location, String name, int length) {
		super(location);
		this.name = name;
		this.length = length;
	}

	public String getName() {
		return name;
	}

	public int getLength() {
		return length;
	}

	public List<ASTPaletteElement> getElements() {
		return elements;
	}

	public void addElements(ASTPaletteElement element) {
		elements.add(element);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("name = ");
		builder.append(name);
		builder.append(",length = ");
		builder.append(length);
		builder.append(",elements = [");
		for (int i = 0; i < elements.size(); i++) {
			ASTPaletteElement statement = elements.get(i);
			builder.append("{");
			builder.append(statement);
			builder.append("}");
			if (i < elements.size() - 1) {
				builder.append(",");
			}
		}
		builder.append("]");
		return builder.toString();
	}
}