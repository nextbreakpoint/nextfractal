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
