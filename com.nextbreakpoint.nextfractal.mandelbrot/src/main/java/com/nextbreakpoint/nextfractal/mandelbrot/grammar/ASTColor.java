/*
 * NextFractal 1.2.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

public class ASTColor extends ASTObject {
	private List<ASTPalette> palettes = new ArrayList<>(); 
	private List<ASTRule> rules = new ArrayList<>(); 
	private ASTColorInit colorInit;
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

	public void setInit(ASTColorInit colorInit) {
		this.colorInit = colorInit;
	}

	public ASTColorInit getInit() {
		return colorInit;
	}

	@Override
	public String toString() {
		StringBuilder driver = new StringBuilder();
		driver.append("argb = ");
		driver.append(argb);
		driver.append(",palettes = [");
		for (int i = 0; i < palettes.size(); i++) {
			ASTPalette palette = palettes.get(i);
			driver.append("{");
			driver.append(palette);
			driver.append("}");
			if (i < palettes.size() - 1) {
				driver.append(",");
			}
		}
		driver.append("]");
		driver.append(",init = {");
		if (colorInit != null) {
			driver.append(colorInit);
		}
		driver.append("}");
		driver.append(",rules = [");
		for (int i = 0; i < rules.size(); i++) {
			ASTRule rule = rules.get(i);
			driver.append("{");
			driver.append(rule);
			driver.append("}");
			if (i < rules.size() - 1) {
				driver.append(",");
			}
		}
		driver.append("]");
		return driver.toString();
	}
}
