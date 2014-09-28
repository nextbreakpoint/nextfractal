/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
 *
 * This file is based on code written by Jan Hubicka and Thomas Marsh (http://xaos.sf.net).
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
package com.nextbreakpoint.nextfractal.mandelbrot.renderer;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.nextbreakpoint.nextfractal.core.math.Complex;
import com.nextbreakpoint.nextfractal.core.util.Colors;
import com.nextbreakpoint.nextfractal.core.util.RenderWorker;
import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaRuntimeElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaRuntimeElement;

/**
 * @author Andrea Medeghini
 */
public final class FastXaosMandelbrotRenderer extends AbstractMandelbrotRenderer {
	private static final boolean DUMP = false;
	private static final boolean DUMP_XAOS = false;
	private static final boolean SHOW_SYMETRY = false;
	private static final boolean SHOW_CALCULATE = false;
	private static final boolean PRINT_REALLOCTABLE = false;
	private static final boolean PRINT_CALCULATE = false;
	private static final boolean PRINT_POSITIONS = false;
	private static final boolean PRINT_MOVETABLE = false;
	private static final boolean PRINT_FILLTABLE = false;
	private static final boolean PRINT_MULTABLE = false;
	private static final boolean PRINT_REGION = false;
	private static final boolean USE_XAOS = true;
	private static final boolean USE_SYMETRY = true;
	private static final boolean USE_MULTITHREAD = true;
	private static final int RANGES = 2;
	private static final int RANGE = 4;
	private static final int STEPS = 8;
	private static final int MASK = 0x7;
	private static final int DSIZE = (FastXaosMandelbrotRenderer.RANGES + 1);
	private static final int FPMUL = 64;
	private static final int FPRANGE = FastXaosMandelbrotRenderer.FPMUL * FastXaosMandelbrotRenderer.RANGE;
	private static final int MAX_PRICE = Integer.MAX_VALUE;
	private static final int NEW_PRICE = FastXaosMandelbrotRenderer.FPRANGE * FastXaosMandelbrotRenderer.FPRANGE;
	private static final int[] multable = new int[FastXaosMandelbrotRenderer.FPRANGE * 2];
	static {
		for (int i = -FastXaosMandelbrotRenderer.FPRANGE; i < FastXaosMandelbrotRenderer.FPRANGE; i++) {
			FastXaosMandelbrotRenderer.multable[FastXaosMandelbrotRenderer.FPRANGE + i] = i * i;
		}
		if (FastXaosMandelbrotRenderer.PRINT_MULTABLE) {
			AbstractMandelbrotRenderer.logger.fine("Multable:");
			for (int i = -FastXaosMandelbrotRenderer.FPRANGE; i < FastXaosMandelbrotRenderer.FPRANGE; i++) {
				AbstractMandelbrotRenderer.logger.fine("i = " + i + ", i * i = " + FastXaosMandelbrotRenderer.multable[FastXaosMandelbrotRenderer.FPRANGE + i]);
			}
		}
	}
	private final RenderingStrategy mandelbrotRenderingStrategy = new MandelbrotRenderingStrategy();
	private final RenderingStrategy juliaRenderingStrategy = new JuliaRenderingStrategy();
	// private final PrepareColumnsWorker prepareColumnsWorker = new PrepareColumnsWorker();
	// private final PrepareLinesWorker prepareLinesWorker = new PrepareLinesWorker();
	private final MandelbrotWorker2 renderWorker2 = new MandelbrotWorker2();
	private final boolean isSymetryEnabled = true;
	private boolean isVerticalSymetrySupported = true;
	private boolean isHorizontalSymetrySupported = true;
	private boolean isAborted = false;
	private RendererData renderedData;

	/**
	 * @param threadPriority
	 */
	public FastXaosMandelbrotRenderer(final int threadPriority) {
		super(threadPriority);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer#start()
	 */
	@Override
	public void start() {
		// prepareLinesWorker.start();
		// prepareColumnsWorker.start();
		renderWorker2.start();
		super.start();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer#stop()
	 */
	@Override
	public void stop() {
		super.stop();
		// prepareLinesWorker.stop();
		// prepareColumnsWorker.stop();
		renderWorker2.stop();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.AbstractMandelbrotRenderer.core.fractal.renderer.AbstractFractalRenderer#free()
	 */
	protected void free() {
		super.free();
		if (renderedData != null) {
			renderedData.free();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.AbstractMandelbrotRenderer.core.fractal.renderer.AbstractFractalRenderer#init()
	 */
	protected void init() {
		super.init();
		renderedData = new RendererData();
		renderedData.reallocate(getBufferWidth(), getBufferHeight());
	}

	/**
	 * 
	 */
	protected void swapBuffers() {
		if (FastXaosMandelbrotRenderer.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Swap buffers...");
		}
		renderedData.swap();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.AbstractMandelbrotRenderer.core.fractal.renderer.AbstractFractalRenderer#doRender(boolean)
	 */
	protected void doRender(final boolean dynamic) {
		isAborted = false;
		if ((renderMode & MODE_REFRESH) != 0) {
			renderMode |= MODE_CALCULATE;
		}
		updateShift();
		updateRegion();
		updateTransform();
		renderingStrategy.updateParameters();
		if (fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) {
			fractalRuntime.getRenderingFormula().getFormulaRuntime().prepareForRendering(fractalRuntime.getProcessingFormula().getFormulaRuntime(), fractalRuntime.getOrbitTrap().getOrbitTrapRuntime());
			if (fractalRuntime.getOrbitTrap().getOrbitTrapRuntime() != null) {
				fractalRuntime.getOrbitTrap().getOrbitTrapRuntime().prepareForProcessing(fractalRuntime.getOrbitTrap().getCenter());
			}
		}
		for (int i = 0; i < fractalRuntime.getOutcolouringFormulaCount(); i++) {
			if (fractalRuntime.getOutcolouringFormula(i).getFormulaRuntime() != null) {
				if (fractalRuntime.getOutcolouringFormula(i).isAutoIterations() && (fractalRuntime.getRenderingFormula().getFormulaRuntime() != null)) {
					fractalRuntime.getOutcolouringFormula(i).getFormulaRuntime().prepareForRendering(fractalRuntime.getRenderingFormula().getFormulaRuntime(), fractalRuntime.getRenderingFormula().getFormulaRuntime().getIterations());
				}
				else {
					fractalRuntime.getOutcolouringFormula(i).getFormulaRuntime().prepareForRendering(fractalRuntime.getRenderingFormula().getFormulaRuntime(), fractalRuntime.getOutcolouringFormula(i).getIterations());
				}
			}
		}
		for (int i = 0; i < fractalRuntime.getIncolouringFormulaCount(); i++) {
			if (fractalRuntime.getIncolouringFormula(i).getFormulaRuntime() != null) {
				if (fractalRuntime.getIncolouringFormula(i).isAutoIterations() && (fractalRuntime.getRenderingFormula().getFormulaRuntime() != null)) {
					fractalRuntime.getIncolouringFormula(i).getFormulaRuntime().prepareForRendering(fractalRuntime.getRenderingFormula().getFormulaRuntime(), fractalRuntime.getRenderingFormula().getFormulaRuntime().getIterations());
				}
				else {
					fractalRuntime.getIncolouringFormula(i).getFormulaRuntime().prepareForRendering(fractalRuntime.getRenderingFormula().getFormulaRuntime(), fractalRuntime.getIncolouringFormula(i).getIterations());
				}
			}
		}
		if (FastXaosMandelbrotRenderer.PRINT_REGION) {
			AbstractMandelbrotRenderer.logger.fine("Region: " + area.toString());
		}
		isVerticalSymetrySupported = FastXaosMandelbrotRenderer.USE_SYMETRY && isSymetryEnabled && isVerticalSymetrySupported();
		isHorizontalSymetrySupported = FastXaosMandelbrotRenderer.USE_SYMETRY && isSymetryEnabled && isHorizontalSymetrySupported();
		if (FastXaosMandelbrotRenderer.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Vertical symetry supported = " + isVerticalSymetrySupported);
			AbstractMandelbrotRenderer.logger.fine("Horizontal symetry supported = " + isHorizontalSymetrySupported);
		}
		if (FastXaosMandelbrotRenderer.USE_MULTITHREAD && !FastXaosMandelbrotRenderer.DUMP_XAOS) {
			// prepareLinesWorker.executeTask();
			// prepareColumnsWorker.executeTask();
			// prepareLinesWorker.waitTasks();
			// prepareColumnsWorker.waitTasks();
			renderWorker2.executeTask();
			prepareLines();
			renderWorker2.waitTasks();
		}
		else {
			prepareLines();
			prepareColumns();
		}
		if (FastXaosMandelbrotRenderer.PRINT_REALLOCTABLE) {
			AbstractMandelbrotRenderer.logger.fine("ReallocTable:");
			for (final Realloc element : renderedData.reallocX) {
				AbstractMandelbrotRenderer.logger.fine(element.toString());
			}
			AbstractMandelbrotRenderer.logger.fine("ReallocTable:");
			for (final Realloc element : renderedData.reallocY) {
				AbstractMandelbrotRenderer.logger.fine(element.toString());
			}
		}
		swapBuffers();
		move();
		processReallocTable(dynamic, false);
		updatePosition();
		renderMode = 0;
	}

	private void prepareLines() {
		final double beginy = area.points[0].i;
		final double endy = area.points[1].i;
		double stepy = 0;
		if (((renderMode & MandelbrotRenderer.MODE_CALCULATE) == 0) && FastXaosMandelbrotRenderer.USE_XAOS) {
			stepy = FastXaosMandelbrotRenderer.makeReallocTable(renderedData.reallocY, renderedData.dynamicy, beginy, endy, renderedData.positionY, false);
		}
		else {
			stepy = FastXaosMandelbrotRenderer.initReallocTableAndPosition(renderedData.reallocY, renderedData.positionY, beginy, endy);
		}
		if ((fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) && (fractalRuntime.getTransformingFormula().getFormulaRuntime() != null)) {
			final double symy = fractalRuntime.getRenderingFormula().getFormulaRuntime().getVerticalSymetryPoint();
			if (isVerticalSymetrySupported && fractalRuntime.getRenderingFormula().getFormulaRuntime().isVerticalSymetryAllowed() && fractalRuntime.getTransformingFormula().getFormulaRuntime().isVerticalSymetryAllowed() && (!((beginy > symy) || (symy > endy)))) {
				FastXaosMandelbrotRenderer.prepareSymetry(renderedData.reallocY, (int) ((symy - beginy) / stepy), symy, stepy);
			}
		}
	}

	private void prepareColumns() {
		final double beginx = area.points[0].r;
		final double endx = area.points[1].r;
		double stepx = 0;
		if (((renderMode & MandelbrotRenderer.MODE_CALCULATE) == 0) && FastXaosMandelbrotRenderer.USE_XAOS) {
			stepx = FastXaosMandelbrotRenderer.makeReallocTable(renderedData.reallocX, renderedData.dynamicx, beginx, endx, renderedData.positionX, false);
		}
		else {
			stepx = FastXaosMandelbrotRenderer.initReallocTableAndPosition(renderedData.reallocX, renderedData.positionX, beginx, endx);
		}
		if ((fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) && (fractalRuntime.getTransformingFormula().getFormulaRuntime() != null)) {
			final double symx = fractalRuntime.getRenderingFormula().getFormulaRuntime().getHorizontalSymetryPoint();
			if (isHorizontalSymetrySupported && fractalRuntime.getRenderingFormula().getFormulaRuntime().isHorizontalSymetryAllowed() && fractalRuntime.getTransformingFormula().getFormulaRuntime().isHorizontalSymetryAllowed() && (!((beginx > symx) || (symx > endx)))) {
				FastXaosMandelbrotRenderer.prepareSymetry(renderedData.reallocX, (int) ((symx - beginx) / stepx), symx, stepx);
			}
		}
	}

	private static double initReallocTableAndPosition(final Realloc[] realloc, final double[] position, final double begin, final double end) {
		if (FastXaosMandelbrotRenderer.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Init ReallocTable and position...");
		}
		final double step = (end - begin) / realloc.length;
		double tmpPosition = begin;
		Realloc tmpRealloc = null;
		for (int i = 0; i < realloc.length; i++) {
			tmpRealloc = realloc[i];
			position[i] = tmpPosition;
			tmpRealloc.position = tmpPosition;
			tmpRealloc.recalculate = true;
			tmpRealloc.refreshed = false;
			tmpRealloc.dirty = true;
			tmpRealloc.plus = i;
			tmpRealloc.symTo = -1;
			tmpRealloc.symRef = -1;
			tmpPosition += step;
		}
		return step;
	}

	private void updatePosition() {
		if (FastXaosMandelbrotRenderer.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Update position...");
		}
		for (int k = 0; k < renderedData.reallocX.length; k++) {
			renderedData.positionX[k] = renderedData.reallocX[k].position;
		}
		for (int k = 0; k < renderedData.reallocY.length; k++) {
			renderedData.positionY[k] = renderedData.reallocY[k].position;
		}
	}

	private static int price(final int p1, final int p2) {
		return FastXaosMandelbrotRenderer.multable[(FastXaosMandelbrotRenderer.FPRANGE + p1) - p2];
	}

	private static void addPrices(final Realloc[] realloc, int r1, final int r2) {
		// if (r1 < r2)
		while (r1 < r2) {
			final int r3 = r1 + ((r2 - r1) >> 1);
			realloc[r3].priority = (realloc[r2].position - realloc[r3].position) * realloc[r3].priority;
			if (realloc[r3].symRef != -1) {
				realloc[r3].priority /= 2.0;
			}
			FastXaosMandelbrotRenderer.addPrices(realloc, r1, r3);
			// XaosFractalRenderer.addPrices(realloc, r3 + 1, r2);
			r1 = r3 + 1;
		}
	}

	private static void prepareSymetry(final Realloc[] realloc, final int symi, double symPosition, final double step) {
		if (FastXaosMandelbrotRenderer.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Prepare symetry...");
		}
		int i = 0;
		int j = 0;
		double tmp;
		double abs;
		double distance;
		double tmpPosition;
		final int size = realloc.length;
		final int max = size - FastXaosMandelbrotRenderer.RANGE - 1;
		int min = FastXaosMandelbrotRenderer.RANGE;
		int istart = 0;
		Realloc tmpRealloc = null;
		Realloc symRealloc = null;
		symPosition *= 2;
		int symj = (2 * symi) - size;
		if (symj < 0) {
			symj = 0;
		}
		distance = step * FastXaosMandelbrotRenderer.RANGE;
		for (i = symj; i < symi; i++) {
			if (realloc[i].symTo != -1) {
				continue;
			}
			tmpRealloc = realloc[i];
			tmpPosition = tmpRealloc.position;
			tmpRealloc.symTo = (2 * symi) - i;
			if (tmpRealloc.symTo > max) {
				tmpRealloc.symTo = max;
			}
			j = ((tmpRealloc.symTo - istart) > FastXaosMandelbrotRenderer.RANGE) ? (-FastXaosMandelbrotRenderer.RANGE) : (-tmpRealloc.symTo + istart);
			if (tmpRealloc.recalculate) {
				while ((j < FastXaosMandelbrotRenderer.RANGE) && ((tmpRealloc.symTo + j) < (size - 1))) {
					tmp = symPosition - realloc[tmpRealloc.symTo + j].position;
					abs = Math.abs(tmp - tmpPosition);
					if (abs < distance) {
						if (((i == 0) || (tmp > realloc[i - 1].position)) && (tmp < realloc[i + 1].position)) {
							distance = abs;
							min = j;
						}
					}
					else if (tmp < tmpPosition) {
						break;
					}
					j += 1;
				}
			}
			else {
				while ((j < FastXaosMandelbrotRenderer.RANGE) && ((tmpRealloc.symTo + j) < (size - 1))) {
					if (tmpRealloc.recalculate) {
						tmp = symPosition - realloc[tmpRealloc.symTo + j].position;
						abs = Math.abs(tmp - tmpPosition);
						if (abs < distance) {
							if (((i == 0) || (tmp > realloc[i - 1].position)) && (tmp < realloc[i + 1].position)) {
								distance = abs;
								min = j;
							}
						}
						else if (tmp < tmpPosition) {
							break;
						}
					}
					j += 1;
				}
			}
			tmpRealloc.symTo += min;
			symRealloc = realloc[tmpRealloc.symTo];
			if ((min == FastXaosMandelbrotRenderer.RANGE) || (tmpRealloc.symTo <= symi) || (symRealloc.symTo != -1) || (symRealloc.symRef != -1)) {
				tmpRealloc.symTo = -1;
				continue;
			}
			if (!tmpRealloc.recalculate) {
				tmpRealloc.symTo = -1;
				if ((symRealloc.symTo != -1) || !symRealloc.recalculate) {
					continue;
				}
				symRealloc.plus = tmpRealloc.plus;
				symRealloc.symTo = i;
				istart = tmpRealloc.symTo - 1;
				symRealloc.recalculate = false;
				symRealloc.refreshed = false;
				symRealloc.dirty = true;
				tmpRealloc.symRef = tmpRealloc.symTo;
				symRealloc.position = symPosition - tmpRealloc.position;
			}
			else {
				if (symRealloc.symTo != -1) {
					tmpRealloc.symTo = -1;
					continue;
				}
				tmpRealloc.plus = symRealloc.plus;
				istart = tmpRealloc.symTo - 1;
				tmpRealloc.recalculate = false;
				tmpRealloc.refreshed = false;
				tmpRealloc.dirty = true;
				symRealloc.symRef = i;
				tmpRealloc.position = symPosition - symRealloc.position;
			}
		}
	}

	private static void prepareMove(final Movetable movetable, final Realloc[] reallocX) {
		if (FastXaosMandelbrotRenderer.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Prepare move...");
		}
		final Movetable.Data[] table = movetable.data;
		Movetable.Data tmpData = null;
		int i = 0;
		int j = 0;
		int s = 0;
		while (i < reallocX.length) {
			if (!reallocX[i].dirty) {
				tmpData = table[s];
				tmpData.to = i;
				tmpData.length = 1;
				tmpData.from = reallocX[i].plus;
				for (j = i + 1; j < reallocX.length; j++) {
					if (reallocX[j].dirty || ((j - reallocX[j].plus) != (tmpData.to - tmpData.from))) {
						break;
					}
					tmpData.length += 1;
				}
				i = j;
				s += 1;
			}
			else {
				i += 1;
			}
		}
		tmpData = table[s];
		tmpData.length = 0;
		if (FastXaosMandelbrotRenderer.PRINT_MOVETABLE) {
			AbstractMandelbrotRenderer.logger.fine("Movetable:");
			for (i = 0; table[i].length > 0; i++) {
				AbstractMandelbrotRenderer.logger.fine("i = " + i + " " + table[i].toString());
			}
		}
	}

	private static void prepareFill(final Filltable filltable, final Realloc[] reallocX) {
		if (FastXaosMandelbrotRenderer.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Prepare fill...");
		}
		final Filltable.Data[] table = filltable.data;
		Filltable.Data tmpData = null;
		int i = 0;
		int j = 0;
		int k = 0;
		int s = 0;
		int n = 0;
		for (i = 0; i < reallocX.length; i++) {
			if (reallocX[i].dirty) {
				j = i - 1;
				for (k = i + 1; (k < reallocX.length) && reallocX[k].dirty; k++) {
					;
				}
				while ((i < reallocX.length) && reallocX[i].dirty) {
					if ((k < reallocX.length) && ((j < i) || ((reallocX[i].position - reallocX[j].position) > (reallocX[k].position - reallocX[i].position)))) {
						j = k;
					}
					else {
						if (j < 0) {
							break;
						}
					}
					n = k - i;
					tmpData = table[s];
					tmpData.length = n;
					tmpData.from = j;
					tmpData.to = i;
					while (n > 0) {
						reallocX[i].position = reallocX[j].position;
						reallocX[i].dirty = false;
						n -= 1;
						i += 1;
					}
					s += 1;
				}
			}
		}
		tmpData = table[s];
		tmpData.length = 0;
		if (FastXaosMandelbrotRenderer.PRINT_FILLTABLE) {
			AbstractMandelbrotRenderer.logger.fine("Filltable:");
			for (i = 0; table[i].length > 0; i++) {
				AbstractMandelbrotRenderer.logger.fine("i = " + i + " " + table[i].toString());
			}
		}
	}

	private static double makeReallocTable(final Realloc[] realloc, final Dynamic dynamic, final double begin, final double end, final double[] position, final boolean invalidate) {
		if (FastXaosMandelbrotRenderer.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Make ReallocTable...");
		}
		Realloc tmpRealloc = null;
		Dynamic.Data prevData = null;
		Dynamic.Data bestData = null;
		Dynamic.Data tmpData = null;
		int bestPrice = FastXaosMandelbrotRenderer.MAX_PRICE;
		int price = 0;
		int price1 = 0;
		int i = 0;
		int y = 0;
		int p = 0;
		int ps = 0;
		int pe = 0;
		int ps1 = 0;
		int yend = 0;
		int flag = 0;
		final int size = realloc.length;
		final double step = (end - begin) / size;
		final double tofix = (size * FastXaosMandelbrotRenderer.FPMUL) / (end - begin);
		final int[] delta = dynamic.delta;
		delta[size] = Integer.MAX_VALUE;
		for (i = size - 1; i >= 0; i--) {
			delta[i] = (int) ((position[i] - begin) * tofix);
			if (delta[i] > delta[i + 1]) {
				delta[i] = delta[i + 1];
			}
		}
		if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
			AbstractMandelbrotRenderer.logger.fine("positions (fixed point):");
			for (i = 0; i < size; i++) {
				AbstractMandelbrotRenderer.logger.fine(String.valueOf(delta[i]));
			}
		}
		for (i = 0; i < size; i++) {
			dynamic.swap();
			yend = y - FastXaosMandelbrotRenderer.FPRANGE;
			if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
				AbstractMandelbrotRenderer.logger.fine("a0) yend = " + yend);
			}
			if (yend < 0) {
				yend = 0;
			}
			p = ps;
			while (delta[p] < yend) {
				p += 1;
			}
			ps1 = p;
			yend = y + FastXaosMandelbrotRenderer.FPRANGE;
			if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
				AbstractMandelbrotRenderer.logger.fine("a1) yend = " + yend);
			}
			if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
				AbstractMandelbrotRenderer.logger.fine("b0) i = " + i + ", y = " + y + ", ps1 = " + ps1 + ", ps = " + ps + ", pe = " + pe);
			}
			if ((ps != pe) && (p > ps)) {
				if (p < pe) {
					prevData = dynamic.oldBest[p - 1];
					if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("c0) previous = " + prevData.toString());
					}
				}
				else {
					prevData = dynamic.oldBest[pe - 1];
					if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("c1) previous = " + prevData.toString());
					}
				}
				price1 = prevData.price;
			}
			else {
				if (i > 0) {
					prevData = dynamic.calData[i - 1];
					price1 = prevData.price;
					if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("c2) previous = " + prevData.toString());
					}
				}
				else {
					prevData = null;
					price1 = 0;
					if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("c3) previous = null");
					}
				}
			}
			tmpData = dynamic.calData[i];
			price = price1 + FastXaosMandelbrotRenderer.NEW_PRICE;
			if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
				AbstractMandelbrotRenderer.logger.fine("d0) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
			}
			bestData = tmpData;
			bestPrice = price;
			tmpData.price = price;
			tmpData.pos = -1;
			tmpData.previous = prevData;
			if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
				// Toolbox.println("d1) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
			}
			if (ps != pe) {
				if (p == ps) {
					if (delta[p] != delta[p + 1]) {
						prevData = dynamic.calData[i - 1];
						price1 = prevData.price;
						price = price1 + FastXaosMandelbrotRenderer.price(delta[p], y);
						if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("g0) approximate row/column " + i + " with old row/column " + p + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[(p << FastXaosMandelbrotRenderer.DSIZE) + (i & FastXaosMandelbrotRenderer.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = p;
							tmpData.previous = prevData;
							if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
								// Toolbox.println("g1) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
							}
						}
					}
					if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("g2) store data: p = " + p + ", bestdata = " + bestData.toString());
					}
					dynamic.newBest[p++] = bestData;
				}
				prevData = null;
				price1 = price;
				while (p < pe) {
					if (delta[p] != delta[p + 1]) {
						// if (prevData != dynamic.oldBest[p - 1])
						// {
						prevData = dynamic.oldBest[p - 1];
						price1 = prevData.price;
						price = price1 + FastXaosMandelbrotRenderer.NEW_PRICE;
						if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("h0) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[((p - 1) << FastXaosMandelbrotRenderer.DSIZE) + (i & FastXaosMandelbrotRenderer.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = -1;
							tmpData.previous = prevData;
							if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
								AbstractMandelbrotRenderer.logger.fine("h1) store data: p - 1 = " + (p - 1) + ", bestdata = " + bestData.toString());
							}
							dynamic.newBest[p - 1] = bestData;
							if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
								// Toolbox.println("h2) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
							}
						}
						price = price1 + FastXaosMandelbrotRenderer.price(delta[p], y);
						if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("h3) approximate row/column " + i + " with old row/column " + p + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[(p << FastXaosMandelbrotRenderer.DSIZE) + (i & FastXaosMandelbrotRenderer.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = p;
							tmpData.previous = prevData;
							if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
								// Toolbox.println("h4) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
							}
						}
						else if (delta[p] > y) {
							if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
								AbstractMandelbrotRenderer.logger.fine("h5) store data: p = " + p + ", bestdata = " + bestData.toString());
							}
							dynamic.newBest[p++] = bestData;
							break;
						}
						// }
					}
					if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("h6) store data: p = " + p + ", bestdata = " + bestData.toString());
					}
					dynamic.newBest[p++] = bestData;
				}
				while (p < pe) {
					if (delta[p] != delta[p + 1]) {
						// if (prevData != dynamic.oldBest[p - 1])
						// {
						prevData = dynamic.oldBest[p - 1];
						price1 = prevData.price;
						price = price1 + FastXaosMandelbrotRenderer.NEW_PRICE;
						if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("i0) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[((p - 1) << FastXaosMandelbrotRenderer.DSIZE) + (i & FastXaosMandelbrotRenderer.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = -1;
							tmpData.previous = prevData;
							if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
								AbstractMandelbrotRenderer.logger.fine("i1) store data: p - 1 = " + (p - 1) + ", bestdata = " + bestData.toString());
							}
							dynamic.newBest[p - 1] = bestData;
							if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
								// Toolbox.println("i2) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
							}
						}
						price = price1 + FastXaosMandelbrotRenderer.price(delta[p], y);
						if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("i3) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[(p << FastXaosMandelbrotRenderer.DSIZE) + (i & FastXaosMandelbrotRenderer.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = p;
							tmpData.previous = prevData;
							if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
								AbstractMandelbrotRenderer.logger.fine("i4) bestprice = " + bestPrice + ", bestdata = " + bestData.toString());
							}
						}
						// }
					}
					if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
						// Toolbox.println("i5) store data: p = " + p + ", bestdata = " + bestdata.toString());
					}
					dynamic.newBest[p++] = bestData;
				}
				if (p > ps) {
					prevData = dynamic.oldBest[p - 1];
					price1 = prevData.price;
				}
				else {
					prevData = dynamic.calData[i - 1];
					price1 = prevData.price;
				}
				price = price1 + FastXaosMandelbrotRenderer.NEW_PRICE;
				if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
					AbstractMandelbrotRenderer.logger.fine("l0) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
				}
				if ((price < bestPrice) && (p > ps1)) {
					tmpData = dynamic.conData[((p - 1) << FastXaosMandelbrotRenderer.DSIZE) + (i & FastXaosMandelbrotRenderer.MASK)];
					bestData = tmpData;
					bestPrice = price;
					tmpData.price = price;
					tmpData.pos = -1;
					tmpData.previous = prevData;
					if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("l1) store data: p - 1 = " + (p - 1) + ", bestdata = " + bestData.toString());
					}
					dynamic.newBest[p - 1] = bestData;
					if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
						// Toolbox.println("l2) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
					}
				}
				while (delta[p] < yend) {
					if (delta[p] != delta[p + 1]) {
						price = price1 + FastXaosMandelbrotRenderer.price(delta[p], y);
						if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("l3) approximate row/column " + i + " with old row/column " + p + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[(p << FastXaosMandelbrotRenderer.DSIZE) + (i & FastXaosMandelbrotRenderer.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = p;
							tmpData.previous = prevData;
							if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
								// Toolbox.println("l4) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
							}
						}
						else if (delta[p] > y) {
							break;
						}
					}
					if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("l5) store data: p = " + p + ", bestdata = " + bestData.toString());
					}
					dynamic.newBest[p++] = bestData;
				}
				while (delta[p] < yend) {
					if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("l6) store data: p = " + p + ", bestdata = " + bestData.toString());
					}
					dynamic.newBest[p++] = bestData;
				}
			}
			else {
				if (delta[p] < yend) {
					if (i > 0) {
						prevData = dynamic.calData[i - 1];
						price1 = prevData.price;
						if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("e0) previous = " + prevData.toString());
						}
					}
					else {
						prevData = null;
						price1 = 0;
						if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("e1) previous = null");
						}
					}
					while (delta[p] < yend) {
						if (delta[p] != delta[p + 1]) {
							price = price1 + FastXaosMandelbrotRenderer.price(delta[p], y);
							if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
								AbstractMandelbrotRenderer.logger.fine("f0) approximate row/column " + i + " with old row/column " + p + ": price = " + price + " (previous price = " + price1 + ")");
							}
							if (price < bestPrice) {
								tmpData = dynamic.conData[(p << FastXaosMandelbrotRenderer.DSIZE) + (i & FastXaosMandelbrotRenderer.MASK)];
								bestData = tmpData;
								bestPrice = price;
								tmpData.price = price;
								tmpData.pos = p;
								tmpData.previous = prevData;
								if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
									// Toolbox.println("f1) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
								}
							}
							else if (delta[p] > y) {
								break;
							}
						}
						if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("f2) store data: p = " + p + ", bestdata = " + bestData.toString());
						}
						dynamic.newBest[p++] = bestData;
					}
					while (delta[p] < yend) {
						if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("f3) store data: p = " + p + ", bestdata = " + bestData.toString());
						}
						dynamic.newBest[p++] = bestData;
					}
				}
			}
			ps = ps1;
			ps1 = pe;
			pe = p;
			y += FastXaosMandelbrotRenderer.FPMUL;
		}
		if ((begin > delta[0]) && (end < delta[size - 1])) {
			flag = 1;
		}
		if ((delta[0] > 0) && (delta[size - 1] < (size * FastXaosMandelbrotRenderer.FPMUL))) {
			flag = 2;
		}
		if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
			AbstractMandelbrotRenderer.logger.fine("flag = " + flag);
		}
		if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
			AbstractMandelbrotRenderer.logger.fine("best table:");
		}
		for (i = size - 1; i >= 0; i--) {
			if (FastXaosMandelbrotRenderer.DUMP_XAOS) {
				AbstractMandelbrotRenderer.logger.fine("data = " + bestData.toString());
			}
			tmpData = bestData.previous;
			tmpRealloc = realloc[i];
			tmpRealloc.symTo = -1;
			tmpRealloc.symRef = -1;
			if (bestData.pos < 0) {
				tmpRealloc.recalculate = true;
				tmpRealloc.refreshed = false;
				tmpRealloc.dirty = true;
				tmpRealloc.plus = tmpRealloc.pos;
			}
			else {
				tmpRealloc.plus = bestData.pos;
				tmpRealloc.position = position[bestData.pos];
				tmpRealloc.recalculate = false;
				tmpRealloc.refreshed = false;
				tmpRealloc.dirty = false;
			}
			bestData = tmpData;
		}
		FastXaosMandelbrotRenderer.newPositions(realloc, size, begin, end, step, position, flag);
		return step;
	}

	private static void newPositions(final Realloc[] realloc, final int size, double begin1, final double end1, final double step, final double[] position, final int flag) {
		Realloc tmpRealloc = null;
		double delta = 0;
		double begin = 0;
		double end = 0;
		final int l = size;
		int s = -1;
		int e = -1;
		if (begin1 > end1) {
			begin1 = end1;
		}
		if (FastXaosMandelbrotRenderer.PRINT_POSITIONS) {
			AbstractMandelbrotRenderer.logger.fine("Positions :");
		}
		while (s < (l - 1)) {
			e = s + 1;
			if (realloc[e].recalculate) {
				while (e < l) {
					if (!realloc[e].recalculate) {
						break;
					}
					e++;
				}
				if (e < l) {
					end = realloc[e].position;
				}
				else {
					end = end1;
				}
				if (s < 0) {
					begin = begin1;
				}
				else {
					begin = realloc[s].position;
				}
				if ((e == l) && (begin > end)) {
					end = begin;
				}
				if ((e - s) == 2) {
					delta = (end - begin) * 0.5;
				}
				else {
					delta = (end - begin) / (e - s);
				}
				switch (flag) {
					case 1: {
						for (s++; s < e; s++) {
							begin += delta;
							tmpRealloc = realloc[s];
							tmpRealloc.position = begin;
							tmpRealloc.priority = 1 / (1 + (Math.abs((position[s] - begin)) * step));
							if (FastXaosMandelbrotRenderer.PRINT_POSITIONS) {
								AbstractMandelbrotRenderer.logger.fine("pos = " + s + ",position = " + tmpRealloc.position + ",price = " + tmpRealloc.priority);
							}
						}
						break;
					}
					case 2: {
						for (s++; s < e; s++) {
							begin += delta;
							tmpRealloc = realloc[s];
							tmpRealloc.position = begin;
							tmpRealloc.priority = Math.abs((position[s] - begin)) * step;
							if (FastXaosMandelbrotRenderer.PRINT_POSITIONS) {
								AbstractMandelbrotRenderer.logger.fine("pos = " + s + ",position = " + tmpRealloc.position + ",price = " + tmpRealloc.priority);
							}
						}
						break;
					}
					default: {
						for (s++; s < e; s++) {
							begin += delta;
							tmpRealloc = realloc[s];
							tmpRealloc.position = begin;
							tmpRealloc.priority = 1.0;
							if (FastXaosMandelbrotRenderer.PRINT_POSITIONS) {
								AbstractMandelbrotRenderer.logger.fine("pos = " + s + ",position = " + tmpRealloc.position + ",price = " + tmpRealloc.priority);
							}
						}
						break;
					}
				}
			}
			s = e;
		}
	}

	private void processReallocTable(final boolean dynamic, final boolean refresh) {
		if (FastXaosMandelbrotRenderer.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Process ReallocTable...");
		}
		if (dynamic || !FastXaosMandelbrotRenderer.USE_XAOS) {
			int total = 0;
			total = FastXaosMandelbrotRenderer.initPrices(renderedData.queue, total, renderedData.reallocX);
			total = FastXaosMandelbrotRenderer.initPrices(renderedData.queue, total, renderedData.reallocY);
			if (FastXaosMandelbrotRenderer.DUMP) {
				AbstractMandelbrotRenderer.logger.fine("total = " + total);
			}
			if (total > 0) {
				if (total > 1) {
					FastXaosMandelbrotRenderer.sortQueue(renderedData.queue, 0, total - 1);
				}
				processQueue(total);
			}
			if (FastXaosMandelbrotRenderer.USE_XAOS) {
				processReallocTable(false, refresh);
			}
		}
		else {
			final int[] position = renderedData.position;
			final int[] offset = renderedData.offset;
			position[0] = 1;
			offset[0] = 0;
			int s = 1;
			int i = 0;
			int j = 0;
			int tocalcx = 0;
			int tocalcy = 0;
			Realloc[] tmpRealloc = null;
			tmpRealloc = renderedData.reallocX;
			for (i = 0; i < tmpRealloc.length; i++) {
				if (tmpRealloc[i].recalculate) {
					tocalcx++;
				}
			}
			tmpRealloc = renderedData.reallocY;
			for (i = 0; i < tmpRealloc.length; i++) {
				if (tmpRealloc[i].recalculate) {
					tocalcy++;
				}
			}
			for (i = 1; i < FastXaosMandelbrotRenderer.STEPS; i++) {
				position[i] = 0;
			}
			while (s < FastXaosMandelbrotRenderer.STEPS) {
				for (i = 0; i < FastXaosMandelbrotRenderer.STEPS; i++) {
					if (position[i] == 0) {
						for (j = i; j < FastXaosMandelbrotRenderer.STEPS; j++) {
							if (position[j] != 0) {
								break;
							}
						}
						position[offset[s] = (j + i) >> 1] = 1;
						s += 1;
					}
				}
			}
			// for (i = 0; i < position.length; i++)
			// {
			// System.out.println(i + " = " + position[i] + ", " + offset[i]);
			// }
			renderedData.oldTime = renderedData.newTime = System.currentTimeMillis();
			for (s = 0; !isAborted && (s < FastXaosMandelbrotRenderer.STEPS); s++) {
				// AbstractFractalRenderer.logger.fine("step = " + s);
				tmpRealloc = renderedData.reallocY;
				for (i = offset[s]; !isAborted && (i < tmpRealloc.length); i += FastXaosMandelbrotRenderer.STEPS) {
					if (tmpRealloc[i].recalculate) {
						renderLine(tmpRealloc[i], renderedData.reallocX, renderedData.reallocY);
						tocalcy -= 1;
					}
					if (isInterrupted()) {
						isAborted = true;
						break;
					}
					Thread.yield();
				}
				tmpRealloc = renderedData.reallocX;
				for (i = offset[s]; !isAborted && (i < tmpRealloc.length); i += FastXaosMandelbrotRenderer.STEPS) {
					if (tmpRealloc[i].recalculate) {
						renderColumn(tmpRealloc[i], renderedData.reallocX, renderedData.reallocY);
						tocalcx -= 1;
					}
					if (isInterrupted()) {
						isAborted = true;
						break;
					}
					Thread.yield();
				}
				renderedData.newTime = System.currentTimeMillis();
				if (!isAborted && ((renderedData.newTime - renderedData.oldTime) > 50) && (s < FastXaosMandelbrotRenderer.STEPS)) {
					tmpRealloc = renderedData.reallocY;
					for (i = 0; i < tmpRealloc.length; i++) {
						tmpRealloc[i].changeDirty = tmpRealloc[i].dirty;
						tmpRealloc[i].changePosition = tmpRealloc[i].position;
					}
					tmpRealloc = renderedData.reallocX;
					for (i = 0; i < tmpRealloc.length; i++) {
						tmpRealloc[i].changeDirty = tmpRealloc[i].dirty;
						tmpRealloc[i].changePosition = tmpRealloc[i].position;
					}
					percent = (int) (((s + 1) * 100) / (float) FastXaosMandelbrotRenderer.STEPS);
					fill();
					copy();
					Thread.yield();
					tmpRealloc = renderedData.reallocY;
					for (i = 0; i < tmpRealloc.length; i++) {
						tmpRealloc[i].dirty = tmpRealloc[i].changeDirty;
						tmpRealloc[i].position = tmpRealloc[i].changePosition;
					}
					tmpRealloc = renderedData.reallocX;
					for (i = 0; i < tmpRealloc.length; i++) {
						tmpRealloc[i].dirty = tmpRealloc[i].changeDirty;
						tmpRealloc[i].position = tmpRealloc[i].changePosition;
					}
					renderedData.oldTime = renderedData.newTime;
				}
				// if (isInterrupted())
				// {
				// isAborted = true;
				// break;
				// }
			}
			if (!isAborted) {
				percent = 100;
			}
		}
		fill();
		copy();
		Thread.yield();
	}

	private void move() {
		FastXaosMandelbrotRenderer.prepareMove(renderedData.moveTable, renderedData.reallocX);
		doMove(renderedData.moveTable, renderedData.reallocY);
	}

	private void fill() {
		if (isVerticalSymetrySupported && isHorizontalSymetrySupported) {
			doSymetry(renderedData.reallocX, renderedData.reallocY);
		}
		FastXaosMandelbrotRenderer.prepareFill(renderedData.fillTable, renderedData.reallocX);
		doFill(renderedData.fillTable, renderedData.reallocY);
	}

	private void copy() {
		final Graphics2D g2d = getGraphics();
		g2d.setComposite(AlphaComposite.Src);
		g2d.drawImage(renderedData.newBuffer, 0, 0, null);
	}

	private static int initPrices(final Realloc[] queue, int total, final Realloc[] realloc) {
		int i = 0;
		int j = 0;
		for (i = 0; i < realloc.length; i++) {
			if (realloc[i].recalculate) {
				for (j = i; (j < realloc.length) && realloc[j].recalculate; j++) {
					queue[total++] = realloc[j];
				}
				if (j == realloc.length) {
					j -= 1;
				}
				FastXaosMandelbrotRenderer.addPrices(realloc, i, j);
				i = j;
			}
		}
		return total;
	}

	private static void sortQueue(final Realloc[] queue, final int l, final int r) {
		if (FastXaosMandelbrotRenderer.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Sort queue...");
		}
		final double m = (queue[l].priority + queue[r].priority) / 2.0;
		Realloc t = null;
		int i = l;
		int j = r;
		do {
			while (queue[i].priority > m) {
				i++;
			}
			while (queue[j].priority < m) {
				j--;
			}
			if (i <= j) {
				t = queue[i];
				queue[i] = queue[j];
				queue[j] = t;
				i++;
				j--;
			}
		}
		while (j >= i);
		if (l < j) {
			FastXaosMandelbrotRenderer.sortQueue(queue, l, j);
		}
		if (r > i) {
			FastXaosMandelbrotRenderer.sortQueue(queue, i, r);
		}
	}

	private void processQueue(final int size) {
		if (FastXaosMandelbrotRenderer.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Process queue...");
		}
		int i = 0;
		for (i = 0; i < size; i++) {
			if (renderedData.queue[i].line) {
				renderLine(renderedData.queue[i], renderedData.reallocX, renderedData.reallocY);
			}
			else {
				renderColumn(renderedData.queue[i], renderedData.reallocX, renderedData.reallocY);
			}
			if (isInterrupted()) {
				isAborted = true;
				break;
			}
			Thread.yield();
		}
	}

	private void doSymetry(final Realloc[] reallocX, final Realloc[] reallocY) {
		if (FastXaosMandelbrotRenderer.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Do symetry...");
		}
		final int rowsize = getBufferWidth();
		int from_offset = 0;
		int to_offset = 0;
		int i = 0;
		int j = 0;
		for (i = 0; i < reallocY.length; i++) {
			if ((reallocY[i].symTo >= 0) && (!reallocY[reallocY[i].symTo].dirty)) {
				from_offset = reallocY[i].symTo * rowsize;
				System.arraycopy(renderedData.newRGB, from_offset, renderedData.newRGB, to_offset, rowsize);
				if (FastXaosMandelbrotRenderer.SHOW_SYMETRY) {
					for (int k = 0; k < rowsize; k++) {
						renderedData.newRGB[to_offset + k] = Colors.mixColors(renderedData.newRGB[to_offset + k], 0xFFFF0000, 127);
					}
				}
				reallocY[i].dirty = false;
			}
			to_offset += rowsize;
			// Thread.yield();
		}
		for (i = 0; i < reallocX.length; i++) {
			if ((reallocX[i].symTo >= 0) && (!reallocX[reallocX[i].symTo].dirty)) {
				to_offset = i;
				from_offset = reallocX[i].symTo;
				final int[] newRGB = renderedData.newRGB;
				for (j = 0; j < reallocY.length; j++) {
					newRGB[to_offset] = newRGB[from_offset];
					if (FastXaosMandelbrotRenderer.SHOW_SYMETRY) {
						newRGB[to_offset] = Colors.mixColors(newRGB[to_offset], 0xFFFF0000, 127);
					}
					to_offset += rowsize;
					from_offset += rowsize;
				}
				reallocX[i].dirty = false;
			}
			// Thread.yield();
		}
	}

	private void doMove(final Movetable movetable, final Realloc[] reallocY) {
		if (FastXaosMandelbrotRenderer.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Do move...");
		}
		final Movetable.Data[] table = movetable.data;
		Movetable.Data tmpData = null;
		final int rowsize = getBufferWidth();
		int new_offset = 0;
		int old_offset = 0;
		int from = 0;
		int to = 0;
		int i = 0;
		int s = 0;
		for (i = 0; i < reallocY.length; i++) {
			if (!reallocY[i].dirty) {
				s = 0;
				old_offset = reallocY[i].plus * rowsize;
				while ((tmpData = table[s]).length > 0) {
					from = old_offset + tmpData.from;
					to = new_offset + tmpData.to;
					System.arraycopy(renderedData.oldRGB, from, renderedData.newRGB, to, tmpData.length);
					s += 1;
				}
			}
			new_offset += rowsize;
			// Thread.yield();
		}
	}

	private void doFill(final Filltable filltable, final Realloc[] reallocY) {
		if (FastXaosMandelbrotRenderer.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Do fill...");
		}
		final Filltable.Data[] table = filltable.data;
		Filltable.Data tmpData = null;
		final int rowsize = getBufferWidth();
		int from_offset = 0;
		int to_offset = 0;
		int from = 0;
		int to = 0;
		int i = 0;
		int j = 0;
		int k = 0;
		int t = 0;
		int s = 0;
		int c = 0;
		int d = 0;
		for (i = 0; i < reallocY.length; i++) {
			if (reallocY[i].dirty) {
				j = i - 1;
				for (k = i + 1; (k < reallocY.length) && reallocY[k].dirty; k++) {
					;
				}
				while ((i < reallocY.length) && reallocY[i].dirty) {
					if ((k < reallocY.length) && ((j < i) || ((reallocY[i].position - reallocY[j].position) > (reallocY[k].position - reallocY[i].position)))) {
						j = k;
					}
					else {
						if (j < 0) {
							break;
						}
					}
					to_offset = i * rowsize;
					from_offset = j * rowsize;
					if (!reallocY[j].dirty) {
						s = 0;
						final int[] newRGB = renderedData.newRGB;
						while ((tmpData = table[s]).length > 0) {
							from = from_offset + tmpData.from;
							to = from_offset + tmpData.to;
							c = newRGB[from];
							for (t = 0; t < tmpData.length; t++) {
								d = to + t;
								newRGB[d] = c;
							}
							s += 1;
						}
					}
					System.arraycopy(renderedData.newRGB, from_offset, renderedData.newRGB, to_offset, rowsize);
					reallocY[i].position = reallocY[j].position;
					reallocY[i].dirty = false;
					i += 1;
				}
			}
			else {
				s = 0;
				from_offset = i * rowsize;
				final int[] newRGB = renderedData.newRGB;
				while ((tmpData = table[s]).length > 0) {
					from = from_offset + tmpData.from;
					to = from_offset + tmpData.to;
					c = newRGB[from];
					for (t = 0; t < tmpData.length; t++) {
						d = to + t;
						newRGB[d] = c;
					}
					s += 1;
				}
				reallocY[i].dirty = false;
			}
			// Thread.yield();
		}
	}

	private void renderLine(final Realloc realloc, final Realloc[] reallocX, final Realloc[] reallocY) {
		if (FastXaosMandelbrotRenderer.PRINT_CALCULATE) {
			AbstractMandelbrotRenderer.logger.fine("Calculate line " + realloc.pos);
		}
		final int rowsize = getBufferWidth();
		final double position = realloc.position;
		final int r = realloc.pos;
		int offset = r * rowsize;
		int k;
		final Complex z = new Complex(0, 0);
		final Complex w = new Complex(0, 0);
		final RenderedPoint p = new RenderedPoint();
		final int[] newRGB = renderedData.newRGB;
		for (k = 0; k < reallocX.length; k++) {
			if (!reallocX[k].dirty) {
				z.r = renderedData.x0;
				z.i = renderedData.y0;
				w.r = reallocX[k].position;
				w.i = position;
				p.pr = reallocX[k].position;
				p.pi = position;
				newRGB[offset] = renderingStrategy.renderPoint(p, z, w);
				if (FastXaosMandelbrotRenderer.SHOW_CALCULATE) {
					newRGB[offset] = Colors.mixColors(newRGB[offset], 0xFFFFFF00, 127);
				}
			}
			offset += 1;
		}
		realloc.recalculate = false;
		realloc.refreshed = true;
		realloc.dirty = false;
	}

	private void renderColumn(final Realloc realloc, final Realloc[] reallocX, final Realloc[] reallocY) {
		if (FastXaosMandelbrotRenderer.PRINT_CALCULATE) {
			AbstractMandelbrotRenderer.logger.fine("Calculate column " + realloc.pos);
		}
		final int rowsize = getBufferWidth();
		final double position = realloc.position;
		final int r = realloc.pos;
		int offset = r;
		int k;
		final Complex z = new Complex(0, 0);
		final Complex w = new Complex(0, 0);
		final RenderedPoint p = new RenderedPoint();
		final int[] newRGB = renderedData.newRGB;
		for (k = 0; k < reallocY.length; k++) {
			if (!reallocY[k].dirty) {
				z.r = renderedData.x0;
				z.i = renderedData.y0;
				w.r = position;
				w.i = reallocY[k].position;
				p.pr = position;
				p.pi = reallocY[k].position;
				newRGB[offset] = renderingStrategy.renderPoint(p, z, w);
				if (FastXaosMandelbrotRenderer.SHOW_CALCULATE) {
					newRGB[offset] = Colors.mixColors(newRGB[offset], 0xFFFFFF00, 127);
				}
			}
			offset += rowsize;
		}
		realloc.recalculate = false;
		realloc.refreshed = true;
		realloc.dirty = false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.AbstractMandelbrotRenderer.core.fractal.renderer.AbstractFractalRenderer#getMandelbrotRenderingStrategy()
	 */
	@Override
	protected RenderingStrategy getMandelbrotRenderingStrategy() {
		return mandelbrotRenderingStrategy;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.AbstractMandelbrotRenderer.core.fractal.renderer.AbstractFractalRenderer#createJuliaRenderingStrategy()
	 */
	@Override
	protected RenderingStrategy getJuliaRenderingStrategy() {
		return juliaRenderingStrategy;
	}

	/**
	 * @author Andrea Medeghini
	 */
	public static class Movetable {
		/**
		 * 
		 */
		public Data[] data;

		/**
		 * @param width
		 */
		public Movetable(final int width) {
			data = new Data[width + 1];
			for (int i = 0; i <= width; i++) {
				data[i] = new Data();
			}
		}

		/**
		 * @see java.lang.Object#finalize()
		 */
		public void finalize() throws Throwable {
			data = null;
			super.finalize();
		}

		/**
		 * @author Andrea Medeghini
		 */
		public class Data {
			int length;
			int from;
			int to;

			/**
			 * @see java.lang.Object#toString()
			 */
			public String toString() {
				return "<from = " + from + ", to = " + to + ", length = " + length + ">";
			}
		}
	}

	/**
	 * @author Andrea Medeghini
	 */
	public static class Filltable {
		/**
		 * 
		 */
		public Data[] data;

		/**
		 * @param width
		 */
		public Filltable(final int width) {
			data = new Data[width + 1];
			for (int i = 0; i <= width; i++) {
				data[i] = new Data();
			}
		}

		/**
		 * @see java.lang.Object#finalize()
		 */
		public void finalize() throws Throwable {
			data = null;
			super.finalize();
		}

		/**
		 * @author Andrea Medeghini
		 */
		public class Data {
			int length;
			int from;
			int to;

			/**
			 * @see java.lang.Object#toString()
			 */
			public String toString() {
				return "<from = " + from + ", to = " + to + ", length = " + length + ">";
			}
		}
	}

	/**
	 * @author Andrea Medeghini
	 */
	public static class Dynamic {
		/**
		 * 
		 */
		public int[] delta;
		/**
		 * 
		 */
		public Data[] oldBest;
		/**
		 * 
		 */
		public Data[] newBest;
		/**
		 * 
		 */
		public Data[] calData;
		/**
		 * 
		 */
		public Data[] conData;

		/**
		 * @param size
		 */
		public Dynamic(final int size) {
			delta = new int[size + 1];
			oldBest = new Data[size];
			newBest = new Data[size];
			calData = new Data[size];
			conData = new Data[size << FastXaosMandelbrotRenderer.DSIZE];
			for (int i = 0; i < size; i++) {
				calData[i] = new Data();
			}
			for (int i = 0; i < (size << FastXaosMandelbrotRenderer.DSIZE); i++) {
				conData[i] = new Data();
			}
		}

		/**
		 * @see java.lang.Object#finalize()
		 */
		public void finalize() throws Throwable {
			oldBest = null;
			newBest = null;
			calData = null;
			conData = null;
			super.finalize();
		}

		/**
		 * 
		 */
		public void swap() {
			final Dynamic.Data[] tmp_best = newBest;
			newBest = oldBest;
			oldBest = tmp_best;
		}

		/**
		 * @author Andrea Medeghini
		 */
		public class Data {
			Data previous;
			int pos;
			int price;

			/**
			 * @see java.lang.Object#toString()
			 */
			public String toString() {
				return "<price = " + price + ", pos = " + pos + ">";
			}
		}
	}

	/**
	 * @author Andrea Medeghini
	 */
	public static class Realloc {
		/**
		 * 
		 */
		public boolean refreshed;
		/**
		 * 
		 */
		public boolean recalculate;
		/**
		 * 
		 */
		public boolean changeDirty;
		/**
		 * 
		 */
		public boolean dirty;
		/**
		 * 
		 */
		public boolean line;
		/**
		 * 
		 */
		public int pos;
		/**
		 * 
		 */
		public int plus;
		/**
		 * 
		 */
		public int symTo;
		/**
		 * 
		 */
		public int symRef;
		/**
		 * 
		 */
		public double changePosition;
		/**
		 * 
		 */
		public double position;
		/**
		 * 
		 */
		public double priority;

		/**
		 * @param line
		 */
		public Realloc(final boolean line) {
			this.line = line;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return "<pos = " + pos + ", symref = " + symRef + ", symto = " + symTo + ", plus = " + plus + ", dirty = " + dirty + ", recalculate = " + recalculate + ", line = " + line + ", priority = " + priority + ", position = " + position + ">";
		}
	}

	/**
	 * @author Andrea Medeghini
	 */
	public static class RendererData {
		/**
		 * 
		 */
		public BufferedImage newBuffer;
		/**
		 * 
		 */
		public BufferedImage oldBuffer;
		/**
		 * 
		 */
		public int[] newRGB;
		/**
		 * 
		 */
		public int[] oldRGB;
		/**
		 * 
		 */
		public double[] positionX;
		/**
		 * 
		 */
		public double[] positionY;
		/**
		 * 
		 */
		public Realloc[] reallocX;
		/**
		 * 
		 */
		public Realloc[] reallocY;
		/**
		 * 
		 */
		public Dynamic dynamicx;
		/**
		 * 
		 */
		public Dynamic dynamicy;
		/**
		 * 
		 */
		public Movetable moveTable;
		/**
		 * 
		 */
		public Filltable fillTable;
		/**
		 * 
		 */
		public Realloc[] queue;
		/**
		 * 
		 */
		public double x0 = 0;
		/**
		 * 
		 */
		public double y0 = 0;
		/**
		 * 
		 */
		public long newTime;
		/**
		 * 
		 */
		public long oldTime;
		/**
		 * 
		 */
		public final int[] position = new int[FastXaosMandelbrotRenderer.STEPS];
		/**
		 * 
		 */
		public final int[] offset = new int[FastXaosMandelbrotRenderer.STEPS];

		/**
		 * @see java.lang.Object#finalize()
		 */
		public void finalize() throws Throwable {
			free();
			super.finalize();
		}

		/**
		 * 
		 */
		public void free() {
			positionX = null;
			positionY = null;
			reallocX = null;
			reallocY = null;
			dynamicx = null;
			dynamicy = null;
			moveTable = null;
			fillTable = null;
			queue = null;
			if (newBuffer != null) {
				newBuffer.flush();
			}
			newBuffer = null;
			newRGB = null;
			if (oldBuffer != null) {
				oldBuffer.flush();
			}
			oldBuffer = null;
			oldRGB = null;
		}

		/**
		 * @param width
		 * @param height
		 */
		public void reallocate(final int width, final int height) {
			positionX = new double[width];
			positionY = new double[height];
			reallocX = new Realloc[width];
			reallocY = new Realloc[height];
			dynamicx = new Dynamic(width);
			dynamicy = new Dynamic(height);
			moveTable = new Movetable(width);
			fillTable = new Filltable(width);
			queue = new Realloc[reallocX.length + reallocY.length];
			for (int i = 0; i < width; i++) {
				reallocX[i] = new Realloc(false);
				reallocX[i].pos = i;
				positionX[i] = 0;
			}
			for (int i = 0; i < height; i++) {
				reallocY[i] = new Realloc(true);
				reallocY[i].pos = i;
				positionY[i] = 0;
			}
			newBuffer = new BufferedImage(width, height, Surface.DEFAULT_TYPE);
			newRGB = ((DataBufferInt) newBuffer.getRaster().getDataBuffer()).getData();
			oldBuffer = new BufferedImage(width, height, Surface.DEFAULT_TYPE);
			oldRGB = ((DataBufferInt) oldBuffer.getRaster().getDataBuffer()).getData();
		}

		/**
		 * 
		 */
		public void swap() {
			final int[] tmpRGB = oldRGB;
			final BufferedImage tmpBuffer = oldBuffer;
			oldRGB = newRGB;
			oldBuffer = newBuffer;
			newRGB = tmpRGB;
			newBuffer = tmpBuffer;
		}
	}

	private class MandelbrotRenderingStrategy implements RenderingStrategy {
		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer.RenderingStrategy#isVerticalSymetrySupported()
		 */
		public boolean isVerticalSymetrySupported() {
			for (int i = 0; i < fractalRuntime.getOutcolouringFormulaCount(); i++) {
				final OutcolouringFormulaRuntimeElement outcolouringFormula = fractalRuntime.getOutcolouringFormula(i);
				if ((outcolouringFormula.getFormulaRuntime() != null) && !outcolouringFormula.getFormulaRuntime().isVerticalSymetryAllowed()) {
					return false;
				}
			}
			for (int i = 0; i < fractalRuntime.getIncolouringFormulaCount(); i++) {
				final IncolouringFormulaRuntimeElement incolouringFormula = fractalRuntime.getIncolouringFormula(i);
				if ((incolouringFormula.getFormulaRuntime() != null) && !incolouringFormula.getFormulaRuntime().isVerticalSymetryAllowed()) {
					return false;
				}
			}
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer.RenderingStrategy#isHorizontalSymetrySupported()
		 */
		public boolean isHorizontalSymetrySupported() {
			for (int i = 0; i < fractalRuntime.getOutcolouringFormulaCount(); i++) {
				final OutcolouringFormulaRuntimeElement outcolouringFormula = fractalRuntime.getOutcolouringFormula(i);
				if ((outcolouringFormula.getFormulaRuntime() != null) && !outcolouringFormula.getFormulaRuntime().isHorizontalSymetryAllowed()) {
					return false;
				}
			}
			for (int i = 0; i < fractalRuntime.getIncolouringFormulaCount(); i++) {
				final IncolouringFormulaRuntimeElement incolouringFormula = fractalRuntime.getIncolouringFormula(i);
				if ((incolouringFormula.getFormulaRuntime() != null) && !incolouringFormula.getFormulaRuntime().isHorizontalSymetryAllowed()) {
					return false;
				}
			}
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer.RenderingStrategy#renderPoint(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
		 */
		public int renderPoint(final RenderedPoint p, final Complex px, final Complex pw) {
			if ((fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) && (fractalRuntime.getTransformingFormula().getFormulaRuntime() != null)) {
				fractalRuntime.getTransformingFormula().getFormulaRuntime().renderPoint(pw);
				p.xr = px.r;
				p.xi = px.i;
				p.wr = pw.r;
				p.wi = pw.i;
				p.dr = 0;
				p.di = 0;
				p.tr = 0;
				p.ti = 0;
				return FastXaosMandelbrotRenderer.this.renderPoint(p);
			}
			return 0;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer.RenderingStrategy#updateParameters()
		 */
		public void updateParameters() {
			if (fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) {
				renderedData.x0 = fractalRuntime.getRenderingFormula().getFormulaRuntime().getInitialPoint().r;
				renderedData.y0 = fractalRuntime.getRenderingFormula().getFormulaRuntime().getInitialPoint().i;
			}
			else {
				renderedData.x0 = 0;
				renderedData.y0 = 0;
			}
		}
	}

	private class JuliaRenderingStrategy implements RenderingStrategy {
		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer.RenderingStrategy#isHorizontalSymetrySupported()
		 */
		public boolean isHorizontalSymetrySupported() {
			return false;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer.RenderingStrategy#isVerticalSymetrySupported()
		 */
		public boolean isVerticalSymetrySupported() {
			return false;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer.RenderingStrategy#renderPoint(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
		 */
		public int renderPoint(final RenderedPoint p, final Complex px, final Complex pw) {
			if ((fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) && (fractalRuntime.getTransformingFormula().getFormulaRuntime() != null)) {
				fractalRuntime.getTransformingFormula().getFormulaRuntime().renderPoint(px);
				p.xr = pw.r;
				p.xi = pw.i;
				p.wr = px.r;
				p.wi = px.i;
				p.dr = 0;
				p.di = 0;
				p.tr = 0;
				p.ti = 0;
				return FastXaosMandelbrotRenderer.this.renderPoint(p);
			}
			return 0;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer.RenderingStrategy#updateParameters()
		 */
		public void updateParameters() {
			renderedData.x0 = oldConstant.getX();
			renderedData.y0 = oldConstant.getY();
		}
	}

	private class MandelbrotWorker2 extends RenderWorker {
		/**
		 * 
		 */
		public MandelbrotWorker2() {
			super(factory);
		}

		/**
		 * 
		 */
		@Override
		protected void execute() {
			prepareColumns();
		}
	}
	// private class PrepareLinesWorker extends RenderWorker {
	// /**
	// *
	// */
	// public PrepareLinesWorker() {
	// super(factory);
	// }
	//
	// /**
	// * @param runnable
	// * @return
	// */
	// @Override
	// protected Thread createThread(final Runnable runnable) {
	// Thread thread = super.createThread(runnable);
	// thread.setName("PrepareLinesWorker");
	// return thread;
	// }
	//
	// /**
	// *
	// */
	// @Override
	// protected void execute() {
	// prepareLines();
	// }
	// }
	//
	// private class PrepareColumnsWorker extends RenderWorker {
	// /**
	// *
	// */
	// public PrepareColumnsWorker() {
	// super(factory);
	// }
	//
	// /**
	// * @param runnable
	// * @return
	// */
	// @Override
	// protected Thread createThread(final Runnable runnable) {
	// Thread thread = super.createThread(runnable);
	// thread.setName("PrepareColumnsWorker");
	// return thread;
	// }
	//
	// /**
	// *
	// */
	// @Override
	// protected void execute() {
	// prepareColumns();
	// }
	// }
}
