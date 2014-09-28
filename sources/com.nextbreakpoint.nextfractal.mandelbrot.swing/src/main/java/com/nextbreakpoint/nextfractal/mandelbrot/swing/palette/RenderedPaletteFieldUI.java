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
package com.nextbreakpoint.nextfractal.mandelbrot.swing.palette;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
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
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.TooManyListenersException;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.SingleSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.swing.color.TransferableColor;
import com.nextbreakpoint.nextfractal.core.swing.palette.PaletteChangeEvent;
import com.nextbreakpoint.nextfractal.core.swing.palette.PaletteChangeListener;
import com.nextbreakpoint.nextfractal.core.swing.palette.PaletteFieldModel;
import com.nextbreakpoint.nextfractal.core.swing.palette.TransferablePalette;
import com.nextbreakpoint.nextfractal.core.util.Colors;
import com.nextbreakpoint.nextfractal.core.util.Palette;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula.extension.PaletteRendererFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPaletteParam;

/**
 * @author Andrea Medeghini
 */
public class RenderedPaletteFieldUI extends ComponentUI {
	private DropTarget target;
	private DragSource source;
	private RenderedPaletteFieldController listener;
	private boolean paintBorder = false;
	private boolean isTarget = true;
	private boolean isDirty = false;
	private int overIndex = -1;
	private int moveIndex = -1;
	private int resizeIndex = -1;
	private int selectedIndex = -1;
	private RenderedPalette newPalette;
	private RenderedPalette oldPalette;
	private int mx = 0;
	private int my = 0;
	private int w = 0;
	private int h = 0;
	private int[] table;

	/**
	 * @param c
	 * @return
	 */
	public static ComponentUI createUI(final JComponent c) {
		return new RenderedPaletteFieldUI();
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
		newPalette = ((RenderedPaletteField) c).getPalette();
		source = new DragSource();
		target = new DropTarget();
		listener = new RenderedPaletteFieldController((RenderedPaletteField) c);
		c.setToolTipText(createPaletteFieldTooltip(((RenderedPaletteField) c).getModel()));
		installListeners((RenderedPaletteField) c);
		if (source.createDefaultDragGestureRecognizer(c, DnDConstants.ACTION_MOVE, listener) != null) {
			c.setDropTarget(target);
		}
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#uninstallUI(javax.swing.JComponent)
	 */
	@Override
	public void uninstallUI(final JComponent c) {
		uninstallListeners((RenderedPaletteField) c);
		LookAndFeel.uninstallBorder(c);
		c.setDropTarget(null);
		listener = null;
		target = null;
		source = null;
		super.uninstallUI(c);
	}

	private void installListeners(final RenderedPaletteField c) {
		c.addMouseListener(listener);
		c.addMouseMotionListener(listener);
		c.addPaletteChangeListener(listener);
		c.getSelectionModel().addChangeListener(listener);
		try {
			target.addDropTargetListener(listener);
		}
		catch (final TooManyListenersException e) {
			e.printStackTrace();
		}
	}

	private void uninstallListeners(final RenderedPaletteField c) {
		c.removeMouseListener(listener);
		c.removeMouseMotionListener(listener);
		c.removePaletteChangeListener(listener);
		c.getSelectionModel().removeChangeListener(listener);
		target.removeDropTargetListener(listener);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getPreferredSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getPreferredSize(final JComponent c) {
		return new Dimension(512, 256);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getMinimumSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getMinimumSize(final JComponent c) {
		return new Dimension(256, 128);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getMaximumSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getMaximumSize(final JComponent c) {
		return new Dimension(1024, 512);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics, javax.swing.JComponent)
	 */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		final Insets insets = c.getInsets();
		w = c.getWidth() - insets.left - insets.right - 8;
		h = c.getHeight() - insets.top - insets.bottom - 8;
		final Graphics2D g2d = (Graphics2D) g;
		if (paintBorder) {
			g2d.setColor(c.getForeground());
			g2d.drawRect(insets.left, insets.top, c.getWidth() - insets.left - insets.right - 1, c.getHeight() - insets.top - insets.bottom - 1);
		}
		g.translate(insets.left + 4, insets.top + 4);
		paintComponent(g2d, c, w, h);
	}

	/**
	 * @param g2d
	 * @param c
	 * @param insets
	 * @param size
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
		final int r = 8;
		int d = 0;
		final double q0 = (h - 20 - r * 5) / 2.0;
		final int q1 = (int) Math.round(q0 / 4.0);
		final int q2 = (int) Math.round(q0);
		double length = 0;
		// fillRect(g2d, w, r, q1);
		try {
			int o = 0;
			for (int i = 0; i < newPalette.getParamCount(); i++) {
				length = (w * newPalette.getParam(i).getSize()) / 100.0;
				int l = (int) Math.round(length);
				if ((i == newPalette.getParamCount() - 1) || (o + l > w)) {
					l = w - o;
				}
				if (l > 0) {
					d = 0;
					double ct;
					double cd;
					double[] T;
					Extension<PaletteRendererFormulaExtensionRuntime> extension = null;
					ct = Colors.getAlpha(newPalette.getParam(i).getColor(0));
					cd = Colors.getAlpha(newPalette.getParam(i).getColor(1)) - ct;
					extension = MandelbrotRegistry.getInstance().getPaletteRendererFormulaExtension(newPalette.getParam(i).getFormula(0).getExtensionId());
					T = extension.createExtensionRuntime().renderPalette(l);
					d += q1 + r;
					drawFormula(g2d, Color.GRAY, w, d, q1, o, l, ct, cd, T);
					ct = Colors.getRed(newPalette.getParam(i).getColor(0));
					cd = Colors.getRed(newPalette.getParam(i).getColor(1)) - ct;
					extension = MandelbrotRegistry.getInstance().getPaletteRendererFormulaExtension(newPalette.getParam(i).getFormula(1).getExtensionId());
					T = extension.createExtensionRuntime().renderPalette(l);
					d += q1 + r;
					drawFormula(g2d, Color.RED, w, d, q1, o, l, ct, cd, T);
					ct = Colors.getGreen(newPalette.getParam(i).getColor(0));
					cd = Colors.getGreen(newPalette.getParam(i).getColor(1)) - ct;
					extension = MandelbrotRegistry.getInstance().getPaletteRendererFormulaExtension(newPalette.getParam(i).getFormula(2).getExtensionId());
					T = extension.createExtensionRuntime().renderPalette(l);
					d += q1 + r;
					drawFormula(g2d, Color.GREEN, w, d, q1, o, l, ct, cd, T);
					ct = Colors.getBlue(newPalette.getParam(i).getColor(0));
					cd = Colors.getBlue(newPalette.getParam(i).getColor(1)) - ct;
					extension = MandelbrotRegistry.getInstance().getPaletteRendererFormulaExtension(newPalette.getParam(i).getFormula(3).getExtensionId());
					T = extension.createExtensionRuntime().renderPalette(l);
					d += q1 + r;
					drawFormula(g2d, Color.BLUE, w, d, q1, o, l, ct, cd, T);
				}
				o = o + l;
			}
		}
		catch (final ExtensionException e) {
			e.printStackTrace();
		}
		if (table == null) {
			table = newPalette.renderTable(w);
		}
		d = 4 * (q1 + r) + (q2 + r);
		drawPalette(g2d, w, d, q2, table);
		if (overIndex >= newPalette.getParamCount()) {
			overIndex = -1;
		}
		// drawRect(g2d, w, r, q1);
		if ((selectedIndex != -1) && (moveIndex == -1)) {
			length = 0;
			int o = 0;
			for (int i = 0; i < selectedIndex; i++) {
				o += (int) Math.round((w * newPalette.getParam(i).getSize()) / 100.0);
			}
			length = (w * newPalette.getParam(selectedIndex).getSize()) / 100.0;
			int l = (int) Math.round(length);
			if ((selectedIndex == newPalette.getParamCount() - 1) || (o + l > w)) {
				l = w - o;
			}
			paintSelectedIndex(g2d, o, o + l - 1, h - 16);
		}
		if (overIndex != -1) {
			if (moveIndex != -1) {
				length = 0;
				int o = 0;
				for (int i = 0; i < moveIndex; i++) {
					o += (int) Math.round((w * newPalette.getParam(i).getSize()) / 100.0);
				}
				length = (w * newPalette.getParam(moveIndex).getSize()) / 100.0;
				int l = (int) Math.round(length);
				if ((moveIndex == newPalette.getParamCount() - 1) || (o + l > w)) {
					l = w - o;
				}
				paintMoveIndex(g2d, o, o + l - 1, h - 16);
				length = 0;
				o = 0;
				for (int i = 0; i < overIndex; i++) {
					o += (int) Math.round((w * newPalette.getParam(i).getSize()) / 100.0);
				}
				length = (w * newPalette.getParam(overIndex).getSize()) / 100.0;
				l = (int) Math.round(length);
				if ((overIndex == newPalette.getParamCount() - 1) || (o + l > w)) {
					l = w - o;
				}
				paintOverIndex(g2d, o, o + l - 1, h - 16);
			}
			length = 0;
			int o = 0;
			for (int i = 0; i < overIndex; i++) {
				o += (int) Math.round((w * newPalette.getParam(i).getSize()) / 100.0);
			}
			length = (w * newPalette.getParam(overIndex).getSize()) / 100.0;
			int l = (int) Math.round(length);
			if ((overIndex == newPalette.getParamCount() - 1) || (o + l > w)) {
				l = w - o;
			}
			// paintCursorCenter((Graphics2D) g2d, o, o + l - 1, h - 16);
			paintCursorRight(g2d, o, h - 16);
			paintCursorLeft(g2d, o + l - 1, h - 16);
		}
	}

	private void paintMoveIndex(final Graphics2D g2d, final int x1, final int x2, final int y) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(x1, y + 12, x2 - x1, 4);
		// g2d.setColor(Color.BLACK);
		//		
		// g2d.drawRect(x1, y + 12, x2 - x1 - 1, 3);
	}

	private void paintOverIndex(final Graphics2D g2d, final int x1, final int x2, final int y) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect(x1, y + 12, x2 - x1, 4);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(x1, y + 12, x2 - x1 - 1, 3);
	}

	private void paintSelectedIndex(final Graphics2D g2d, final int x1, final int x2, final int y) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect(x1, y + 12, x2 - x1, 4);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(x1, y + 12, x2 - x1 - 1, 3);
	}

	// private void fillRect(final Graphics g, final int w, final int r, int q) {
	// final int x = 0;
	// final int y = -q;
	// final int h = q + 1;
	// int d = 0;
	// d += q + r;
	// g.setColor(Color.WHITE);
	// g.fillRect(x, y + d, w, h);
	// d += q + r;
	// g.fillRect(x, y + d, w, h);
	// d += q + r;
	// g.fillRect(x, y + d, w, h);
	// }
	//
	// private void drawRect(final Graphics g, final int w, final int r, int q) {
	// final int x = 0;
	// final int y = -q - 1;
	// final int h = q + 2;
	// int d = 0;
	// d += q + r;
	// g.setColor(Color.GRAY);
	// g.drawRect(x, y + d, w - 1, h);
	// d += q + r;
	// g.drawRect(x, y + d, w - 1, h);
	// d += q + r;
	// g.drawRect(x, y + d, w - 1, h);
	// }
	private void drawPalette(final Graphics g, final int w, final int d, final int q, final int[] table) {
		final int x = 0;
		final int y = d;
		for (int i = 0; i < w; i++) {
			g.setColor(new Color(table[i], true));
			g.drawLine(x + i, y, x + i, y - q);
		}
	}

	private void drawFormula(final Graphics g, final Color color, final int w, final int d, final int q, final int o, final int l, final double ct, final double cd, final double[] T) {
		final int x = o;
		final int y = d;
		g.setColor(color);
		for (int i = 0; i < l; i++) {
			final int t = (int) Math.round(((ct + cd * T[i]) * q) / 255.0);
			g.drawLine(x + i, y, x + i, y - t);
		}
	}

	private void paintCursorLeft(final Graphics2D g, final int x, final int y) {
		// g.setColor(Color.WHITE);
		final Polygon p = new Polygon();
		p.addPoint(x, y);
		p.addPoint(x - 5, y + 10);
		p.addPoint(x, y + 10);
		p.addPoint(x, y);
		g.setColor(Color.BLACK);
		g.fillPolygon(p);
		// g.drawPolygon(p);
	}

	private void paintCursorRight(final Graphics2D g, final int x, final int y) {
		// g.setColor(Color.WHITE);
		final Polygon p = new Polygon();
		p.addPoint(x, y);
		p.addPoint(x, y + 10);
		p.addPoint(x + 5, y + 10);
		p.addPoint(x, y);
		g.setColor(Color.BLACK);
		g.fillPolygon(p);
		// g.drawPolygon(p);
	}

	// private void paintCursorCenter(final Graphics2D g, final int x1, final int x2, final int y) {
	// // g.setColor(Color.WHITE);
	// final int x = x1 + (x2 - x1) / 2;
	// final Polygon p = new Polygon();
	// p.addPoint(x, y);
	// p.addPoint(x - 5, y + 10);
	// p.addPoint(x + 5, y + 10);
	// p.addPoint(x, y);
	// g.setColor(Color.BLACK);
	// g.fillPolygon(p);
	// // g.drawPolygon(p);
	// // g.fillRect(x, y, 10, 10);
	// }
	/**
	 * @return
	 */
	private String createPaletteFieldTooltip(final PaletteFieldModel model) {
		return model.getPalette().getName();
	}

	private class RenderedPaletteFieldController implements PaletteChangeListener, DropTargetListener, DragGestureListener, DragSourceListener, MouseListener, MouseMotionListener, ChangeListener {
		private final RenderedPaletteField field;

		/**
		 * @param field
		 */
		public RenderedPaletteFieldController(final RenderedPaletteField field) {
			this.field = field;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.platform.ui.swing.color.PaletteChangeListener#paletteChanged(com.nextbreakpoint.nextfractal.platform.ui.swing.color.PaletteChangeEvent)
		 */
		public void paletteChanged(final PaletteChangeEvent e) {
			final RenderedPalette palette = ((RenderedPaletteModel) e.getSource()).getRenderedPalette();
			field.setToolTipText(createPaletteFieldTooltip((RenderedPaletteModel) e.getSource()));
			newPalette = palette;
			table = null;
			field.repaint();
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
		 */
		public void dragEnter(final DropTargetDragEvent e) {
			if (!field.isDropEnabled() || (resizeIndex != -1) || (moveIndex != -1)) {
				e.rejectDrag();
			}
			else if (isTarget) {
				final DataFlavor[] flavors = e.getCurrentDataFlavors();
				boolean accept = false;
				for (final DataFlavor element : flavors) {
					if (element.equals(TransferablePalette.PALETTE_FLAVOR) || element.equals(TransferableRenderedPalette.RENDERED_PALETTE_FLAVOR) || element.equals(TransferableColor.COLOR_FLAVOR)) {
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
							final Palette palette = (Palette) e.getTransferable().getTransferData(TransferablePalette.PALETTE_FLAVOR);
							if (palette instanceof RenderedPalette) {
								field.getModel().setPalette(palette, false);
								accept = true;
							}
						}
						catch (final UnsupportedFlavorException x) {
							x.printStackTrace();
						}
						catch (final IOException x) {
							x.printStackTrace();
						}
						break;
					}
					else if (element.equals(TransferableRenderedPalette.RENDERED_PALETTE_FLAVOR)) {
						try {
							e.acceptDrop(DnDConstants.ACTION_COPY);
							field.getModel().setRenderedPalette((RenderedPalette) e.getTransferable().getTransferData(TransferableRenderedPalette.RENDERED_PALETTE_FLAVOR), false);
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
					else if (element.equals(TransferableColor.COLOR_FLAVOR)) {
						try {
							e.acceptDrop(DnDConstants.ACTION_COPY);
							Color color = (Color) e.getTransferable().getTransferData(TransferableColor.COLOR_FLAVOR);
							int index = -1;
							boolean startColor = true;
							final Insets insets = field.getInsets();
							final double mx = e.getLocation().getX() - insets.left - 4;
							final double my = e.getLocation().getY() - insets.top - 4;
							double offset = 0;
							double length = 0;
							for (int i = 0; i < newPalette.getParamCount(); i++) {
								length = (w * newPalette.getParam(i).getSize()) / 100.0;
								if (offset + length > w) {
									length = w - offset;
								}
								if (length >= 0) {
									if ((my > 0) && (my < h)) {
										if ((mx > offset) && (mx < offset + length / 2)) {
											index = i;
											break;
										}
										else if ((mx >= offset + length / 2) && (mx < offset + length)) {
											index = i;
											startColor = false;
											break;
										}
									}
								}
								offset += length;
							}
							if (index > -1) {
								final RenderedPaletteParam[] newParams = new RenderedPaletteParam[newPalette.getParamCount()];
								for (int i = 0; i < newPalette.getParamCount(); i++) {
									if (i == index) {
										final ExtensionReference[] formulas = new ExtensionReference[4];
										formulas[0] = newPalette.getParam(i).getFormula(0);
										formulas[1] = newPalette.getParam(i).getFormula(1);
										formulas[2] = newPalette.getParam(i).getFormula(2);
										formulas[3] = newPalette.getParam(i).getFormula(3);
										final int[] colors = new int[2];
										if (startColor) {
											colors[0] = color.getRGB();
											colors[1] = newPalette.getParam(i).getColor(1);
										}
										else {
											colors[0] = newPalette.getParam(i).getColor(0);
											colors[1] = color.getRGB();
										}
										newParams[i] = new RenderedPaletteParam(formulas, colors, newPalette.getParam(i).getSize());
									}
									else {
										newParams[i] = newPalette.getParam(i);
									}
								}
								final RenderedPalette palette = new RenderedPalette(newParams);
								field.getModel().setRenderedPalette(palette, false);
							}
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
			if (field.isDragEnabled() && (resizeIndex == -1) && (moveIndex == -1)) {
				source.startDrag(e, DragSource.DefaultCopyDrop, new TransferablePalette(field.getModel().getRenderedPalette()), this);
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

		/**
		 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
		 */
		public void mouseDragged(final MouseEvent e) {
			if ((resizeIndex != -1) && ((e.getModifiers() & InputEvent.SHIFT_MASK) == 0)) {
				final Insets insets = field.getInsets();
				final double dx = e.getX() - insets.left - 4 - mx;
				final RenderedPaletteParam[] newParams = new RenderedPaletteParam[oldPalette.getParamCount()];
				double oldTotalSize = 0;
				double newTotalSize = 0;
				for (int i = 0; i < resizeIndex; i++) {
					newParams[i] = oldPalette.getParam(i);
					newTotalSize += newParams[i].getSize();
					oldTotalSize += oldPalette.getParam(i).getSize();
				}
				double size = oldPalette.getParam(resizeIndex).getSize() + (dx / w) * 100.0;
				if (size < 0) {
					size = 0;
				}
				if (size > 100 - newTotalSize) {
					size = 100 - newTotalSize;
				}
				ExtensionReference[] formulas = new ExtensionReference[4];
				formulas[0] = oldPalette.getParam(resizeIndex).getFormula(0);
				formulas[1] = oldPalette.getParam(resizeIndex).getFormula(1);
				formulas[2] = oldPalette.getParam(resizeIndex).getFormula(2);
				formulas[3] = oldPalette.getParam(resizeIndex).getFormula(3);
				int[] colors = new int[2];
				colors[0] = oldPalette.getParam(resizeIndex).getColor(0);
				colors[1] = oldPalette.getParam(resizeIndex).getColor(1);
				newParams[resizeIndex] = new RenderedPaletteParam(formulas, colors, size);
				newTotalSize += newParams[resizeIndex].getSize();
				oldTotalSize += oldPalette.getParam(resizeIndex).getSize();
				if (newTotalSize < 0) {
					newTotalSize = 0;
				}
				if (newTotalSize > 100) {
					newTotalSize = 100;
				}
				if (oldTotalSize < 0) {
					oldTotalSize = 0;
				}
				if (oldTotalSize > 100) {
					oldTotalSize = 100;
				}
				newTotalSize = 100.0 - newTotalSize;
				oldTotalSize = 100.0 - oldTotalSize;
				int zeroCount = 0;
				for (int i = resizeIndex + 1; i < oldPalette.getParamCount(); i++) {
					if (oldPalette.getParam(i).getSize() < 0.001) {
						zeroCount += 1;
					}
				}
				if ((zeroCount > 0) && (zeroCount == newParams.length - resizeIndex - 1)) {
					final double newSize = newTotalSize / zeroCount;
					for (int i = resizeIndex + 1; i < oldPalette.getParamCount(); i++) {
						formulas = new ExtensionReference[4];
						formulas[0] = oldPalette.getParam(i).getFormula(0);
						formulas[1] = oldPalette.getParam(i).getFormula(1);
						formulas[2] = oldPalette.getParam(i).getFormula(2);
						formulas[3] = oldPalette.getParam(i).getFormula(3);
						colors = new int[2];
						colors[0] = oldPalette.getParam(i).getColor(0);
						colors[1] = oldPalette.getParam(i).getColor(1);
						newParams[i] = new RenderedPaletteParam(formulas, colors, newSize);
					}
				}
				else {
					for (int i = resizeIndex + 1; i < oldPalette.getParamCount(); i++) {
						double newSize = oldPalette.getParam(i).getSize() * (newTotalSize / oldTotalSize);
						if (newSize > newTotalSize) {
							newSize = newTotalSize;
						}
						formulas = new ExtensionReference[4];
						formulas[0] = oldPalette.getParam(i).getFormula(0);
						formulas[1] = oldPalette.getParam(i).getFormula(1);
						formulas[2] = oldPalette.getParam(i).getFormula(2);
						formulas[3] = oldPalette.getParam(i).getFormula(3);
						colors = new int[2];
						colors[0] = oldPalette.getParam(i).getColor(0);
						colors[1] = oldPalette.getParam(i).getColor(1);
						newParams[i] = new RenderedPaletteParam(formulas, colors, newSize);
					}
				}
				final RenderedPalette palette = new RenderedPalette(newParams);
				newPalette = palette;
				table = null;
				isDirty = true;
				field.repaint();
			}
			else if ((resizeIndex != -1) && ((e.getModifiers() & InputEvent.SHIFT_MASK) != 0)) {
				final Insets insets = field.getInsets();
				final double dx = e.getX() - insets.left - 4 - mx;
				final RenderedPaletteParam[] newParams = new RenderedPaletteParam[oldPalette.getParamCount()];
				double oldTotalSize = 0;
				double newTotalSize = 0;
				for (int i = oldPalette.getParamCount() - 1; i > resizeIndex; i--) {
					newParams[i] = oldPalette.getParam(i);
					newTotalSize += newParams[i].getSize();
					oldTotalSize += oldPalette.getParam(i).getSize();
				}
				double size = oldPalette.getParam(resizeIndex).getSize() - (dx / w) * 100.0;
				if (size < 0) {
					size = 0;
				}
				if (size > 100 - newTotalSize) {
					size = 100 - newTotalSize;
				}
				ExtensionReference[] formulas = new ExtensionReference[4];
				formulas[0] = oldPalette.getParam(resizeIndex).getFormula(0);
				formulas[1] = oldPalette.getParam(resizeIndex).getFormula(1);
				formulas[2] = oldPalette.getParam(resizeIndex).getFormula(2);
				formulas[3] = oldPalette.getParam(resizeIndex).getFormula(3);
				int[] colors = new int[2];
				colors[0] = oldPalette.getParam(resizeIndex).getColor(0);
				colors[1] = oldPalette.getParam(resizeIndex).getColor(1);
				newParams[resizeIndex] = new RenderedPaletteParam(formulas, colors, size);
				newTotalSize += newParams[resizeIndex].getSize();
				oldTotalSize += oldPalette.getParam(resizeIndex).getSize();
				if (newTotalSize < 0) {
					newTotalSize = 0;
				}
				if (newTotalSize > 100) {
					newTotalSize = 100;
				}
				if (oldTotalSize < 0) {
					oldTotalSize = 0;
				}
				if (oldTotalSize > 100) {
					oldTotalSize = 100;
				}
				newTotalSize = 100.0 - newTotalSize;
				oldTotalSize = 100.0 - oldTotalSize;
				int zeroCount = 0;
				for (int i = resizeIndex - 1; i >= 0; i--) {
					if (oldPalette.getParam(i).getSize() < 0.001) {
						zeroCount += 1;
					}
				}
				if ((zeroCount > 0) && (zeroCount == resizeIndex)) {
					final double newSize = newTotalSize / zeroCount;
					for (int i = resizeIndex - 1; i >= 0; i--) {
						formulas = new ExtensionReference[4];
						formulas[0] = oldPalette.getParam(i).getFormula(0);
						formulas[1] = oldPalette.getParam(i).getFormula(1);
						formulas[2] = oldPalette.getParam(i).getFormula(2);
						formulas[3] = oldPalette.getParam(i).getFormula(3);
						colors = new int[2];
						colors[0] = oldPalette.getParam(i).getColor(0);
						colors[1] = oldPalette.getParam(i).getColor(1);
						newParams[i] = new RenderedPaletteParam(formulas, colors, newSize);
					}
				}
				else {
					for (int i = resizeIndex - 1; i >= 0; i--) {
						double newSize = oldPalette.getParam(i).getSize() * (newTotalSize / oldTotalSize);
						if (newSize > newTotalSize) {
							newSize = newTotalSize;
						}
						formulas = new ExtensionReference[4];
						formulas[0] = oldPalette.getParam(i).getFormula(0);
						formulas[1] = oldPalette.getParam(i).getFormula(1);
						formulas[2] = oldPalette.getParam(i).getFormula(2);
						formulas[3] = oldPalette.getParam(i).getFormula(3);
						colors = new int[2];
						colors[0] = oldPalette.getParam(i).getColor(0);
						colors[1] = oldPalette.getParam(i).getColor(1);
						newParams[i] = new RenderedPaletteParam(formulas, colors, newSize);
					}
				}
				final RenderedPalette palette = new RenderedPalette(newParams);
				newPalette = palette;
				table = null;
				isDirty = true;
				field.repaint();
			}
			if (moveIndex != -1) {
				final RenderedPalette palette = field.getModel().getRenderedPalette();
				final Insets insets = field.getInsets();
				final int mx = e.getX() - insets.left - 4;
				final int my = e.getY() - insets.top - 4;
				double offset = 0;
				double length = 0;
				int index = -1;
				for (int i = 0; i < palette.getParamCount(); i++) {
					length = (w * palette.getParam(i).getSize()) / 100.0;
					if (offset + length > w) {
						length = w - offset;
					}
					if (length >= 0) {
						if ((mx > offset) && (mx < offset + length)) {
							if ((my > 0) && (my < h)) {
								index = i;
								break;
							}
						}
					}
					offset += length;
				}
				if (index != overIndex) {
					overIndex = index;
					field.repaint();
				}
			}
		}

		/**
		 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
		 */
		public void mouseMoved(final MouseEvent e) {
			final RenderedPalette palette = field.getModel().getRenderedPalette();
			final Insets insets = field.getInsets();
			final int mx = e.getX() - insets.left - 4;
			final int my = e.getY() - insets.top - 4;
			double offset = 0;
			double length = 0;
			int index = -1;
			for (int i = 0; i < palette.getParamCount(); i++) {
				length = (w * palette.getParam(i).getSize()) / 100.0;
				if (offset + length > w) {
					length = w - offset;
				}
				if (length >= 0) {
					if ((mx > offset) && (mx < offset + length)) {
						if ((my > 0) && (my < h)) {
							index = i;
							break;
						}
					}
				}
				offset += length;
			}
			if (index != overIndex) {
				overIndex = index;
				field.repaint();
			}
		}

		/**
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(final MouseEvent e) {
			if (e.getClickCount() == 2) {
				final RenderedPaletteParam[] newParams = new RenderedPaletteParam[oldPalette.getParamCount()];
				for (int i = 0; i < oldPalette.getParamCount(); i++) {
					if (i == selectedIndex) {
						final ExtensionReference[] formulas = new ExtensionReference[4];
						formulas[0] = oldPalette.getParam(i).getFormula(0);
						formulas[1] = oldPalette.getParam(i).getFormula(1);
						formulas[2] = oldPalette.getParam(i).getFormula(2);
						formulas[3] = oldPalette.getParam(i).getFormula(3);
						final int[] colors = new int[2];
						colors[0] = oldPalette.getParam(i).getColor(1);
						colors[1] = oldPalette.getParam(i).getColor(0);
						newParams[i] = new RenderedPaletteParam(formulas, colors, oldPalette.getParam(i).getSize());
					}
					else {
						newParams[i] = oldPalette.getParam(i);
					}
				}
				final RenderedPalette palette = new RenderedPalette(newParams);
				field.getModel().setRenderedPalette(palette, false);
			}
			else if (field.getSelectionModel() != null) {
				selectedIndex = overIndex;
				field.getSelectionModel().setSelectedIndex(selectedIndex);
				field.repaint();
			}
		}

		/**
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		public void mouseEntered(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		public void mouseExited(final MouseEvent e) {
			if ((resizeIndex == -1) && (moveIndex == -1)) {
				overIndex = -1;
				field.repaint();
			}
		}

		/**
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		public void mousePressed(final MouseEvent e) {
			final RenderedPalette palette = field.getModel().getRenderedPalette();
			oldPalette = palette;
			final Insets insets = field.getInsets();
			mx = e.getX() - insets.left - 4;
			my = e.getY() - insets.top - 4;
			double offset = 0;
			double length = 0;
			for (int i = 0; i < palette.getParamCount() - 1; i++) {
				length = (w * palette.getParam(i).getSize()) / 100.0;
				if (offset + length > w) {
					length = w - offset;
				}
				if (length >= 0) {
					if ((e.getModifiers() & InputEvent.SHIFT_MASK) == 0) {
						if ((mx > offset + length - 5) && (mx < offset + length)) {
							if ((my > h - 16) && (my < h)) {
								resizeIndex = i;
								break;
							}
						}
						else if ((mx >= offset + length) && (mx < offset + length + 5)) {
							if ((my > h - 16) && (my < h)) {
								if (palette.getParam(i + 1).getSize() < 0.001) {
									resizeIndex = i + 1;
								}
								else {
									resizeIndex = i;
								}
								break;
							}
						}
					}
				}
				offset += length;
			}
			for (int i = palette.getParamCount() - 1; i > 0; i--) {
				length = (w * palette.getParam(i - 1).getSize()) / 100.0;
				if (offset - length < 0) {
					length = offset;
				}
				if (length >= 0) {
					if ((e.getModifiers() & InputEvent.SHIFT_MASK) != 0) {
						if ((mx > offset) && (mx < offset + 5)) {
							if ((my > h - 16) && (my < h)) {
								resizeIndex = i;
								break;
							}
						}
						else if ((mx > offset - 5) && (mx <= offset)) {
							if ((my > h - 16) && (my < h)) {
								if (palette.getParam(i - 1).getSize() < 0.001) {
									resizeIndex = i - 1;
								}
								else {
									resizeIndex = i;
								}
								break;
							}
						}
					}
				}
				offset -= length;
			}
			offset = 0;
			for (int i = 0; i < palette.getParamCount(); i++) {
				length = (w * palette.getParam(i).getSize()) / 100.0;
				if (offset + length > w) {
					length = w - offset;
				}
				if (length >= 0) {
					if ((mx > offset + 5) && (mx < offset + length - 10)) {
						if ((my > h - 16) && (my < h)) {
							moveIndex = i;
							break;
						}
					}
				}
				offset += length;
			}
			field.repaint();
		}

		/**
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		public void mouseReleased(final MouseEvent e) {
			resizeIndex = -1;
			if (moveIndex != -1) {
				if ((overIndex != -1) && (overIndex != moveIndex)) {
					final int srcIndex = moveIndex;
					final int dstIndex = overIndex;
					if (dstIndex < srcIndex) {
						final RenderedPaletteParam[] newParams = new RenderedPaletteParam[oldPalette.getParamCount()];
						for (int i = 0; i < dstIndex; i++) {
							newParams[i] = oldPalette.getParam(i);
						}
						newParams[dstIndex] = oldPalette.getParam(srcIndex);
						for (int i = dstIndex + 1; i <= srcIndex; i++) {
							newParams[i] = oldPalette.getParam(i - 1);
						}
						for (int i = srcIndex + 1; i < oldPalette.getParamCount(); i++) {
							newParams[i] = oldPalette.getParam(i);
						}
						field.getSelectionModel().setSelectedIndex(overIndex);
						final RenderedPalette palette = new RenderedPalette(newParams);
						field.getModel().setRenderedPalette(palette, false);
					}
					else {
						final RenderedPaletteParam[] newParams = new RenderedPaletteParam[oldPalette.getParamCount()];
						for (int i = 0; i < srcIndex; i++) {
							newParams[i] = oldPalette.getParam(i);
						}
						for (int i = srcIndex + 1; i <= dstIndex; i++) {
							newParams[i - 1] = oldPalette.getParam(i);
						}
						newParams[dstIndex] = oldPalette.getParam(srcIndex);
						for (int i = dstIndex + 1; i < oldPalette.getParamCount(); i++) {
							newParams[i] = oldPalette.getParam(i);
						}
						field.getSelectionModel().setSelectedIndex(overIndex);
						final RenderedPalette palette = new RenderedPalette(newParams);
						field.getModel().setRenderedPalette(palette, false);
					}
				}
				moveIndex = -1;
			}
			if (isDirty) {
				isDirty = false;
				field.getModel().setRenderedPalette(newPalette, false);
				// logger.debug(newPalette);
			}
			else {
				field.repaint();
			}
		}

		/**
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		public void stateChanged(final ChangeEvent e) {
			selectedIndex = ((SingleSelectionModel) e.getSource()).getSelectedIndex();
			field.repaint();
		}
	}
}
