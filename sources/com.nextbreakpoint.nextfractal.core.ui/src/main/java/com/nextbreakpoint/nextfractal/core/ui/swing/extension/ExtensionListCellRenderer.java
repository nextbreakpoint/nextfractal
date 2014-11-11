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
package com.nextbreakpoint.nextfractal.core.ui.swing.extension;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.Extension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.NullConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.NullExtension;

/**
 * A cell renderer for extensions lists.
 * 
 * @author Andrea Medeghini
 */
public class ExtensionListCellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;

	/**
	 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
		if ((value != null) && (value instanceof NullConfigurableExtension)) {
			return super.getListCellRendererComponent(list, "-", index, isSelected, cellHasFocus);
		}
		else if ((value != null) && (value instanceof NullExtension)) {
			return super.getListCellRendererComponent(list, "-", index, isSelected, cellHasFocus);
		}
		else if ((value != null) && (value instanceof ConfigurableExtension<?, ?>)) {
			return super.getListCellRendererComponent(list, ((Extension<?>) value).getExtensionName(), index, isSelected, cellHasFocus);
		}
		else if ((value != null) && (value instanceof Extension<?>)) {
			return super.getListCellRendererComponent(list, ((Extension<?>) value).getExtensionName(), index, isSelected, cellHasFocus);
		}
		else {
			return super.getListCellRendererComponent(list, "-", index, isSelected, cellHasFocus);
		}
	}
}
