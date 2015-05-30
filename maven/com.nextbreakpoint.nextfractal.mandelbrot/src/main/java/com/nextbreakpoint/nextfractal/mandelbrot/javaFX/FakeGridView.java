package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

public class FakeGridView extends GridView {
	public FakeGridView(int numOfRows, int numOfColumns, int cellSize) {
		super(numOfRows, numOfColumns, cellSize);
	}

	@Override
	protected GridViewCell createCell(int index) {
		return new FakeGridViewCell(index, cellSize, cellSize);
	}
}
