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
package com.nextbreakpoint.nextfractal.twister.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;

import com.nextbreakpoint.nextfractal.queue.LibraryService;
import com.nextbreakpoint.nextfractal.queue.PreviewListener;
import com.nextbreakpoint.nextfractal.queue.RenderService;
import com.nextbreakpoint.nextfractal.queue.RenderService.ServiceCallback;
import com.nextbreakpoint.nextfractal.queue.RenderServiceRegistry;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClip;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow;
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfile;
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataRow;
import com.nextbreakpoint.nextfractal.queue.spool.JobServiceListener;
import com.nextbreakpoint.nextfractal.queue.spool.extension.SpoolExtensionConfig;
import com.nextbreakpoint.nextfractal.queue.spool.extension.SpoolExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.TwisterClip;
import com.nextbreakpoint.nextfractal.twister.TwisterClipXMLImporter;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterConfigBuilder;
import com.nextbreakpoint.nextfractal.twister.TwisterSequence;

import org.w3c.dom.Document;

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.swing.extension.ConfigurableExtensionComboBoxModel;
import com.nextbreakpoint.nextfractal.core.swing.extension.ExtensionListCellRenderer;
import com.nextbreakpoint.nextfractal.core.swing.osgi.IExtensionPointTreeCellRenderer;
import com.nextbreakpoint.nextfractal.core.swing.osgi.IExtensionPointTreeModel;
import com.nextbreakpoint.nextfractal.core.swing.util.AlternateTableCellRenderer;
import com.nextbreakpoint.nextfractal.core.swing.util.ExtendedGUIWorker;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIUtil;
import com.nextbreakpoint.nextfractal.core.swing.util.WorkerProgressDialog;
import com.nextbreakpoint.nextfractal.core.swing.util.ZIPFileFilter;
import com.nextbreakpoint.nextfractal.core.util.Rectangle;
import com.nextbreakpoint.nextfractal.core.xml.XML;
import com.nextbreakpoint.nextfractal.twister.swing.encoder.EncoderDialog;

public class ServiceFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String SERVICE_FRAME_TITLE = "serviceFrame.title";
	private static final String SERVICE_FRAME_WIDTH = "serviceFrame.width";
	private static final String SERVICE_FRAME_HEIGHT = "serviceFrame.height";
	private static final String SERVICE_FRAME_ICON = "serviceFrame.icon";
	private ServicePanel servicePanel;

	/**
	 * @param context
	 * @param service
	 * @param clipModel
	 * @param profileModel
	 * @param jobModel
	 * @throws HeadlessException
	 */
	public ServiceFrame(final ServiceContext context, final RenderService service, final RenderClipTableModel clipModel, final RenderProfileTableModel profileModel, final RenderJobTableModel jobModel) throws HeadlessException {
		servicePanel = new ServicePanel(context, service, clipModel, profileModel, jobModel);
		getContentPane().add(servicePanel);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		final int defaultWidth = Integer.parseInt(TwisterSwingResources.getInstance().getString(ServiceFrame.SERVICE_FRAME_WIDTH));
		final int defaultHeight = Integer.parseInt(TwisterSwingResources.getInstance().getString(ServiceFrame.SERVICE_FRAME_HEIGHT));
		final int width = Integer.getInteger(ServiceFrame.SERVICE_FRAME_WIDTH, defaultWidth);
		final int height = Integer.getInteger(ServiceFrame.SERVICE_FRAME_HEIGHT, defaultHeight);
		setTitle(TwisterSwingResources.getInstance().getString(ServiceFrame.SERVICE_FRAME_TITLE));
		final URL resource = ServiceFrame.class.getClassLoader().getResource(TwisterSwingResources.getInstance().getString(ServiceFrame.SERVICE_FRAME_ICON));
		if (resource != null) {
			setIconImage(getToolkit().createImage(resource));
		}
		setSize(new Dimension(width, height));
		final Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		p.x -= getWidth() / 2;
		p.y -= getHeight() / 2;
		this.setLocation(p);
		addWindowListener(new ServiceWindowListener(servicePanel));
	}

	/**
	 * @see java.awt.Window#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		if (servicePanel != null) {
			servicePanel.dispose();
			servicePanel = null;
		}
	}

	private class ServiceWindowListener extends WindowAdapter {
		private final ServicePanel servicePanel;

		/**
		 * @param canvas
		 */
		public ServiceWindowListener(final ServicePanel servicePanel) {
			this.servicePanel = servicePanel;
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowOpened(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowOpened(final WindowEvent e) {
			GUIUtil.executeTask(new Runnable() {
				public void run() {
					if (servicePanel != null) {
						try {
							if (!servicePanel.isStarted()) {
								servicePanel.start();
							}
						}
						catch (final ExtensionException x) {
							x.printStackTrace();
						}
					}
				}
			}, true);
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowClosing(final WindowEvent e) {
			if (servicePanel != null) {
				if (servicePanel.isStarted()) {
					servicePanel.stop();
				}
			}
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowDeiconified(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowDeiconified(final WindowEvent e) {
			GUIUtil.executeTask(new Runnable() {
				public void run() {
					if (servicePanel != null) {
						try {
							if (!servicePanel.isStarted()) {
								servicePanel.start();
							}
						}
						catch (final ExtensionException x) {
							x.printStackTrace();
						}
					}
				}
			}, true);
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowIconified(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowIconified(final WindowEvent e) {
			if (servicePanel != null) {
				if (servicePanel.isStarted()) {
					servicePanel.stop();
				}
			}
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowClosed(final WindowEvent e) {
		}
	}

	public static class ServicePanel extends JPanel {
		private static final Logger logger = Logger.getLogger(ServicePanel.class.getName());
		private static final long serialVersionUID = 1L;
		private static final String STRING_FRAME_TAB_CLIPS = "tab.clips";
		private static final String STRING_FRAME_TAB_JOBS = "tab.jobs";
		private static final String STRING_FRAME_TAB_BUNDLES = "tab.bundles";
		private static final String STRING_FRAME_TAB_EXTENSIONPOINTS = "tab.extensionPoints";
		private static final String STRING_FRAME_TREE_EXTENSIONPOINTS = "tree.extensionPoints";
		private final JButton clipOpenButton = GUIFactory.createSmallButton(new ClipOpenAction(), TwisterSwingResources.getInstance().getString("tooltip.openClip"));
		private final JButton clipCreateButton = GUIFactory.createSmallButton(new ClipCreateAction(), TwisterSwingResources.getInstance().getString("tooltip.createClip"));
		private final JButton clipDeleteButton = GUIFactory.createSmallButton(new ClipDeleteAction(), TwisterSwingResources.getInstance().getString("tooltip.deleteClip"));
		private final JButton clipModifyButton = GUIFactory.createSmallButton(new ClipModifyAction(), TwisterSwingResources.getInstance().getString("tooltip.modifyClip"));
		private final JButton clipImportButton = GUIFactory.createSmallButton(new ClipImportAction(), TwisterSwingResources.getInstance().getString("tooltip.importClip"));
		private final JButton clipExportButton = GUIFactory.createSmallButton(new ClipExportAction(), TwisterSwingResources.getInstance().getString("tooltip.exportClip"));
		private final JButton profileCreateButton = GUIFactory.createSmallButton(new ProfileCreateAction(), TwisterSwingResources.getInstance().getString("tooltip.createProfile"));
		private final JButton profileDeleteButton = GUIFactory.createSmallButton(new ProfileDeleteAction(), TwisterSwingResources.getInstance().getString("tooltip.deleteProfile"));
		private final JButton profileModifyButton = GUIFactory.createSmallButton(new ProfileModifyAction(), TwisterSwingResources.getInstance().getString("tooltip.modifyProfile"));
		private final JButton profileRenderButton = GUIFactory.createSmallButton(new ProfileRenderAction(), TwisterSwingResources.getInstance().getString("tooltip.renderProfile"));
		private final JButton profileCleanButton = GUIFactory.createSmallButton(new ProfileCleanAction(), TwisterSwingResources.getInstance().getString("tooltip.cleanProfile"));
		private final JButton profileAbortButton = GUIFactory.createSmallButton(new ProfileAbortAction(), TwisterSwingResources.getInstance().getString("tooltip.abortProfile"));
		private final JButton profileStartButton = GUIFactory.createSmallButton(new ProfileStartAction(), TwisterSwingResources.getInstance().getString("tooltip.startProfile"));
		private final JButton profileStopButton = GUIFactory.createSmallButton(new ProfileStopAction(), TwisterSwingResources.getInstance().getString("tooltip.stopProfile"));
		private final JButton profileExportButton = GUIFactory.createSmallButton(new ProfileExportAction(), TwisterSwingResources.getInstance().getString("tooltip.exportProfile"));
		private final JButton checkUpdateButton = GUIFactory.createSmallButton(new CheckUpdateAction(), TwisterSwingResources.getInstance().getString("tooltip.checkUpdate"));
		private final JButton showAboutButton = GUIFactory.createSmallButton(new ShowAboutAction(), TwisterSwingResources.getInstance().getString("tooltip.showAbout"));
		private final JButton changeWorkspaceButton = GUIFactory.createSmallButton(new ChangeWorkspaceAction(), TwisterSwingResources.getInstance().getString("tooltip.changeWorkspace"));
		private final JFileChooser clipChooser = new JFileChooser(System.getProperty("user.home"));
		private final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		private final JLabel spoolStatusLabel;
		private final JComboBox extensionComboBox;
		private final EncoderDialog encoderDialog;
		private final TwisterPreviewCanvas preview;
		private final ServiceTable clipTable;
		private final ServiceTable profileTable;
		private final ServiceTable jobTable;
		private final RenderClipTableModel clipModel;
		private final RenderProfileTableModel profileModel;
		private final RenderJobTableModel jobModel;
		private final Semaphore semaphore = new Semaphore(0, true);
		private final ServiceContext context;
		private final RenderService service;
		private final JobServiceListener listener;
		private final RefreshTask refreshTask;
		private final TableListener tableListener;
		private final PreviewListener previewListener;
		private final JLabel jobLabel;
		private final JLabel clipLabel;
		private final JLabel profileLabel;
		private volatile int copyProcessServiceStatus;
		private volatile int postProcessServiceStatus;
		private volatile int processServiceStatus;

		/**
		 * @param context
		 * @param service
		 * @param clipModel
		 * @param profileModel
		 * @param jobModel
		 */
		@SuppressWarnings("unchecked")
		public ServicePanel(final ServiceContext context, final RenderService service, final RenderClipTableModel clipModel, final RenderProfileTableModel profileModel, final RenderJobTableModel jobModel) {
			this.context = context;
			final JPanel clipPreview = new JPanel(new BorderLayout());
			preview = new TwisterPreviewCanvas();
			clipPreview.add(preview, BorderLayout.CENTER);
			clipPreview.add(Box.createVerticalStrut(40), BorderLayout.SOUTH);
			clipPreview.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.preview"));
			clipPreview.setBorder(BorderFactory.createEmptyBorder(28, 2, 2, 4));
			encoderDialog = new EncoderDialog(new JFrame(), service);
			setLayout(new BorderLayout());
			clipChooser.setFileFilter(new ZIPFileFilter());
			clipChooser.setMultiSelectionEnabled(false);
			fileChooser.setDialogTitle(TwisterSwingResources.getInstance().getString("label.workspace"));
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setMultiSelectionEnabled(false);
			final JPanel panel = new JPanel(new BorderLayout());
			clipTable = new ServiceTable(clipModel);
			clipTable.setRowHeight(24);
			clipTable.setGridColor(Color.LIGHT_GRAY);
			final JPanel clipTablePanel = new JPanel(new BorderLayout());
			final Box clipButtonPanel = Box.createHorizontalBox();
			clipButtonPanel.add(Box.createHorizontalGlue());
			clipButtonPanel.add(clipCreateButton);
			clipButtonPanel.add(clipModifyButton);
			clipButtonPanel.add(clipDeleteButton);
			clipButtonPanel.add(clipImportButton);
			clipButtonPanel.add(clipExportButton);
			clipButtonPanel.add(clipOpenButton);
			clipButtonPanel.add(Box.createHorizontalGlue());
			clipButtonPanel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
			Dimension labelSize = new Dimension(200, 20);
			jobLabel = new JLabel(TwisterSwingResources.getInstance().getString("label.jobPanel"));
			jobLabel.setMinimumSize(labelSize);
			jobLabel.setMaximumSize(labelSize);
			jobLabel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			clipLabel = new JLabel(TwisterSwingResources.getInstance().getString("label.clipPanel"));
			clipLabel.setMinimumSize(labelSize);
			clipLabel.setMaximumSize(labelSize);
			clipLabel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			profileLabel = new JLabel(TwisterSwingResources.getInstance().getString("label.profilePanel"));
			profileLabel.setMinimumSize(labelSize);
			profileLabel.setMaximumSize(labelSize);
			profileLabel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			clipTablePanel.add(clipLabel, BorderLayout.NORTH);
			clipTablePanel.add(new JScrollPane(clipTable), BorderLayout.CENTER);
			clipTablePanel.add(clipButtonPanel, BorderLayout.SOUTH);
			profileTable = new ServiceTable(profileModel);
			profileTable.setGridColor(Color.LIGHT_GRAY);
			final JPanel profileTablePanel = new JPanel(new BorderLayout());
			final Box profileButtonPanel = Box.createHorizontalBox();
			profileButtonPanel.add(Box.createHorizontalGlue());
			profileButtonPanel.add(profileCreateButton);
			profileButtonPanel.add(profileModifyButton);
			profileButtonPanel.add(profileDeleteButton);
			profileButtonPanel.add(profileRenderButton);
			profileButtonPanel.add(profileCleanButton);
			profileButtonPanel.add(profileAbortButton);
			profileButtonPanel.add(profileStartButton);
			profileButtonPanel.add(profileStopButton);
			profileButtonPanel.add(profileExportButton);
			profileButtonPanel.add(Box.createHorizontalGlue());
			profileButtonPanel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
			profileTablePanel.add(profileLabel, BorderLayout.NORTH);
			profileTablePanel.add(new JScrollPane(profileTable), BorderLayout.CENTER);
			profileTablePanel.add(profileButtonPanel, BorderLayout.SOUTH);
			jobTable = new ServiceTable(jobModel);
			jobTable.setGridColor(Color.LIGHT_GRAY);
			final JPanel jobTablePanel = new JPanel(new BorderLayout());
			final Box jobButtonPanel = Box.createHorizontalBox();
			final ConfigurableExtensionComboBoxModel model = new ConfigurableExtensionComboBoxModel(RenderServiceRegistry.getInstance().getSpoolRegistry(), false);
			spoolStatusLabel = GUIFactory.createLabel("", JLabel.LEFT);
			spoolStatusLabel.setPreferredSize(new Dimension(300, GUIFactory.DEFAULT_HEIGHT));
			model.setSelectedItemByExtensionId(service.getJobServiceReference().getExtensionId());
			extensionComboBox = GUIFactory.createSmallComboBox(model, TwisterSwingResources.getInstance().getString("tooltip.spool"));
			listener = new JobServiceListener() {
				public void stateChanged(final int serviceId, final int status, final String message) {
					GUIUtil.executeTask(new Runnable() {
						public void run() {
							switch (serviceId) {
								case RenderService.SERVICE_COPY_PROCESS: {
									copyProcessServiceStatus = status;
									updateButtons();
									break;
								}
								case RenderService.SERVICE_POST_PROCESS: {
									postProcessServiceStatus = status;
									updateButtons();
									break;
								}
								case RenderService.SERVICE_PROCESS: {
									spoolStatusLabel.setText(message);
									processServiceStatus = status;
									updateButtons();
									break;
								}
								default:
									break;
							}
						}
					}, true);
				}
			};
			spoolStatusLabel.setText("");
			service.addJobServiceListener(listener);
			extensionComboBox.setPreferredSize(new Dimension(160, GUIFactory.DEFAULT_HEIGHT));
			extensionComboBox.setRenderer(new ExtensionListCellRenderer());
			extensionComboBox.setOpaque(false);
			jobButtonPanel.add(extensionComboBox);
			jobButtonPanel.add(Box.createHorizontalStrut(8));
			jobButtonPanel.add(spoolStatusLabel);
			jobButtonPanel.add(Box.createHorizontalGlue());
			jobButtonPanel.add(changeWorkspaceButton);
			jobButtonPanel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
			jobTablePanel.add(jobLabel, BorderLayout.NORTH);
			jobTablePanel.add(new JScrollPane(jobTable), BorderLayout.CENTER);
			jobTablePanel.add(jobButtonPanel, BorderLayout.SOUTH);
			clipTablePanel.setOpaque(false);
			profileTablePanel.setOpaque(false);
			jobTablePanel.setOpaque(false);
			final JSplitPane clipsTableSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, clipTablePanel, clipPreview);
			clipsTableSplitPane.setOneTouchExpandable(true);
			clipsTableSplitPane.setDividerLocation(550);
			clipsTableSplitPane.setDividerSize(10);
			final JSplitPane clipsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, clipsTableSplitPane, profileTablePanel);
			clipsSplitPane.setOneTouchExpandable(false);
			clipsSplitPane.setDividerLocation(250);
			clipsSplitPane.setDividerSize(10);
			final FontMetrics clipTableFM = getFontMetrics(clipTable.getTableHeader().getFont());
			for (int i = 0; i < clipTable.getColumnCount(); i++) {
				clipTable.getColumnModel().getColumn(i).setCellRenderer(new AlternateTableCellRenderer());
				clipTable.getColumnModel().getColumn(i).setPreferredWidth(SwingUtilities.computeStringWidth(clipTableFM, (((String) clipTable.getColumnModel().getColumn(i).getHeaderValue()))));
			}
			clipTable.getColumnModel().getColumn(RenderClipTableModel.PREVIEW).setCellRenderer(new ImageRenderer());
			clipTable.getColumnModel().getColumn(RenderClipTableModel.PREVIEW).setMinWidth(64);
			clipTable.getColumnModel().getColumn(RenderClipTableModel.PREVIEW).setMaxWidth(64);
			clipTable.getColumnModel().getColumn(RenderClipTableModel.NAME).setPreferredWidth(100);
			final FontMetrics profileTableFM = getFontMetrics(profileTable.getTableHeader().getFont());
			for (int i = 0; i < profileTable.getColumnCount(); i++) {
				profileTable.getColumnModel().getColumn(i).setCellRenderer(new AlternateTableCellRenderer());
				profileTable.getColumnModel().getColumn(i).setPreferredWidth(SwingUtilities.computeStringWidth(profileTableFM, (((String) profileTable.getColumnModel().getColumn(i).getHeaderValue()))));
			}
			profileTable.getColumnModel().getColumn(RenderProfileTableModel.NAME).setPreferredWidth(60);
			profileTable.getColumnModel().getColumn(RenderProfileTableModel.STATUS).setPreferredWidth(50);
			final FontMetrics jobTableFM = getFontMetrics(jobTable.getTableHeader().getFont());
			for (int i = 0; i < jobTable.getColumnCount(); i++) {
				jobTable.getColumnModel().getColumn(i).setCellRenderer(new AlternateTableCellRenderer());
				jobTable.getColumnModel().getColumn(i).setPreferredWidth(SwingUtilities.computeStringWidth(jobTableFM, (((String) jobTable.getColumnModel().getColumn(i).getHeaderValue()))));
			}
			jobTable.getColumnModel().getColumn(RenderJobTableModel.NAME).setPreferredWidth(100);
			jobTable.getColumnModel().getColumn(RenderJobTableModel.TYPE).setPreferredWidth(80);
			jobTablePanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			clipTablePanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			profileTablePanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			clipsTableSplitPane.setBorder(BorderFactory.createEmptyBorder());
			clipsSplitPane.setBorder(BorderFactory.createEmptyBorder());
			this.add(panel, BorderLayout.CENTER);
			final JPanel clipsPanel = new JPanel(new BorderLayout());
			final JPanel jobsPanel = new JPanel(new BorderLayout());
			clipsPanel.setOpaque(false);
			jobsPanel.setOpaque(false);
			jobTablePanel.setOpaque(true);
			clipsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
			jobsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
			clipsPanel.add(clipsSplitPane);
			jobsPanel.add(jobTablePanel, BorderLayout.CENTER);
			final JTree bundleTree = createBundleTree();
			final JTree extensionPointTree = createExtensionPointTree();
			final JTabbedPane tabbedPane = new JTabbedPane();
			final JPanel bundlePanel = new JPanel(new BorderLayout());
			final Box bundleButtons = Box.createHorizontalBox();
			bundleButtons.add(Box.createHorizontalGlue());
			bundleButtons.add(checkUpdateButton);
			bundleButtons.add(showAboutButton);
			bundleButtons.add(Box.createHorizontalGlue());
			bundleButtons.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
			final JPanel bundleTreePanel = new JPanel(new BorderLayout());
			bundleTreePanel.add(new JScrollPane(bundleTree), BorderLayout.CENTER);
			bundleTreePanel.add(bundleButtons, BorderLayout.SOUTH);
			bundleTreePanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			bundlePanel.add(bundleTreePanel, BorderLayout.CENTER);
			bundlePanel.setOpaque(false);
			bundlePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
			final JPanel extensionPointTreePanel = new JPanel(new BorderLayout());
			extensionPointTreePanel.add(new JScrollPane(extensionPointTree), BorderLayout.CENTER);
			extensionPointTreePanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			final JPanel extensionPointPanel = new JPanel(new BorderLayout());
			extensionPointPanel.add(extensionPointTreePanel);
			extensionPointPanel.setOpaque(false);
			extensionPointPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
			tabbedPane.addTab(TwisterSwingResources.getInstance().getString(ServicePanel.STRING_FRAME_TAB_CLIPS), clipsPanel);
			tabbedPane.addTab(TwisterSwingResources.getInstance().getString(ServicePanel.STRING_FRAME_TAB_JOBS), jobsPanel);
			tabbedPane.addTab(TwisterSwingResources.getInstance().getString(ServicePanel.STRING_FRAME_TAB_BUNDLES), bundlePanel);
			tabbedPane.addTab(TwisterSwingResources.getInstance().getString(ServicePanel.STRING_FRAME_TAB_EXTENSIONPOINTS), extensionPointPanel);
			panel.add(tabbedPane);
			tabbedPane.addChangeListener(new ChangeListener() {
				public void stateChanged(final ChangeEvent e) {
					if (tabbedPane.getSelectedIndex() == 0) {
						if (clipTable.getSelectedRowCount() == 1) {
							profileModel.clear();
							updateButtons();
							final int clipId = clipModel.getClip(clipTable.convertRowIndexToModel(clipTable.getSelectedRow())).getClipId();
							service.execute(new ServiceCallback<RenderClipDataRow>() {
								public void executed(final RenderClipDataRow profile) {
									profileModel.reload(clipId);
									try {
										final TwisterClipXMLImporter importer = new TwisterClipXMLImporter();
										final InputStream is = service.getLibraryService().getClipInputStream(clipId);
										final Document doc = XML.loadDocument(is, "twister-clip.xml");
										is.close();
										final TwisterClip clip = importer.importFromElement(doc.getDocumentElement());
										try {
											preview.acquire();
											preview.stopRenderers();
											preview.stop();
											preview.start(clip);
											preview.startRenderers();
											preview.setArea(null);
											preview.release();
											preview.refresh();
										}
										catch (InterruptedException e) {
											Thread.currentThread().interrupt();
										}
									}
									catch (final Exception x) {
										ServicePanel.logger.log(Level.WARNING, "Can't load the clip " + clipId, x);
										x.printStackTrace();
										JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.loadClip"), TwisterSwingResources.getInstance().getString("label.loadClip"), JOptionPane.ERROR_MESSAGE);
									}
								}

								public void failed(final Throwable throwable) {
									ServicePanel.logger.log(Level.WARNING, "Can't get the clip " + clipId);
									throwable.printStackTrace();
								}

								public RenderClipDataRow execute(final LibraryService service) throws Exception {
									return service.getClip(clipId);
								}
							});
						}
						else {
							profileModel.clear();
							updateButtons();
						}
					}
					else {
						try {
							preview.acquire();
							preview.stopRenderers();
							preview.stop();
							preview.setArea(null);
							preview.release();
							preview.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				}
			});
			clipTable.getSelectionModel().addListSelectionListener(new ClipListSelectionListener());
			profileTable.getSelectionModel().addListSelectionListener(new ProfileListSelectionListener());
			this.clipModel = clipModel;
			this.profileModel = profileModel;
			this.jobModel = jobModel;
			this.service = service;
			previewListener = new PreviewListener() {
				public void updated(final int clipId) {
					clipTable.repaint();
				}
			};
			service.addPreviewListener(previewListener);
			extensionComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					service.setJobServiceReference(((ConfigurableExtension<SpoolExtensionRuntime<?>, SpoolExtensionConfig>) ((ConfigurableExtensionComboBoxModel) extensionComboBox.getModel()).getSelectedItem()).getExtensionReference());
					resumeSpool();
				}
			});
			tableListener = new TableListener();
			profileModel.addTableModelListener(tableListener);
			clipModel.addTableModelListener(tableListener);
			jobModel.addTableModelListener(tableListener);
			resumeSpool();
			refreshTask = new RefreshTask();
			refreshTask.start();
		}

		private void resumeSpool() {
			service.execute(new ServiceCallback<Object>() {
				public void executed(final Object value) {
					jobModel.reload();
				}

				public void failed(final Throwable throwable) {
				}

				public Object execute(final LibraryService service) throws Exception {
					service.resumeJobs();
					return null;
				}
			});
		}

		public void dispose() {
			profileModel.removeTableModelListener(tableListener);
			clipModel.removeTableModelListener(tableListener);
			jobModel.removeTableModelListener(tableListener);
			service.removeJobServiceListener(listener);
		}

		/**
		 * @return
		 */
		public boolean isStarted() {
			return preview.isStarted();
		}

		/**
		 * @throws ExtensionException
		 */
		public void start() throws ExtensionException {
			if (clipTable.getSelectedRowCount() > 0) {
				profileModel.clear();
				updateButtons();
			}
			else {
				if (clipTable.getRowCount() > 0) {
					clipTable.getSelectionModel().setSelectionInterval(0, 0);
				}
				profileModel.clear();
				updateButtons();
			}
			service.addPreviewListener(previewListener);
			preview.start();
			refreshTask.start();
		}

		/**
		 * 
		 */
		public void stop() {
			refreshTask.stop();
			preview.stop();
			service.removePreviewListener(previewListener);
		}

		private void updateButtons() {
			clipCreateButton.setEnabled(true);
			clipDeleteButton.setEnabled((clipTable.getSelectedRowCount() > 0) && clipStatusEquals(0));
			clipModifyButton.setEnabled((clipTable.getSelectedRowCount() == 1) && clipStatusEquals(0));
			clipImportButton.setEnabled((clipTable.getSelectedRowCount() == 1) && clipStatusEquals(0));
			clipExportButton.setEnabled(clipTable.getSelectedRowCount() > 0);
			clipOpenButton.setEnabled(clipTable.getSelectedRowCount() == 1);
			profileCreateButton.setEnabled(clipTable.getSelectedRowCount() == 1);
			profileDeleteButton.setEnabled((clipTable.getSelectedRowCount() == 1) && (profileTable.getSelectedRowCount() > 0) && profileStatusEquals(0));
			profileModifyButton.setEnabled((clipTable.getSelectedRowCount() == 1) && (profileTable.getSelectedRowCount() == 1) && profileStatusEquals(0));
			profileRenderButton.setEnabled((clipTable.getSelectedRowCount() == 1) && (profileTable.getSelectedRowCount() > 0) && profileStatusEquals(0));
			profileCleanButton.setEnabled((clipTable.getSelectedRowCount() == 1) && (profileTable.getSelectedRowCount() > 0) && profileStatusEquals(0));
			profileAbortButton.setEnabled((clipTable.getSelectedRowCount() == 1) && (profileTable.getSelectedRowCount() > 0) && profileStatusNotEquals(0));
			profileStartButton.setEnabled((clipTable.getSelectedRowCount() == 1) && (profileTable.getSelectedRowCount() > 0) && profileStatusNotEquals(0));
			profileStopButton.setEnabled((clipTable.getSelectedRowCount() == 1) && (profileTable.getSelectedRowCount() > 0) && profileStatusNotEquals(0));
			if (profileTable.getSelectedRowCount() == 1) {
				final RenderProfileDataRow profile = profileModel.getProfile(profileTable.convertRowIndexToModel(profileTable.getSelectedRow()));
				final boolean busy = (profile.getStatus() == 0) && (profile.getJobCreated() == profile.getJobStored()) && (profile.getJobStored() > 0);
				profileExportButton.setEnabled((clipTable.getSelectedRowCount() == 1) && (profileTable.getSelectedRowCount() == 1) && busy);
			}
			else {
				profileExportButton.setEnabled(false);
			}
			final boolean idle = (processServiceStatus == JobServiceListener.STATUS_IDLE) && (copyProcessServiceStatus == JobServiceListener.STATUS_IDLE) && (postProcessServiceStatus == JobServiceListener.STATUS_IDLE);
			changeWorkspaceButton.setEnabled(idle);
			extensionComboBox.setEnabled(idle);
		}

		private boolean clipStatusEquals(final int status) {
			if (clipTable.getSelectedRowCount() == 0) {
				return false;
			}
			final int[] rows = clipTable.getSelectedRows();
			for (int i = 0; i < rows.length; i++) {
				if (clipModel.getClip(clipTable.convertRowIndexToModel(rows[i])).getStatus() != status) {
					return false;
				}
			}
			return true;
		}

		private boolean profileStatusEquals(final int status) {
			if (profileTable.getSelectedRowCount() == 0) {
				return false;
			}
			final int[] rows = profileTable.getSelectedRows();
			for (int i = 0; i < rows.length; i++) {
				if (profileModel.getProfile(profileTable.convertRowIndexToModel(rows[i])).getStatus() != status) {
					return false;
				}
			}
			return true;
		}

		private boolean profileStatusNotEquals(final int status) {
			if (profileTable.getSelectedRowCount() == 0) {
				return false;
			}
			final int[] rows = profileTable.getSelectedRows();
			for (int i = 0; i < rows.length; i++) {
				if (profileModel.getProfile(profileTable.convertRowIndexToModel(rows[i])).getStatus() == status) {
					return false;
				}
			}
			return true;
		}

		private RenderClipDataRow createDefaultRenderClip() {
			final RenderClipDataRow clip = new RenderClipDataRow(new RenderClip());
			clip.setClipName("New Clip");
			clip.setDescription("Default Configuration");
			return clip;
		}

		private RenderProfileDataRow createDefaultRenderProfile(final int clipId) {
			final RenderProfileDataRow profile = new RenderProfileDataRow(new RenderProfile());
			profile.setClipId(clipId);
			profile.setProfileName("New Profile");
			profile.setImageWidth(640);
			profile.setImageHeight(480);
			profile.setQuality(100);
			return profile;
		}

		private void showEditClipWindow(final RenderClipDataRow clip) {
			final EditClipDialog editClipDialog = new EditClipDialog(service, clip);
			GUIUtil.centerWindow(editClipDialog, ServicePanel.this.getLocationOnScreen(), ServicePanel.this.getBounds());
			editClipDialog.setVisible(true);
		}

		private void showEditProfileWindow(final RenderProfileDataRow profile) {
			final EditProfileDialog editProfileDialog = new EditProfileDialog(service, profile);
			GUIUtil.centerWindow(editProfileDialog, ServicePanel.this.getLocationOnScreen(), ServicePanel.this.getBounds());
			editProfileDialog.setVisible(true);
		}

		private JTree createBundleTree() {
//			final BundleTreeModel treeModel = new BundleTreeModel(new DefaultMutableTreeNode(TwisterSwingResources.getInstance().getString(ServicePanel.STRING_FRAME_TREE_BUNDLES)));
			final JTree tree = new JTree(/*treeModel*/);
			tree.setFont(GUIFactory.NORMAL_FONT);
			tree.setShowsRootHandles(true);
//			tree.setCellRenderer(new BundleTreeCellRenderer());
			return tree;
		}

		private JTree createExtensionPointTree() {
			final IExtensionPointTreeModel treeModel = new IExtensionPointTreeModel(new DefaultMutableTreeNode(TwisterSwingResources.getInstance().getString(ServicePanel.STRING_FRAME_TREE_EXTENSIONPOINTS)));
			final JTree tree = new JTree(treeModel);
			tree.setFont(GUIFactory.NORMAL_FONT);
			tree.setShowsRootHandles(true);
			tree.setCellRenderer(new IExtensionPointTreeCellRenderer());
			return tree;
		}

		private class RefreshTask implements Runnable {
			private final Object lock = new Object();
			private Thread refreshThread;
			private boolean running;
			private boolean dirty;

			/**
			 * @see java.lang.Runnable#run()
			 */
			public void run() {
				try {
					for (;;) {
						synchronized (lock) {
							if (!running) {
								break;
							}
							while (!dirty) {
								lock.wait();
							}
							dirty = false;
						}
						Thread.sleep(500);
						GUIUtil.executeTask(new Runnable() {
							public void run() {
								int[] selectedRows = clipTable.getSelectedRows();
								if ((selectedRows != null) && (selectedRows.length > 0)) {
									final int clipId = clipModel.getClip(clipTable.convertRowIndexToModel(selectedRows[selectedRows.length - 1])).getClipId();
									service.execute(new ServiceCallback<RenderClipDataRow>() {
										public void executed(final RenderClipDataRow profile) {
											profileModel.reload(clipId);
											try {
												final TwisterClipXMLImporter importer = new TwisterClipXMLImporter();
												final InputStream is = service.getLibraryService().getClipInputStream(clipId);
												final Document doc = XML.loadDocument(is, "twister-clip.xml");
												is.close();
												final TwisterClip clip = importer.importFromElement(doc.getDocumentElement());
												try {
													preview.acquire();
													preview.stopRenderers();
													preview.stop();
													preview.start(clip);
													preview.startRenderers();
													preview.setArea(null);
													preview.release();
													preview.refresh();
												}
												catch (InterruptedException e) {
													Thread.currentThread().interrupt();
												}
											}
											catch (final Exception x) {
												ServicePanel.logger.log(Level.WARNING, "Can't load the clip " + clipId, x);
												JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.loadClip"), TwisterSwingResources.getInstance().getString("label.loadClip"), JOptionPane.ERROR_MESSAGE);
											}
										}

										public void failed(final Throwable throwable) {
											ServicePanel.logger.log(Level.WARNING, "Can't get the clip " + clipId);
											JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.loadClip"), TwisterSwingResources.getInstance().getString("label.loadClip"), JOptionPane.ERROR_MESSAGE);
										}

										public RenderClipDataRow execute(final LibraryService service) throws Exception {
											return service.getClip(clipId);
										}
									});
								}
								else {
									try {
										preview.acquire();
										preview.stopRenderers();
										preview.stop();
										preview.setArea(null);
										preview.release();
										preview.refresh();
									}
									catch (InterruptedException e) {
										Thread.currentThread().interrupt();
									}
								}
								updateButtons();
							}
						}, false);
					}
				}
				catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}

			public void start() {
				if (refreshThread == null) {
					refreshThread = new Thread(this, "Refresh Thread");
					refreshThread.setPriority(Thread.NORM_PRIORITY);
					refreshThread.setDaemon(true);
					running = true;
					refreshThread.start();
				}
			}

			public void stop() {
				if (refreshThread != null) {
					running = false;
					refreshThread.interrupt();
					try {
						refreshThread.join();
					}
					catch (InterruptedException e) {
					}
					refreshThread = null;
				}
			}

			public void wakeup() {
				synchronized (lock) {
					dirty = true;
					lock.notify();
				}
			}
		}

		private class ClipListSelectionListener implements ListSelectionListener {
			public void valueChanged(final ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					refreshTask.wakeup();
				}
			}
		}

		private class ProfileListSelectionListener implements ListSelectionListener {
			public void valueChanged(final ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if (profileTable.getSelectedRowCount() > 0) {
						final int profileId = profileModel.getProfile(profileTable.convertRowIndexToModel(profileTable.getSelectedRow())).getProfileId();
						service.execute(new ServiceCallback<RenderProfileDataRow>() {
							public void executed(final RenderProfileDataRow profile) {
								preview.setArea(new Rectangle(profile.getOffsetX(), profile.getOffsetY(), profile.getImageWidth(), profile.getImageHeight()));
							}

							public void failed(final Throwable throwable) {
								ServicePanel.logger.log(Level.WARNING, "Can't load the profile " + profileId);
								JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.loadProfile"), TwisterSwingResources.getInstance().getString("label.loadProfile"), JOptionPane.ERROR_MESSAGE);
							}

							public RenderProfileDataRow execute(final LibraryService service) throws Exception {
								return service.getProfile(profileId);
							}
						});
					}
					updateButtons();
				}
			}
		}

		private class ClipOpenAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ClipOpenAction() {
				super(TwisterSwingResources.getInstance().getString("action.open"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				if (clipTable.getSelectedRowCount() == 1) {
					final int row = clipTable.getSelectedRow();
					final RenderClipDataRow clip = clipModel.getClip(clipTable.convertRowIndexToModel(row));
					service.execute(new ServiceCallback<RenderClipDataRow>() {
						public void executed(final RenderClipDataRow value) {
							try {
								final InputStream is = service.getLibraryService().getClipInputStream(clip.getClipId());
								final Document doc = XML.loadDocument(is, "twister-clip.xml");
								final TwisterClipXMLImporter importer = new TwisterClipXMLImporter();
								final TwisterClip clip = importer.importFromElement(doc.getDocumentElement());
								is.close();
								context.openClip(clip);
								semaphore.release();
							}
							catch (final Exception x) {
								ServicePanel.logger.log(Level.WARNING, "Can't load the clip", x);
								semaphore.release();
								JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.loadClip"), TwisterSwingResources.getInstance().getString("label.openClip"), JOptionPane.ERROR_MESSAGE);
							}
						}

						public void failed(final Throwable throwable) {
							ServicePanel.logger.log(Level.WARNING, "Can't open the clip", throwable);
							semaphore.release();
							JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.openClip"), TwisterSwingResources.getInstance().getString("label.openClip"), JOptionPane.ERROR_MESSAGE);
						}

						public RenderClipDataRow execute(final LibraryService service) throws Exception {
							return service.getClip(clip.getClipId());
						}
					});
					try {
						semaphore.acquire();
					}
					catch (final InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		private class ClipCreateAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ClipCreateAction() {
				super(TwisterSwingResources.getInstance().getString("action.create"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				final RenderClipDataRow dataRow = createDefaultRenderClip();
				service.execute(new ServiceCallback<Object>() {
					public void executed(final Object value) {
						semaphore.release();
					}

					public void failed(final Throwable throwable) {
						ServicePanel.logger.log(Level.WARNING, "Can't create the clip", throwable);
						semaphore.release();
						JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.createClip"), TwisterSwingResources.getInstance().getString("label.createClip"), JOptionPane.ERROR_MESSAGE);
					}

					public Object execute(final LibraryService service) throws Exception {
						final TwisterConfigBuilder configBuilder = new TwisterConfigBuilder();
						final TwisterClip clip = new TwisterClip();
						final TwisterSequence sequence = new TwisterSequence();
						clip.addSequence(sequence);
						final TwisterConfig config = configBuilder.createDefaultConfig();
						sequence.setFinalConfig(config);
						service.createClip(dataRow, clip);
						return null;
					}
				});
				try {
					semaphore.acquire();
				}
				catch (final InterruptedException x) {
					Thread.currentThread().interrupt();
				}
			}
		}

		private class ClipDeleteAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ClipDeleteAction() {
				super(TwisterSwingResources.getInstance().getString("action.delete"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				if (clipTable.getSelectedRowCount() > 0) {
					if (JOptionPane.showConfirmDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("message.confirmDeleteClips"), TwisterSwingResources.getInstance().getString("label.deleteClips"), JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
						final WorkerProgressDialog dialog = new WorkerProgressDialog(new ClipDeleteWorker(), false);
						GUIUtil.centerWindow(dialog, getLocationOnScreen(), ServicePanel.this.getBounds());
						dialog.start();
						updateButtons();
					}
				}
			}

			private class ClipDeleteWorker extends ExtendedGUIWorker {
				/**
				 * @see com.nextbreakpoint.nextfractal.core.swing.util.DefaultGUIWorker#doInBackground()
				 */
				@Override
				public Object doInBackground() throws Exception {
					final int[] rows = clipTable.getSelectedRows();
					final RenderClipDataRow[] clips = new RenderClipDataRow[rows.length];
					final boolean[] error = new boolean[] { false };
					for (int i = 0; i < rows.length; i++) {
						clips[i] = clipModel.getClip(clipTable.convertRowIndexToModel(rows[i]));
					}
					for (int i = 0; i < rows.length; i++) {
						final int percentage = (int) Math.rint((((float) i) / (rows.length - 1)) * 100f);
						final RenderClipDataRow clip = clips[i];
						service.execute(new ServiceCallback<Object>() {
							public void executed(final Object value) {
								semaphore.release();
							}

							public void failed(final Throwable throwable) {
								ServicePanel.logger.log(Level.WARNING, "Can't delete the clip", throwable);
								error[0] = true;
								semaphore.release();
								failed(throwable);
								JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.deleteClip"), TwisterSwingResources.getInstance().getString("label.deleteClip"), JOptionPane.ERROR_MESSAGE);
							}

							public Object execute(final LibraryService service) throws Exception {
								stateChanged(TwisterSwingResources.getInstance().getString("message.deletingClip") + " " + clip.getClipId() + "...", percentage);
								service.deleteClip(clip);
								return null;
							}
						});
						try {
							semaphore.acquire();
						}
						catch (final InterruptedException x) {
							Thread.currentThread().interrupt();
						}
						if (error[0]) {
							break;
						}
					}
					clipTable.clearSelection();
					return null;
				}
			}
		}

		private class ClipModifyAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ClipModifyAction() {
				super(TwisterSwingResources.getInstance().getString("action.modify"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				if (clipTable.getSelectedRowCount() == 1) {
					final int row = clipTable.getSelectedRow();
					final RenderClipDataRow clip = clipModel.getClip(clipTable.convertRowIndexToModel(row));
					service.execute(new ServiceCallback<RenderClipDataRow>() {
						public void executed(final RenderClipDataRow clip) {
							GUIUtil.executeTask(new Runnable() {
								public void run() {
									showEditClipWindow(clip);
								}
							}, true);
							semaphore.release();
						}

						public void failed(final Throwable throwable) {
							ServicePanel.logger.log(Level.WARNING, "Can't get the clip " + clip.getClipId(), throwable);
							semaphore.release();
						}

						public RenderClipDataRow execute(final LibraryService service) throws Exception {
							return service.getClip(clip.getClipId());
						}
					});
					try {
						semaphore.acquire();
					}
					catch (final InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		private class ClipImportAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ClipImportAction() {
				super(TwisterSwingResources.getInstance().getString("action.importClip"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				if (clipTable.getSelectedRowCount() > 0) {
					if (JOptionPane.showConfirmDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("message.confirmImportClips"), TwisterSwingResources.getInstance().getString("label.importClips"), JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
						final int[] rows = clipTable.getSelectedRows();
						final boolean[] error = new boolean[] { false };
						for (final int row : rows) {
							final RenderClipDataRow clip = clipModel.getClip(clipTable.convertRowIndexToModel(row));
							clipChooser.setDialogTitle(TwisterSwingResources.getInstance().getString("label.importClip"));
							final int returnVal = clipChooser.showOpenDialog(ServicePanel.this);
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								final File file = clipChooser.getSelectedFile();
								service.execute(new ServiceCallback<Object>() {
									public void executed(final Object value) {
										semaphore.release();
									}

									public void failed(final Throwable throwable) {
										ServicePanel.logger.log(Level.WARNING, "Can't import the clip", throwable);
										error[0] = true;
										semaphore.release();
										JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.importClip"), TwisterSwingResources.getInstance().getString("label.importClip"), JOptionPane.ERROR_MESSAGE);
									}

									public Object execute(final LibraryService service) throws Exception {
										service.importClip(clip, file);
										service.resetProfiles(clip.getClipId());
										return null;
									}
								});
								try {
									semaphore.acquire();
								}
								catch (final InterruptedException x) {
									Thread.currentThread().interrupt();
								}
							}
							if (error[0]) {
								break;
							}
						}
					}
				}
				if (clipTable.getSelectedRowCount() > 0) {
					profileModel.clear();
					updateButtons();
					final int clipId = clipModel.getClip(clipTable.convertRowIndexToModel(clipTable.getSelectedRow())).getClipId();
					service.execute(new ServiceCallback<RenderClipDataRow>() {
						public void executed(final RenderClipDataRow profile) {
							profileModel.reload(clipId);
							try {
								final TwisterClipXMLImporter importer = new TwisterClipXMLImporter();
								final InputStream is = service.getLibraryService().getClipInputStream(clipId);
								final Document doc = XML.loadDocument(is, "twister-clip.xml");
								is.close();
								final TwisterClip clip = importer.importFromElement(doc.getDocumentElement());
								try {
									preview.acquire();
									preview.stopRenderers();
									preview.stop();
									preview.start(clip);
									preview.startRenderers();
									preview.setArea(null);
									preview.release();
									preview.refresh();
								}
								catch (InterruptedException e) {
									Thread.currentThread().interrupt();
								}
							}
							catch (final Exception x) {
								ServicePanel.logger.log(Level.WARNING, "Can't load the clip " + clipId, x);
								x.printStackTrace();
								JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.loadClip"), TwisterSwingResources.getInstance().getString("label.loadClip"), JOptionPane.ERROR_MESSAGE);
							}
						}

						public void failed(final Throwable throwable) {
							ServicePanel.logger.log(Level.WARNING, "Can't get the clip " + clipId);
							throwable.printStackTrace();
						}

						public RenderClipDataRow execute(final LibraryService service) throws Exception {
							return service.getClip(clipId);
						}
					});
				}
				else {
					profileModel.clear();
					updateButtons();
				}
			}
		}

		private class ClipExportAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ClipExportAction() {
				super(TwisterSwingResources.getInstance().getString("action.exportClip"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				if (clipTable.getSelectedRowCount() > 0) {
					final int[] rows = clipTable.getSelectedRows();
					final boolean[] error = new boolean[] { false };
					for (final int row : rows) {
						clipChooser.setDialogTitle(TwisterSwingResources.getInstance().getString("label.exportClip"));
						final int returnVal = clipChooser.showSaveDialog(ServicePanel.this);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							final File file = clipChooser.getSelectedFile();
							if (file.exists()) {
								if (JOptionPane.showConfirmDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("message.confirmOverwrite"), TwisterSwingResources.getInstance().getString("label.exportClips"), JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
									service.execute(new ServiceCallback<Object>() {
										public void executed(final Object value) {
											semaphore.release();
										}

										public void failed(final Throwable throwable) {
											ServicePanel.logger.log(Level.WARNING, "Can't export the clip", throwable);
											error[0] = true;
											semaphore.release();
											JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.exportClip"), TwisterSwingResources.getInstance().getString("label.exportClip"), JOptionPane.ERROR_MESSAGE);
										}

										public Object execute(final LibraryService service) throws Exception {
											service.exportClip(clipModel.getClip(clipTable.convertRowIndexToModel(row)), file);
											return null;
										}
									});
									try {
										semaphore.acquire();
									}
									catch (final InterruptedException x) {
										Thread.currentThread().interrupt();
									}
								}
							}
							else {
								service.execute(new ServiceCallback<Object>() {
									public void executed(final Object value) {
										semaphore.release();
									}

									public void failed(final Throwable throwable) {
										ServicePanel.logger.log(Level.WARNING, "Can't export the clip", throwable);
										error[0] = true;
										semaphore.release();
										JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.exportClip"), TwisterSwingResources.getInstance().getString("label.exportClip"), JOptionPane.ERROR_MESSAGE);
									}

									public Object execute(final LibraryService service) throws Exception {
										service.exportClip(clipModel.getClip(clipTable.convertRowIndexToModel(row)), file);
										return null;
									}
								});
								try {
									semaphore.acquire();
								}
								catch (final InterruptedException x) {
									Thread.currentThread().interrupt();
								}
							}
						}
						if (error[0]) {
							break;
						}
					}
				}
			}
		}

		private class ProfileCreateAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ProfileCreateAction() {
				super(TwisterSwingResources.getInstance().getString("action.create"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				if (clipTable.getSelectedRowCount() == 1) {
					final int clipId = clipModel.getClip(clipTable.convertRowIndexToModel(clipTable.getSelectedRow())).getClipId();
					service.execute(new ServiceCallback<Object>() {
						public void executed(final Object value) {
							semaphore.release();
						}

						public void failed(final Throwable throwable) {
							ServicePanel.logger.log(Level.WARNING, "Can't create the profile", throwable);
							semaphore.release();
							JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.createProfile"), TwisterSwingResources.getInstance().getString("label.createProfile"), JOptionPane.ERROR_MESSAGE);
						}

						public Object execute(final LibraryService service) throws Exception {
							service.createProfile(createDefaultRenderProfile(clipId));
							return null;
						}
					});
					try {
						semaphore.acquire();
					}
					catch (final InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		private class ProfileDeleteAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ProfileDeleteAction() {
				super(TwisterSwingResources.getInstance().getString("action.delete"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				if (profileTable.getSelectedRowCount() > 0) {
					if (JOptionPane.showConfirmDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("message.confirmDeleteProfiles"), TwisterSwingResources.getInstance().getString("label.deleteProfiles"), JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
						final WorkerProgressDialog dialog = new WorkerProgressDialog(new ProfileDeleteWorker(), false);
						GUIUtil.centerWindow(dialog, getLocationOnScreen(), ServicePanel.this.getBounds());
						dialog.start();
						updateButtons();
					}
				}
			}

			private class ProfileDeleteWorker extends ExtendedGUIWorker {
				/**
				 * @see com.nextbreakpoint.nextfractal.core.swing.util.DefaultGUIWorker#doInBackground()
				 */
				@Override
				public Object doInBackground() throws Exception {
					final int[] rows = profileTable.getSelectedRows();
					final boolean[] error = new boolean[] { false };
					final RenderProfileDataRow[] profiles = new RenderProfileDataRow[rows.length];
					for (int i = 0; i < rows.length; i++) {
						profiles[i] = profileModel.getProfile(profileTable.convertRowIndexToModel(rows[i]));
					}
					for (int i = 0; i < rows.length; i++) {
						final int percentage = (int) Math.rint((((float) i) / (rows.length - 1)) * 100f);
						final RenderProfileDataRow profile = profiles[i];
						service.execute(new ServiceCallback<Object>() {
							public void executed(final Object value) {
								semaphore.release();
							}

							public void failed(final Throwable throwable) {
								ServicePanel.logger.log(Level.WARNING, "Can't delete the profile", throwable);
								error[0] = true;
								semaphore.release();
								failed(throwable);
								JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.deleteProfile"), TwisterSwingResources.getInstance().getString("label.deleteProfile"), JOptionPane.ERROR_MESSAGE);
							}

							public Object execute(final LibraryService service) throws Exception {
								stateChanged(TwisterSwingResources.getInstance().getString("message.deletingProfile") + " " + profile.getProfileId() + "...", percentage);
								service.deleteProfile(profile);
								return null;
							}
						});
						try {
							semaphore.acquire();
						}
						catch (final InterruptedException x) {
							Thread.currentThread().interrupt();
						}
						if (error[0]) {
							break;
						}
						profileTable.clearSelection();
					}
					return null;
				}
			}
		}

		private class ProfileModifyAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ProfileModifyAction() {
				super(TwisterSwingResources.getInstance().getString("action.modify"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				if (profileTable.getSelectedRowCount() == 1) {
					final int row = profileTable.getSelectedRow();
					final RenderProfileDataRow profile = profileModel.getProfile(profileTable.convertRowIndexToModel(row));
					service.execute(new ServiceCallback<RenderProfileDataRow>() {
						public void executed(final RenderProfileDataRow profile) {
							GUIUtil.executeTask(new Runnable() {
								public void run() {
									showEditProfileWindow(profile);
									updateButtons();
									service.execute(new ServiceCallback<RenderProfileDataRow>() {
										public RenderProfileDataRow execute(final LibraryService service) throws Exception {
											RenderProfileDataRow profileDataRow = service.getProfile(profile.getProfileId());
											preview.setArea(new Rectangle(profileDataRow.getOffsetX(), profileDataRow.getOffsetY(), profileDataRow.getImageWidth(), profileDataRow.getImageHeight()));
											return null;
										}

										public void executed(final RenderProfileDataRow value) {
										}

										public void failed(final Throwable throwable) {
										}
									});
								}
							}, true);
							semaphore.release();
						}

						public void failed(final Throwable throwable) {
							ServicePanel.logger.log(Level.WARNING, "Can't get the profile " + profile.getProfileId(), throwable);
							semaphore.release();
						}

						public RenderProfileDataRow execute(final LibraryService service) throws Exception {
							return service.getProfile(profile.getProfileId());
						}
					});
					try {
						semaphore.acquire();
					}
					catch (final InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		private class ProfileRenderAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ProfileRenderAction() {
				super(TwisterSwingResources.getInstance().getString("action.render"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				if (profileTable.getSelectedRowCount() > 0) {
					if (JOptionPane.showConfirmDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("message.confirmRenderProfiles"), TwisterSwingResources.getInstance().getString("label.renderProfiles"), JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
						final WorkerProgressDialog dialog = new WorkerProgressDialog(new ProfileRenderWorker(), false);
						GUIUtil.centerWindow(dialog, getLocationOnScreen(), ServicePanel.this.getBounds());
						dialog.start();
						updateButtons();
					}
				}
			}

			private class ProfileRenderWorker extends ExtendedGUIWorker {
				/**
				 * @see com.nextbreakpoint.nextfractal.core.swing.util.DefaultGUIWorker#doInBackground()
				 */
				@Override
				public Object doInBackground() throws Exception {
					final int[] rows = profileTable.getSelectedRows();
					final boolean[] error = new boolean[] { false };
					for (int i = 0; i < rows.length; i++) {
						final int percentage = (int) Math.rint((((float) i) / (rows.length - 1)) * 100f);
						final RenderProfileDataRow profile = profileModel.getProfile(profileTable.convertRowIndexToModel(rows[i]));
						service.execute(new ServiceCallback<Object>() {
							public void executed(final Object value) {
								semaphore.release();
							}

							public void failed(final Throwable throwable) {
								ServicePanel.logger.log(Level.WARNING, "Can't render the profile", throwable);
								error[0] = true;
								semaphore.release();
								failed(throwable);
								JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.deleteJobs"), TwisterSwingResources.getInstance().getString("label.renderProfile"), JOptionPane.ERROR_MESSAGE);
							}

							public Object execute(final LibraryService service) throws Exception {
								service.deleteJobs(profile.getProfileId(), ProfileRenderWorker.this, TwisterSwingResources.getInstance().getString("message.deletingJobs") + " " + profile.getProfileId() + "...", percentage / 3f);
								service.createJobs(profile.getProfileId(), ProfileRenderWorker.this, TwisterSwingResources.getInstance().getString("message.creatingJobs") + " " + profile.getProfileId() + "...", percentage / 3f);
								service.startJobs(profile.getProfileId(), ProfileRenderWorker.this, TwisterSwingResources.getInstance().getString("message.startingJobs") + " " + profile.getProfileId() + "...", percentage / 3f);
								return null;
							}
						});
						try {
							semaphore.acquire();
						}
						catch (final InterruptedException x) {
							Thread.currentThread().interrupt();
						}
						if (error[0]) {
							break;
						}
					}
					return null;
				}
			}
		}

		private class ProfileAbortAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ProfileAbortAction() {
				super(TwisterSwingResources.getInstance().getString("action.abort"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				if (profileTable.getSelectedRowCount() > 0) {
					if (JOptionPane.showConfirmDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("message.confirmAbortProfiles"), TwisterSwingResources.getInstance().getString("label.abortProfiles"), JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
						final WorkerProgressDialog dialog = new WorkerProgressDialog(new ProfileAbortWorker(), false);
						GUIUtil.centerWindow(dialog, getLocationOnScreen(), ServicePanel.this.getBounds());
						dialog.start();
						updateButtons();
					}
				}
			}

			private class ProfileAbortWorker extends ExtendedGUIWorker {
				/**
				 * @see com.nextbreakpoint.nextfractal.core.swing.util.DefaultGUIWorker#doInBackground()
				 */
				@Override
				public Object doInBackground() throws Exception {
					final int[] rows = profileTable.getSelectedRows();
					final boolean[] error = new boolean[] { false };
					for (int i = 0; i < rows.length; i++) {
						final int percentage = (int) Math.rint((((float) i) / (rows.length - 1)) * 100f);
						final RenderProfileDataRow profile = profileModel.getProfile(profileTable.convertRowIndexToModel(rows[i]));
						service.execute(new ServiceCallback<Object>() {
							public void executed(final Object value) {
								semaphore.release();
							}

							public void failed(final Throwable throwable) {
								ServicePanel.logger.log(Level.WARNING, "Can't abort the profile", throwable);
								error[0] = true;
								semaphore.release();
								failed(throwable);
								JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.deleteJobs"), TwisterSwingResources.getInstance().getString("label.abortProfile"), JOptionPane.ERROR_MESSAGE);
							}

							public Object execute(final LibraryService service) throws Exception {
								service.deleteJobs(profile.getProfileId(), ProfileAbortWorker.this, TwisterSwingResources.getInstance().getString("message.deletingJobs") + " " + profile.getProfileId() + "...", percentage);
								return null;
							}
						});
						try {
							semaphore.acquire();
						}
						catch (final InterruptedException x) {
							Thread.currentThread().interrupt();
						}
						if (error[0]) {
							break;
						}
					}
					return null;
				}
			}
		}

		private class ProfileCleanAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ProfileCleanAction() {
				super(TwisterSwingResources.getInstance().getString("action.clean"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				if (profileTable.getSelectedRowCount() > 0) {
					if (JOptionPane.showConfirmDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("message.confirmCleanProfiles"), TwisterSwingResources.getInstance().getString("label.cleanProfiles"), JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
						final WorkerProgressDialog dialog = new WorkerProgressDialog(new ProfileCleanWorker(), false);
						GUIUtil.centerWindow(dialog, getLocationOnScreen(), ServicePanel.this.getBounds());
						dialog.start();
						updateButtons();
					}
				}
			}

			private class ProfileCleanWorker extends ExtendedGUIWorker {
				/**
				 * @see com.nextbreakpoint.nextfractal.core.swing.util.DefaultGUIWorker#doInBackground()
				 */
				@Override
				public Object doInBackground() throws Exception {
					final int[] rows = profileTable.getSelectedRows();
					final boolean[] error = new boolean[] { false };
					for (int i = 0; i < rows.length; i++) {
						final int percentage = (int) Math.rint((((float) i) / (rows.length - 1)) * 100f);
						final RenderProfileDataRow profile = profileModel.getProfile(profileTable.convertRowIndexToModel(rows[i]));
						service.execute(new ServiceCallback<Object>() {
							public void executed(final Object value) {
								semaphore.release();
							}

							public void failed(final Throwable throwable) {
								ServicePanel.logger.log(Level.WARNING, "Can't clean the profile", throwable);
								error[0] = true;
								semaphore.release();
								failed(throwable);
								JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.cleanProfile"), TwisterSwingResources.getInstance().getString("label.cleanProfile"), JOptionPane.ERROR_MESSAGE);
							}

							public Object execute(final LibraryService service) throws Exception {
								stateChanged(TwisterSwingResources.getInstance().getString("message.cleaningProfile") + " " + profile.getProfileId() + "...", percentage);
								service.cleanProfile(profile);
								return null;
							}
						});
						try {
							semaphore.acquire();
						}
						catch (final InterruptedException x) {
							Thread.currentThread().interrupt();
						}
						if (error[0]) {
							break;
						}
					}
					return null;
				}
			}
		}

		private class ProfileStartAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ProfileStartAction() {
				super(TwisterSwingResources.getInstance().getString("action.start"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				if (profileTable.getSelectedRowCount() > 0) {
					final WorkerProgressDialog dialog = new WorkerProgressDialog(new ProfileStartWorker(), false);
					GUIUtil.centerWindow(dialog, getLocationOnScreen(), ServicePanel.this.getBounds());
					dialog.start();
					updateButtons();
				}
			}
		}

		private class ProfileStartWorker extends ExtendedGUIWorker {
			/**
			 * @see com.nextbreakpoint.nextfractal.core.swing.util.DefaultGUIWorker#doInBackground()
			 */
			@Override
			public Object doInBackground() throws Exception {
				final int[] rows = profileTable.getSelectedRows();
				final boolean[] error = new boolean[] { false };
				for (int i = 0; i < rows.length; i++) {
					final int percentage = (int) Math.rint((((float) i) / (rows.length - 1)) * 100f);
					final RenderProfileDataRow profile = profileModel.getProfile(profileTable.convertRowIndexToModel(rows[i]));
					service.execute(new ServiceCallback<Object>() {
						public void executed(final Object value) {
							semaphore.release();
						}

						public void failed(final Throwable throwable) {
							ServicePanel.logger.log(Level.WARNING, "Can't start the profile", throwable);
							error[0] = true;
							semaphore.release();
							failed(throwable);
							JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.startJobs"), TwisterSwingResources.getInstance().getString("label.startProfile"), JOptionPane.ERROR_MESSAGE);
						}

						public Object execute(final LibraryService service) throws Exception {
							service.startJobs(profile.getProfileId(), ProfileStartWorker.this, TwisterSwingResources.getInstance().getString("message.startingJobs") + " " + profile.getProfileId() + "...", percentage);
							return null;
						}
					});
					try {
						semaphore.acquire();
					}
					catch (final InterruptedException x) {
						Thread.currentThread().interrupt();
					}
					if (error[0]) {
						break;
					}
				}
				return null;
			}
		}

		private class ProfileStopAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ProfileStopAction() {
				super(TwisterSwingResources.getInstance().getString("action.stop"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				if (profileTable.getSelectedRowCount() > 0) {
					if (JOptionPane.showConfirmDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("message.confirmStopProfiles"), TwisterSwingResources.getInstance().getString("label.stopProfiles"), JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
						final WorkerProgressDialog dialog = new WorkerProgressDialog(new ProfileStopWorker(), false);
						GUIUtil.centerWindow(dialog, getLocationOnScreen(), ServicePanel.this.getBounds());
						dialog.start();
						updateButtons();
					}
				}
			}

			private class ProfileStopWorker extends ExtendedGUIWorker {
				/**
				 * @see com.nextbreakpoint.nextfractal.core.swing.util.DefaultGUIWorker#doInBackground()
				 */
				@Override
				public Object doInBackground() throws Exception {
					final int[] rows = profileTable.getSelectedRows();
					final boolean[] error = new boolean[] { false };
					for (int i = 0; i < rows.length; i++) {
						final int percentage = (int) Math.rint((((float) i) / (rows.length - 1)) * 100f);
						final RenderProfileDataRow profile = profileModel.getProfile(profileTable.convertRowIndexToModel(rows[i]));
						service.execute(new ServiceCallback<Object>() {
							public void executed(final Object value) {
								semaphore.release();
							}

							public void failed(final Throwable throwable) {
								ServicePanel.logger.log(Level.WARNING, "Can't stop the profile", throwable);
								error[0] = true;
								semaphore.release();
								failed(throwable);
								JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.stopJobs"), TwisterSwingResources.getInstance().getString("label.stopProfile"), JOptionPane.ERROR_MESSAGE);
							}

							public Object execute(final LibraryService service) throws Exception {
								service.stopJobs(profile.getProfileId(), ProfileStopWorker.this, TwisterSwingResources.getInstance().getString("message.stoppingJobs") + " " + profile.getProfileId() + "...", percentage);
								return null;
							}
						});
						try {
							semaphore.acquire();
						}
						catch (final InterruptedException x) {
							Thread.currentThread().interrupt();
						}
						if (error[0]) {
							break;
						}
					}
					return null;
				}
			}
		}

		private class ProfileExportAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ProfileExportAction() {
				super(TwisterSwingResources.getInstance().getString("action.export"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				if (profileTable.getSelectedRowCount() > 0) {
					final int[] rows = profileTable.getSelectedRows();
					encoderDialog.setModal(true);
					for (final int row : rows) {
						final RenderProfileDataRow profile = profileModel.getProfile(profileTable.convertRowIndexToModel(row));
						GUIUtil.centerWindow(encoderDialog, getLocationOnScreen(), ServicePanel.this.getBounds());
						encoderDialog.setProfile(profile);
						encoderDialog.setVisible(true);
					}
					updateButtons();
				}
			}
		}

		private class CheckUpdateAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public CheckUpdateAction() {
				super(TwisterSwingResources.getInstance().getString("action.checkUpdate"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				final WorkerProgressDialog dialog = new WorkerProgressDialog(TwisterSwingResources.getInstance().getString("action.checkUpdate"), new CheckUpdateWorker(), true);
				GUIUtil.centerWindow(dialog, getLocationOnScreen(), ServicePanel.this.getBounds());
				dialog.start();
			}

			private class CheckUpdateWorker extends ExtendedGUIWorker {
				/**
				 * @see com.nextbreakpoint.nextfractal.core.swing.util.DefaultGUIWorker#doInBackground()
				 */
				@Override
				public Object doInBackground() throws Exception {
					BufferedReader reader = null;
					try {
						stateChanged(TwisterSwingResources.getInstance().getString("message.downloadingReleases") + "...");
						final URL url = new URL(TwisterSwingResources.getInstance().getString("url.releases"));
						final URLConnection connection = url.openConnection();
						reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String newRelease = null;
						String release = null;
						String line = null;
						stateChanged(TwisterSwingResources.getInstance().getString("message.checkingReleases") + "...");
						while ((line = reader.readLine()) != null) {
							if (line.length() > 0) {
								release = line;
								if (release.equals(TwisterSwingResources.getInstance().getString("release"))) {
									break;
								}
							}
						}
						while ((line = reader.readLine()) != null) {
							if (line.length() > 0) {
								release = line;
								if (!release.equals(TwisterSwingResources.getInstance().getString("release"))) {
									if (TwisterSwingResources.getInstance().getString("acceptRC").equals("true") || (release.indexOf("RC") == -1)) {
										newRelease = release;
										break;
									}
								}
							}
						}
						if (newRelease != null) {
							GUIUtil.executeTask(new Runnable() {
								public void run() {
									try {
										JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("message.newReleaseAvailable"), TwisterSwingResources.getInstance().getString("action.checkUpdate"), JOptionPane.PLAIN_MESSAGE);
										ServiceDesktop.browse(new URI(TwisterSwingResources.getInstance().getString("url.download")));
									}
									catch (Exception e) {
										e.printStackTrace();
									}
								}
							}, false);
						}
						else {
							GUIUtil.executeTask(new Runnable() {
								public void run() {
									JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("message.newReleaseNotAvailable"), TwisterSwingResources.getInstance().getString("action.checkUpdate"), JOptionPane.PLAIN_MESSAGE);
								}
							}, false);
						}
					}
					catch (final Exception x) {
						GUIUtil.executeTask(new Runnable() {
							public void run() {
								JOptionPane.showMessageDialog(ServicePanel.this, TwisterSwingResources.getInstance().getString("error.checkUpdate"), TwisterSwingResources.getInstance().getString("action.checkUpdate"), JOptionPane.WARNING_MESSAGE);
							}
						}, false);
					}
					finally {
						if (reader != null) {
							try {
								reader.close();
							}
							catch (final IOException x) {
							}
						}
					}
					return null;
				}
			}
		}

		private class ShowAboutAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ShowAboutAction() {
				super(TwisterSwingResources.getInstance().getString("action.showAbout"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/com/nextbreakpoint/nextfractal/about.txt")));
					String line = null;
					final StringBuilder builder = new StringBuilder();
					while ((line = reader.readLine()) != null) {
						builder.append(line);
						builder.append("\n");
					}
					builder.append("\nProgram version = ");
					builder.append(TwisterSwingResources.getInstance().getString("release"));
					builder.append("\nJava version = ");
					builder.append(System.getProperty("java.version"));
					builder.append("\nJava vendor = ");
					builder.append(System.getProperty("java.vendor"));
					builder.append("\nOS = ");
					builder.append(System.getProperty("os.name"));
					builder.append(" ");
					builder.append(System.getProperty("os.version"));
					builder.append(" (");
					builder.append(System.getProperty("os.arch"));
					builder.append(")");
					builder.append("\nAvailable processors = ");
					builder.append(Runtime.getRuntime().availableProcessors());
					builder.append("\nUsed memory = ");
					builder.append(Runtime.getRuntime().totalMemory() / 1024);
					builder.append("kb");
					builder.append("\nFree memory = ");
					builder.append(Runtime.getRuntime().freeMemory() / 1024);
					builder.append("kb");
					JOptionPane.showMessageDialog(ServicePanel.this, builder.toString(), TwisterSwingResources.getInstance().getString("label.about"), JOptionPane.PLAIN_MESSAGE);
				}
				catch (final Exception x) {
					x.printStackTrace();
				}
				finally {
					if (reader != null) {
						try {
							reader.close();
						}
						catch (final IOException x) {
						}
					}
				}
			}
		}

		private class ChangeWorkspaceAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ChangeWorkspaceAction() {
				super(TwisterSwingResources.getInstance().getString("action.changeWorkspace"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				final Properties properties = new Properties();
				File workspace = null;
				try {
					properties.load(new FileInputStream(System.getProperty("user.home") + "/NextFractal.properties"));
					String path = (String) properties.get("workspace");
					if (path == null) {
						path = System.getProperty("user.home") + "/" + System.getProperty("nextfractal.workspace", "NextFractal-workspace");
					}
					workspace = new File(path);
				}
				catch (final Exception x) {
					x.printStackTrace();
				}
				fileChooser.setCurrentDirectory(workspace);
				final int returnVal = fileChooser.showSaveDialog(new JFrame());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					workspace = fileChooser.getSelectedFile().getAbsoluteFile().getAbsoluteFile();
					if (workspace != null) {
						properties.put("workspace", workspace.getAbsolutePath());
						try {
							properties.store(new FileOutputStream(System.getProperty("user.home") + "/NextFractal.properties"), null);
							context.restart();
						}
						catch (final Exception x) {
							x.printStackTrace();
						}
					}
				}
			}
		}

		// private class DefaultServiceListener extends ServiceAdapter {
		// /**
		// * @see com.nextbreakpoint.nextfractal.service.swing.ServiceAdapter#clipCreated(com.nextbreakpoint.nextfractal.service.clip.RenderClipDataRow)
		// */
		// @Override
		// public void clipCreated(final RenderClipDataRow clip) {
		// }
		//
		// /**
		// * @see com.nextbreakpoint.nextfractal.service.swing.ServiceAdapter#clipDeleted(com.nextbreakpoint.nextfractal.service.clip.RenderClipDataRow)
		// */
		// @Override
		// public void clipDeleted(final RenderClipDataRow clip) {
		// }
		//
		// /**
		// * @see com.nextbreakpoint.nextfractal.service.swing.ServiceAdapter#clipUpdated(com.nextbreakpoint.nextfractal.service.clip.RenderClipDataRow)
		// */
		// @Override
		// public void clipUpdated(final RenderClipDataRow clip) {
		// }
		//
		// /**
		// * @see com.nextbreakpoint.nextfractal.service.swing.ServiceAdapter#profileCreated(com.nextbreakpoint.nextfractal.service.profile.RenderProfileDataRow)
		// */
		// @Override
		// public void profileCreated(final RenderProfileDataRow profile) {
		// }
		//
		// /**
		// * @see com.nextbreakpoint.nextfractal.service.swing.ServiceAdapter#profileDeleted(com.nextbreakpoint.nextfractal.service.profile.RenderProfileDataRow)
		// */
		// @Override
		// public void profileDeleted(final RenderProfileDataRow profile) {
		// }
		//
		// /**
		// * @see com.nextbreakpoint.nextfractal.service.swing.ServiceAdapter#profileUpdated(com.nextbreakpoint.nextfractal.service.profile.RenderProfileDataRow)
		// */
		// @Override
		// public void profileUpdated(final RenderProfileDataRow profile) {
		// }
		// }
		private class TableListener implements TableModelListener {
			private final String jobLabelText = TwisterSwingResources.getInstance().getString("label.jobPanel");
			private final String clipLabelText = TwisterSwingResources.getInstance().getString("label.clipPanel");
			private final String profileLabelText = TwisterSwingResources.getInstance().getString("label.profilePanel");
			private final String elementText = TwisterSwingResources.getInstance().getString("label.element");
			private final String elementsText = TwisterSwingResources.getInstance().getString("label.elements");

			/**
			 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
			 */
			public void tableChanged(final TableModelEvent e) {
				jobLabel.setText(jobLabelText + " (" + jobTable.getRowCount() + " " + (jobTable.getRowCount() == 1 ? elementText : elementsText) + ")");
				clipLabel.setText(clipLabelText + " (" + clipTable.getRowCount() + " " + (clipTable.getRowCount() == 1 ? elementText : elementsText) + ")");
				profileLabel.setText(profileLabelText + " (" + profileTable.getRowCount() + " " + (profileTable.getRowCount() == 1 ? elementText : elementsText) + ")");
				updateButtons();
			}
		}

		private class ImageRenderer extends JComponent implements TableCellRenderer {
			private static final long serialVersionUID = 1L;
			private Image image;

			/**
			 * 
			 */
			public ImageRenderer() {
				setPreferredSize(new Dimension(20, 20));
				setMinimumSize(new Dimension(20, 20));
				setMaximumSize(new Dimension(40, 40));
			}

			/**
			 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
			 */
			public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
				// if (row % 2 == 0) {
				// if (isSelected) {
				// setBackground(table.getSelectionBackground().darker());
				// }
				// else {
				// setBackground(table.getBackground().darker());
				// }
				// }
				// else {
				// if (isSelected) {
				// setBackground(table.getSelectionBackground());
				// }
				// else {
				// setBackground(table.getBackground());
				// }
				// }
				if (isSelected) {
					setBackground(table.getSelectionBackground());
				}
				else {
					setBackground(table.getBackground());
				}
				if (value instanceof Image) {
					image = (Image) value;
				}
				else {
					image = null;
				}
				return this;
			}

			/**
			 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
			 */
			@Override
			protected void paintComponent(final Graphics g) {
				final int x = getWidth() / 2 - 10;
				final int y = getHeight() / 2 - 10;
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				if (image != null) {
					g.drawImage(image, x, y, null);
				}
				g.setColor(Color.DARK_GRAY);
				g.drawRect(x, y, 19, 19);
			}
		}
	}
}
