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
package com.nextbreakpoint.nextfractal.mandelbrot.ui.swing.palette;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import com.nextbreakpoint.nextfractal.core.ui.swing.palette.TransferablePalette;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPalette;

/**
 * @author Andrea Medeghini
 */
public class TransferableRenderedPalette implements Transferable {
	/**
	 * 
	 */
	public static final DataFlavor RENDERED_PALETTE_FLAVOR = new DataFlavor(RenderedPalette.class, "renderedPalette");
	private static final DataFlavor[] flavors = new DataFlavor[] { TransferableRenderedPalette.RENDERED_PALETTE_FLAVOR, TransferablePalette.PALETTE_FLAVOR };
	private RenderedPalette palette;

	/**
	 * @param palette
	 */
	public TransferableRenderedPalette(final RenderedPalette palette) {
		this.palette = palette;
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		palette = null;
		super.finalize();
	}

	/**
	 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 */
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return TransferableRenderedPalette.flavors;
	}

	/**
	 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
	 */
	@Override
	public boolean isDataFlavorSupported(final DataFlavor flavor) {
		for (final DataFlavor validFlavor : getTransferDataFlavors()) {
			if (validFlavor.equals(flavor)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
	 */
	@Override
	public Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (isDataFlavorSupported(flavor)) {
			return palette;
		}
		throw new UnsupportedFlavorException(flavor);
	}
}
