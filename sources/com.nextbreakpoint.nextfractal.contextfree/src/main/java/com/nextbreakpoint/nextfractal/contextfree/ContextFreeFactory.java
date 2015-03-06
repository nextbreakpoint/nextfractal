package com.nextbreakpoint.nextfractal.contextfree;

import java.util.concurrent.ThreadFactory;

import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.FractalFactory;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.session.Session;

public class ContextFreeFactory implements FractalFactory {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#getId()
	 */
	public String getId() {
		return "ContextFree";
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createSession(com.nextbreakpoint.nextfractal.ExportService)
	 */
	@Override
	public Session createSession() {
//		MandelbrotSession session = new MandelbrotSession();
//        session.setSource(getInitialSource());
//		return session;
		return null;
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createEditorPane(com.nextbreakpoint.nextfractal.core.session.Session)
	 */
	@Override
	public Pane createEditorPane(Session session) {
		return null;//new MandelbrotEditorPane(session);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createRenderPane(com.nextbreakpoint.nextfractal.core.session.Session, int, int)
	 */
	@Override
	public Pane createRenderPane(Session session, int width, int height) {
		return null;//new MandelbrotRenderPane(session, width, height, 1, 1);
	}

	protected String getInitialSource() {
		String source = "";
		return source;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createImageGenerator(java.util.concurrent.ThreadFactory, com.nextbreakpoint.nextfractal.core.renderer.RendererFactory, com.nextbreakpoint.nextfractal.render.mandelbrot.renderer.RendererTile)
	 */
	@Override
	public ImageGenerator createImageGenerator(ThreadFactory threadFactory,	RendererFactory renderFactory, RendererTile tile) {
		return null;//new MandelbrotImageGenerator(threadFactory, renderFactory, tile);
	}
}
