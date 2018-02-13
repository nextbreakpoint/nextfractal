/*
 * NextFractal 2.0.3
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2018 Andrea Medeghini
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

import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.Metadata;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.utils.Double2D;
import com.nextbreakpoint.nextfractal.core.utils.Double4D;
import com.nextbreakpoint.nextfractal.core.utils.Integer4D;
import com.nextbreakpoint.nextfractal.core.utils.Time;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.Renderer;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;

import java.nio.IntBuffer;
import java.util.concurrent.ThreadFactory;

public class MandelbrotImageGenerator implements ImageGenerator {
	private boolean aborted;
	private boolean opaque;
	private RendererTile tile;
	private ThreadFactory threadFactory;
	private RendererFactory renderFactory;

	public MandelbrotImageGenerator(ThreadFactory threadFactory, RendererFactory renderFactory, RendererTile tile, boolean opaque) {
		this.tile = tile;
		this.opaque = opaque;
		this.threadFactory = threadFactory;
		this.renderFactory = renderFactory;
	}

	@Override
	public IntBuffer renderImage(String script, Metadata data) {
		MandelbrotMetadata metadata = (MandelbrotMetadata) data;
		RendererSize suggestedSize = tile.getTileSize();
		int[] pixels = new int[suggestedSize.getWidth() * suggestedSize.getHeight()];
		IntBuffer buffer = IntBuffer.wrap(pixels);
		try {
			Compiler compiler = new Compiler(Compiler.class.getPackage().getName(), "Compile" + System.nanoTime());
			CompilerReport report = compiler.compileReport(script);
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
			renderer.setOpaque(opaque);
			Double4D translation = metadata.getTranslation();
			Double4D rotation = metadata.getRotation();
			Double4D scale = metadata.getScale();
			Double2D constant = metadata.getPoint();
			Time time = metadata.getTime();
			boolean julia = metadata.isJulia();
			renderer.setOrbit(orbitBuilder.build());
			renderer.setColor(colorBuilder.build());
			renderer.init();
			RendererView view = new RendererView();
			view .setTraslation(translation);
			view.setRotation(rotation);
			view.setScale(scale);
			view.setState(new Integer4D(0, 0, 0, 0));
			view.setJulia(julia);
			view.setPoint(new Number(constant.getX(), constant.getY()));
			renderer.setView(view);
			renderer.setTime(time);
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
