package com.nextbreakpoint.nextfractal.flux.mandelbrot;

import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.flux.FractalFactory;
import com.nextbreakpoint.nextfractal.flux.FractalSession;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.ui.MandelbrotEditorPane;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.ui.MandelbrotRenderPane;

public class MandelbrotFactory implements FractalFactory {
	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalFactory#getId()
	 */
	public String getId() {
		return "Mandelbrot";
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalFactory#createSession()
	 */
	@Override
	public FractalSession createSession() {
		return new MandelbrotSession();
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalFactory#createEditorPane(com.nextbreakpoint.nextfractal.flux.FractalSession)
	 */
	@Override
	public Pane createEditorPane(FractalSession session) {
		return new MandelbrotEditorPane(session);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalFactory#createRenderPane(com.nextbreakpoint.nextfractal.flux.FractalSession, int, int)
	 */
	@Override
	public Pane createRenderPane(FractalSession session, int width, int height) {
		return new MandelbrotRenderPane(session, width, height);
	}
}
