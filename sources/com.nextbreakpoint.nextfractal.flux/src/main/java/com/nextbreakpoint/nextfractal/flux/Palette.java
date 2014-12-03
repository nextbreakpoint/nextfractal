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

	public float[] get(Number index) {
		return table[index.n() % table.length];
	}

	public void build() {
		//TODO build
	}

	public void add(PaletteElement element) {
		elements.add(element);
	}
}
