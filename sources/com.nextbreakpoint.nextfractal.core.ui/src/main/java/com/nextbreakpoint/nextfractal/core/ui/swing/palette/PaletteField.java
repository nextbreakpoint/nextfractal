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
package com.nextbreakpoint.nextfractal.core.ui.swing.palette;

import javax.swing.JComponent;

import com.nextbreakpoint.nextfractal.core.util.Palette;

/**
 * @author Andrea Medeghini
 */
public class PaletteField extends JComponent {
	private static final long serialVersionUID = 1L;
	private PaletteFieldModel model;
	private boolean dropEnabled = true;
	private boolean dragEnabled = true;

	/**
	 * @param model
	 */
	public PaletteField(final PaletteFieldModel model) {
		setModel(model);
		setUI(new PaletteFieldUI());
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		model = null;
		super.finalize();
	}

	/**
	 * @return
	 */
	public Palette getPalette() {
		return model.getPalette();
	}

	/**
	 * @return
	 */
	public PaletteFieldModel getModel() {
		return model;
	}

	/**
	 * @param model
	 */
	public void setModel(final PaletteFieldModel model) {
		if (model == null) {
			throw new NullPointerException("model == null");
		}
		this.model = model;
	}

	/**
	 * @return
	 */
	public boolean isDragEnabled() {
		return dragEnabled;
	}

	/**
	 * @param dragEnabled
	 */
	public void setDragEnabled(final boolean dragEnabled) {
		this.dragEnabled = dragEnabled;
	}

	/**
	 * @return
	 */
	public boolean isDropEnabled() {
		return dropEnabled;
	}

	/**
	 * @param dropEnabled
	 */
	public void setDropEnabled(final boolean dropEnabled) {
		this.dropEnabled = dropEnabled;
	}

	/**
	 * @param listener
	 */
	public void addPaletteChangeListener(final PaletteChangeListener listener) {
		model.addPaletteChangeListener(listener);
	}

	/**
	 * @param listener
	 */
	public void removePaletteChangeListener(final PaletteChangeListener listener) {
		model.removePaletteChangeListener(listener);
	}
}
