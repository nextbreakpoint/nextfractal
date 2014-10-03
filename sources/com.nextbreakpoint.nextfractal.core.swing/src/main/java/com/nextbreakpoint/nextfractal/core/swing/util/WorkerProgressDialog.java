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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.nextbreakpoint.nextfractal.core.swing.CoreSwingResources;
import com.nextbreakpoint.nextfractal.core.util.ProgressListener;

/**
 * @author Andrea Medeghini
 */
public class WorkerProgressDialog extends JDialog implements ProgressListener {
	private static final long serialVersionUID = 1L;
	private JLabel messageLabel;
	private final GUIWorker worker;
	private String message = " ";
	private boolean useInterminateProgressBar = true;
	private JProgressBar progressBar;
	private static final int MIN_WIDTH = 500;

	/**
	 * @param message
	 * @param worker
	 * @param useInterminateProgressBar
	 */
	public WorkerProgressDialog(final String message, final GUIWorker worker, final boolean useInterminateProgressBar) {
		this.worker = worker;
		this.message = message;
		this.useInterminateProgressBar = useInterminateProgressBar;
		setTitle(message);
		setUndecorated(true);
		initGui();
	}

	/**
	 * @param worker
	 * @param useInterminateProgressBar
	 */
	public WorkerProgressDialog(final GUIWorker worker, final boolean useInterminateProgressBar) {
		this(CoreSwingResources.getInstance().getString("message.wait"), worker, useInterminateProgressBar);
	}

	/**
	 * @param message
	 * @param worker
	 */
	public WorkerProgressDialog(final String message, final GUIWorker worker) {
		this(worker, true);
	}

	/**
	 * @param worker
	 */
	public WorkerProgressDialog(final GUIWorker worker) {
		this(worker, true);
	}

	private void initGui() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setModal(true);
		messageLabel = new JLabel(message);
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(useInterminateProgressBar);
		progressBar.setMinimumSize(new Dimension(MIN_WIDTH, progressBar.getSize().height));
		JPanel mainPanel = new JPanel(new BorderLayout(4, 4));
		mainPanel.setPreferredSize(new Dimension(MIN_WIDTH, 100));
		messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		mainPanel.add(messageLabel, BorderLayout.NORTH);
		mainPanel.add(progressBar, BorderLayout.CENTER);
		getContentPane().add(mainPanel);
		mainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		pack();
	}

	/**
	 * @param message
	 */
	public void setMessage(final String message) {
		GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
				messageLabel.setText(message);
				}
						}, true);
	}

	/**
	 * 
	 */
	public void start() {
		GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
				worker.addProgressListener(WorkerProgressDialog.this);
				worker.execute();
				setVisible(true);
				}
						}, true);
	}

	/**
	 * @see it.trend.lit.studio.workbench.ProgressListener#done()
	 */
	@Override
	public void done() {
		GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
				worker.removeProgressListener(WorkerProgressDialog.this);
				setVisible(false);
				dispose();
				}
						}, true);
	}

	/**
	 * @see it.trend.lit.studio.workbench.ProgressListener#failed(java.lang.Exception)
	 */
	@Override
	public void failed(final Throwable e) {
		GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
				worker.removeProgressListener(WorkerProgressDialog.this);
				setVisible(false);
				dispose();
				}
						}, true);
	}

	/**
	 * @see it.trend.lit.studio.workbench.ProgressListener#stateChanged(java.lang.String, float)
	 */
	@Override
	public void stateChanged(final String message, final int percentage) {
		GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
				messageLabel.setText(message);
				progressBar.getModel().setValue(percentage);
				}
						}, true);
	}

	/**
	 * @see it.trend.lit.studio.workbench.ProgressListener#stateChanged(java.lang.String)
	 */
	@Override
	public void stateChanged(final String message) {
		GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
				messageLabel.setText(message);
				}
						}, true);
	}
}
