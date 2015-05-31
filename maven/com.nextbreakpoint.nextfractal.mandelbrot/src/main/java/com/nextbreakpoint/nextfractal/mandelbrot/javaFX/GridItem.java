package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.io.File;

import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;

public class GridItem {
	public volatile String astColor;
	public volatile String astOrbit;
	public volatile CompilerBuilder<Color> colorBuilder;
	public volatile CompilerBuilder<Orbit> orbitBuilder;
	public volatile int col;
	public volatile int row;
	public volatile File file;
	public volatile MandelbrotData data;
	public volatile RendererCoordinator coordinator;
}