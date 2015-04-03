package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

import java.text.DecimalFormat;

import com.nextbreakpoint.nextfractal.core.utils.Colors;

public class ASTColorARGB {
	private static final DecimalFormat format = new DecimalFormat("0.##");
	private final float[] components;

	public ASTColorARGB(int argb) {
		components = Colors.color(argb);
	}

	public ASTColorARGB(float a, float r, float g, float b) {
		components = new float[] { a, r, g, b };
	}

	public float[] getComponents() {
		return components;
	}

	public int getARGB() {
		return Colors.toARGB(components);
	}

	@Override
	public String toString() {
		return "(" + format.format(components[0]) + "," + format.format(components[1]) + "," + format.format(components[2]) + "," + format.format(components[3]) + ")";
	}
}
