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
package com.nextbreakpoint.nextfractal.core.ui.swing.color;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.nextbreakpoint.nextfractal.core.ui.swing.CoreSwingResources;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIUtil;

/**
 * @author Andrea Medeghini
 */
public class ColorChooserDialog extends JDialog {
	private static final String STRING_APPLY_LABEL = "colorChooser.apply.label";
	private static final String STRING_CANCEL_LABEL = "colorChooser.cancel.label";
	private static final String STRING_APPLY_TOOLTIP = "colorChooser.apply.tooltip";
	private static final String STRING_CANCEL_TOOLTIP = "colorChooser.cancel.tooltip";
	private static final long serialVersionUID = 1L;
	private final ColorChooser chooser;
	private Color copy;

	/**
	 * @param c
	 * @param title
	 * @param color
	 */
	public ColorChooserDialog(final JComponent c, final String title, final Color color) {
		chooser = new ColorChooser(color);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(chooser, BorderLayout.CENTER);
		getContentPane().add(createButtonsPanel(), BorderLayout.SOUTH);
		addWindowListener(new DialogListener());
		copy = chooser.getColor();
		pack();
		setTitle(title);
		setLocationRelativeTo(c);
		setResizable(false);
		setModal(true);
	}

	/**
	 * @return
	 */
	public Color getColor() {
		return chooser.getColor();
	}

	/**
	 * Creates a new buttons panel.
	 * 
	 * @return the buttons panel.
	 */
	protected JPanel createButtonsPanel() {
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		final JButton applyButton = GUIFactory.createButton(new ApplyButtonAction(), CoreSwingResources.getInstance().getString(ColorChooserDialog.STRING_APPLY_TOOLTIP));
		panel.add(applyButton);
		final JButton cancelButton = GUIFactory.createButton(new CancelButtonAction(), CoreSwingResources.getInstance().getString(ColorChooserDialog.STRING_CANCEL_TOOLTIP));
		panel.add(cancelButton);
		return panel;
	}

	private void doApply() {
		copy = chooser.getColor();
	}

	private void doCancel() {
		chooser.setColor(copy);
	}

	private class ApplyButtonAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public ApplyButtonAction() {
			super(CoreSwingResources.getInstance().getString(ColorChooserDialog.STRING_APPLY_LABEL), null);
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
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
			super(CoreSwingResources.getInstance().getString(ColorChooserDialog.STRING_CANCEL_LABEL), null);
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
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
		@Override
		public void run() {
			setVisible(false);
		}
	}
}
