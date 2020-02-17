/*
 * NextFractal 2.1.2-rc1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2020 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.contextfree.module;

import com.nextbreakpoint.nextfractal.contextfree.compiler.Compiler;
import com.nextbreakpoint.nextfractal.contextfree.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDG;
import com.nextbreakpoint.nextfractal.contextfree.renderer.Renderer;
import com.nextbreakpoint.nextfractal.core.common.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.common.Metadata;
import com.nextbreakpoint.nextfractal.core.render.RendererFactory;
import com.nextbreakpoint.nextfractal.core.render.RendererSize;
import com.nextbreakpoint.nextfractal.core.render.RendererTile;

import java.nio.IntBuffer;
import java.util.concurrent.ThreadFactory;

public class ContextFreeImageGenerator implements ImageGenerator {
	private boolean aborted;
	private boolean opaque;
	private RendererTile tile;
	private ThreadFactory threadFactory;
	private RendererFactory renderFactory;

	public ContextFreeImageGenerator(ThreadFactory threadFactory, RendererFactory renderFactory, RendererTile tile, boolean opaque) {
		this.tile = tile;
		this.opaque = opaque;
		this.threadFactory = threadFactory;
		this.renderFactory = renderFactory;
	}

	@Override
	public IntBuffer renderImage(String script, Metadata data) {
		ContextFreeMetadata metadata = (ContextFreeMetadata)data;
		RendererSize suggestedSize = tile.getTileSize();
		int[] pixels = new int[suggestedSize.getWidth() * suggestedSize.getHeight()];
		for (int i = 0; i < pixels.length; i++) pixels[i] = 0xFF000000;
		IntBuffer buffer = IntBuffer.wrap(pixels);
		try {
			Compiler compiler = new Compiler();
			CompilerReport report = compiler.compileReport(script);
			if (report.getErrors().size() > 0) {
				throw new RuntimeException("Failed to compile source");
			}
			CFDG cfdg = report.getCFDG();
			Renderer renderer = new Renderer(threadFactory, renderFactory, tile);
			renderer.setSeed(metadata.getSeed());
			renderer.setOpaque(opaque);
			renderer.setCFDG(cfdg);
			renderer.setSeed(metadata.getSeed());
			renderer.init();
			renderer.runTask();
			renderer.waitForTasks();
			renderer.getPixels(pixels);
			aborted = renderer.isInterrupted();
		} catch (Exception e) {
			//TODO display errors
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
