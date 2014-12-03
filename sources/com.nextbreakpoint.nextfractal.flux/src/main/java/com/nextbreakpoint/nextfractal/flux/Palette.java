package com.nextbreakpoint.nextfractal.flux;

import java.util.ArrayList;
import java.util.List;

public class Palette {
	private List<PaletteElement> elements = new ArrayList<>();
	private float[][] table;
	
	public Palette(int size) {
		table = new float[4][size];
	}
	
	public int getSize() {
		return table.length;
	}

	public float[] get(double n) {
		return table[((int)Math.rint(n)) % table.length];
	}

	public void build() {
		//TODO build
	}

	public Palette add(PaletteElement element) {
		elements.add(element);
		return this;
	}
}
