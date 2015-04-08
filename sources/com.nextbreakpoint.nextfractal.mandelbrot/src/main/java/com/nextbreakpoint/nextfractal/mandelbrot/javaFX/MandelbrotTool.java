package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import javafx.scene.input.MouseEvent;

public interface MandelbrotTool {
	public void clicked(MouseEvent e);

	public void moved(MouseEvent e);

	public void dragged(MouseEvent e);

	public void released(MouseEvent e);

	public void pressed(MouseEvent e);

	public void update(long time);
}