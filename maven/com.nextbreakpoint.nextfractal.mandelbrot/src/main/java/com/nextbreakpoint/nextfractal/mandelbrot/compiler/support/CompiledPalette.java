package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import java.util.List;

public class CompiledPalette {
	private String name;
	private List<CompiledPaletteElement> elements;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CompiledPaletteElement> getElements() {
		return elements;
	}

	public void setElements(List<CompiledPaletteElement> elements) {
		this.elements = elements;
	}
}
