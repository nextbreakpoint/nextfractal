package com.nextbreakpoint.nextfractal.contextfree.core;

public class ShapeType {
	private String name;
	private int type;
	private boolean hasRules;
	public static int TYPE_NEW = -1;
	public static int TYPE_PATH = 1;
	public static int TYPE_RULE = 2;
	public static int TYPE_LOOP_START = 3;
	public static int TYPE_LOOP_END = 4;

	public ShapeType(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public final int getType() {
		return type;
	}

	public final boolean hasRules() {
		return hasRules;
	}

	public void setHasRules(boolean hasRules) {
		this.hasRules = hasRules;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "EShapeType [name=" + name + ", type=" + type + ", hasRules=" + hasRules + "]";
	}
}
