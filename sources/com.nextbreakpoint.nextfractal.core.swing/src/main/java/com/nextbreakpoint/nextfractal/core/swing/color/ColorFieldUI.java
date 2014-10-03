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
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.InvalidDnDOperationException;
import java.io.IOException;
import java.util.TooManyListenersException;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;

/**
 * @author Andrea Medeghini
 */
public class ColorFieldUI extends ComponentUI {
	private DropTarget target;
	private DragSource source;
	private ColorFieldController listener;
	boolean paintBorder = false;
	boolean isTarget = true;

	/**
	 * @param c
	 * @return
	 */
	public static ComponentUI createUI(final JComponent c) {
		return new ColorFieldUI();
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		listener = null;
		target = null;
		source = null;
		super.finalize();
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#installUI(javax.swing.JComponent)
	 */
	@Override
	public void installUI(final JComponent c) {
		super.installUI(c);
		source = new DragSource();
		target = new DropTarget();
		listener = new ColorFieldController((ColorField) c);
		c.setToolTipText(createColorFieldTooltip(((ColorField) c).getModel()));
		installListeners((ColorField) c);
		if (source.createDefaultDragGestureRecognizer(c, DnDConstants.ACTION_MOVE, listener) != null) {
			c.setDropTarget(target);
		}
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#uninstallUI(javax.swing.JComponent)
	 */
	@Override
	public void uninstallUI(final JComponent c) {
		uninstallListeners((ColorField) c);
		LookAndFeel.uninstallBorder(c);
		c.setDropTarget(null);
		listener = null;
		target = null;
		source = null;
		super.uninstallUI(c);
	}

	private void installListeners(final ColorField c) {
		c.addColorChangeListener(listener);
		try {
			target.addDropTargetListener(listener);
		}
		catch (final TooManyListenersException e) {
			e.printStackTrace();
		}
	}

	private void uninstallListeners(final ColorField c) {
		c.removeColorChangeListener(listener);
		target.removeDropTargetListener(listener);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getPreferredSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getPreferredSize(final JComponent c) {
		return new Dimension(20, 20);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getMinimumSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getMinimumSize(final JComponent c) {
		return new Dimension(5, 5);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getMaximumSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getMaximumSize(final JComponent c) {
		return new Dimension(200, 200);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics, javax.swing.JComponent)
	 */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		final ColorFieldModel model = ((ColorField) c).getModel();
		final Insets insets = c.getInsets();
		final Dimension size = c.getSize();
		g.setColor(model.getColor());
		g.fillRect(insets.left, insets.top, size.width - insets.right - insets.left, size.height - insets.bottom - insets.top);
		g.setColor(Color.BLACK);
		if (paintBorder) {
			g.drawRect(insets.left, insets.top, size.width - insets.right - insets.left - 1, size.height - insets.bottom - insets.top - 1);
		}
	}

	/**
	 * @return
	 */
	private String createColorFieldTooltip(final ColorFieldModel model) {
		return "Color #" + Integer.toHexString(model.getColor().getRGB()).toUpperCase();
	}

	private class ColorFieldController implements ColorChangeListener, DropTargetListener, DragGestureListener, DragSourceListener {
		private final ColorField field;

		/**
		 * @param field
		 */
		public ColorFieldController(final ColorField field) {
			this.field = field;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.color.ColorChangeListener#colorChanged(com.nextbreakpoint.nextfractal.core.swing.color.ColorChangeEvent)
		 */
		@Override
		public void colorChanged(final ColorChangeEvent e) {
			field.setToolTipText(createColorFieldTooltip(field.getModel()));
			field.repaint();
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
		 */
		@Override
		public void dragEnter(final DropTargetDragEvent e) {
			if (!field.isDropEnabled()) {
				e.rejectDrag();
			}
			else if (isTarget) {
				final DataFlavor[] flavors = e.getCurrentDataFlavors();
				boolean accept = false;
				for (final DataFlavor element : flavors) {
					if (element.equals(TransferableColor.COLOR_FLAVOR)) {
						accept = true;
						break;
					}
				}
				if (accept) {
					e.acceptDrag(DnDConstants.ACTION_MOVE);
					paintBorder = true;
				}
				else {
					e.rejectDrag();
				}
			}
			field.repaint();
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
		 */
		@Override
		public void dragOver(final DropTargetDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
		 */
		@Override
		public void dragExit(final DropTargetEvent e) {
			if (isTarget) {
				paintBorder = false;
			}
			field.repaint();
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)
		 */
		@Override
		public void dropActionChanged(final DropTargetDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
		 */
		@Override
		public void drop(final DropTargetDropEvent e) {
			if (isTarget) {
				final DataFlavor[] flavors = e.getCurrentDataFlavors();
				boolean accept = false;
				for (final DataFlavor element : flavors) {
					if (element.equals(TransferableColor.COLOR_FLAVOR)) {
						try {
							e.acceptDrop(DnDConstants.ACTION_COPY);
							field.getModel().setColor((Color) e.getTransferable().getTransferData(TransferableColor.COLOR_FLAVOR), false);
							accept = true;
						}
						catch (final UnsupportedFlavorException x) {
							x.printStackTrace();
						}
						catch (final IOException x) {
							x.printStackTrace();
						}
						break;
					}
				}
				if (accept) {
					e.dropComplete(true);
					paintBorder = false;
					field.repaint();
				}
				else {
					e.rejectDrop();
				}
			}
		}

		/**
		 * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(java.awt.dnd.DragGestureEvent)
		 */
		@Override
		public void dragGestureRecognized(final DragGestureEvent e) {
			if (field.isDragEnabled()) {
				try {
					source.startDrag(e, DragSource.DefaultCopyDrop, new TransferableColor(field.getModel().getColor()), this);
					paintBorder = true;
					isTarget = false;
					field.repaint();
				}
				catch (final InvalidDnDOperationException x) {
					x.printStackTrace();
				}
			}
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragEnter(java.awt.dnd.DragSourceDragEvent)
		 */
		@Override
		public void dragEnter(final DragSourceDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragOver(java.awt.dnd.DragSourceDragEvent)
		 */
		@Override
		public void dragOver(final DragSourceDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragExit(java.awt.dnd.DragSourceEvent)
		 */
		@Override
		public void dragExit(final DragSourceEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dropActionChanged(java.awt.dnd.DragSourceDragEvent)
		 */
		@Override
		public void dropActionChanged(final DragSourceDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragDropEnd(java.awt.dnd.DragSourceDropEvent)
		 */
		@Override
		public void dragDropEnd(final DragSourceDropEvent e) {
			paintBorder = false;
			isTarget = true;
			field.repaint();
		}
	}
}
