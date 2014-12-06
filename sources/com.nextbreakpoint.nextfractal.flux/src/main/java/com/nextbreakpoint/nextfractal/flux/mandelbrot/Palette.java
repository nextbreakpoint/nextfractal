package com.nextbreakpoint.nextfractal.flux.mandelbrot;

import java.util.ArrayList;
import java.util.List;

public class Palette {
	private List<PaletteElement> elements = new ArrayList<>();
	private float[][] table;
	
	public Palette(int size) {
		table = new float[size][4];
	}
	
	public int getSize() {
		return table.length;
	}

	public float[] get(double n) {
		return table[((int)Math.rint(n)) % table.length];
	}

	public Palette build() {
		//TODO build
		for (int i = 0; i < table.length; i++) {
			table[i] = new float[] { 1, 1, 0, 0 };
		}
		return this;
	}

	public Palette add(PaletteElement element) {
		elements.add(element);
		return this;
	}
}
