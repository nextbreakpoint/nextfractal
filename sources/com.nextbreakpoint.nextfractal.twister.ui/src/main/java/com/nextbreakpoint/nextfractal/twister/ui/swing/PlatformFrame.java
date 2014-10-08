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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import com.nextbreakpoint.nextfractal.core.ui.swing.osgi.IExtensionPointTreeCellRenderer;
import com.nextbreakpoint.nextfractal.core.ui.swing.osgi.IExtensionPointTreeModel;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIFactory;

public class PlatformFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private ServicePanel servicePanel;

	/**
	 * @throws HeadlessException
	 */
	public PlatformFrame() throws HeadlessException {
		servicePanel = new ServicePanel();
		getContentPane().add(servicePanel);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(TwisterSwingResources.getInstance().getString("platformFrame.title"));
		setSize(new Dimension(600, 500));
		final Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		p.x -= getWidth() / 2;
		p.y -= getHeight() / 2;
		this.setLocation(p);
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

	public static class ServicePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private static final String STRING_FRAME_TAB_BUNDLES = "tab.bundles";
		private static final String STRING_FRAME_TAB_EXTENSIONPOINTS = "tab.extensionPoints";
		private static final String STRING_FRAME_TREE_BUNDLES = "tree.bundles";
		private static final String STRING_FRAME_TREE_EXTENSIONPOINTS = "tree.extensionPoints";
		private final JButton showVersionButton = GUIFactory.createSmallButton(new ShowVersionAction(), TwisterSwingResources.getInstance().getString("tooltip.showVersion"));
		private final JButton showSystemInfoButton = GUIFactory.createSmallButton(new ShowSystemInfoAction(), TwisterSwingResources.getInstance().getString("tooltip.showSystemInfo"));
		private final JButton showAboutButton = GUIFactory.createSmallButton(new ShowAboutAction(), TwisterSwingResources.getInstance().getString("tooltip.showAbout"));

		/**
		 * 
		 */
		public ServicePanel() {
			setLayout(new BorderLayout());
			final JTree bundleTree = createBundleTree();
			final JTree extensionPointTree = createExtensionPointTree();
			final JTabbedPane tabbedPane = new JTabbedPane();
			final JPanel bundlePanel = new JPanel(new BorderLayout());
			final Box bundleButtons = Box.createHorizontalBox();
			bundleButtons.add(Box.createHorizontalGlue());
			bundleButtons.add(showVersionButton);
			bundleButtons.add(showSystemInfoButton);
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
			tabbedPane.addTab(TwisterSwingResources.getInstance().getString(ServicePanel.STRING_FRAME_TAB_BUNDLES), bundlePanel);
			tabbedPane.addTab(TwisterSwingResources.getInstance().getString(ServicePanel.STRING_FRAME_TAB_EXTENSIONPOINTS), extensionPointPanel);
			add(tabbedPane);
		}

		public void dispose() {
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

		private class ShowVersionAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ShowVersionAction() {
				super(TwisterSwingResources.getInstance().getString("action.showVersion"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				JOptionPane.showMessageDialog(ServicePanel.this, "Version " + TwisterSwingResources.getInstance().getString("release"), TwisterSwingResources.getInstance().getString("label.version"), JOptionPane.PLAIN_MESSAGE);
			}
		}

		private class ShowSystemInfoAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public ShowSystemInfoAction() {
				super(TwisterSwingResources.getInstance().getString("action.showSystemInfo"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				final StringBuilder builder = new StringBuilder();
				builder.append("JRE VER ");
				builder.append(System.getProperty("java.version"));
				builder.append(", ");
				builder.append(System.getProperty("java.vendor"));
				builder.append("\n");
				builder.append("OS NAME ");
				builder.append(System.getProperty("os.name"));
				builder.append(", VER ");
				builder.append(System.getProperty("os.version"));
				builder.append(", ARCH ");
				builder.append(System.getProperty("os.arch"));
				builder.append(", CPUs ");
				builder.append(Runtime.getRuntime().availableProcessors());
				builder.append("\n");
				builder.append("MEM MAX ");
				builder.append(Runtime.getRuntime().maxMemory() / 1024);
				builder.append("kb, USED ");
				builder.append(Runtime.getRuntime().totalMemory() / 1024);
				builder.append("kb, FREE ");
				builder.append(Runtime.getRuntime().freeMemory() / 1024);
				builder.append("kb\n");
				JOptionPane.showMessageDialog(ServicePanel.this, builder.toString(), TwisterSwingResources.getInstance().getString("label.systemInfo"), JOptionPane.PLAIN_MESSAGE);
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
			@Override
			public void actionPerformed(final ActionEvent e) {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/about.txt")));
					String line = null;
					final StringBuilder builder = new StringBuilder();
					while ((line = reader.readLine()) != null) {
						builder.append(line);
						builder.append("\n");
					}
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
	}
}
