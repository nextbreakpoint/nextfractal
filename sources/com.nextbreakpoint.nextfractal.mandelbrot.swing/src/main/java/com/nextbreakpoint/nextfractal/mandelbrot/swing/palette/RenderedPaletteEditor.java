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

import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPalette;

/**
 * @author Andrea Medeghini
 */
public class RenderedPaletteEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	private RenderedPaletteModel model;

	/**
	 * @param palette
	 */
	public RenderedPaletteEditor(final RenderedPalette palette) {
		if (palette == null) {
			throw new NullPointerException("palette == nul");
		}
		setupPanel(this, palette);
	}

	/**
	 * @param panel
	 * @param palette
	 */
	protected void setupPanel(final JPanel panel, final RenderedPalette palette) {
		model = new DefaultRenderedPaletteModel(palette);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(createRenderedPalettePanel(palette));
	}

	/**
	 * @param palette
	 * @return the panel.
	 */
	protected JPanel createRenderedPalettePanel(final RenderedPalette palette) {
		return new RenderedPalettePanel(model);
	}

	/**
	 * @param c
	 * @param title
	 * @param palette
	 * @return the palette.
	 */
	public static RenderedPalette showRenderedPaletteEditor(final JComponent c, final String title, final RenderedPalette palette) {
		final RenderedPaletteEditorDialog dialog = RenderedPaletteEditor.createRenderedPaletteEditor(c, title, palette);
		dialog.setVisible(true);
		return dialog.getPalette();
	}

	/**
	 * @param c
	 * @param title
	 * @param palette
	 * @param color
	 * @return the dialog.
	 */
	protected static RenderedPaletteEditorDialog createRenderedPaletteEditor(final JComponent c, final String title, final RenderedPalette palette) {
		return new RenderedPaletteEditorDialog(c, title, palette);
	}

	/**
	 * @return the palette.
	 */
	protected RenderedPalette getPalette() {
		return model.getRenderedPalette();
	}

	/**
	 * @param palette
	 */
	protected void getPalette(final RenderedPalette palette) {
		model.setRenderedPalette(palette, false);
	}
}
