package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import javafx.scene.image.Image;

public interface RenderableImage {
	public Image getImage();

	public boolean hasChanged();

	public void setDelegate(RenderableImageDelegate delegate);

	public void start();

	public void abort();

	public void waitFor();

	public boolean isStarted();
}
