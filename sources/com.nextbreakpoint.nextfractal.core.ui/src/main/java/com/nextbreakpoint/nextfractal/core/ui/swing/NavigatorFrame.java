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
package com.nextbreakpoint.nextfractal.core.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.tree.NodeEvent;
import com.nextbreakpoint.nextfractal.core.tree.NodeListener;
import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.tree.Tree;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIUtil;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;

/**
 * @author Andrea Medeghini
 */
public class NavigatorFrame extends JFrame {
	// private static final Logger logger = Logger.getLogger(NavigatorFrame.class);
	private static final long serialVersionUID = 1L;
	private static final String NAVIGATOR_FRAME_TITLE = "navigatorFrame.title";
	private static final String NAVIGATOR_FRAME_WIDTH = "navigatorFrame.width";
	private static final String NAVIGATOR_FRAME_HEIGHT = "navigatorFrame.height";
	private static final String NAVIGATOR_FRAME_ICON = "navigatorFrame.icon";
	private NavigationPanel navigationPanel;

	/**
	 * @param config
	 * @param context
	 * @param session
	 * @throws HeadlessException
	 */
	public NavigatorFrame(final Tree twisterTree, final RenderContext context, final NodeSession session) throws HeadlessException {
		navigationPanel = new NavigationPanel(new NavigatorViewContext(), twisterTree, context, session);
		getContentPane().add(navigationPanel);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		final int defaultWidth = Integer.parseInt(CoreSwingResources.getInstance().getString(NavigatorFrame.NAVIGATOR_FRAME_WIDTH));
		final int defaultHeight = Integer.parseInt(CoreSwingResources.getInstance().getString(NavigatorFrame.NAVIGATOR_FRAME_HEIGHT));
		final int width = Integer.getInteger(NavigatorFrame.NAVIGATOR_FRAME_WIDTH, defaultWidth);
		final int height = Integer.getInteger(NavigatorFrame.NAVIGATOR_FRAME_HEIGHT, defaultHeight);
		setTitle(CoreSwingResources.getInstance().getString(NavigatorFrame.NAVIGATOR_FRAME_TITLE));
		final URL resource = NavigatorFrame.class.getClassLoader().getResource(CoreSwingResources.getInstance().getString(NavigatorFrame.NAVIGATOR_FRAME_ICON));
		if (resource != null) {
			setIconImage(getToolkit().createImage(resource));
		}
		this.setSize(new Dimension(width, height));
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
		if (navigationPanel != null) {
			navigationPanel.dispose();
			navigationPanel = null;
		}
	}

	public class NavigationPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private final NavigatorPanel navigatorPanel;
		private final NavigatorTree navigatorTree;
		private final Tree twisterTree;
		private final RenderContext context;
		private final NavigatorPanelSelectionListener panelSelectionListener;
		private final NavigatorTreeSelectionListener treeSelectionListener;
		private final NavigatorTreeModelListener treeModelListener;
		private final NavigatorTreeListener navigatorTreeListener;
		private final NavigatorChangeListener navigatorChangeListener;

		/**
		 * @param viewContext
		 * @param twisterTree
		 * @param context
		 * @param session
		 */
		public NavigationPanel(final ViewContext viewContext, final Tree twisterTree, final RenderContext context, final NodeSession session) {
			this.context = context;
			// this.session = session;
			this.twisterTree = twisterTree;
			setLayout(new BorderLayout());
			navigatorTree = new NavigatorTree(twisterTree.getRootNode());
			navigatorTree.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
			navigatorPanel = new NavigatorPanel(viewContext, twisterTree.getRootNode());
			navigatorPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
			final JScrollPane scrollPane = new JScrollPane(navigatorTree);
			scrollPane.setPreferredSize(new Dimension(300, 500));
			navigatorPanel.setPreferredSize(new Dimension(400, 500));
			scrollPane.setBorder(BorderFactory.createEmptyBorder());
			final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, navigatorPanel);
			splitPane.setOneTouchExpandable(true);
			splitPane.setDividerSize(10);
			final Box buttonsPanel = Box.createHorizontalBox();
			final JButton acceptButton = GUIFactory.createButton(new AcceptAction(), CoreSwingResources.getInstance().getString("tooltip.treeAccept"));
			final JButton cancelButton = GUIFactory.createButton(new CancelAction(), CoreSwingResources.getInstance().getString("tooltip.treeCancel"));
			final JCheckBox acceptImmediatlyCheckBox = GUIFactory.createCheckBox(CoreSwingResources.getInstance().getString("label.acceptImmediatly"), CoreSwingResources.getInstance().getString("tooltip.acceptImmediatly"));
			buttonsPanel.add(acceptImmediatlyCheckBox);
			buttonsPanel.add(Box.createHorizontalGlue());
			buttonsPanel.add(cancelButton);
			buttonsPanel.add(Box.createHorizontalStrut(8));
			buttonsPanel.add(acceptButton);
			buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
			this.add(splitPane, BorderLayout.CENTER);
			this.add(buttonsPanel, BorderLayout.SOUTH);
			buttonsPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
			setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
			acceptImmediatlyCheckBox.setSelected(session.isAcceptImmediatly());
			acceptButton.setEnabled(!session.isAcceptImmediatly());
			cancelButton.setEnabled(!session.isAcceptImmediatly());
			panelSelectionListener = new NavigatorPanelSelectionListener();
			treeSelectionListener = new NavigatorTreeSelectionListener();
			treeModelListener = new NavigatorTreeModelListener();
			navigatorTreeListener = new NavigatorTreeListener(context, session);
			navigatorChangeListener = new NavigatorChangeListener(acceptImmediatlyCheckBox, acceptButton, cancelButton, session);
			navigatorPanel.addChangeListener(panelSelectionListener);
			navigatorTree.getModel().addTreeModelListener(treeModelListener);
			navigatorTree.getSelectionModel().addTreeSelectionListener(treeSelectionListener);
			twisterTree.getRootNode().addNodeListener(navigatorTreeListener);
			acceptImmediatlyCheckBox.addChangeListener(navigatorChangeListener);
		}

		public void dispose() {
			navigatorPanel.removeChangeListener(panelSelectionListener);
			navigatorTree.getModel().removeTreeModelListener(treeModelListener);
			navigatorTree.getSelectionModel().removeTreeSelectionListener(treeSelectionListener);
			twisterTree.getRootNode().removeTreeListener(navigatorTreeListener);
		}

		private final class NavigatorChangeListener implements ChangeListener {
			private final JCheckBox acceptImmediatlyCheckBox;
			private final JButton acceptButton;
			private final JButton cancelButton;
			private final NodeSession session;

			private NavigatorChangeListener(final JCheckBox acceptImmediatlyCheckBox, final JButton acceptButton, final JButton cancelButton, final NodeSession session) {
				this.acceptImmediatlyCheckBox = acceptImmediatlyCheckBox;
				this.acceptButton = acceptButton;
				this.cancelButton = cancelButton;
				this.session = session;
			}

			@Override
			public void stateChanged(final ChangeEvent e) {
				GUIUtil.executeTask(new Runnable() {
					@Override
					public void run() {
						final boolean isAcceptImmediatly = acceptImmediatlyCheckBox.isSelected();
						acceptButton.setEnabled(!isAcceptImmediatly);
						cancelButton.setEnabled(!isAcceptImmediatly);
						session.setAcceptImmediatly(isAcceptImmediatly);
						if (isAcceptImmediatly) {
							doAccept();
						}
					}
				}, true);
			}
		}

		private final class NavigatorTreeListener implements NodeListener {
			private final RenderContext context;
			private final NodeSession session;

			private NavigatorTreeListener(final RenderContext context, final NodeSession session) {
				this.context = context;
				this.session = session;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeChanged(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
			 */
			@Override
			public void nodeChanged(final NodeEvent e) {
				twisterTree.getRootNode().getSession().fireSessionChanged();
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeAdded(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
			 */
			@Override
			public void nodeAdded(final NodeEvent e) {
				twisterTree.getRootNode().getSession().fireSessionChanged();
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeRemoved(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
			 */
			@Override
			public void nodeRemoved(final NodeEvent e) {
				twisterTree.getRootNode().getSession().fireSessionChanged();
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeAccepted(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
			 */
			@Override
			public void nodeAccepted(final NodeEvent e) {
				GUIUtil.executeTask(new Runnable() {
					@Override
					public void run() {
						if (session.isAcceptImmediatly()) {
							context.refresh();
						}
					}
				}, true);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeCancelled(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
			 */
			@Override
			public void nodeCancelled(final NodeEvent e) {
			}
		}

		private class NavigatorPanelSelectionListener implements ChangeListener {
			/**
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			@Override
			public void stateChanged(final ChangeEvent e) {
				GUIUtil.executeTask(new Runnable() {
					@Override
					public void run() {
						if (navigatorPanel.getEditorNode() != null) {
							navigatorTree.getSelectionModel().removeTreeSelectionListener(treeSelectionListener);
							final TreePath path = navigatorTree.creareTreePath(navigatorPanel.getEditorNode().getNodePath());
							navigatorTree.expandPath(path.getParentPath());
							navigatorTree.setSelectionPath(path);
							navigatorTree.getSelectionModel().addTreeSelectionListener(treeSelectionListener);
						}
					}
				}, true);
			}
		}

		private class NavigatorTreeSelectionListener implements TreeSelectionListener {
			/**
			 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
			 */
			@Override
			public void valueChanged(final TreeSelectionEvent e) {
				GUIUtil.executeTask(new Runnable() {
					@Override
					public void run() {
						navigatorPanel.removeChangeListener(panelSelectionListener);
						if (navigatorTree.getSelectionPath() != null) {
							final NodeObject node = (NodeObject) ((DefaultMutableTreeNode) navigatorTree.getSelectionPath().getLastPathComponent()).getUserObject();
							navigatorPanel.loadNode(node);
						}
						else {
							navigatorPanel.loadNode(null);
						}
						navigatorPanel.addChangeListener(panelSelectionListener);
					}
				}, true);
			}
		}

		private class NavigatorTreeModelListener implements TreeModelListener {
			/**
			 * @see javax.swing.event.TreeModelListener#treeNodesChanged(javax.swing.event.TreeModelEvent)
			 */
			@Override
			public void treeNodesChanged(final TreeModelEvent e) {
				GUIUtil.executeTask(new Runnable() {
					@Override
					public void run() {
						navigatorTree.expandPath(e.getTreePath());
					}
				}, true);
			}

			/**
			 * @see javax.swing.event.TreeModelListener#treeNodesInserted(javax.swing.event.TreeModelEvent)
			 */
			@Override
			public void treeNodesInserted(final TreeModelEvent e) {
				GUIUtil.executeTask(new Runnable() {
					@Override
					public void run() {
						navigatorTree.expandPath(e.getTreePath());
					}
				}, true);
			}

			/**
			 * @see javax.swing.event.TreeModelListener#treeNodesRemoved(javax.swing.event.TreeModelEvent)
			 */
			@Override
			public void treeNodesRemoved(final TreeModelEvent e) {
			}

			/**
			 * @see javax.swing.event.TreeModelListener#treeStructureChanged(javax.swing.event.TreeModelEvent)
			 */
			@Override
			public void treeStructureChanged(final TreeModelEvent e) {
			}
		}

		private class AcceptAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public AcceptAction() {
				super(CoreSwingResources.getInstance().getString("action.accept"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				GUIUtil.executeTask(new Runnable() {
					@Override
					public void run() {
						doAccept();
					}
				}, true);
			}
		}

		private class CancelAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public CancelAction() {
				super(CoreSwingResources.getInstance().getString("action.cancel"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				GUIUtil.executeTask(new Runnable() {
					@Override
					public void run() {
						doCancel();
					}
				}, true);
			}
		}

		/**
		 * @param renderer
		 */
		public void setCellRenderer(final TreeCellRenderer renderer) {
			navigatorTree.setCellRenderer(renderer);
		}

		/**
		 * @param session
		 */
		public void doAccept() {
			try {
				context.acquire();
				context.stopRenderers();
				twisterTree.getRootNode().getContext().updateTimestamp();
				twisterTree.getRootNode().getSession().fireSessionAccepted();
				twisterTree.getRootNode().accept();
				context.startRenderers();
				context.release();
				context.refresh();
			}
			catch (InterruptedException x) {
				Thread.currentThread().interrupt();
			}
		}

		/**
		 * 
		 */
		public void doCancel() {
			twisterTree.getRootNode().cancel();
			twisterTree.getRootNode().getSession().fireSessionCancelled();
		}
	}

	private class NavigatorViewContext implements ViewContext {
		@Override
		public void removeComponent(final Component c) {
		}

		@Override
		public void resize() {
			validate();
			NavigatorFrame.this.repaint();
		}

		@Override
		public void resize(final int amount) {
			validate();
			NavigatorFrame.this.repaint();
		}

		@Override
		public void setComponent(final Component c) {
			validate();
			NavigatorFrame.this.repaint();
		}

		@Override
		public void restoreComponent(final Component c) {
			validate();
			NavigatorFrame.this.repaint();
		}
	}
}
