package com.nextbreakpoint.nextfractal;

import java.util.concurrent.ThreadFactory;

import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.render.RenderFactory;

public interface FractalFactory {
	/**
	 * @return
	 */
	public String getId();

	/**
	 * @return
	 */
	public FractalSession createSession();
	
	/**
	 * @param session
	 * @return
	 */
	public Pane createEditorPane(FractalSession session);

	/**
	 * @param session
	 * @param width
	 * @param height
	 * @return
	 */
	public Pane createRenderPane(FractalSession session, int width, int height);

	/**
	 * @param threadFactory
	 * @param renderFactory
	 * @param tile
	 * @return
	 */
	public ImageGenerator createImageGenerator(ThreadFactory threadFactory, RenderFactory renderFactory, RendererTile tile);
}
