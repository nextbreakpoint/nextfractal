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
import com.nextbreakpoint.nextfractal.core.util.Palette;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPalette;

/**
 * @author Andrea Medeghini
 */
public class DefaultRenderedPaletteModel implements RenderedPaletteModel {
	private ArrayList<PaletteChangeListener> listeners = new ArrayList<PaletteChangeListener>();
	private RenderedPalette palette;

	/**
	 * @param palette
	 */
	public DefaultRenderedPaletteModel(final RenderedPalette palette) {
		if (palette == null) {
			throw new NullPointerException("palette == null");
		}
		this.palette = palette;
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		palette = null;
		listeners.clear();
		listeners = null;
		super.finalize();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.palette.PaletteFieldModel#addPaletteChangeListener(com.nextbreakpoint.nextfractal.core.swing.palette.PaletteChangeListener)
	 */
	@Override
	public void addPaletteChangeListener(final PaletteChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.palette.PaletteFieldModel#removePaletteChangeListener(com.nextbreakpoint.nextfractal.core.swing.palette.PaletteChangeListener)
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
	 * @see com.nextbreakpoint.nextfractal.core.swing.palette.PaletteFieldModel#setPalette(com.nextbreakpoint.nextfractal.core.util.Palette, boolean)
	 */
	@Override
	public void setPalette(final Palette palette, final boolean isAdjusting) {
		if (palette instanceof RenderedPalette) {
			setRenderedPalette((RenderedPalette) palette, isAdjusting);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.palette.PaletteFieldModel#getPalette()
	 */
	@Override
	public Palette getPalette() {
		return palette;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.swing.palette.RenderedPaletteModel#setRenderedPalette(com.nextbreakpoint.nextfractal.mandelbrot.colorRenderer.RenderedPalette, boolean)
	 */
	@Override
	public void setRenderedPalette(final RenderedPalette palette, final boolean isAdjusting) {
		if (palette == null) {
			throw new NullPointerException("palette == null");
		}
		this.palette = palette;
		firePaletteChangeEvent(new PaletteChangeEvent(this, isAdjusting));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.swing.palette.RenderedPaletteModel#getRenderedPalette()
	 */
	@Override
	public RenderedPalette getRenderedPalette() {
		return palette;
	}
}
