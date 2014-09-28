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
package com.nextbreakpoint.nextfractal.core.swing.palette;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
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
import java.io.IOException;
import java.util.TooManyListenersException;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;

import com.nextbreakpoint.nextfractal.core.util.Palette;

/**
 * @author Andrea Medeghini
 */
public class PaletteFieldUI extends ComponentUI {
	private DropTarget target;
	private DragSource source;
	private PaletteFieldController listener;
	private boolean paintBorder = false;
	private boolean isTarget = true;
	private Palette palette;

	/**
	 * @param c
	 * @return
	 */
	public static ComponentUI createUI(final JComponent c) {
		return new PaletteFieldUI();
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
		palette = ((PaletteField) c).getPalette();
		source = new DragSource();
		target = new DropTarget();
		listener = new PaletteFieldController((PaletteField) c);
		c.setToolTipText(createPaletteFieldTooltip(((PaletteField) c).getModel()));
		installListeners((PaletteField) c);
		if (source.createDefaultDragGestureRecognizer(c, DnDConstants.ACTION_MOVE, listener) != null) {
			c.setDropTarget(target);
		}
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#uninstallUI(javax.swing.JComponent)
	 */
	@Override
	public void uninstallUI(final JComponent c) {
		uninstallListeners((PaletteField) c);
		LookAndFeel.uninstallBorder(c);
		c.setDropTarget(null);
		listener = null;
		target = null;
		source = null;
		super.uninstallUI(c);
	}

	private void installListeners(final PaletteField c) {
		c.addPaletteChangeListener(listener);
		try {
			target.addDropTargetListener(listener);
		}
		catch (final TooManyListenersException e) {
			e.printStackTrace();
		}
	}

	private void uninstallListeners(final PaletteField c) {
		c.removePaletteChangeListener(listener);
		target.removeDropTargetListener(listener);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getPreferredSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getPreferredSize(final JComponent c) {
		return new Dimension(256, 64);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getMinimumSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getMinimumSize(final JComponent c) {
		return new Dimension(128, 32);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getMaximumSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getMaximumSize(final JComponent c) {
		return new Dimension(1024, 128);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics, javax.swing.JComponent)
	 */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		final Insets insets = c.getInsets();
		final int w = c.getWidth() - insets.left - insets.right;
		final int h = c.getHeight() - insets.top - insets.bottom;
		final Graphics2D g2d = (Graphics2D) g;
		g.translate(insets.left, insets.top);
		paintComponent(g2d, c, w, h);
	}

	/**
	 * @param g2d
	 * @param c
	 * @param w
	 * @param h
	 */
	protected void paintComponent(final Graphics2D g2d, final JComponent c, final int w, final int h) {
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		// g2d.setColor(c.getBackground());
		//
		// g2d.fillRect(0, 0, w, h);
		final int[] table = palette.renderTable(w);
		for (int i = 0; i < w; i++) {
			g2d.setColor(new Color(table[i], true));
			g2d.drawLine(i, 0, i, h);
		}
		if (paintBorder) {
			g2d.setColor(c.getForeground());
			g2d.drawRect(0, 0, w - 1, h - 1);
		}
	}

	/**
	 * @return
	 */
	private String createPaletteFieldTooltip(final PaletteFieldModel model) {
		return model.getPalette().getName();
	}

	private class PaletteFieldController implements PaletteChangeListener, DropTargetListener, DragGestureListener, DragSourceListener {
		private final PaletteField field;

		/**
		 * @param field
		 */
		public PaletteFieldController(final PaletteField field) {
			this.field = field;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.palette.PaletteChangeListener#paletteChanged(com.nextbreakpoint.nextfractal.core.swing.palette.PaletteChangeEvent)
		 */
		public void paletteChanged(final PaletteChangeEvent e) {
			palette = ((PaletteFieldModel) e.getSource()).getPalette();
			field.setToolTipText(createPaletteFieldTooltip((PaletteFieldModel) e.getSource()));
			field.repaint();
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
		 */
		public void dragEnter(final DropTargetDragEvent e) {
			if (!field.isDropEnabled()) {
				e.rejectDrag();
			}
			else if (isTarget) {
				final DataFlavor[] flavors = e.getCurrentDataFlavors();
				boolean accept = false;
				for (final DataFlavor element : flavors) {
					if (element.equals(TransferablePalette.PALETTE_FLAVOR)) {
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
		public void dragOver(final DropTargetDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
		 */
		public void dragExit(final DropTargetEvent e) {
			if (isTarget) {
				paintBorder = false;
			}
			field.repaint();
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)
		 */
		public void dropActionChanged(final DropTargetDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
		 */
		public void drop(final DropTargetDropEvent e) {
			if (isTarget) {
				final DataFlavor[] flavors = e.getCurrentDataFlavors();
				boolean accept = false;
				for (final DataFlavor element : flavors) {
					if (element.equals(TransferablePalette.PALETTE_FLAVOR)) {
						try {
							e.acceptDrop(DnDConstants.ACTION_COPY);
							field.getModel().setPalette((Palette) e.getTransferable().getTransferData(TransferablePalette.PALETTE_FLAVOR), false);
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
		public void dragGestureRecognized(final DragGestureEvent e) {
			if (field.isDragEnabled()) {
				source.startDrag(e, DragSource.DefaultCopyDrop, new TransferablePalette(field.getModel().getPalette()), this);
				paintBorder = true;
				isTarget = false;
				field.repaint();
			}
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragEnter(java.awt.dnd.DragSourceDragEvent)
		 */
		public void dragEnter(final DragSourceDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragOver(java.awt.dnd.DragSourceDragEvent)
		 */
		public void dragOver(final DragSourceDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragExit(java.awt.dnd.DragSourceEvent)
		 */
		public void dragExit(final DragSourceEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dropActionChanged(java.awt.dnd.DragSourceDragEvent)
		 */
		public void dropActionChanged(final DragSourceDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragDropEnd(java.awt.dnd.DragSourceDropEvent)
		 */
		public void dragDropEnd(final DragSourceDropEvent e) {
			paintBorder = false;
			isTarget = true;
			field.repaint();
		}
	}
}
