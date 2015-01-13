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
package com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.xaos;

import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.flux.core.Colors;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.Renderer;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererData;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.strategy.JuliaRendererStrategy;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.strategy.MandelbrotRendererStrategy;

/**
 * @author Andrea Medeghini
 */
public final class XaosRenderer extends Renderer {
	protected static final Logger logger = Logger.getLogger(XaosRenderer.class.getName());
	static {
		if (XaosConstants.PRINT_MULTABLE) {
			logger.fine("Multable:");
			for (int i = -XaosConstants.FPRANGE; i < XaosConstants.FPRANGE; i++) {
				logger.fine("i = " + i + ", i * i = " + XaosConstants.MULTABLE[XaosConstants.FPRANGE + i]);
			}
		}
	}
	
	private boolean isSolidguessSupported = true;
	private boolean isVerticalSymetrySupported = true;
	private boolean isHorizontalSymetrySupported = true;
	private final XaosRendererData xaosRendererData;
	private boolean cacheActive;

	/**
	 * @param rendererDelegate
	 * @param rendererStrategy
	 * @param width
	 * @param height
	 */
	public XaosRenderer(ThreadFactory threadFactory, int width, int height) {
		super(threadFactory, width, height);
		this.xaosRendererData = (XaosRendererData)rendererData;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.Renderer#createRendererData()
	 */
	@Override
	protected RendererData createRendererData() {
		return new XaosRendererData();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.Renderer#doRender(boolean, int)
	 */
	@Override
	protected void doRender(final boolean dynamic, final int mode) {
		if (rendererFractal == null) {
			progress = 1;
			return;
		}
		aborted = false;
		rendererFractal.setConstant(constant);
		if (julia) {
			rendererStrategy = new JuliaRendererStrategy(rendererFractal);
		} else {
			rendererStrategy = new MandelbrotRendererStrategy(rendererFractal);
		}
		rendererStrategy.prepare();
		rendererData.setRegion(rendererFractal.getRegion());
		//rendererData.initPositions();
		if (XaosConstants.PRINT_REGION) {
			logger.fine("Region: (" + xaosRendererData.left() + "," + xaosRendererData.right() + ") -> (" + xaosRendererData.left() + "," + xaosRendererData.right() + ")");
		}
		cacheActive = (mode & Renderer.MODE_REFRESH) != 0 && !dynamic;
		isSolidguessSupported = XaosConstants.USE_SOLIDGUESS && rendererStrategy.isSolidGuessSupported();
		isVerticalSymetrySupported = XaosConstants.USE_SYMETRY && rendererStrategy.isVerticalSymetrySupported();
		isHorizontalSymetrySupported = XaosConstants.USE_SYMETRY && rendererStrategy.isHorizontalSymetrySupported();
		if (XaosConstants.DUMP) {
			logger.fine("Solidguess supported = " + isSolidguessSupported);
			logger.fine("Vertical symetry supported = " + isVerticalSymetrySupported);
			logger.fine("Horizontal symetry supported = " + isHorizontalSymetrySupported);
		}
		prepareLines(mode);
		prepareColumns(mode);
//		if (XaosConstants.USE_MULTITHREAD && !XaosConstants.DUMP_XAOS) {
//			prepareLines(mode);
//			prepareColumns(mode);
//		}
//		else {
//			prepareLines(mode);
//			prepareColumns(mode);
//		}
		if (XaosConstants.PRINT_REALLOCTABLE) {
			logger.fine("ReallocTable:");
			for (final XaosRealloc element : xaosRendererData.reallocX()) {
				logger.fine(element.toString());
			}
			logger.fine("ReallocTable:");
			for (final XaosRealloc element : xaosRendererData.reallocY()) {
				logger.fine(element.toString());
			}
		}
		xaosRendererData.swap();
		move();
		processReallocTable(dynamic, (mode & Renderer.MODE_REFRESH) != 0);
		updatePositions();
	}

	private void prepareLines(int mode) {
		final double beginy = xaosRendererData.bottom();
		final double endy = xaosRendererData.top();
		double stepy = 0;
		if (((mode & Renderer.MODE_CALCULATE) == 0) && XaosConstants.USE_XAOS) {
			stepy = XaosRenderer.makeReallocTable(xaosRendererData.reallocY(), xaosRendererData.dynamicY(), xaosRendererData.bottom(), xaosRendererData.top(), xaosRendererData.positionY(), !cacheActive);
		}
		else {
			stepy = XaosRenderer.initReallocTableAndPosition(xaosRendererData.reallocY(), xaosRendererData.positionY(), beginy, endy);
		}
		final double symy = rendererStrategy.getVerticalSymetryPoint();
		if (isVerticalSymetrySupported && rendererStrategy.isVerticalSymetrySupported() && (!((beginy > symy) || (symy > endy)))) {
			XaosRenderer.prepareSymetry(xaosRendererData.reallocY(), (int) ((symy - beginy) / stepy), symy, stepy);
		}
	}

	private void prepareColumns(int mode) {
		final double beginx = xaosRendererData.left();
		final double endx = xaosRendererData.right();
		double stepy = 0;
		if (((mode & Renderer.MODE_CALCULATE) == 0) && XaosConstants.USE_XAOS) {
			stepy = XaosRenderer.makeReallocTable(xaosRendererData.reallocX(), xaosRendererData.dynamicX(), beginx, endx, xaosRendererData.positionX(), !cacheActive);
		}
		else {
			stepy = XaosRenderer.initReallocTableAndPosition(xaosRendererData.reallocX(), xaosRendererData.positionX(), beginx, endx);
		}
		final double symy = rendererStrategy.getHorizontalSymetryPoint();
		if (isVerticalSymetrySupported && rendererStrategy.isVerticalSymetrySupported() && (!((beginx > symy) || (symy > endx)))) {
			XaosRenderer.prepareSymetry(xaosRendererData.reallocY(), (int) ((symy - beginx) / stepy), symy, stepy);
		}
	}

	private static double initReallocTableAndPosition(final XaosRealloc[] realloc, final double[] position, final double begin, final double end) {
		if (XaosConstants.DUMP) {
			logger.fine("Init ReallocTable and position...");
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

	private void updatePositions() {
		if (XaosConstants.DUMP) {
			logger.fine("Update positions...");
		}
		for (int k = 0; k < xaosRendererData.reallocX().length; k++) {
			xaosRendererData.setPositionX(k, xaosRendererData.reallocX()[k].position);
		}
		for (int k = 0; k < xaosRendererData.reallocY().length; k++) {
			xaosRendererData.setPositionY(k, xaosRendererData.reallocY()[k].position);
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
			XaosRenderer.addPrices(realloc, r1, r3);
			// XaosFractalRenderer.addPrices(realloc, r3 + 1, r2);
			r1 = r3 + 1;
		}
	}

	private static void prepareSymetry(final XaosRealloc[] realloc, final int symi, double symPosition, final double step) {
		if (XaosConstants.DUMP) {
			logger.fine("Prepare symetry...");
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

	private static void prepareMove(final XaosChunkTable movetable, final XaosRealloc[] reallocX) {
		if (XaosConstants.DUMP) {
			logger.fine("Prepare move...");
		}
		final XaosChunk[] table = movetable.data;
		XaosChunk tmpData = null;
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
			logger.fine("Movetable:");
			for (i = 0; table[i].length > 0; i++) {
				logger.fine("i = " + i + " " + table[i].toString());
			}
		}
	}

	private static void prepareFill(final XaosChunkTable filltable, final XaosRealloc[] reallocX) {
		if (XaosConstants.DUMP) {
			logger.fine("Prepare fill...");
		}
		final XaosChunk[] table = filltable.data;
		XaosChunk tmpData = null;
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
			logger.fine("XaosChunkTable:");
			for (i = 0; table[i].length > 0; i++) {
				logger.fine("i = " + i + " " + table[i].toString());
			}
		}
	}

	private static double makeReallocTable(final XaosRealloc[] realloc, final XaosDynamic dynamic, final double begin, final double end, final double[] position, final boolean invalidate) {
		if (XaosConstants.DUMP) {
			logger.fine("Make ReallocTable...");
		}
		XaosRealloc tmpRealloc = null;
		XaosPrice prevData = null;
		XaosPrice bestData = null;
		XaosPrice tmpData = null;
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
			logger.fine("positions (fixed point):");
			for (i = 0; i < size; i++) {
				logger.fine(String.valueOf(delta[i]));
			}
		}
		for (i = 0; i < size; i++) {
			dynamic.swap();
			yend = y - XaosConstants.FPRANGE;
			if (XaosConstants.DUMP_XAOS) {
				logger.fine("a0) yend = " + yend);
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
				logger.fine("a1) yend = " + yend);
			}
			if (XaosConstants.DUMP_XAOS) {
				logger.fine("b0) i = " + i + ", y = " + y + ", ps1 = " + ps1 + ", ps = " + ps + ", pe = " + pe);
			}
			if ((ps != pe) && (p > ps)) {
				if (p < pe) {
					prevData = dynamic.oldBest[p - 1];
					if (XaosConstants.DUMP_XAOS) {
						logger.fine("c0) previous = " + prevData.toString());
					}
				}
				else {
					prevData = dynamic.oldBest[pe - 1];
					if (XaosConstants.DUMP_XAOS) {
						logger.fine("c1) previous = " + prevData.toString());
					}
				}
				price1 = prevData.price;
			}
			else {
				if (i > 0) {
					prevData = dynamic.calData[i - 1];
					price1 = prevData.price;
					if (XaosConstants.DUMP_XAOS) {
						logger.fine("c2) previous = " + prevData.toString());
					}
				}
				else {
					prevData = null;
					price1 = 0;
					if (XaosConstants.DUMP_XAOS) {
						logger.fine("c3) previous = null");
					}
				}
			}
			tmpData = dynamic.calData[i];
			price = price1 + XaosConstants.NEW_PRICE;
			if (XaosConstants.DUMP_XAOS) {
				logger.fine("d0) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
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
						price = price1 + XaosRenderer.price(delta[p], y);
						if (XaosConstants.DUMP_XAOS) {
							logger.fine("g0) approximate row/column " + i + " with old row/column " + p + ": price = " + price + " (previous price = " + price1 + ")");
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
						logger.fine("g2) store data: p = " + p + ", bestdata = " + bestData.toString());
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
							logger.fine("h0) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[((p - 1) << XaosConstants.DSIZE) + (i & XaosConstants.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = -1;
							tmpData.previous = prevData;
							if (XaosConstants.DUMP_XAOS) {
								logger.fine("h1) store data: p - 1 = " + (p - 1) + ", bestdata = " + bestData.toString());
							}
							dynamic.newBest[p - 1] = bestData;
							if (XaosConstants.DUMP_XAOS) {
								// Toolbox.println("h2) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
							}
						}
						price = price1 + XaosRenderer.price(delta[p], y);
						if (XaosConstants.DUMP_XAOS) {
							logger.fine("h3) approximate row/column " + i + " with old row/column " + p + ": price = " + price + " (previous price = " + price1 + ")");
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
								logger.fine("h5) store data: p = " + p + ", bestdata = " + bestData.toString());
							}
							dynamic.newBest[p++] = bestData;
							break;
						}
						// }
					}
					if (XaosConstants.DUMP_XAOS) {
						logger.fine("h6) store data: p = " + p + ", bestdata = " + bestData.toString());
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
							logger.fine("i0) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[((p - 1) << XaosConstants.DSIZE) + (i & XaosConstants.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = -1;
							tmpData.previous = prevData;
							if (XaosConstants.DUMP_XAOS) {
								logger.fine("i1) store data: p - 1 = " + (p - 1) + ", bestdata = " + bestData.toString());
							}
							dynamic.newBest[p - 1] = bestData;
							if (XaosConstants.DUMP_XAOS) {
								// Toolbox.println("i2) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
							}
						}
						price = price1 + XaosRenderer.price(delta[p], y);
						if (XaosConstants.DUMP_XAOS) {
							logger.fine("i3) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
						}
						if (price < bestPrice) {
							tmpData = dynamic.conData[(p << XaosConstants.DSIZE) + (i & XaosConstants.MASK)];
							bestData = tmpData;
							bestPrice = price;
							tmpData.price = price;
							tmpData.pos = p;
							tmpData.previous = prevData;
							if (XaosConstants.DUMP_XAOS) {
								logger.fine("i4) bestprice = " + bestPrice + ", bestdata = " + bestData.toString());
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
					logger.fine("l0) add row/column " + i + ": price = " + price + " (previous price = " + price1 + ")");
				}
				if ((price < bestPrice) && (p > ps1)) {
					tmpData = dynamic.conData[((p - 1) << XaosConstants.DSIZE) + (i & XaosConstants.MASK)];
					bestData = tmpData;
					bestPrice = price;
					tmpData.price = price;
					tmpData.pos = -1;
					tmpData.previous = prevData;
					if (XaosConstants.DUMP_XAOS) {
						logger.fine("l1) store data: p - 1 = " + (p - 1) + ", bestdata = " + bestData.toString());
					}
					dynamic.newBest[p - 1] = bestData;
					if (XaosConstants.DUMP_XAOS) {
						// Toolbox.println("l2) bestprice = " + bestprice + ", bestdata = " + bestdata.toString());
					}
				}
				while (delta[p] < yend) {
					if (delta[p] != delta[p + 1]) {
						price = price1 + XaosRenderer.price(delta[p], y);
						if (XaosConstants.DUMP_XAOS) {
							logger.fine("l3) approximate row/column " + i + " with old row/column " + p + ": price = " + price + " (previous price = " + price1 + ")");
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
						logger.fine("l5) store data: p = " + p + ", bestdata = " + bestData.toString());
					}
					dynamic.newBest[p++] = bestData;
				}
				while (delta[p] < yend) {
					if (XaosConstants.DUMP_XAOS) {
						logger.fine("l6) store data: p = " + p + ", bestdata = " + bestData.toString());
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
							logger.fine("e0) previous = " + prevData.toString());
						}
					}
					else {
						prevData = null;
						price1 = 0;
						if (XaosConstants.DUMP_XAOS) {
							logger.fine("e1) previous = null");
						}
					}
					while (delta[p] < yend) {
						if (delta[p] != delta[p + 1]) {
							price = price1 + XaosRenderer.price(delta[p], y);
							if (XaosConstants.DUMP_XAOS) {
								logger.fine("f0) approximate row/column " + i + " with old row/column " + p + ": price = " + price + " (previous price = " + price1 + ")");
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
							logger.fine("f2) store data: p = " + p + ", bestdata = " + bestData.toString());
						}
						dynamic.newBest[p++] = bestData;
					}
					while (delta[p] < yend) {
						if (XaosConstants.DUMP_XAOS) {
							logger.fine("f3) store data: p = " + p + ", bestdata = " + bestData.toString());
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
			logger.fine("flag = " + flag);
		}
		if (XaosConstants.DUMP_XAOS) {
			logger.fine("best table:");
		}
		for (i = size - 1; i >= 0; i--) {
			if (XaosConstants.DUMP_XAOS) {
				logger.fine("data = " + bestData.toString());
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
		XaosRenderer.newPositions(realloc, size, begin, end, step, position, flag);
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
			logger.fine("Positions :");
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
								logger.fine("pos = " + s + ",position = " + tmpRealloc.position + ",price = " + tmpRealloc.priority);
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
								logger.fine("pos = " + s + ",position = " + tmpRealloc.position + ",price = " + tmpRealloc.priority);
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
								logger.fine("pos = " + s + ",position = " + tmpRealloc.position + ",price = " + tmpRealloc.priority);
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
			logger.fine("Process ReallocTable...");
		}
		if (dynamic || !XaosConstants.USE_XAOS) {
			int total = 0;
			total = XaosRenderer.initPrices(xaosRendererData.queue(), total, xaosRendererData.reallocX());
			total = XaosRenderer.initPrices(xaosRendererData.queue(), total, xaosRendererData.reallocY());
			if (XaosConstants.DUMP) {
				logger.fine("total = " + total);
			}
			if (total > 0) {
				if (total > 1) {
					XaosRenderer.sortQueue(xaosRendererData.queue(), 0, total - 1);
				}
				processQueue(total);
			}
			if (XaosConstants.USE_XAOS) {
				processReallocTable(false, refresh);
			}
		}
		else {
			final int[] position = xaosRendererData.position();
			final int[] offset = xaosRendererData.offset();
			position[0] = 1;
			offset[0] = 0;
			int s = 1;
			int i = 0;
			int j = 0;
			int tocalcx = 0;
			int tocalcy = 0;
			XaosRealloc[] tmpRealloc = null;
			tmpRealloc = xaosRendererData.reallocX();
			for (i = 0; i < tmpRealloc.length; i++) {
				if (tmpRealloc[i].recalculate) {
					tocalcx++;
				}
			}
			tmpRealloc = xaosRendererData.reallocY();
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
				tmpRealloc = xaosRendererData.reallocY();
				for (final XaosRealloc element : tmpRealloc) {
					if (element.isCached && !element.refreshed) {
						refreshLine(element, xaosRendererData.reallocX(), xaosRendererData.reallocY());
					}
				}
				tmpRealloc = xaosRendererData.reallocX();
				for (final XaosRealloc element : tmpRealloc) {
					if (element.isCached && !element.refreshed) {
						refreshColumn(element, xaosRendererData.reallocX(), xaosRendererData.reallocY());
					}
				}
			}
			long oldTime = System.currentTimeMillis();
			for (s = 0; !aborted && (s < XaosConstants.STEPS); s++) {
				// AbstractFractalRenderer.logger.fine("step = " + s);
				tmpRealloc = xaosRendererData.reallocY();
				for (i = offset[s]; !aborted && (i < tmpRealloc.length); i += XaosConstants.STEPS) {
					if (tmpRealloc[i].recalculate) {
						renderLine(tmpRealloc[i], xaosRendererData.reallocX(), xaosRendererData.reallocY());
						tocalcy -= 1;
					}
					else if (!tmpRealloc[i].isCached) {
						renderLine(tmpRealloc[i], xaosRendererData.reallocX(), xaosRendererData.reallocY());
					}
					if (isInterrupted()) {
						aborted = true;
						break;
					}
					Thread.yield();
				}
				tmpRealloc = xaosRendererData.reallocX();
				for (i = offset[s]; !aborted && (i < tmpRealloc.length); i += XaosConstants.STEPS) {
					if (tmpRealloc[i].recalculate) {
						renderColumn(tmpRealloc[i], xaosRendererData.reallocX(), xaosRendererData.reallocY());
						tocalcx -= 1;
					}
					else if (!tmpRealloc[i].isCached) {
						renderColumn(tmpRealloc[i], xaosRendererData.reallocX(), xaosRendererData.reallocY());
					}
					if (isInterrupted()) {
						aborted = true;
						break;
					}
					Thread.yield();
				}
				long newTime = System.currentTimeMillis();
				if (!aborted && ((newTime - oldTime) > 50) && (s < XaosConstants.STEPS)) {
					tmpRealloc = xaosRendererData.reallocY();
					for (i = 0; i < tmpRealloc.length; i++) {
						tmpRealloc[i].changeDirty = tmpRealloc[i].dirty;
						tmpRealloc[i].changePosition = tmpRealloc[i].position;
					}
					tmpRealloc = xaosRendererData.reallocX();
					for (i = 0; i < tmpRealloc.length; i++) {
						tmpRealloc[i].changeDirty = tmpRealloc[i].dirty;
						tmpRealloc[i].changePosition = tmpRealloc[i].position;
					}
					progress = (s + 1f) / (float)XaosConstants.STEPS;
					fill();
					if (rendererDelegate != null) {
						rendererDelegate.didChanged(progress, xaosRendererData.getPixels());
					}
					Thread.yield();
					tmpRealloc = xaosRendererData.reallocY();
					for (i = 0; i < tmpRealloc.length; i++) {
						tmpRealloc[i].dirty = tmpRealloc[i].changeDirty;
						tmpRealloc[i].position = tmpRealloc[i].changePosition;
					}
					tmpRealloc = xaosRendererData.reallocX();
					for (i = 0; i < tmpRealloc.length; i++) {
						tmpRealloc[i].dirty = tmpRealloc[i].changeDirty;
						tmpRealloc[i].position = tmpRealloc[i].changePosition;
					}
					oldTime = newTime;
				}
				// if (isInterrupted())
				// {
				// aborted = true;
				// break;
				// }
			}
			if (!aborted) {
				progress = 1f;
			}
		}
		fill();
		if (rendererDelegate != null) {
			rendererDelegate.didChanged(progress, xaosRendererData.getPixels());
		}
		Thread.yield();
	}

	private void move() {
		XaosRenderer.prepareMove(xaosRendererData.moveTable(), xaosRendererData.reallocX());
		doMove(xaosRendererData.moveTable(), xaosRendererData.reallocY());
	}

	private void fill() {
		if (isVerticalSymetrySupported && isHorizontalSymetrySupported) {
			doSymetry(xaosRendererData.reallocX(), xaosRendererData.reallocY());
		}
		XaosRenderer.prepareFill(xaosRendererData.fillTable(), xaosRendererData.reallocX());
		doFill(xaosRendererData.fillTable(), xaosRendererData.reallocY());
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
				XaosRenderer.addPrices(realloc, i, j);
				i = j;
			}
		}
		return total;
	}

	private static void sortQueue(final XaosRealloc[] queue, final int l, final int r) {
		if (XaosConstants.DUMP) {
			logger.fine("Sort queue...");
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
			XaosRenderer.sortQueue(queue, l, j);
		}
		if (r > i) {
			XaosRenderer.sortQueue(queue, i, r);
		}
	}

	private void processQueue(final int size) {
		if (XaosConstants.DUMP) {
			logger.fine("Process queue...");
		}
		int i = 0;
		for (i = 0; i < size; i++) {
			if (xaosRendererData.queue()[i].line) {
				renderLine(xaosRendererData.queue()[i], xaosRendererData.reallocX(), xaosRendererData.reallocY());
			}
			else {
				renderColumn(xaosRendererData.queue()[i], xaosRendererData.reallocX(), xaosRendererData.reallocY());
			}
			if (isInterrupted()) {
				aborted = true;
				break;
			}
			Thread.yield();
		}
	}

	private void doSymetry(final XaosRealloc[] reallocX, final XaosRealloc[] reallocY) {
		if (XaosConstants.DUMP) {
			logger.fine("Do symetry...");
		}
		final int rowsize = width;
		int from_offset = 0;
		int to_offset = 0;
		int i = 0;
		int j = 0;
		for (i = 0; i < reallocY.length; i++) {
			if ((reallocY[i].symTo >= 0) && (!reallocY[reallocY[i].symTo].dirty)) {
				from_offset = reallocY[i].symTo * rowsize;
				xaosRendererData.movePixels(from_offset, to_offset, rowsize);
				if (cacheActive) {
					xaosRendererData.moveCache(from_offset, to_offset, rowsize);
				}
				if (XaosConstants.SHOW_SYMETRY) {
					for (int k = 0; k < rowsize; k++) {
						xaosRendererData.setPixel(to_offset + k, Colors.mixColors(xaosRendererData.getPixel(to_offset + k), 0xFFFF0000, 127));
					}
				}
				reallocY[i].dirty = false;
				reallocY[i].isCached = cacheActive;
			}
			to_offset += rowsize;
			// Thread.yield();
		}
		for (i = 0; i < reallocX.length; i++) {
			if ((reallocX[i].symTo >= 0) && (!reallocX[reallocX[i].symTo].dirty)) {
				to_offset = i;
				from_offset = reallocX[i].symTo;
				for (j = 0; j < reallocY.length; j++) {
					xaosRendererData.movePixels(from_offset, to_offset, 1);
					if (cacheActive) {
						xaosRendererData.moveCache(from_offset, to_offset, 1);
					}
					if (XaosConstants.SHOW_SYMETRY) {
						xaosRendererData.setPixel(to_offset, Colors.mixColors(xaosRendererData.getPixel(to_offset), 0xFFFF0000, 127));
					}
					to_offset += rowsize;
					from_offset += rowsize;
				}
				reallocX[i].dirty = false;
				reallocX[i].isCached = cacheActive;
			}
			// Thread.yield();
		}
	}
	
	private void doMove(final XaosChunkTable movetable, final XaosRealloc[] reallocY) {
		if (XaosConstants.DUMP) {
			logger.fine("Do move...");
		}
		final XaosChunk[] table = movetable.data;
		XaosChunk tmpData = null;
		final int rowsize = width;
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
					xaosRendererData.copyPixels(from, to, tmpData.length);
					if (cacheActive) {
						xaosRendererData.copyCache(from, to, tmpData.length);
					}
					s += 1;
				}
			}
			new_offset += rowsize;
			// Thread.yield();
		}
	}

	private void doFill(final XaosChunkTable filltable, final XaosRealloc[] reallocY) {
		if (XaosConstants.DUMP) {
			logger.fine("Do fill...");
		}
		final XaosChunk[] table = filltable.data;
		XaosChunk tmpData = null;
		final int rowsize = width;
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
						while ((tmpData = table[s]).length > 0) {
							from = from_offset + tmpData.from;
							to = from_offset + tmpData.to;
							c = xaosRendererData.getPixel(from);
							for (t = 0; t < tmpData.length; t++) {
								d = to + t;
								xaosRendererData.setPixel(d, c);
							}
							s += 1;
						}
					}
					xaosRendererData.movePixels(from_offset, to_offset, rowsize);
					reallocY[i].position = reallocY[j].position;
					reallocY[i].dirty = false;
					i += 1;
				}
			}
			else {
				s = 0;
				from_offset = i * rowsize;
				while ((tmpData = table[s]).length > 0) {
					from = from_offset + tmpData.from;
					to = from_offset + tmpData.to;
					c = xaosRendererData.getPixel(from);
					for (t = 0; t < tmpData.length; t++) {
						d = to + t;
						xaosRendererData.setPixel(d, c);
					}
					s += 1;
				}
				reallocY[i].dirty = false;
			}
			// Thread.yield();
		}
	}

	private void renderLine(final XaosRealloc realloc, final XaosRealloc[] reallocX, final XaosRealloc[] reallocY) {
		if (XaosConstants.PRINT_CALCULATE) {
			logger.fine("Calculate line " + realloc.pos);
		}
		final int rowsize = width;
		final double position = realloc.position;
		final int r = realloc.pos;
		int offset = r * rowsize;
		int i;
		int j;
		int k;
		int n;
		int c;
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
		MutableNumber z = new MutableNumber(0, 0);
		MutableNumber w = new MutableNumber(0, 0);
		final RendererPoint p = xaosRendererData.newPoint();
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
					z.set(xaosRendererData.point());
					w.set(reallocX[k].position, position);
//					p.pr = reallocX[k].position;
//					p.pi = position;
					c = rendererStrategy.renderPoint(p, z, w);
					xaosRendererData.setPixel(offset, c);
					xaosRendererData.setPoint(offset, p);
					if (XaosConstants.SHOW_CALCULATE) {
						xaosRendererData.setPixel(offset, Colors.mixColors(xaosRendererData.getPixel(offset), 0xFFFFFF00, 127));
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
						n = xaosRendererData.getPixel(offsetl);
						if (cacheActive) {
							xaosRendererData.getPoint(offset, p);
						}
						if ((n == xaosRendererData.getPixel(offsetu)) && (n == xaosRendererData.getPixel(offsetd)) && (n == xaosRendererData.getPixel(offsetul)) && (n == xaosRendererData.getPixel(offsetur)) && (n == xaosRendererData.getPixel(offsetdl)) && (n == xaosRendererData.getPixel(offsetdr))) {
							xaosRendererData.setPixel(offset, n);
							if (cacheActive) {
								xaosRendererData.setPoint(offset, p);
							}
							if (XaosConstants.SHOW_SOLIDGUESS) {
								xaosRendererData.setPixel(offset, Colors.mixColors(xaosRendererData.getPixel(offset), 0xFFFF0000, 127));
							}
						}
						else {
							z.set(xaosRendererData.point());
							w.set(reallocX[k].position, position);
//							p.pr = reallocX[k].position;
//							p.pi = position;
							c = rendererStrategy.renderPoint(p, z, w);
							xaosRendererData.setPixel(offset, c);
							xaosRendererData.setPoint(offset, p);
							if (XaosConstants.SHOW_CALCULATE) {
								xaosRendererData.setPixel(offset, Colors.mixColors(xaosRendererData.getPixel(offset), 0xFFFFFF00, 127));
							}
						}
					}
					else {
						z.set(xaosRendererData.point());
						w.set(reallocX[k].position, position);
//						p.pr = reallocX[k].position;
//						p.pi = position;
						c = rendererStrategy.renderPoint(p, z, w);
						xaosRendererData.setPixel(offset, c);
						xaosRendererData.setPoint(offset, p);
						if (XaosConstants.SHOW_CALCULATE) {
							xaosRendererData.setPixel(offset, Colors.mixColors(xaosRendererData.getPixel(offset), 0xFFFFFF00, 127));
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
		realloc.isCached = cacheActive;
	}

	private void renderColumn(final XaosRealloc realloc, final XaosRealloc[] reallocX, final XaosRealloc[] reallocY) {
		if (XaosConstants.PRINT_CALCULATE) {
			logger.fine("Calculate column " + realloc.pos);
		}
		final int rowsize = width;
		final double position = realloc.position;
		final int r = realloc.pos;
		int offset = r;
		int rend = r - XaosConstants.GUESS_RANGE;
		int i;
		int j;
		int k;
		int n;
		int c;
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
		MutableNumber z = new MutableNumber(0, 0);
		MutableNumber w = new MutableNumber(0, 0);
		final RendererPoint p = xaosRendererData.newPoint();
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
					z.set(xaosRendererData.point());
					w.set(position, reallocY[k].position);
//					p.pr = position;
//					p.pi = reallocY[k].position;
					c = rendererStrategy.renderPoint(p, z, w);
					xaosRendererData.setPixel(offset, c);
					xaosRendererData.setPoint(offset, p);
					if (XaosConstants.SHOW_CALCULATE) {
						xaosRendererData.setPixel(offset, Colors.mixColors(xaosRendererData.getPixel(offset), 0xFFFFFF00, 127));
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
						n = xaosRendererData.getPixel(offsetu);
						if (cacheActive) {
							xaosRendererData.getPoint(offset, p);
						}
						if ((n == xaosRendererData.getPixel(offsetl)) && (n == xaosRendererData.getPixel(offsetr)) && (n == xaosRendererData.getPixel(offsetlu)) && (n == xaosRendererData.getPixel(offsetru)) && (n == xaosRendererData.getPixel(offsetld)) && (n == xaosRendererData.getPixel(offsetrd))) {
							xaosRendererData.setPixel(offset, n);
							if (cacheActive) {
								xaosRendererData.setPoint(offset, p);
							}
							if (XaosConstants.SHOW_SOLIDGUESS) {
								xaosRendererData.setPixel(offset, Colors.mixColors(xaosRendererData.getPixel(offset), 0xFFFF0000, 127));
							}
						}
						else {
							z.set(xaosRendererData.point());
							w.set(position, reallocY[k].position);
//							p.pr = position;
//							p.pi = reallocY[k].position;
							c = rendererStrategy.renderPoint(p, z, w);
							xaosRendererData.setPixel(offset, c);
							xaosRendererData.setPoint(offset, p);
							if (XaosConstants.SHOW_CALCULATE) {
								xaosRendererData.setPixel(offset, Colors.mixColors(xaosRendererData.getPixel(offset), 0xFFFFFF00, 127));
							}
						}
					}
					else {
						z.set(xaosRendererData.point());
						w.set(position, reallocY[k].position);
//						p.pr = position;
//						p.pi = reallocY[k].position;
						c = rendererStrategy.renderPoint(p, z, w);
						xaosRendererData.setPixel(offset, c);
						xaosRendererData.setPoint(offset, p);
						if (XaosConstants.SHOW_CALCULATE) {
							xaosRendererData.setPixel(offset, Colors.mixColors(xaosRendererData.getPixel(offset), 0xFFFFFF00, 127));
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
		realloc.isCached = cacheActive;
	}

	private void refreshLine(final XaosRealloc realloc, final XaosRealloc[] reallocX, final XaosRealloc[] reallocY) {
		if (XaosConstants.DUMP) {
			logger.fine("Refresh line...");
		}
		final int rowsize = width;
		int offset = realloc.pos * rowsize;
		int k = 0;
		int c = 0;
		RendererPoint p = xaosRendererData.newPoint();
		if (realloc.isCached && !realloc.refreshed) {
			for (final XaosRealloc tmpRealloc : reallocX) {
				if (tmpRealloc.isCached && !tmpRealloc.refreshed) {
					k = offset;
					xaosRendererData.getPoint(offset, p);
					c = rendererStrategy.renderColor(p);
					xaosRendererData.setPixel(k, c);
					if (XaosConstants.SHOW_REFRESH) {
						xaosRendererData.setPixel(k, Colors.mixColors(xaosRendererData.getPixel(k), 0xFF0000FF, 127));
					}
				}
				offset += 1;
			}
			realloc.refreshed = true;
		}
	}

	private void refreshColumn(final XaosRealloc realloc, final XaosRealloc[] reallocX, final XaosRealloc[] reallocY) {
		if (XaosConstants.DUMP) {
			logger.fine("Refresh column...");
		}
		final int rowsize = width;
		int offset = realloc.pos;
		int k = 0;
		int c = 0;
		RendererPoint p = xaosRendererData.newPoint();
		if (realloc.isCached && !realloc.refreshed) {
			for (final XaosRealloc tmpRealloc : reallocY) {
				if (tmpRealloc.isCached && !tmpRealloc.refreshed) {
					k = offset;
					xaosRendererData.getPoint(offset, p);
					c = rendererStrategy.renderColor(p);
					xaosRendererData.setPixel(k, c);
					if (XaosConstants.SHOW_REFRESH) {
						xaosRendererData.setPixel(k, Colors.mixColors(xaosRendererData.getPixel(k), 0xFF0000FF, 127));
					}
				}
				offset += rowsize;
			}
			realloc.refreshed = true;
		}
	}
}
