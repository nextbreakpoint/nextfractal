/*
 * NextFractal 1.1.2
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.contextfree.core;

import java.util.ArrayList;
import java.util.List;

public class CFRule extends CFRuleSpecifier {
	private CFPath path;
	private List<CFPathAttribute> attributes = new ArrayList<CFPathAttribute>();
	private List<CFReplacement> replacements = new ArrayList<CFReplacement>();
	
	public CFRule(int initialShapeType, double weight) {
		super(initialShapeType, weight);
	}

	public boolean hasPath() {
		return path != null;
	}
	
	public final CFPath getPath() {
		return path;
	}

	public final void setPath(CFPath path) {
		this.path = path;
	}

	public void addReplacement(CFReplacement replacement) {
		replacements.add(replacement);
	}
	
	public void removeReplacement(CFReplacement replacement) {
		replacements.remove(replacement);
	}
	
	public CFReplacement getReplacement(int index) {
		return replacements.get(index);
	}
	
	public int getReplacementCount() {
		return replacements.size();
	}

	public void addAttribute(CFPathAttribute attribute) {
		attributes.add(attribute);
	}
	
	public void removeAttribute(CFPathAttribute attribute) {
		attributes.remove(attribute);
	}
	
	public CFPathAttribute getAttribute(int index) {
		return attributes.get(index);
	}
	
	public int getAttributeCount() {
		return attributes.size();
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "CFRule [initialShapeType=" + initialShapeType + ", weight=" + weight + ", path=" + path + ", attributes=" + attributes + ", replacements=" + replacements + "]";
	}
}
