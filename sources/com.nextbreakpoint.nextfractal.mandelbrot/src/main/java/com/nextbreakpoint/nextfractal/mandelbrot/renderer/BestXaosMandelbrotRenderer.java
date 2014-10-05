/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
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

import com.nextbreakpoint.nextfractal.core.math.Complex;
import com.nextbreakpoint.nextfractal.core.util.Colors;
import com.nextbreakpoint.nextfractal.core.util.RenderWorker;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaRuntimeElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaRuntimeElement;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderBuffer;

/**
 * @author Andrea Medeghini
 */
public final class BestXaosMandelbrotRenderer extends AbstractMandelbrotRenderer {
	static {
		if (XaosConstants.PRINT_MULTABLE) {
			AbstractMandelbrotRenderer.logger.fine("Multable:");
			for (int i = -XaosConstants.FPRANGE; i < XaosConstants.FPRANGE; i++) {
				AbstractMandelbrotRenderer.logger.fine("i = " + i + ", i * i = " + XaosConstants.MULTABLE[XaosConstants.FPRANGE + i]);
			}
		}
	}
	private final RenderingStrategy mandelbrotRenderingStrategy = new MandelbrotRenderingStrategy();
	private final RenderingStrategy juliaRenderingStrategy = new JuliaRenderingStrategy();
	// private final PrepareColumnsWorker prepareColumnsWorker = new PrepareColumnsWorker();
	// private final PrepareLinesWorker prepareLinesWorker = new PrepareLinesWorker();
	private final MandelbrotWorker2 renderWorker2 = new MandelbrotWorker2();
	private final boolean isSolidguessEnabled = true;
	private boolean isSolidguessSupported = true;
	private final boolean isSymetryEnabled = true;
	private boolean isVerticalSymetrySupported = true;
	private boolean isHorizontalSymetrySupported = true;
	private boolean useCache = false;
	private boolean isAborted = false;
	private XaosRendererData renderedData;

	/**
	 * @param threadPriority
	 */
	public BestXaosMandelbrotRenderer(final int threadPriority) {
		super(threadPriority);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer#start()
	 */
	@Override
	public void start() {
		// prepareLinesWorker.start();
		// prepareColumnsWorker.start();
		super.start();
		renderWorker2.start();
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
	@Override
	protected void free() {
		super.free();
		if (renderedData != null) {
			renderedData.free();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.AbstractMandelbrotRenderer.core.fractal.renderer.AbstractFractalRenderer#init()
	 */
	@Override
	protected void init() {
		super.init();
		renderedData = new XaosRendererData();
		renderedData.reallocate(getBufferWidth(), getBufferHeight());
	}

	/**
	 * 
	 */
	protected void swapBuffers() {
		if (XaosConstants.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Swap buffers...");
		}
		renderedData.swap();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.AbstractMandelbrotRenderer.core.fractal.renderer.AbstractFractalRenderer#doRender(boolean)
	 */
	@Override
	protected void doRender(final boolean dynamic) {
		isAborted = false;
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
		if (XaosConstants.PRINT_REGION) {
			AbstractMandelbrotRenderer.logger.fine("Region: " + area.toString());
		}
		final boolean refresh = (renderMode & MandelbrotRenderer.MODE_REFRESH) != 0;
		useCache = refresh || !dynamic;
		isSolidguessSupported = XaosConstants.USE_SOLIDGUESS && isSolidguessEnabled && isSolidGuessSupported();
		isVerticalSymetrySupported = XaosConstants.USE_SYMETRY && isSymetryEnabled && isVerticalSymetrySupported();
		isHorizontalSymetrySupported = XaosConstants.USE_SYMETRY && isSymetryEnabled && isHorizontalSymetrySupported();
		if (XaosConstants.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Solidguess supported = " + isSolidguessSupported);
			AbstractMandelbrotRenderer.logger.fine("Vertical symetry supported = " + isVerticalSymetrySupported);
			AbstractMandelbrotRenderer.logger.fine("Horizontal symetry supported = " + isHorizontalSymetrySupported);
			AbstractMandelbrotRenderer.logger.fine("Use cache = " + useCache);
		}
		if (XaosConstants.USE_MULTITHREAD && !XaosConstants.DUMP_XAOS) {
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
		if (XaosConstants.PRINT_REALLOCTABLE) {
			AbstractMandelbrotRenderer.logger.fine("ReallocTable:");
			for (final XaosRealloc element : renderedData.reallocX) {
				AbstractMandelbrotRenderer.logger.fine(element.toString());
			}
			AbstractMandelbrotRenderer.logger.fine("ReallocTable:");
			for (final XaosRealloc element : renderedData.reallocY) {
				AbstractMandelbrotRenderer.logger.fine(element.toString());
			}
		}
		swapBuffers();
		move();
		processReallocTable(dynamic, refresh);
		updatePosition();
		renderMode = 0;
	}

	private void prepareLines() {
		final double beginy = area.points[0].i;
		final double endy = area.points[1].i;
		double stepy = 0;
		if (((renderMode & MandelbrotRenderer.MODE_CALCULATE) == 0) && XaosConstants.USE_XAOS) {
			stepy = BestXaosMandelbrotRenderer.makeReallocTable(renderedData.reallocY, renderedData.dynamicy, beginy, endy, renderedData.positionY, !useCache);
		}
		else {
			stepy = BestXaosMandelbrotRenderer.initReallocTableAndPosition(renderedData.reallocY, renderedData.positionY, beginy, endy);
		}
		if ((fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) && (fractalRuntime.getTransformingFormula().getFormulaRuntime() != null)) {
			final double symy = fractalRuntime.getRenderingFormula().getFormulaRuntime().getVerticalSymetryPoint();
			if (isVerticalSymetrySupported && fractalRuntime.getRenderingFormula().getFormulaRuntime().isVerticalSymetryAllowed() && fractalRuntime.getTransformingFormula().getFormulaRuntime().isVerticalSymetryAllowed() && (!((beginy > symy) || (symy > endy)))) {
				BestXaosMandelbrotRenderer.prepareSymetry(renderedData.reallocY, (int) ((symy - beginy) / stepy), symy, stepy);
			}
		}
	}

	private void prepareColumns() {
		final double beginx = area.points[0].r;
		final double endx = area.points[1].r;
		double stepx = 0;
		if (((renderMode & MandelbrotRenderer.MODE_CALCULATE) == 0) && XaosConstants.USE_XAOS) {
			stepx = BestXaosMandelbrotRenderer.makeReallocTable(renderedData.reallocX, renderedData.dynamicx, beginx, endx, renderedData.positionX, !useCache);
		}
		else {
			stepx = BestXaosMandelbrotRenderer.initReallocTableAndPosition(renderedData.reallocX, renderedData.positionX, beginx, endx);
		}
		if ((fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) && (fractalRuntime.getTransformingFormula().getFormulaRuntime() != null)) {
			final double symx = fractalRuntime.getRenderingFormula().getFormulaRuntime().getHorizontalSymetryPoint();
			if (isHorizontalSymetrySupported && fractalRuntime.getRenderingFormula().getFormulaRuntime().isHorizontalSymetryAllowed() && fractalRuntime.getTransformingFormula().getFormulaRuntime().isHorizontalSymetryAllowed() && (!((beginx > symx) || (symx > endx)))) {
				BestXaosMandelbrotRenderer.prepareSymetry(renderedData.reallocX, (int) ((symx - beginx) / stepx), symx, stepx);
			}
		}
	}

	private static double initReallocTableAndPosition(final XaosRealloc[] realloc, final double[] position, final double begin, final double end) {
		if (XaosConstants.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Init ReallocTable and position...");
		}
		final double step = (end - begin) / realloc.length;
		double tmpPosition = begin;
		XaosRealloc tmpRealloc = null;
		for (int i = 0; i < realloc.length; i++) {
			tmpRealloc = realloc[i];
			position[i] = tmpPosition;
			tmpRealloc.position = tmpPosition;
			tmpRealloc.recalculate = true;
			tmpRealloc.refreshed = false;
			tmpRealloc.dirty = true;
			tmpRealloc.isCached = false;
			tmpRealloc.plus = i;
			tmpRealloc.symTo = -1;
			tmpRealloc.symRef = -1;
			tmpPosition += step;
		}
		return step;
	}

	private void updatePosition() {
		if (XaosConstants.DUMP) {
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
		return XaosConstants.MULTABLE[(XaosConstants.FPRANGE + p1) - p2];
	}

	private static void addPrices(final XaosRealloc[] realloc, int r1, final int r2) {
		// if (r1 < r2)
		while (r1 < r2) {
			final int r3 = r1 + ((r2 - r1) >> 1);
			realloc[r3].priority = (realloc[r2].position - realloc[r3].position) * realloc[r3].priority;
			if (realloc[r3].symRef != -1) {
				realloc[r3].priority /= 2.0;
			}
			BestXaosMandelbrotRenderer.addPrices(realloc, r1, r3);
			// XaosFractalRenderer.addPrices(realloc, r3 + 1, r2);
			r1 = r3 + 1;
		}
	}

	private static void prepareSymetry(final XaosRealloc[] realloc, final int symi, double symPosition, final double step) {
		if (XaosConstants.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Prepare symetry...");
		}
		int i = 0;
		int j = 0;
		double tmp;
		double abs;
		double distance;
		double tmpPosition;
		final int size = realloc.length;
		final int max = size - XaosConstants.RANGE - 1;
		int min = XaosConstants.RANGE;
		int istart = 0;
		XaosRealloc tmpRealloc = null;
		XaosRealloc symRealloc = null;
		symPosition *= 2;
		int symj = (2 * symi) - size;
		if (symj < 0) {
			symj = 0;
		}
		distance = step * XaosConstants.RANGE;
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
			j = ((tmpRealloc.symTo - istart) > XaosConstants.RANGE) ? (-XaosConstants.RANGE) : (-tmpRealloc.symTo + istart);
			if (tmpRealloc.recalculate) {
				while ((j < XaosConstants.RANGE) && ((tmpRealloc.symTo + j) < (size - 1))) {
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
				while ((j < XaosConstants.RANGE) && ((tmpRealloc.symTo + j) < (size - 1))) {
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
			if ((min == XaosConstants.RANGE) || (tmpRealloc.symTo <= symi) || (symRealloc.symTo != -1) || (symRealloc.symRef != -1)) {
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
				symRealloc.isCached = false;
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
				tmpRealloc.isCached = false;
				symRealloc.symRef = i;
				tmpRealloc.position = symPosition - symRealloc.position;
			}
		}
	}

	private static void prepareMove(final Movetable movetable, final XaosRealloc[] reallocX) {
		if (XaosConstants.DUMP) {
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
		if (XaosConstants.PRINT_MOVETABLE) {
			AbstractMandelbrotRenderer.logger.fine("Movetable:");
			for (i = 0; table[i].length > 0; i++) {
				AbstractMandelbrotRenderer.logger.fine("i = " + i + " " + table[i].toString());
			}
		}
	}

	private static void prepareFill(final Filltable filltable, final XaosRealloc[] reallocX) {
		if (XaosConstants.DUMP) {
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
		if (XaosConstants.PRINT_FILLTABLE) {
			AbstractMandelbrotRenderer.logger.fine("Filltable:");
			for (i = 0; table[i].length > 0; i++) {
				AbstractMandelbrotRenderer.logger.fine("i = " + i + " " + table[i].toString());
			}
		}
	}

	private static double makeReallocTable(final XaosRealloc[] realloc, final XaosDynamic dynamic, final double begin, final double end, final double[] position, final boolean invalidate) {
		if (XaosConstants.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Make ReallocTable...");
		}
		XaosRealloc tmpRealloc = null;
		XaosDynamic.Data prevData = null;
		XaosDynamic.Data bestData = null;
		XaosDynamic.Data tmpData = null;
		int bestPrice = XaosConstants.MAX_PRICE;
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
		final double tofix = (size * XaosConstants.FPMUL) / (end - begin);
		final int[] delta = dynamic.delta;
		delta[size] = Integer.MAX_VALUE;
		for (i = size - 1; i >= 0; i--) {
			delta[i] = (int) ((position[i] - begin) * tofix);
			if (delta[i] > delta[i + 1]) {
				delta[i] = delta[i + 1];
			}
		}
		if (XaosConstants.DUMP_XAOS) {
			AbstractMandelbrotRenderer.logger.fine("positions (fixed point):");
			for (i = 0; i < size; i++) {
				AbstractMandelbrotRenderer.logger.fine(String.valueOf(delta[i]));
			}
		}
		for (i = 0; i < size; i++) {
			dynamic.swap();
			yend = y - XaosConstants.FPRANGE;
			if (XaosConstants.DUMP_XAOS) {
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
			yend = y + XaosConstants.FPRANGE;
			if (XaosConstants.DUMP_XAOS) {
				AbstractMandelbrotRenderer.logger.fine("a1) yend = " + yend);
			}
			if (XaosConstants.DUMP_XAOS) {
				AbstractMandelbrotRenderer.logger.fine("b0) i = " + i + ", y = " + y + ", ps1 = " + ps1 + ", ps = " + ps + ", pe = " + pe);
			}
			if ((ps != pe) && (p > ps)) {
				if (p < pe) {
					prevData = dynamic.oldBest[p - 1];
					if (XaosConstants.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("c0) previous = " + prevData.toString());
					}
				}
				else {
					prevData = dynamic.oldBest[pe - 1];
					if (XaosConstants.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("c1) previous = " + prevData.toString());
					}
				}
				price1 = prevData.price;
			}
			else {
				if (i > 0) {
					prevData = dynamic.calData[i - 1];
					price1 = prevData.price;
					if (XaosConstants.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("c2) previous = " + prevData.toString());
					}
				}
				else {
					prevData = null;
					price1 = 0;
					if (XaosConstants.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("c3) previous = null");
					}
				}
			}
			tmpData = dynamic.calData[i];
			price = price1 + XaosConstants.NEW_PRICE;
			if (XaosConstants.DUMP_XAOS) {
				AbstractMandelbrotRenderer.logger.fine("d0) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
			}
			bestData = tmpData;
			bestPrice = price;
			tmpData.price = price;
			tmpData.pos = -1;
			tmpData.previous = prevData;
			if (XaosConstants.DUMP_XAOS) {
				// Toolbox.println("d1) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
			}
			if (ps != pe) {
				if (p == ps) {
					if (delta[p] != delta[p + 1]) {
						prevData = dynamic.calData[i - 1];
						price1 = prevData.price;
						price = price1 + BestXaosMandelbrotRenderer.price(delta[p], y);
						if (XaosConstants.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("g0) approximate row/column " + i + " with old row/column " + p + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[(p << XaosConstants.DSIZE) + (i & XaosConstants.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = p;
							tmpData.previous = prevData;
							if (XaosConstants.DUMP_XAOS) {
								// Toolbox.println("g1) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
							}
						}
					}
					if (XaosConstants.DUMP_XAOS) {
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
						price = price1 + XaosConstants.NEW_PRICE;
						if (XaosConstants.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("h0) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[((p - 1) << XaosConstants.DSIZE) + (i & XaosConstants.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = -1;
							tmpData.previous = prevData;
							if (XaosConstants.DUMP_XAOS) {
								AbstractMandelbrotRenderer.logger.fine("h1) store data: p - 1 = " + (p - 1) + ", bestdata = " + bestData.toString());
							}
							dynamic.newBest[p - 1] = bestData;
							if (XaosConstants.DUMP_XAOS) {
								// Toolbox.println("h2) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
							}
						}
						price = price1 + BestXaosMandelbrotRenderer.price(delta[p], y);
						if (XaosConstants.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("h3) approximate row/column " + i + " with old row/column " + p + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[(p << XaosConstants.DSIZE) + (i & XaosConstants.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = p;
							tmpData.previous = prevData;
							if (XaosConstants.DUMP_XAOS) {
								// Toolbox.println("h4) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
							}
						}
						else if (delta[p] > y) {
							if (XaosConstants.DUMP_XAOS) {
								AbstractMandelbrotRenderer.logger.fine("h5) store data: p = " + p + ", bestdata = " + bestData.toString());
							}
							dynamic.newBest[p++] = bestData;
							break;
						}
						// }
					}
					if (XaosConstants.DUMP_XAOS) {
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
						price = price1 + XaosConstants.NEW_PRICE;
						if (XaosConstants.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("i0) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[((p - 1) << XaosConstants.DSIZE) + (i & XaosConstants.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = -1;
							tmpData.previous = prevData;
							if (XaosConstants.DUMP_XAOS) {
								AbstractMandelbrotRenderer.logger.fine("i1) store data: p - 1 = " + (p - 1) + ", bestdata = " + bestData.toString());
							}
							dynamic.newBest[p - 1] = bestData;
							if (XaosConstants.DUMP_XAOS) {
								// Toolbox.println("i2) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
							}
						}
						price = price1 + BestXaosMandelbrotRenderer.price(delta[p], y);
						if (XaosConstants.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("i3) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[(p << XaosConstants.DSIZE) + (i & XaosConstants.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = p;
							tmpData.previous = prevData;
							if (XaosConstants.DUMP_XAOS) {
								AbstractMandelbrotRenderer.logger.fine("i4) bestprice = " + bestPrice + ", bestdata = " + bestData.toString());
							}
						}
						// }
					}
					if (XaosConstants.DUMP_XAOS) {
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
				price = price1 + XaosConstants.NEW_PRICE;
				if (XaosConstants.DUMP_XAOS) {
					AbstractMandelbrotRenderer.logger.fine("l0) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
				}
				if ((price < bestPrice) && (p > ps1)) {
					tmpData = dynamic.conData[((p - 1) << XaosConstants.DSIZE) + (i & XaosConstants.MASK)];
					bestData = tmpData;
					bestPrice = price;
					tmpData.price = price;
					tmpData.pos = -1;
					tmpData.previous = prevData;
					if (XaosConstants.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("l1) store data: p - 1 = " + (p - 1) + ", bestdata = " + bestData.toString());
					}
					dynamic.newBest[p - 1] = bestData;
					if (XaosConstants.DUMP_XAOS) {
						// Toolbox.println("l2) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
					}
				}
				while (delta[p] < yend) {
					if (delta[p] != delta[p + 1]) {
						price = price1 + BestXaosMandelbrotRenderer.price(delta[p], y);
						if (XaosConstants.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("l3) approximate row/column " + i + " with old row/column " + p + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[(p << XaosConstants.DSIZE) + (i & XaosConstants.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = p;
							tmpData.previous = prevData;
							if (XaosConstants.DUMP_XAOS) {
								// Toolbox.println("l4) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
							}
						}
						else if (delta[p] > y) {
							break;
						}
					}
					if (XaosConstants.DUMP_XAOS) {
						AbstractMandelbrotRenderer.logger.fine("l5) store data: p = " + p + ", bestdata = " + bestData.toString());
					}
					dynamic.newBest[p++] = bestData;
				}
				while (delta[p] < yend) {
					if (XaosConstants.DUMP_XAOS) {
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
						if (XaosConstants.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("e0) previous = " + prevData.toString());
						}
					}
					else {
						prevData = null;
						price1 = 0;
						if (XaosConstants.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("e1) previous = null");
						}
					}
					while (delta[p] < yend) {
						if (delta[p] != delta[p + 1]) {
							price = price1 + BestXaosMandelbrotRenderer.price(delta[p], y);
							if (XaosConstants.DUMP_XAOS) {
								AbstractMandelbrotRenderer.logger.fine("f0) approximate row/column " + i + " with old row/column " + p + ": price = " + price + " (previous price = " + price1 + ")");
							}
							if (price < bestPrice) {
								tmpData = dynamic.conData[(p << XaosConstants.DSIZE) + (i & XaosConstants.MASK)];
								bestData = tmpData;
								bestPrice = price;
								tmpData.price = price;
								tmpData.pos = p;
								tmpData.previous = prevData;
								if (XaosConstants.DUMP_XAOS) {
									// Toolbox.println("f1) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
								}
							}
							else if (delta[p] > y) {
								break;
							}
						}
						if (XaosConstants.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("f2) store data: p = " + p + ", bestdata = " + bestData.toString());
						}
						dynamic.newBest[p++] = bestData;
					}
					while (delta[p] < yend) {
						if (XaosConstants.DUMP_XAOS) {
							AbstractMandelbrotRenderer.logger.fine("f3) store data: p = " + p + ", bestdata = " + bestData.toString());
						}
						dynamic.newBest[p++] = bestData;
					}
				}
			}
			ps = ps1;
			ps1 = pe;
			pe = p;
			y += XaosConstants.FPMUL;
		}
		if ((begin > delta[0]) && (end < delta[size - 1])) {
			flag = 1;
		}
		if ((delta[0] > 0) && (delta[size - 1] < (size * XaosConstants.FPMUL))) {
			flag = 2;
		}
		if (XaosConstants.DUMP_XAOS) {
			AbstractMandelbrotRenderer.logger.fine("flag = " + flag);
		}
		if (XaosConstants.DUMP_XAOS) {
			AbstractMandelbrotRenderer.logger.fine("best table:");
		}
		for (i = size - 1; i >= 0; i--) {
			if (XaosConstants.DUMP_XAOS) {
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
				tmpRealloc.isCached = false;
				tmpRealloc.plus = tmpRealloc.pos;
			}
			else {
				tmpRealloc.plus = bestData.pos;
				tmpRealloc.position = position[bestData.pos];
				if (invalidate) {
					tmpRealloc.isCached = false;
				}
				tmpRealloc.recalculate = false;
				tmpRealloc.refreshed = false;
				tmpRealloc.dirty = false;
			}
			bestData = tmpData;
		}
		BestXaosMandelbrotRenderer.newPositions(realloc, size, begin, end, step, position, flag);
		return step;
	}

	private static void newPositions(final XaosRealloc[] realloc, final int size, double begin1, final double end1, final double step, final double[] position, final int flag) {
		XaosRealloc tmpRealloc = null;
		double delta = 0;
		double begin = 0;
		double end = 0;
		final int l = size;
		int s = -1;
		int e = -1;
		if (begin1 > end1) {
			begin1 = end1;
		}
		if (XaosConstants.PRINT_POSITIONS) {
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
							if (XaosConstants.PRINT_POSITIONS) {
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
							if (XaosConstants.PRINT_POSITIONS) {
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
							if (XaosConstants.PRINT_POSITIONS) {
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
		if (XaosConstants.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Process ReallocTable...");
		}
		if (dynamic || !XaosConstants.USE_XAOS) {
			int total = 0;
			total = BestXaosMandelbrotRenderer.initPrices(renderedData.queue, total, renderedData.reallocX);
			total = BestXaosMandelbrotRenderer.initPrices(renderedData.queue, total, renderedData.reallocY);
			if (XaosConstants.DUMP) {
				AbstractMandelbrotRenderer.logger.fine("total = " + total);
			}
			if (total > 0) {
				if (total > 1) {
					BestXaosMandelbrotRenderer.sortQueue(renderedData.queue, 0, total - 1);
				}
				processQueue(total);
			}
			if (XaosConstants.USE_XAOS) {
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
			XaosRealloc[] tmpRealloc = null;
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
			for (i = 1; i < XaosConstants.STEPS; i++) {
				position[i] = 0;
			}
			while (s < XaosConstants.STEPS) {
				for (i = 0; i < XaosConstants.STEPS; i++) {
					if (position[i] == 0) {
						for (j = i; j < XaosConstants.STEPS; j++) {
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
			if (refresh) {
				tmpRealloc = renderedData.reallocY;
				for (final XaosRealloc element : tmpRealloc) {
					if (element.isCached && !element.refreshed) {
						refreshLine(element, renderedData.reallocX, renderedData.reallocY);
					}
				}
				tmpRealloc = renderedData.reallocX;
				for (final XaosRealloc element : tmpRealloc) {
					if (element.isCached && !element.refreshed) {
						refreshColumn(element, renderedData.reallocX, renderedData.reallocY);
					}
				}
			}
			renderedData.oldTime = renderedData.newTime = System.currentTimeMillis();
			for (s = 0; !isAborted && (s < XaosConstants.STEPS); s++) {
				// AbstractFractalRenderer.logger.fine("step = " + s);
				tmpRealloc = renderedData.reallocY;
				for (i = offset[s]; !isAborted && (i < tmpRealloc.length); i += XaosConstants.STEPS) {
					if (tmpRealloc[i].recalculate) {
						renderLine(tmpRealloc[i], renderedData.reallocX, renderedData.reallocY);
						tocalcy -= 1;
					}
					else if (!tmpRealloc[i].isCached) {
						renderLine(tmpRealloc[i], renderedData.reallocX, renderedData.reallocY);
					}
					if (isInterrupted()) {
						isAborted = true;
						break;
					}
					Thread.yield();
				}
				tmpRealloc = renderedData.reallocX;
				for (i = offset[s]; !isAborted && (i < tmpRealloc.length); i += XaosConstants.STEPS) {
					if (tmpRealloc[i].recalculate) {
						renderColumn(tmpRealloc[i], renderedData.reallocX, renderedData.reallocY);
						tocalcx -= 1;
					}
					else if (!tmpRealloc[i].isCached) {
						renderColumn(tmpRealloc[i], renderedData.reallocX, renderedData.reallocY);
					}
					if (isInterrupted()) {
						isAborted = true;
						break;
					}
					Thread.yield();
				}
				renderedData.newTime = System.currentTimeMillis();
				if (!isAborted && ((renderedData.newTime - renderedData.oldTime) > 50) && (s < XaosConstants.STEPS)) {
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
					percent = (int) (((s + 1) * 100) / (float) XaosConstants.STEPS);
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
		BestXaosMandelbrotRenderer.prepareMove(renderedData.moveTable, renderedData.reallocX);
		doMove(renderedData.moveTable, renderedData.reallocY);
	}

	private void fill() {
		if (isVerticalSymetrySupported && isHorizontalSymetrySupported) {
			doSymetry(renderedData.reallocX, renderedData.reallocY);
		}
		BestXaosMandelbrotRenderer.prepareFill(renderedData.fillTable, renderedData.reallocX);
		doFill(renderedData.fillTable, renderedData.reallocY);
	}

	private void copy() {
		final RenderBuffer buffer = getRenderBuffer();
		buffer.update(renderedData.newRGB);
	}

	private static int initPrices(final XaosRealloc[] queue, int total, final XaosRealloc[] realloc) {
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
				BestXaosMandelbrotRenderer.addPrices(realloc, i, j);
				i = j;
			}
		}
		return total;
	}

	private static void sortQueue(final XaosRealloc[] queue, final int l, final int r) {
		if (XaosConstants.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Sort queue...");
		}
		final double m = (queue[l].priority + queue[r].priority) / 2.0;
		XaosRealloc t = null;
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
			BestXaosMandelbrotRenderer.sortQueue(queue, l, j);
		}
		if (r > i) {
			BestXaosMandelbrotRenderer.sortQueue(queue, i, r);
		}
	}

	private void processQueue(final int size) {
		if (XaosConstants.DUMP) {
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

	private void doSymetry(final XaosRealloc[] reallocX, final XaosRealloc[] reallocY) {
		if (XaosConstants.DUMP) {
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
				if (useCache) {
					System.arraycopy(renderedData.newCacheZR, from_offset, renderedData.newCacheZR, to_offset, rowsize);
					System.arraycopy(renderedData.newCacheZI, from_offset, renderedData.newCacheZI, to_offset, rowsize);
					System.arraycopy(renderedData.newCacheTR, from_offset, renderedData.newCacheTR, to_offset, rowsize);
					System.arraycopy(renderedData.newCacheTI, from_offset, renderedData.newCacheTI, to_offset, rowsize);
					System.arraycopy(renderedData.newCacheTime, from_offset, renderedData.newCacheTime, to_offset, rowsize);
				}
				if (XaosConstants.SHOW_SYMETRY) {
					for (int k = 0; k < rowsize; k++) {
						renderedData.newRGB[to_offset + k] = Colors.mixColors(renderedData.newRGB[to_offset + k], 0xFFFF0000, 127);
					}
				}
				reallocY[i].dirty = false;
				reallocY[i].isCached = useCache;
			}
			to_offset += rowsize;
			// Thread.yield();
		}
		for (i = 0; i < reallocX.length; i++) {
			if ((reallocX[i].symTo >= 0) && (!reallocX[reallocX[i].symTo].dirty)) {
				to_offset = i;
				from_offset = reallocX[i].symTo;
				final int[] newRGB = renderedData.newRGB;
				final double[] newCacheZR = renderedData.newCacheZR;
				final double[] newCacheZI = renderedData.newCacheZI;
				final double[] newCacheTR = renderedData.newCacheTR;
				final double[] newCacheTI = renderedData.newCacheTI;
				final int[] newCacheTime = renderedData.newCacheTime;
				for (j = 0; j < reallocY.length; j++) {
					newRGB[to_offset] = newRGB[from_offset];
					if (useCache) {
						newCacheZR[to_offset] = newCacheZR[from_offset];
						newCacheZI[to_offset] = newCacheZI[from_offset];
						newCacheTR[to_offset] = newCacheTR[from_offset];
						newCacheTI[to_offset] = newCacheTI[from_offset];
						newCacheTime[to_offset] = newCacheTime[from_offset];
					}
					if (XaosConstants.SHOW_SYMETRY) {
						newRGB[to_offset] = Colors.mixColors(newRGB[to_offset], 0xFFFF0000, 127);
					}
					to_offset += rowsize;
					from_offset += rowsize;
				}
				reallocX[i].dirty = false;
				reallocX[i].isCached = useCache;
			}
			// Thread.yield();
		}
	}

	private void doMove(final Movetable movetable, final XaosRealloc[] reallocY) {
		if (XaosConstants.DUMP) {
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
					if (useCache) {
						System.arraycopy(renderedData.oldCacheZR, from, renderedData.newCacheZR, to, tmpData.length);
						System.arraycopy(renderedData.oldCacheZI, from, renderedData.newCacheZI, to, tmpData.length);
						System.arraycopy(renderedData.oldCacheTR, from, renderedData.newCacheTR, to, tmpData.length);
						System.arraycopy(renderedData.oldCacheTI, from, renderedData.newCacheTI, to, tmpData.length);
						System.arraycopy(renderedData.oldCacheTime, from, renderedData.newCacheTime, to, tmpData.length);
					}
					s += 1;
				}
			}
			new_offset += rowsize;
			// Thread.yield();
		}
	}

	private void doFill(final Filltable filltable, final XaosRealloc[] reallocY) {
		if (XaosConstants.DUMP) {
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
		// double c_xr = 0;
		// double c_xi = 0;
		// int c_time = 0;
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
						// final double[] newCacheR = renderedData.newCacheR;
						// final double[] newCacheI = renderedData.newCacheI;
						// final int[] newCacheTime = renderedData.newCacheTime;
						while ((tmpData = table[s]).length > 0) {
							from = from_offset + tmpData.from;
							to = from_offset + tmpData.to;
							c = newRGB[from];
							// if (useCache)
							// {
							// c_xr = newCacheR[from];
							// c_xi = newCacheI[from];
							// c_time = newCacheTime[from];
							// }
							for (t = 0; t < tmpData.length; t++) {
								d = to + t;
								newRGB[d] = c;
								// if (useCache)
								// {
								// newCacheR[d] = c_xr;
								// newCacheI[d] = c_xi;
								// newCacheTime[d] = c_time;
								// }
							}
							s += 1;
						}
					}
					System.arraycopy(renderedData.newRGB, from_offset, renderedData.newRGB, to_offset, rowsize);
					// if (useCache)
					// {
					// System.arraycopy(renderedData.newCacheR, from_offset, renderedData.newCacheR, to_offset, rowsize);
					// System.arraycopy(renderedData.newCacheI, from_offset, renderedData.newCacheI, to_offset, rowsize);
					// System.arraycopy(renderedData.newCacheTime, from_offset, renderedData.newCacheTime, to_offset, rowsize);
					// }
					reallocY[i].position = reallocY[j].position;
					// reallocY[i].isCached = false;
					reallocY[i].dirty = false;
					i += 1;
				}
			}
			else {
				s = 0;
				from_offset = i * rowsize;
				final int[] newRGB = renderedData.newRGB;
				// final double[] newCacheR = renderedData.newCacheR;
				// final double[] newCacheI = renderedData.newCacheI;
				// final int[] newCacheTime = renderedData.newCacheTime;
				while ((tmpData = table[s]).length > 0) {
					from = from_offset + tmpData.from;
					to = from_offset + tmpData.to;
					c = newRGB[from];
					// if (useCache)
					// {
					// c_xr = newCacheR[from];
					// c_xi = newCacheI[from];
					// c_time = newCacheTime[from];
					// }
					for (t = 0; t < tmpData.length; t++) {
						d = to + t;
						newRGB[d] = c;
						// if (useCache)
						// {
						// newCacheR[d] = c_xr;
						// newCacheI[d] = c_xi;
						// newCacheTime[d] = c_time;
						// }
					}
					s += 1;
				}
				// reallocY[i].isCached = false;
				reallocY[i].dirty = false;
			}
			// Thread.yield();
		}
	}

	private void renderLine(final XaosRealloc realloc, final XaosRealloc[] reallocX, final XaosRealloc[] reallocY) {
		if (XaosConstants.PRINT_CALCULATE) {
			AbstractMandelbrotRenderer.logger.fine("Calculate line " + realloc.pos);
		}
		final int rowsize = getBufferWidth();
		final double position = realloc.position;
		final int r = realloc.pos;
		int offset = r * rowsize;
		int i;
		int j;
		int k;
		int n;
		double n_zr = 0;
		double n_zi = 0;
		double n_tr = 0;
		double n_ti = 0;
		int n_time = 0;
		int distl = 0;
		int distr = 0;
		int distu = 0;
		int distd = 0;
		int offsetu;
		int offsetd;
		int offsetl;
		int offsetul;
		int offsetur;
		int offsetdl;
		int offsetdr;
		int rend = r - XaosConstants.GUESS_RANGE;
		final Complex z = new Complex(0, 0);
		final Complex w = new Complex(0, 0);
		final RenderedPoint p = new RenderedPoint();
		final int[] newRGB = renderedData.newRGB;
		final double[] newCacheZR = renderedData.newCacheZR;
		final double[] newCacheZI = renderedData.newCacheZI;
		final double[] newCacheTR = renderedData.newCacheTR;
		final double[] newCacheTI = renderedData.newCacheTI;
		final int[] newCacheTime = renderedData.newCacheTime;
		if (rend < 0) {
			rend = 0;
		}
		for (i = r - 1; (i >= rend) && reallocY[i].dirty; i--) {
			;
		}
		distu = r - i;
		rend = r + XaosConstants.GUESS_RANGE;
		if (rend >= reallocY.length) {
			rend = reallocY.length - 1;
		}
		for (j = r + 1; (j < rend) && reallocY[j].dirty; j++) {
			;
		}
		distd = j - r;
		if ((!isSolidguessSupported) || (i < 0) || (j >= reallocY.length) || reallocY[i].dirty || reallocY[j].dirty) {
			for (k = 0; k < reallocX.length; k++) {
				if (!reallocX[k].dirty) {
					z.r = renderedData.x0;
					z.i = renderedData.y0;
					w.r = reallocX[k].position;
					w.i = position;
					p.pr = reallocX[k].position;
					p.pi = position;
					newRGB[offset] = renderingStrategy.renderPoint(p, z, w);
					if (useCache) {
						newCacheZR[offset] = p.zr;
						newCacheZI[offset] = p.zi;
						newCacheTR[offset] = p.tr;
						newCacheTI[offset] = p.ti;
						newCacheTime[offset] = p.time;
					}
					if (XaosConstants.SHOW_CALCULATE) {
						newRGB[offset] = Colors.mixColors(newRGB[offset], 0xFFFFFF00, 127);
					}
				}
				offset += 1;
			}
		}
		else {
			distr = 0;
			distl = Integer.MAX_VALUE / 2;
			offsetu = offset - (distu * rowsize);
			offsetd = offset + (distd * rowsize);
			for (k = 0; k < reallocX.length; k++) {
				if (!reallocX[k].dirty) {
					if (distr <= 0) {
						rend = k + XaosConstants.GUESS_RANGE;
						if (rend >= reallocX.length) {
							rend = reallocX.length - 1;
						}
						for (j = k + 1; (j < rend) && reallocX[j].dirty; j++) {
							distr = j - k;
						}
						if (j >= rend) {
							distr = Integer.MAX_VALUE / 2;
						}
					}
					if ((distr < (Integer.MAX_VALUE / 4)) && (distl < (Integer.MAX_VALUE / 4))) {
						offsetl = offset - distl;
						offsetul = offsetu - distl;
						offsetdl = offsetd - distl;
						offsetur = offsetu + distr;
						offsetdr = offsetd + distr;
						n = newRGB[offsetl];
						if (useCache) {
							n_zr = newCacheZR[offsetl];
							n_zi = newCacheZI[offsetl];
							n_tr = newCacheTR[offsetu];
							n_ti = newCacheTI[offsetu];
							n_time = newCacheTime[offsetl];
						}
						if ((n == newRGB[offsetu]) && (n == newRGB[offsetd]) && (n == newRGB[offsetul]) && (n == newRGB[offsetur]) && (n == newRGB[offsetdl]) && (n == newRGB[offsetdr])) {
							newRGB[offset] = n;
							if (useCache) {
								newCacheZR[offset] = n_zr;
								newCacheZI[offset] = n_zi;
								newCacheTR[offset] = n_tr;
								newCacheTI[offset] = n_ti;
								newCacheTime[offset] = n_time;
							}
							if (XaosConstants.SHOW_SOLIDGUESS) {
								newRGB[offset] = Colors.mixColors(newRGB[offset], 0xFFFF0000, 127);
							}
						}
						else {
							z.r = renderedData.x0;
							z.i = renderedData.y0;
							w.r = reallocX[k].position;
							w.i = position;
							p.pr = reallocX[k].position;
							p.pi = position;
							newRGB[offset] = renderingStrategy.renderPoint(p, z, w);
							if (useCache) {
								newCacheZR[offset] = p.zr;
								newCacheZI[offset] = p.zi;
								newCacheTR[offset] = p.tr;
								newCacheTI[offset] = p.ti;
								newCacheTime[offset] = p.time;
							}
							if (XaosConstants.SHOW_CALCULATE) {
								newRGB[offset] = Colors.mixColors(newRGB[offset], 0xFFFFFF00, 127);
							}
						}
					}
					else {
						z.r = renderedData.x0;
						z.i = renderedData.y0;
						w.r = reallocX[k].position;
						w.i = position;
						p.pr = reallocX[k].position;
						p.pi = position;
						newRGB[offset] = renderingStrategy.renderPoint(p, z, w);
						if (useCache) {
							newCacheZR[offset] = p.zr;
							newCacheZI[offset] = p.zi;
							newCacheTR[offset] = p.tr;
							newCacheTI[offset] = p.ti;
							newCacheTime[offset] = p.time;
						}
						if (XaosConstants.SHOW_CALCULATE) {
							newRGB[offset] = Colors.mixColors(newRGB[offset], 0xFFFFFF00, 127);
						}
					}
					distl = 0;
				}
				offset += 1;
				offsetu += 1;
				offsetd += 1;
				distr -= 1;
				distl += 1;
			}
		}
		realloc.recalculate = false;
		realloc.refreshed = true;
		realloc.dirty = false;
		realloc.isCached = useCache;
	}

	private void renderColumn(final XaosRealloc realloc, final XaosRealloc[] reallocX, final XaosRealloc[] reallocY) {
		if (XaosConstants.PRINT_CALCULATE) {
			AbstractMandelbrotRenderer.logger.fine("Calculate column " + realloc.pos);
		}
		final int rowsize = getBufferWidth();
		final double position = realloc.position;
		final int r = realloc.pos;
		int offset = r;
		int rend = r - XaosConstants.GUESS_RANGE;
		int i;
		int j;
		int k;
		int n;
		double n_zr = 0;
		double n_zi = 0;
		double n_tr = 0;
		double n_ti = 0;
		int n_time = 0;
		int distl = 0;
		int distr = 0;
		int distu = 0;
		int distd = 0;
		int offsetl;
		int offsetr;
		int offsetu;
		int offsetlu;
		int offsetru;
		int offsetld;
		int offsetrd;
		int sumu;
		int sumd;
		final Complex z = new Complex(0, 0);
		final Complex w = new Complex(0, 0);
		final RenderedPoint p = new RenderedPoint();
		final int[] newRGB = renderedData.newRGB;
		final double[] newCacheZR = renderedData.newCacheZR;
		final double[] newCacheZI = renderedData.newCacheZI;
		final double[] newCacheTR = renderedData.newCacheTR;
		final double[] newCacheTI = renderedData.newCacheTI;
		final int[] newCacheTime = renderedData.newCacheTime;
		if (rend < 0) {
			rend = 0;
		}
		for (i = r - 1; (i >= rend) && reallocX[i].dirty; i--) {
			;
		}
		distl = r - i;
		rend = r + XaosConstants.GUESS_RANGE;
		if (rend >= reallocX.length) {
			rend = reallocX.length - 1;
		}
		for (j = r + 1; (j < rend) && reallocX[j].dirty; j++) {
			;
		}
		distr = j - r;
		if ((!isSolidguessSupported) || (i < 0) || (j >= reallocX.length) || reallocX[i].dirty || reallocX[j].dirty) {
			for (k = 0; k < reallocY.length; k++) {
				if (!reallocY[k].dirty) {
					z.r = renderedData.x0;
					z.i = renderedData.y0;
					w.r = position;
					w.i = reallocY[k].position;
					p.pr = position;
					p.pi = reallocY[k].position;
					newRGB[offset] = renderingStrategy.renderPoint(p, z, w);
					if (useCache) {
						newCacheZR[offset] = p.zr;
						newCacheZI[offset] = p.zi;
						newCacheTR[offset] = p.tr;
						newCacheTI[offset] = p.ti;
						newCacheTime[offset] = p.time;
					}
					if (XaosConstants.SHOW_CALCULATE) {
						newRGB[offset] = Colors.mixColors(newRGB[offset], 0xFFFFFF00, 127);
					}
				}
				offset += rowsize;
			}
		}
		else {
			distd = 0;
			distu = Integer.MAX_VALUE / 2;
			offsetl = offset - distl;
			offsetr = offset + distr;
			for (k = 0; k < reallocY.length; k++) {
				if (!reallocY[k].dirty) {
					if (distd <= 0) {
						rend = k + XaosConstants.GUESS_RANGE;
						if (rend >= reallocY.length) {
							rend = reallocY.length - 1;
						}
						for (j = k + 1; (j < rend) && reallocY[j].dirty; j++) {
							distd = j - k;
						}
						if (j >= rend) {
							distd = Integer.MAX_VALUE / 2;
						}
					}
					if ((distd < (Integer.MAX_VALUE / 4)) && (distu < (Integer.MAX_VALUE / 4))) {
						sumu = distu * rowsize;
						sumd = distd * rowsize;
						offsetu = offset - sumu;
						offsetlu = offsetl - sumu;
						offsetru = offsetr - sumu;
						offsetld = offsetl + sumd;
						offsetrd = offsetr + sumd;
						n = newRGB[offsetu];
						if (useCache) {
							n_zr = newCacheZR[offsetu];
							n_zi = newCacheZI[offsetu];
							n_tr = newCacheTR[offsetu];
							n_ti = newCacheTI[offsetu];
							n_time = newCacheTime[offsetu];
						}
						if ((n == newRGB[offsetl]) && (n == newRGB[offsetr]) && (n == newRGB[offsetlu]) && (n == newRGB[offsetru]) && (n == newRGB[offsetld]) && (n == newRGB[offsetrd])) {
							newRGB[offset] = n;
							if (useCache) {
								newCacheZR[offset] = n_zr;
								newCacheZI[offset] = n_zi;
								newCacheTR[offset] = n_tr;
								newCacheTI[offset] = n_ti;
								newCacheTime[offset] = n_time;
							}
							if (XaosConstants.SHOW_SOLIDGUESS) {
								newRGB[offset] = Colors.mixColors(newRGB[offset], 0xFFFF0000, 127);
							}
						}
						else {
							z.r = renderedData.x0;
							z.i = renderedData.y0;
							w.r = position;
							w.i = reallocY[k].position;
							p.pr = position;
							p.pi = reallocY[k].position;
							newRGB[offset] = renderingStrategy.renderPoint(p, z, w);
							if (useCache) {
								newCacheZR[offset] = p.zr;
								newCacheZI[offset] = p.zi;
								newCacheTR[offset] = p.tr;
								newCacheTI[offset] = p.ti;
								newCacheTime[offset] = p.time;
							}
							if (XaosConstants.SHOW_CALCULATE) {
								newRGB[offset] = Colors.mixColors(newRGB[offset], 0xFFFFFF00, 127);
							}
						}
					}
					else {
						z.r = renderedData.x0;
						z.i = renderedData.y0;
						w.r = position;
						w.i = reallocY[k].position;
						p.pr = position;
						p.pi = reallocY[k].position;
						newRGB[offset] = renderingStrategy.renderPoint(p, z, w);
						if (useCache) {
							newCacheZR[offset] = p.zr;
							newCacheZI[offset] = p.zi;
							newCacheTR[offset] = p.tr;
							newCacheTI[offset] = p.ti;
							newCacheTime[offset] = p.time;
						}
						if (XaosConstants.SHOW_CALCULATE) {
							newRGB[offset] = Colors.mixColors(newRGB[offset], 0xFFFFFF00, 127);
						}
					}
					distu = 0;
				}
				offset += rowsize;
				offsetl += rowsize;
				offsetr += rowsize;
				distd -= 1;
				distu += 1;
			}
		}
		realloc.recalculate = false;
		realloc.refreshed = true;
		realloc.dirty = false;
		realloc.isCached = useCache;
	}

	private void refreshLine(final XaosRealloc realloc, final XaosRealloc[] reallocX, final XaosRealloc[] reallocY) {
		if (XaosConstants.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Refresh line...");
		}
		final int rowsize = getBufferWidth();
		int offset = realloc.pos * rowsize;
		int k = 0;
		final RenderedPoint p = new RenderedPoint();
		final int[] newRGB = renderedData.newRGB;
		final double[] newCacheZR = renderedData.newCacheZR;
		final double[] newCacheZI = renderedData.newCacheZI;
		final double[] newCacheTR = renderedData.newCacheTR;
		final double[] newCacheTI = renderedData.newCacheTI;
		final int[] newCacheTime = renderedData.newCacheTime;
		if (realloc.isCached && !realloc.refreshed) {
			for (final XaosRealloc tmpRealloc : reallocX) {
				if (tmpRealloc.isCached && !tmpRealloc.refreshed) {
					k = offset;
					p.zr = newCacheZR[k];
					p.zi = newCacheZI[k];
					p.tr = newCacheTR[k];
					p.ti = newCacheTI[k];
					p.time = newCacheTime[k];
					p.pr = tmpRealloc.position;
					p.pi = realloc.position;
					newRGB[k] = renderColor(p);
					if (XaosConstants.SHOW_REFRESH) {
						newRGB[k] = Colors.mixColors(newRGB[k], 0xFF0000FF, 127);
					}
				}
				offset += 1;
			}
			realloc.refreshed = true;
		}
	}

	private void refreshColumn(final XaosRealloc realloc, final XaosRealloc[] reallocX, final XaosRealloc[] reallocY) {
		if (XaosConstants.DUMP) {
			AbstractMandelbrotRenderer.logger.fine("Refresh column...");
		}
		final int rowsize = getBufferWidth();
		int offset = realloc.pos;
		int k = 0;
		final RenderedPoint p = new RenderedPoint();
		final int[] newRGB = renderedData.newRGB;
		final double[] newCacheZR = renderedData.newCacheZR;
		final double[] newCacheZI = renderedData.newCacheZI;
		final double[] newCacheTR = renderedData.newCacheTR;
		final double[] newCacheTI = renderedData.newCacheTI;
		final int[] newCacheTime = renderedData.newCacheTime;
		if (realloc.isCached && !realloc.refreshed) {
			for (final XaosRealloc tmpRealloc : reallocY) {
				if (tmpRealloc.isCached && !tmpRealloc.refreshed) {
					k = offset;
					p.zr = newCacheZR[k];
					p.zi = newCacheZI[k];
					p.tr = newCacheTR[k];
					p.ti = newCacheTI[k];
					p.time = newCacheTime[k];
					p.pr = realloc.position;
					p.pi = tmpRealloc.position;
					newRGB[k] = renderColor(p);
					if (XaosConstants.SHOW_REFRESH) {
						newRGB[k] = Colors.mixColors(newRGB[k], 0xFF0000FF, 127);
					}
				}
				offset += rowsize;
			}
			realloc.refreshed = true;
		}
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
		@Override
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
			@Override
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
		@Override
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
			@Override
			public String toString() {
				return "<from = " + from + ", to = " + to + ", length = " + length + ">";
			}
		}
	}

	private class MandelbrotRenderingStrategy implements RenderingStrategy {
		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer.RenderingStrategy#isVerticalSymetrySupported()
		 */
		@Override
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
		@Override
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
		@Override
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
				return BestXaosMandelbrotRenderer.this.renderPoint(p);
			}
			return 0;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer.RenderingStrategy#updateParameters()
		 */
		@Override
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
		@Override
		public boolean isHorizontalSymetrySupported() {
			return false;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer.RenderingStrategy#isVerticalSymetrySupported()
		 */
		@Override
		public boolean isVerticalSymetrySupported() {
			return false;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer.RenderingStrategy#renderPoint(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
		 */
		@Override
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
				return BestXaosMandelbrotRenderer.this.renderPoint(p);
			}
			return 0;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.AbstractMandelbrotRenderer.RenderingStrategy#updateParameters()
		 */
		@Override
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
