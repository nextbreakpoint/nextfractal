package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

public interface GridViewDelegate {
	public void didRangeChange(GridView source, int firstRow, int lastRow);

	public void didSelectionChange(GridView source, int selectedRow, int selectedCol);
}
