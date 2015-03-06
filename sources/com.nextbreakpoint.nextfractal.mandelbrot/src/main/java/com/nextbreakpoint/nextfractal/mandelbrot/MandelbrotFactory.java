package com.nextbreakpoint.nextfractal.mandelbrot;

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
		String source = ""
				+ "fractal {\n"
				+ "\torbit [-2.5 - 1.5i,+0.5 + 1.5i] [z,n] {\n"
				+ "\t\tbegin {\n"
				+ "\t\t\tz = x;\n"
				+ "\t\t}\n"
				+ "\t\tloop [0, 200] (|pow(re(z),2)+pow(im(z),2)| > 4) {\n"
				+ "\t\t\tz = z * z + w;\n"
				+ "\t\t}\n"
				+ "\t}\n\tcolor [#FF000000] {\n"
				+ "\t\trule (re(n) = 0) [1.0] {\n"
				+ "\t\t\t1,0,0,0\n"
				+ "\t\t}\n"
				+ "\t\trule (re(n) > 0) [1.0] {\n"
				+ "\t\t\t1,1,1,1\n"
				+ "\t\t}\n"
				+ "\t}\n"
				+ "}\n";
		return source;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createImageGenerator(java.util.concurrent.ThreadFactory, com.nextbreakpoint.nextfractal.core.renderer.RendererFactory, com.nextbreakpoint.nextfractal.core.renderer.RendererTile)
	 */
	@Override
	public ImageGenerator createImageGenerator(ThreadFactory threadFactory,	RendererFactory renderFactory, RendererTile tile) {
		return new MandelbrotImageGenerator(threadFactory, renderFactory, tile);
	}
}
