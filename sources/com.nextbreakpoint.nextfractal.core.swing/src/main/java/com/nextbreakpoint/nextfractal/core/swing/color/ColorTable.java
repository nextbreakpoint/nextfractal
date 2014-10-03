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
package com.nextbreakpoint.nextfractal.core.swing.color;

import javax.swing.JComponent;

/**
 * @author Andrea Medeghini
 */
public class ColorTable extends JComponent {
	private static final long serialVersionUID = 1L;
	private ColorTableModel model;
	private boolean showColorEnabled = true;

	/**
	 * @param width
	 * @param height
	 * @param horizontal
	 */
	public ColorTable(final int width, final int height, final boolean horizontal) {
		this(new DefaultColorTableModel(width, height, horizontal));
	}

	/**
	 * @param model
	 */
	public ColorTable(final ColorTableModel model) {
		setModel(model);
		setUI(new ColorTableUI());
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
	public ColorTableModel getModel() {
		return model;
	}

	/**
	 * @param model
	 */
	public void setModel(final ColorTableModel model) {
		if (model == null) {
			throw new NullPointerException("model == null");
		}
		this.model = model;
	}

	/**
	 * @param listener
	 */
	public void addColorChangeListener(final ColorChangeListener listener) {
		model.addColorChangeListener(listener);
	}

	/**
	 * @param listener
	 */
	public void removeColorChangeListener(final ColorChangeListener listener) {
		model.removeColorChangeListener(listener);
	}

	/**
	 * @return
	 */
	public boolean isShowColorEnabled() {
		return showColorEnabled;
	}

	/**
	 * @param showColorEnabled
	 */
	public void setShowColorEnabled(final boolean showColorEnabled) {
		this.showColorEnabled = showColorEnabled;
	}
}
