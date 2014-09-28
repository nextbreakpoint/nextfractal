/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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
package com.nextbreakpoint.nextfractal.twister.swing;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import com.nextbreakpoint.nextfractal.twister.TwisterClip;

/**
 * @author Andrea Medeghini
 */
public class TransferableTwisterClip implements Transferable {
	/**
	 * 
	 */
	public static final DataFlavor TWISTER_CLIP_FLAVOR = new DataFlavor(TwisterClip.class, "twisterClip");
	private static final DataFlavor[] flavors = new DataFlavor[] { TransferableTwisterClip.TWISTER_CLIP_FLAVOR };
	private TwisterClip clip;

	/**
	 * @param clip
	 */
	public TransferableTwisterClip(final TwisterClip clip) {
		this.clip = clip;
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		clip = null;
		super.finalize();
	}

	/**
	 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 */
	public DataFlavor[] getTransferDataFlavors() {
		return TransferableTwisterClip.flavors;
	}

	/**
	 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
	 */
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
	public Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (isDataFlavorSupported(flavor)) {
			return clip;
		}
		throw new UnsupportedFlavorException(flavor);
	}
}
