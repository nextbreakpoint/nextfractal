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
package com.nextbreakpoint.nextfractal.twister.ui.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIUtil;
import com.nextbreakpoint.nextfractal.queue.LibraryService;
import com.nextbreakpoint.nextfractal.queue.RenderService;
import com.nextbreakpoint.nextfractal.queue.RenderService.ServiceCallback;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow;

/**
 * @author Andrea Medeghini
 */
public class EditClipDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private static final String EDIT_CLIP_FRAME_TITLE = "editClipFrame.title";
	// private static final String EDIT_CLIP_FRAME_WIDTH = "editClipFrame.width";
	// private static final String EDIT_CLIP_FRAME_HEIGHT = "editClipFrame.height";
	// private static final String EDIT_CLIP_FRAME_ICON = "editClipFrame.icon";
	private final RenderClipDataRow clip;
	private final RenderService service;
	private JTextField nameTextField;
	private JTextField descriptionTextField;

	/**
	 * @param service
	 * @param clip
	 */
	public EditClipDialog(final RenderService service, final RenderClipDataRow clip) {
		this.clip = clip;
		this.service = service;
		// final int defaultWidth = Integer.parseInt(ServiceResources.getInstance().getString(EditClipDialog.EDIT_CLIP_FRAME_WIDTH));
		// final int defaultHeight = Integer.parseInt(ServiceResources.getInstance().getString(EditClipDialog.EDIT_CLIP_FRAME_HEIGHT));
		// final int width = Integer.getInteger(EditClipFrame.EDIT_CLIP_FRAME_WIDTH, defaultWidth);
		// final int height = Integer.getInteger(EditClipFrame.EDIT_CLIP_FRAME_HEIGHT, defaultHeight);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle(TwisterSwingResources.getInstance().getString(EditClipDialog.EDIT_CLIP_FRAME_TITLE));
		getContentPane().add(createClipPanel());
		addWindowListener(new DialogListener());
		// setSize(new Dimension(width, height));
		pack();
		setResizable(false);
		setModal(true);
	}

	private Box createClipPanel() {
		final Box namePanel = Box.createHorizontalBox();
		final Box descriptionPanel = Box.createHorizontalBox();
		nameTextField = GUIFactory.createTextField(clip.getClipName(), null, 20);
		final Dimension labelSize = new Dimension(80, 20);
		final JLabel nameLabel = GUIFactory.createLabel(TwisterSwingResources.getInstance().getString("label.name"), SwingConstants.RIGHT);
		nameLabel.setPreferredSize(labelSize);
		namePanel.add(nameLabel);
		namePanel.add(Box.createHorizontalStrut(8));
		namePanel.add(nameTextField);
		namePanel.add(Box.createHorizontalStrut(200));
		namePanel.add(Box.createHorizontalGlue());
		descriptionTextField = GUIFactory.createTextField(clip.getDescription(), null, 40);
		final JLabel descriptionLabel = GUIFactory.createLabel(TwisterSwingResources.getInstance().getString("label.description"), SwingConstants.RIGHT);
		descriptionLabel.setPreferredSize(labelSize);
		descriptionPanel.add(descriptionLabel);
		descriptionPanel.add(Box.createHorizontalStrut(8));
		descriptionPanel.add(descriptionTextField);
		final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonsPanel.add(GUIFactory.createButton(new SaveAction(), null));
		buttonsPanel.add(GUIFactory.createButton(new CloseAction(), null));
		final Box panel = Box.createVerticalBox();
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		panel.add(namePanel);
		panel.add(Box.createVerticalStrut(8));
		panel.add(descriptionPanel);
		panel.add(Box.createVerticalStrut(8));
		panel.add(buttonsPanel);
		return panel;
	}

	private class SaveAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public SaveAction() {
			super(TwisterSwingResources.getInstance().getString("action.save"));
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			boolean error = false;
			if (nameTextField.getText().trim().length() == 0) {
				nameTextField.setBackground(Color.RED);
				error = true;
			}
			if (!error) {
				clip.setClipName(nameTextField.getText());
				clip.setDescription(descriptionTextField.getText());
				service.execute(new ServiceCallback<Object>() {
					@Override
					public void executed(final Object value) {
						GUIUtil.executeTask(new CloseRunnable(), true);
					}

					@Override
					public void failed(final Throwable throwable) {
						JOptionPane.showMessageDialog(EditClipDialog.this, TwisterSwingResources.getInstance().getString("error.saveClip"), TwisterSwingResources.getInstance().getString("label.saveClip"), JOptionPane.ERROR_MESSAGE);
					}

					@Override
					public Object execute(final LibraryService service) throws Exception {
						service.saveClip(clip);
						return null;
					}
				});
			}
			else {
				JOptionPane.showMessageDialog(EditClipDialog.this, TwisterSwingResources.getInstance().getString("error.saveClip"), TwisterSwingResources.getInstance().getString("label.saveClip"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class CloseAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public CloseAction() {
			super(TwisterSwingResources.getInstance().getString("action.close"));
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			GUIUtil.executeTask(new CloseRunnable(), true);
		}
	}

	private class DialogListener extends WindowAdapter {
		/**
		 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowClosing(final WindowEvent e) {
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
