package com.nextbreakpoint.nextfractal.flux.mandelbrot.plugin;

import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.flux.FractalFactory;
import com.nextbreakpoint.nextfractal.flux.FractalParser;
import com.nextbreakpoint.nextfractal.flux.FractalSession;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.MandelbrotFractalSession;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.parser.MandelbrotFractalParser;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.ui.editor.MandelbrotEditorPane;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.ui.render.MandelbrotRenderPane;

public class MandelbrotFactory implements FractalFactory {
	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalFactory#getId()
	 */
	public String getId() {
		return "Mandelbrot";
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalFactory#createParser()
	 */
	@Override
	public FractalParser createParser() {
		return new MandelbrotFractalParser();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalFactory#createSession()
	 */
	@Override
	public FractalSession createSession() {
		return new MandelbrotFractalSession();
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
