/*
 * NextFractal 1.1.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot;

import java.nio.IntBuffer;
import java.util.concurrent.ThreadFactory;

import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.utils.Double4D;
import com.nextbreakpoint.nextfractal.core.utils.Integer4D;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.Renderer;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;

public class MandelbrotImageGenerator implements ImageGenerator {
	private boolean aborted;
	private RendererTile tile;
	private ThreadFactory threadFactory;
	private RendererFactory renderFactory;

	public MandelbrotImageGenerator(ThreadFactory threadFactory, RendererFactory renderFactory, RendererTile tile) {
		this.tile = tile;
		this.threadFactory = threadFactory;
		this.renderFactory = renderFactory;
	}

	@Override
	public IntBuffer renderImage(Object data) {
		MandelbrotData generatorData = (MandelbrotData)data;
		RendererSize suggestedSize = tile.getTileSize();
		int[] pixels = new int[suggestedSize.getWidth() * suggestedSize.getHeight()];
		IntBuffer buffer = IntBuffer.wrap(pixels);
		try {
			Compiler compiler = new Compiler();
			CompilerReport report = compiler.compileReport(generatorData.getSource());
			if (report.getErrors().size() > 0) {
				throw new RuntimeException("Failed to compile source");
			}
			CompilerBuilder<Orbit> orbitBuilder = compiler.compileOrbit(report);
			if (orbitBuilder.getErrors().size() > 0) {
				throw new RuntimeException("Failed to compile Orbit class");
			}
			CompilerBuilder<Color> colorBuilder = compiler.compileColor(report);
			if (colorBuilder.getErrors().size() > 0) {
				throw new RuntimeException("Failed to compile Color class");
			}
			Renderer renderer = new Renderer(threadFactory, renderFactory, tile);
			double[] traslation = generatorData.getTraslation();
			double[] rotation = generatorData.getRotation();
			double[] scale = generatorData.getScale();
			double[] constant = generatorData.getPoint();
			boolean julia = generatorData.isJulia();
			renderer.setOrbit(orbitBuilder.build());
			renderer.setColor(colorBuilder.build());
			renderer.init();
			RendererView view = new RendererView();
			view .setTraslation(new Double4D(traslation));
			view.setRotation(new Double4D(rotation));
			view.setScale(new Double4D(scale));
			view.setState(new Integer4D(0, 0, 0, 0));
			view.setJulia(julia);
			view.setPoint(new Number(constant));
			renderer.setView(view);
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
