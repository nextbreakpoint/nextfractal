package com.nextbreakpoint.nextfractal.core;

import java.util.concurrent.ThreadFactory;

import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.session.Session;

public interface FractalFactory {
	/**
	 * @return
	 */
	public String getId();

	/**
	 * @return
	 */
	public Session createSession();
	
	/**
	 * @param session
	 * @return
	 */
	public Pane createEditorPane(Session session);

	/**
	 * @param session
	 * @param width
	 * @param height
	 * @return
	 */
	public Pane createRenderPane(Session session, int width, int height);

	/**
	 * @param threadFactory
	 * @param renderFactory
	 * @param tile
	 * @return
	 */
	public ImageGenerator createImageGenerator(ThreadFactory threadFactory, RendererFactory renderFactory, RendererTile tile);
}
