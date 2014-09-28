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
package com.nextbreakpoint.nextfractal.core.swing.color;

import java.awt.Color;

import javax.swing.JComponent;

/**
 * @author Andrea Medeghini
 */
public class ColorField extends JComponent {
	private static final long serialVersionUID = 1L;
	private ColorFieldModel model;
	private boolean dropEnabled = true;
	private boolean dragEnabled = true;

	/**
	 * 
	 */
	public ColorField() {
		this(new DefaultColorFieldModel());
	}

	/**
	 * @param color
	 */
	public ColorField(final Color color) {
		this(new DefaultColorFieldModel());
		model.setColor(color, false);
	}

	/**
	 * @param rgb
	 */
	public ColorField(final int rgb) {
		this(new DefaultColorFieldModel());
		model.setColor(new Color(rgb), false);
	}

	/**
	 * @param model
	 */
	public ColorField(final ColorFieldModel model) {
		setModel(model);
		setUI(new ColorFieldUI());
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
	public Color getColor() {
		return model.getColor();
	}

	/**
	 * @param color
	 */
	public void setColor(final Color color) {
		model.setColor(color, false);
	}

	/**
	 * @return
	 */
	public ColorFieldModel getModel() {
		return model;
	}

	/**
	 * @param model
	 */
	public void setModel(final ColorFieldModel model) {
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
	public void addColorChangeListener(final ColorChangeListener listener) {
		model.addColorChangeListener(listener);
	}

	/**
	 * @param listener
	 */
	public void removeColorChangeListener(final ColorChangeListener listener) {
		model.removeColorChangeListener(listener);
	}
}
