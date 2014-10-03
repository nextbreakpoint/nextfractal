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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * @author Andrea Medeghini
 */
public class ColorTableUI extends ComponentUI {
	private ColorTableController listener;
	private int mouse_x = 0;
	private int mouse_y = 0;
	private boolean paintCross = false;

	/**
	 * @param c
	 * @return
	 */
	public static ComponentUI createUI(final JComponent c) {
		return new ColorTableUI();
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		listener = null;
		super.finalize();
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#installUI(javax.swing.JComponent)
	 */
	@Override
	public void installUI(final JComponent c) {
		super.installUI(c);
		listener = new ColorTableController((ColorTable) c);
		installListeners((ColorTable) c);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#uninstallUI(javax.swing.JComponent)
	 */
	@Override
	public void uninstallUI(final JComponent c) {
		uninstallListeners((ColorTable) c);
		listener = null;
		super.uninstallUI(c);
	}

	/**
	 * @param c
	 */
	private void installListeners(final ColorTable c) {
		c.addColorChangeListener(listener);
		c.addMouseListener(listener);
		c.addMouseMotionListener(listener);
	}

	/**
	 * @param c
	 */
	private void uninstallListeners(final ColorTable c) {
		c.removeColorChangeListener(listener);
		c.removeMouseListener(listener);
		c.removeMouseMotionListener(listener);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getPreferredSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getPreferredSize(final JComponent c) {
		final Insets insets = c.getInsets();
		final ColorTableModel model = ((ColorTable) c).getModel();
		return new Dimension(model.getImageWidth() + insets.left + insets.right, model.getImageHeight() + insets.top + insets.bottom);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getMinimumSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getMinimumSize(final JComponent c) {
		return getPreferredSize(c);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getMaximumSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getMaximumSize(final JComponent c) {
		return getPreferredSize(c);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics, javax.swing.JComponent)
	 */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		final Insets insets = c.getInsets();
		final int dw = getHorizontalPosition((ColorTable) c, insets);
		final int dh = getVerticalPosition((ColorTable) c, insets);
		final ColorTableModel model = ((ColorTable) c).getModel();
		g.drawImage(model.getImage(), dw, dh, null);
		g.setColor(Color.white);
		if (paintCross) {
			g.drawLine(mouse_x - 5, mouse_y - 5, mouse_x + 5, mouse_y + 5);
			g.drawLine(mouse_x - 5, mouse_y + 5, mouse_x + 5, mouse_y - 5);
		}
		if (((ColorTable) c).isShowColorEnabled()) {
			g.drawLine(model.getX() - 5, model.getY() - 5, model.getX() + 5, model.getY() + 5);
			g.drawLine(model.getX() - 5, model.getY() + 5, model.getX() + 5, model.getY() - 5);
		}
	}

	/**
	 * @param insets
	 * @return
	 */
	private int getHorizontalPosition(final ColorTable table, final Insets insets) {
		int dw = table.getWidth() - table.getModel().getImage().getWidth() - insets.left - insets.right;
		dw = insets.left + (dw < 0 ? 0 : dw) / 2;
		return dw;
	}

	/**
	 * @param insets
	 * @return
	 */
	private int getVerticalPosition(final ColorTable table, final Insets insets) {
		int dh = table.getHeight() - table.getModel().getImage().getHeight() - insets.top - insets.bottom;
		dh = insets.top + (dh < 0 ? 0 : dh) / 2;
		return dh;
	}

	private class ColorTableController implements MouseListener, MouseMotionListener, ColorChangeListener {
		private final ColorTable table;

		/**
		 * @param table
		 */
		public ColorTableController(final ColorTable table) {
			this.table = table;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.color.ColorChangeListener#colorChanged(com.nextbreakpoint.nextfractal.core.swing.color.ColorChangeEvent)
		 */
		@Override
		public void colorChanged(final ColorChangeEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseEntered(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseExited(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(final MouseEvent e) {
			paintCross = true;
			table.repaint();
		}

		/**
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(final MouseEvent e) {
			paintCross = false;
			final Insets insets = table.getInsets();
			final int dw = getHorizontalPosition(table, insets);
			final int dh = getVerticalPosition(table, insets);
			table.getModel().setColor(mouse_x - dw, mouse_y - dh, false);
			table.repaint();
		}

		/**
		 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseDragged(final MouseEvent e) {
			mouse_x = e.getX();
			mouse_y = e.getY();
			final Insets insets = table.getInsets();
			updateMousePosition(insets);
			table.repaint();
		}

		/**
		 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseMoved(final MouseEvent e) {
			mouse_x = e.getX();
			mouse_y = e.getY();
			final Insets insets = table.getInsets();
			updateMousePosition(insets);
		}

		private void updateMousePosition(final Insets insets) {
			final int dw = getHorizontalPosition(table, insets);
			final int dh = getVerticalPosition(table, insets);
			mouse_x = (mouse_x > dw) ? mouse_x : dw;
			mouse_x = (mouse_x < table.getModel().getImageWidth() + dw) ? mouse_x : table.getModel().getImageWidth() + dw - 1;
			mouse_y = (mouse_y > dh) ? mouse_y : dh;
			mouse_y = (mouse_y < table.getModel().getImageHeight() + dh) ? mouse_y : table.getModel().getImageHeight() + dh - 1;
		}
	}
}
