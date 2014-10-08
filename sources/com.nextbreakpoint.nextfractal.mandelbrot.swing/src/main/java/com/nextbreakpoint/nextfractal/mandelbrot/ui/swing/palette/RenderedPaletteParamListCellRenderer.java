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

import java.awt.Component;
import java.text.MessageFormat;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.nextbreakpoint.nextfractal.mandelbrot.ui.swing.MandelbrotSwingResources;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPaletteParam;

/**
 * @author Andrea Medeghini
 */
public class RenderedPaletteParamListCellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;

	/**
	 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
		if (value != null) {
			if (index < 0) {
				return super.getListCellRendererComponent(list, buildString(list.getSelectedIndex(), value), index, isSelected, cellHasFocus);
			}
			else {
				return super.getListCellRendererComponent(list, buildString(index, value), index, isSelected, cellHasFocus);
			}
		}
		return super.getListCellRendererComponent(list, null, index, isSelected, cellHasFocus);
	}

	/**
	 * @param index
	 * @param value
	 * @return
	 */
	protected String buildString(final int index, final Object value) {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(MandelbrotSwingResources.getInstance().getString("label.block"));
		buffer.append(" ");
		buffer.append(index);
		buffer.append(" [ #");
		buffer.append(Integer.toHexString(((RenderedPaletteParam) value).getColor(0)).toUpperCase());
		buffer.append(", #");
		buffer.append(Integer.toHexString(((RenderedPaletteParam) value).getColor(1)).toUpperCase());
		buffer.append(", ");
		buffer.append(MessageFormat.format("{0,number,0.##}%", ((RenderedPaletteParam) value).getSize()));
		buffer.append(" ]");
		return buffer.toString();
	}
}
