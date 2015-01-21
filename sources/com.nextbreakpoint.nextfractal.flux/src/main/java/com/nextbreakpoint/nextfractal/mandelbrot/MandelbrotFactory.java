package com.nextbreakpoint.nextfractal.mandelbrot;

import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.FractalFactory;
import com.nextbreakpoint.nextfractal.FractalSession;
import com.nextbreakpoint.nextfractal.mandelbrot.javaFX.MandelbrotEditorPane;
import com.nextbreakpoint.nextfractal.mandelbrot.javaFX.MandelbrotRenderPane;

public class MandelbrotFactory implements FractalFactory {
	/**
	 * @see com.nextbreakpoint.nextfractal.FractalFactory#getId()
	 */
	public String getId() {
		return "Mandelbrot";
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.FractalFactory#createSession()
	 */
	@Override
	public FractalSession createSession() {
		return new MandelbrotSession();
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.FractalFactory#createEditorPane(com.nextbreakpoint.nextfractal.FractalSession)
	 */
	@Override
	public Pane createEditorPane(FractalSession session) {
		return new MandelbrotEditorPane(session);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.FractalFactory#createRenderPane(com.nextbreakpoint.nextfractal.FractalSession, int, int)
	 */
	@Override
	public Pane createRenderPane(FractalSession session, int width, int height) {
		return new MandelbrotRenderPane(session, width, height, 2, 2);
	}
}
