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
package com.nextbreakpoint.nextfractal.core.swing.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

import javax.swing.JComponent;

/**
 * @author Andrea Medeghini
 */
public class StackLayout implements LayoutManager2 {
	/**
	 * @see java.awt.LayoutManager2#getLayoutAlignmentX(java.awt.Container)
	 */
	@Override
	public float getLayoutAlignmentX(final Container target) {
		return 0;
	}

	/**
	 * @see java.awt.LayoutManager2#getLayoutAlignmentY(java.awt.Container)
	 */
	@Override
	public float getLayoutAlignmentY(final Container target) {
		return 0;
	}

	/**
	 * @see java.awt.LayoutManager2#invalidateLayout(java.awt.Container)
	 */
	@Override
	public void invalidateLayout(final Container target) {
	}

	/**
	 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
	 */
	@Override
	public void addLayoutComponent(final String name, final Component comp) {
	}

	/**
	 * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component, java.lang.Object)
	 */
	@Override
	public void addLayoutComponent(final Component comp, final Object constraints) {
	}

	/**
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	@Override
	public void removeLayoutComponent(final Component comp) {
	}

	/**
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	@Override
	public void layoutContainer(final Container parent) {
		final Insets insets = parent.getInsets();
		final int x = insets.left;
		int y = insets.top;
		int w = parent.getWidth();
		for (int i = 0; i < parent.getComponentCount(); i++) {
			final JComponent c = (JComponent) parent.getComponent(i);
			if (c.isVisible()) {
				w = Math.max(c.getPreferredSize().width, w);
			}
		}
		w = w - insets.left - insets.right;
		// final int h = parent.getHeight() - insets.top - insets.bottom;
		for (int i = 0; i < parent.getComponentCount(); i++) {
			final JComponent c = (JComponent) parent.getComponent(i);
			if (c.isVisible()) {
				c.setBounds(x, y, w, c.getMinimumSize().height);
				y += c.getHeight();
			}
		}
	}

	/**
	 * @see java.awt.LayoutManager2#maximumLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension maximumLayoutSize(final Container parent) {
		final Dimension size = new Dimension();
		final Insets insets = parent.getInsets();
		size.width = 0;
		for (int i = 0; i < parent.getComponentCount(); i++) {
			final JComponent c = (JComponent) parent.getComponent(i);
			if (c.isVisible()) {
				size.width = Math.max(c.getPreferredSize().width, size.width);
			}
		}
		for (int i = 0; i < parent.getComponentCount(); i++) {
			final JComponent c = (JComponent) parent.getComponent(i);
			if (c.isVisible()) {
				size.height += c.getMaximumSize().height;
			}
		}
		size.width = size.width + insets.left + insets.right;
		size.height = size.height + insets.top + insets.bottom;
		return size;
	}

	/**
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension minimumLayoutSize(final Container parent) {
		final Dimension size = new Dimension();
		final Insets insets = parent.getInsets();
		size.width = 0;
		for (int i = 0; i < parent.getComponentCount(); i++) {
			final JComponent c = (JComponent) parent.getComponent(i);
			if (c.isVisible()) {
				size.width = Math.max(c.getPreferredSize().width, size.width);
			}
		}
		for (int i = 0; i < parent.getComponentCount(); i++) {
			final JComponent c = (JComponent) parent.getComponent(i);
			if (c.isVisible()) {
				size.height += c.getMinimumSize().height;
			}
		}
		size.width = size.width + insets.left + insets.right;
		size.height = size.height + insets.top + insets.bottom;
		return size;
	}

	/**
	 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension preferredLayoutSize(final Container parent) {
		final Dimension size = new Dimension();
		final Insets insets = parent.getInsets();
		size.width = 0;
		for (int i = 0; i < parent.getComponentCount(); i++) {
			final JComponent c = (JComponent) parent.getComponent(i);
			if (c.isVisible()) {
				size.width = Math.max(c.getPreferredSize().width, size.width);
			}
		}
		for (int i = 0; i < parent.getComponentCount(); i++) {
			final JComponent c = (JComponent) parent.getComponent(i);
			if (c.isVisible()) {
				size.height += c.getPreferredSize().height;
			}
		}
		size.width = size.width + insets.left + insets.right;
		size.height = size.height + insets.top + insets.bottom;
		return size;
	}
}
