package com.nextbreakpoint.nextfractal.mandelbrot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadFactory;

import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.FractalFactory;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.session.Session;
import com.nextbreakpoint.nextfractal.mandelbrot.javaFX.MandelbrotEditorPane;
import com.nextbreakpoint.nextfractal.mandelbrot.javaFX.MandelbrotRenderPane;

public class MandelbrotFactory implements FractalFactory {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#getId()
	 */
	public String getId() {
		return "Mandelbrot";
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createSession(com.nextbreakpoint.nextfractal.ExportService)
	 */
	@Override
	public Session createSession() {
		MandelbrotSession session = new MandelbrotSession();
        session.setSource(getInitialSource());
		return session;
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createEditorPane(com.nextbreakpoint.nextfractal.core.session.Session)
	 */
	@Override
	public Pane createEditorPane(Session session) {
		return new MandelbrotEditorPane(session);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createRenderPane(com.nextbreakpoint.nextfractal.core.session.Session, int, int)
	 */
	@Override
	public Pane createRenderPane(Session session, int width, int height) {
		return new MandelbrotRenderPane(session, width, height, 1, 1);
	}

	protected String getInitialSource() {
		try {
			return getSource("source.m");
		} catch (IOException e) {
		}
		return "";
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createImageGenerator(java.util.concurrent.ThreadFactory, com.nextbreakpoint.nextfractal.core.renderer.RendererFactory, com.nextbreakpoint.nextfractal.core.renderer.RendererTile)
	 */
	@Override
	public ImageGenerator createImageGenerator(ThreadFactory threadFactory,	RendererFactory renderFactory, RendererTile tile) {
		return new MandelbrotImageGenerator(threadFactory, renderFactory, tile);
	}

	protected String getSource(String name) throws IOException {
		InputStream is = getClass().getResourceAsStream(name);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int length = 0;
		while ((length = is.read(buffer)) > 0) {
			baos.write(buffer, 0, length);
		}
		return baos.toString();
	}
}
