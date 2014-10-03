/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
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
package com.nextbreakpoint.nextfractal.mandelbrot.swing.palette;

import java.util.ArrayList;

import com.nextbreakpoint.nextfractal.core.swing.palette.PaletteChangeEvent;
import com.nextbreakpoint.nextfractal.core.swing.palette.PaletteChangeListener;
import com.nextbreakpoint.nextfractal.mandelbrot.util.DefaultRenderedPaletteParam;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPaletteParam;

/**
 * @author Andrea Medeghini
 */
public class DefaultRenderedPaletteParamModel implements RenderedPaletteParamModel {
	private ArrayList<PaletteChangeListener> listeners = new ArrayList<PaletteChangeListener>();
	private RenderedPaletteParam paletteParam;

	/**
	 * 
	 */
	public DefaultRenderedPaletteParamModel() {
		paletteParam = new DefaultRenderedPaletteParam(100);
	}

	/**
	 * @param paletteParam
	 */
	public DefaultRenderedPaletteParamModel(final RenderedPaletteParam paletteParam) {
		if (paletteParam == null) {
			throw new NullPointerException("paletteParam == null");
		}
		this.paletteParam = paletteParam;
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		paletteParam = null;
		listeners.clear();
		listeners = null;
		super.finalize();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.swing.palette.RenderedPaletteParamModel#addPaletteChangeListener(com.nextbreakpoint.nextfractal.core.swing.palette.PaletteChangeListener)
	 */
	@Override
	public void addPaletteChangeListener(final PaletteChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.swing.palette.RenderedPaletteParamModel#removePaletteChangeListener(com.nextbreakpoint.nextfractal.core.swing.palette.PaletteChangeListener)
	 */
	@Override
	public void removePaletteChangeListener(final PaletteChangeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * @param e
	 */
	protected void firePaletteChangeEvent(final PaletteChangeEvent e) {
		for (final PaletteChangeListener listener : listeners) {
			listener.paletteChanged(e);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.swing.palette.RenderedPaletteParamModel#setPaletteParam(com.nextbreakpoint.nextfractal.mandelbrot.colorRenderer.RenderedPaletteParam, boolean)
	 */
	@Override
	public void setPaletteParam(final RenderedPaletteParam paletteParam, final boolean isAdjusting) {
		if (paletteParam == null) {
			throw new NullPointerException("paletteParam == null");
		}
		this.paletteParam = paletteParam;
		firePaletteChangeEvent(new PaletteChangeEvent(this, isAdjusting));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.swing.palette.RenderedPaletteParamModel#getPaletteParam()
	 */
	@Override
	public RenderedPaletteParam getPaletteParam() {
		return paletteParam;
	}
}
