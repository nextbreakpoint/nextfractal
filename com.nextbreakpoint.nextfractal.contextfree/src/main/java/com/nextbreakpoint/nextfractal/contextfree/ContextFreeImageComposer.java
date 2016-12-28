/*
 * NextFractal 2.0.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2017 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.contextfree;

import com.nextbreakpoint.nextfractal.contextfree.compiler.Compiler;
import com.nextbreakpoint.nextfractal.contextfree.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDG;
import com.nextbreakpoint.nextfractal.contextfree.renderer.Renderer;
import com.nextbreakpoint.nextfractal.core.ImageComposer;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.java2D.Java2DRendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.java2D.Java2DRendererGraphicsContext;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.nio.IntBuffer;
import java.util.concurrent.ThreadFactory;

public class ContextFreeImageComposer implements ImageComposer {
	private boolean aborted;
	private boolean opaque;
	private RendererTile tile;
	private ThreadFactory threadFactory;

	public ContextFreeImageComposer(ThreadFactory threadFactory, RendererTile tile, boolean opaque) {
		this.tile = tile;
		this.opaque = opaque;
		this.threadFactory = threadFactory;
	}

	@Override
	public IntBuffer renderImage(String script, Object data) {
		ContextFreeMetadata metadata = (ContextFreeMetadata)data;
		RendererSize suggestedSize = tile.getTileSize();
		BufferedImage image = new BufferedImage(suggestedSize.getWidth(), suggestedSize.getHeight(), BufferedImage.TYPE_INT_ARGB);
		IntBuffer buffer = IntBuffer.wrap(((DataBufferInt)image.getRaster().getDataBuffer()).getData());
		Graphics2D g2d = null;
		try {
			g2d = image.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			Compiler compiler = new Compiler();
			CompilerReport report = compiler.compileReport(script);
			if (report.getErrors().size() > 0) {
				throw new RuntimeException("Failed to compile source");
			}
			CFDG cfdg = report.getCFDG();
			Java2DRendererFactory renderFactory = new Java2DRendererFactory();
			Renderer renderer = new Renderer(threadFactory, renderFactory, tile);
			renderer.setSeed(metadata.getSeed());
			renderer.setOpaque(opaque);
			renderer.setCFDG(cfdg);
			renderer.setSeed(metadata.getSeed());
			renderer.init();
			renderer.runTask();
			renderer.waitForTasks();
			renderer.copyImage(new Java2DRendererGraphicsContext(g2d));
			aborted = renderer.isInterrupted();
		} catch (Exception e) {
			//TODO display errors
		} finally {
			if (g2d != null) {
				g2d.dispose();
			}
		}
		return buffer;
	}

	@Override
	public RendererSize getSize() {
		return tile.getTileSize();
	}
	
	@Override
	public boolean isInterrupted() {
		return aborted;
	}
}
