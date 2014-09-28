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
package com.nextbreakpoint.nextfractal.core.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

import javax.swing.JComponent;

/**
 * @author Andrea Medeghini
 */
public class NavigatorLayout implements LayoutManager2 {
	private final int gridW;
	private final int gridH;

	/**
	 * @param gridW
	 * @param gridH
	 */
	public NavigatorLayout(final int gridW, final int gridH) {
		this.gridW = gridW;
		this.gridH = gridH;
	}

	/**
	 * @see java.awt.LayoutManager2#getLayoutAlignmentX(java.awt.Container)
	 */
	public float getLayoutAlignmentX(final Container target) {
		return 0;
	}

	/**
	 * @see java.awt.LayoutManager2#getLayoutAlignmentY(java.awt.Container)
	 */
	public float getLayoutAlignmentY(final Container target) {
		return 0;
	}

	/**
	 * @see java.awt.LayoutManager2#invalidateLayout(java.awt.Container)
	 */
	public void invalidateLayout(final Container target) {
	}

	/**
	 * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component, java.lang.Object)
	 */
	public void addLayoutComponent(final Component comp, final Object constraints) {
	}

	/**
	 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
	 */
	public void addLayoutComponent(final String name, final Component comp) {
	}

	/**
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	public void removeLayoutComponent(final Component comp) {
	}

	/**
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	public void layoutContainer(final Container parent) {
		final Insets insets = parent.getInsets();
		final int width = gridW * (parent.getComponentCount() > 4 ? 4 : parent.getComponentCount());
		int x = insets.left;
		int y = insets.top;
		for (int i = 0; i < parent.getComponentCount(); i++) {
			final JComponent c = (JComponent) parent.getComponent(i);
			c.setBounds(x, y, gridW, gridH);
			x += gridW;
			if (x >= width) {
				x = insets.left;
				y += gridH;
			}
		}
	}

	/**
	 * @see java.awt.LayoutManager2#maximumLayoutSize(java.awt.Container)
	 */
	public Dimension maximumLayoutSize(final Container parent) {
		return preferredLayoutSize(parent);
	}

	/**
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	public Dimension minimumLayoutSize(final Container parent) {
		return preferredLayoutSize(parent);
	}

	/**
	 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
	 */
	public Dimension preferredLayoutSize(final Container parent) {
		final Dimension size = new Dimension();
		final Insets insets = parent.getInsets();
		final int width = gridW * (parent.getComponentCount() > 4 ? 4 : parent.getComponentCount());
		size.width = width + insets.left + insets.right + 20;
		size.height = gridH + insets.top + insets.bottom + 20;
		int x = 0;
		for (int i = 0; i < parent.getComponentCount(); i++) {
			x += gridW;
			if (x >= width) {
				x = 0;
				if (i != parent.getComponentCount() - 1) {
					size.height += gridH;
				}
			}
		}
		return size;
	}
}
