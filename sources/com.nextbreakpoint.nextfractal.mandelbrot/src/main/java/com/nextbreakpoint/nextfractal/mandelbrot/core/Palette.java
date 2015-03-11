package com.nextbreakpoint.nextfractal.mandelbrot.core;

import java.util.ArrayList;
import java.util.List;

public class Palette {
	private List<PaletteElement> elements = new ArrayList<>();
	private float[][] table;
	
	public Palette() {
	}
	
	public int getSize() {
		return table.length;
	}

	public float[] get(double n) {
		return table[((int)Math.rint(n)) % table.length];
	}

	public Palette build() {
		int size = 1;//TODO
		table = new float[size][4];
		//TODO build
		for (int i = 0; i < table.length; i++) {
			table[i] = new float[] { 1, 0, 0, 0 };
		}
		return this;
	}

	public Palette add(PaletteElement element) {
		elements.add(element);
		return this;
	}
}
