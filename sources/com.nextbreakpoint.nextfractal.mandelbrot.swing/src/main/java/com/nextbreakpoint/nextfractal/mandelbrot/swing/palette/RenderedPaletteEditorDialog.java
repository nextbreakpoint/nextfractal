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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.nextbreakpoint.nextfractal.core.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIUtil;
import com.nextbreakpoint.nextfractal.mandelbrot.swing.MandelbrotSwingResources;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPalette;

/**
 * @author Andrea Medeghini
 */
public class RenderedPaletteEditorDialog extends JDialog {
	private static final String STRING_APPLY_LABEL = "paletteEditor.apply.label";
	private static final String STRING_CANCEL_LABEL = "paletteEditor.cancel.label";
	private static final String STRING_APPLY_TOOLTIP = "paletteEditor.apply.tooltip";
	private static final String STRING_CANCEL_TOOLTIP = "paletteEditor.cancel.tooltip";
	private static final long serialVersionUID = 1L;
	private final RenderedPaletteEditor editor;
	private RenderedPalette copy;

	/**
	 * @param c
	 * @param title
	 * @param palette
	 */
	public RenderedPaletteEditorDialog(final JComponent c, final String title, final RenderedPalette palette) {
		editor = new RenderedPaletteEditor(palette);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(editor, BorderLayout.CENTER);
		getContentPane().add(createButtonsPanel(), BorderLayout.SOUTH);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new DialogListener());
		copy = editor.getPalette();
		pack();
		setTitle(title);
		setLocationRelativeTo(c);
		setResizable(false);
		setModal(true);
	}

	/**
	 * @return the palette.
	 */
	public RenderedPalette getPalette() {
		return editor.getPalette();
	}

	/**
	 * Creates a new buttons panel.
	 * 
	 * @return the buttons panel.
	 */
	protected JPanel createButtonsPanel() {
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		final JButton applyButton = GUIFactory.createButton(new ApplyButtonAction(), MandelbrotSwingResources.getInstance().getString(RenderedPaletteEditorDialog.STRING_APPLY_TOOLTIP));
		panel.add(applyButton);
		final JButton cancelButton = GUIFactory.createButton(new CancelButtonAction(), MandelbrotSwingResources.getInstance().getString(RenderedPaletteEditorDialog.STRING_CANCEL_TOOLTIP));
		panel.add(cancelButton);
		return panel;
	}

	private void doApply() {
		copy = editor.getPalette();
	}

	private void doCancel() {
		editor.getPalette(copy);
	}

	private class ApplyButtonAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public ApplyButtonAction() {
			super(MandelbrotSwingResources.getInstance().getString(RenderedPaletteEditorDialog.STRING_APPLY_LABEL), null);
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			doApply();
			GUIUtil.executeTask(new CloseRunnable(), true);
		}
	}

	private class CancelButtonAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public CancelButtonAction() {
			super(MandelbrotSwingResources.getInstance().getString(RenderedPaletteEditorDialog.STRING_CANCEL_LABEL), null);
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			doCancel();
			GUIUtil.executeTask(new CloseRunnable(), true);
		}
	}

	private class DialogListener extends WindowAdapter {
		/**
		 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowClosing(final WindowEvent e) {
			doCancel();
			GUIUtil.executeTask(new CloseRunnable(), true);
		}
	}

	private class CloseRunnable implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			setVisible(false);
		}
	}
}
