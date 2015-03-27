package com.nextbreakpoint.nextfractal.mandelbrot.core;

import java.util.ArrayList;
import java.util.List;

public class Palette {
	private static final float[] DEFAULT = new float[] { 1, 0, 0, 0 };
	private List<PaletteElement> elements = new ArrayList<>();
	private float[][] table;
	
	public Palette() {
	}
	
	public int getSize() {
		return table.length;
	}

	public float[] get(double n) {
		if (table != null) {
			return table[((int)Math.rint(n - 1)) % table.length];
		}
		return DEFAULT;
	}

	public Palette build() {
		int size = 0;
		for (PaletteElement element : elements) {
			size += element.getSteps();
		}
		if (size > 0) {
			table = new float[size][4];
			int i = 0;
			for (PaletteElement element : elements) {
				int steps = element.getSteps();
				for (int step = 0; step < steps; step++) {
					float[] bc = element.getBeginColor();
					float[] ec = element.getEndColor();
					double vc = element.getExpression().evaluate(0, steps, step);
					float a = (float)((ec[0] - bc[0]) * vc + bc[0]);
					float r = (float)((ec[1] - bc[1]) * vc + bc[1]);
					float g = (float)((ec[2] - bc[2]) * vc + bc[2]);
					float b = (float)((ec[3] - bc[3]) * vc + bc[3]);
					table[i++] = new float[] { a, r, g, b };
				}
			}
		}
		return this;
	}

	public Palette add(PaletteElement element) {
		elements.add(element);
		return this;
	}
}
