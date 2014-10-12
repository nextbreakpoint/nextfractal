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
package com.nextbreakpoint.nextfractal.twister.ui.javafx.extensionPoints.view;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.CoreRegistry;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.tree.NodeSessionListener;
import com.nextbreakpoint.nextfractal.core.tree.RootNode;
import com.nextbreakpoint.nextfractal.core.tree.Tree;
import com.nextbreakpoint.nextfractal.core.ui.javafx.CoreUIRegistry;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.twister.ui.javafx.View;

/**
 * @author Andrea Medeghini
 */
public class DefaultViewRuntime extends ViewExtensionRuntime {
	private static final Logger logger = Logger.getLogger(DefaultViewRuntime.class.getName());
	
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.view.ViewExtensionRuntime#createView(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig, com.nextbreakpoint.nextfractal.core.ui.swing.ViewContext, com.nextbreakpoint.nextfractal.core.util.RenderContext)
	 */
	@Override
	public View createView(final ExtensionConfig config, final ViewContext viewContext, final RenderContext context, final NodeSession session) {
		try {
			final Extension<NodeBuilderExtensionRuntime> extension = CoreRegistry.getInstance().getNodeBuilderExtension(config.getExtensionId());
			final NodeBuilder nodeBuilder = extension.createExtensionRuntime().createNodeBuilder(config);
			final Tree tree = new Tree(new RootNode("navigator.root", extension.getExtensionName() + " extension"));
			nodeBuilder.createNodes(tree.getRootNode());
			tree.getRootNode().setContext(config.getContext());
			tree.getRootNode().setSession(new NavigatorNodeSession());
			return new NavigatorView(viewContext, context, tree);
		}
		catch (final ExtensionException e) {
			e.printStackTrace();
		}
		return new EmptyView();
	}

	private class EmptyView extends View {
		/**
		 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.ViewPanel#dispose()
		 */
		@Override
		public void dispose() {
		}
	}

	private class NavigatorView extends View {
		/**
		 * @param viewContext
		 * @param context
		 * @param tree
		 */
		public NavigatorView(ViewContext viewContext, RenderContext context, Tree tree) {
			// TODO Auto-generated constructor stub
			VBox panel = new VBox(10);
			for (int i = 0; i < tree.getRootNode().getChildNodeCount(); i++) {
				NodeEditorComponent editor = createEditor(tree.getRootNode().getChildNode(i));
				if (editor != null) {
					panel.getChildren().add(editor.getComponent());
				}
			}
			setStyle("-fx-padding:10px;-fx-background-color:#FF0000");
			getChildren().add(panel);
		}
		
		protected NodeEditorComponent createEditor(Node node) {
			NodeEditorComponent editor = null;
			if (node.getNodeEditor() != null) {
				try {
					final Extension<EditorExtensionRuntime> extension = CoreUIRegistry.getInstance().getEditorExtension(node.getNodeId());
					final EditorExtensionRuntime runtime = extension.createExtensionRuntime();
					if (DefaultViewRuntime.logger.isLoggable(Level.INFO)) {
						DefaultViewRuntime.logger.info("Found editor for node = " + node.getNodeId());
					}
					editor = runtime.createEditor(node.getNodeEditor());
				}
				catch (final ExtensionNotFoundException x) {
					if (DefaultViewRuntime.logger.isLoggable(Level.INFO)) {
						DefaultViewRuntime.logger.info("Can't find editor for node = " + node.getNodeId());
					}
				}
				catch (final Exception x) {
					x.printStackTrace();
				}
				if (editor == null) {
					try {
						final Extension<EditorExtensionRuntime> extension = CoreUIRegistry.getInstance().getEditorExtension(node.getNodeClass());
						final EditorExtensionRuntime runtime = extension.createExtensionRuntime();
						DefaultViewRuntime.logger.info("Found editor for node class = " + node.getNodeClass());
						editor = runtime.createEditor(node.getNodeEditor());
					}
					catch (final ExtensionNotFoundException x) {
						DefaultViewRuntime.logger.info("Can't find editor for node class = " + node.getNodeClass());
					}
					catch (final Exception x) {
						x.printStackTrace();
					}
				}
			}
			else {
				if (DefaultViewRuntime.logger.isLoggable(Level.INFO)) {
					DefaultViewRuntime.logger.info("Undefined editor for node = " + node.getNodeId());
				}
			}
			return editor;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.ui.javafx.View#dispose()
		 */
		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}
		
//		private static final long serialVersionUID = 1L;
//		private final NavigatorPanelSelectionListener panelSelectionListener;
//		private final NavigatorTreeSelectionListener treeSelectionListener;
//		private final NavigatorTreeModelListener treeModelListener;
//		private final NavigatorTreeListener navigatorTreeListener;
//		private final NavigatorPanel navigatorPanel;
//		private final NavigatorTree navigatorTree;
//		private final RenderContext context;
//		private final Tree tree;
//
//		/**
//		 * @param viewContext
//		 * @param context
//		 * @param tree
//		 */
//		public NavigatorViewPanel(final ViewContext viewContext, final RenderContext context, final Tree tree) {
//			this.tree = tree;
//			this.context = context;
//			setLayout(new BorderLayout());
//			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20), BorderFactory.createLineBorder(Color.DARK_GRAY)));
//			navigatorTree = new NavigatorTree(tree.getRootNode());
//			navigatorPanel = new NavigatorPanel(viewContext, tree.getRootNode());
//			navigatorTree.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.DARK_GRAY), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
//			add(navigatorTree, BorderLayout.WEST);
//			add(navigatorPanel, BorderLayout.CENTER);
//			panelSelectionListener = new NavigatorPanelSelectionListener();
//			treeSelectionListener = new NavigatorTreeSelectionListener();
//			treeModelListener = new NavigatorTreeModelListener();
//			navigatorTreeListener = new NavigatorTreeListener();
//			tree.getRootNode().addNodeListener(navigatorTreeListener);
//			navigatorPanel.addChangeListener(panelSelectionListener);
//			navigatorTree.getModel().addTreeModelListener(treeModelListener);
//			navigatorTree.getSelectionModel().addTreeSelectionListener(treeSelectionListener);
//			navigatorTree.expandAll();
//		}
//
//		private class NavigatorTreeListener implements NodeListener {
//			/**
//			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeChanged(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
//			 */
//			@Override
//			public void nodeChanged(final NodeEvent e) {
//			}
//
//			/**
//			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeAdded(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
//			 */
//			@Override
//			public void nodeAdded(final NodeEvent e) {
//			}
//
//			/**
//			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeRemoved(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
//			 */
//			@Override
//			public void nodeRemoved(final NodeEvent e) {
//			}
//
//			/**
//			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeAccepted(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
//			 */
//			@Override
//			public void nodeAccepted(final NodeEvent e) {
//				GUIUtil.executeTask(new Runnable() {
//					@Override
//					public void run() {
//						context.refresh();
//					}
//				}, true);
//			}
//
//			/**
//			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeCancelled(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
//			 */
//			@Override
//			public void nodeCancelled(final NodeEvent e) {
//			}
//		}
//
//		private class NavigatorPanelSelectionListener implements ChangeListener {
//			/**
//			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
//			 */
//			@Override
//			public void stateChanged(final ChangeEvent e) {
//				GUIUtil.executeTask(new Runnable() {
//					@Override
//					public void run() {
//						if (navigatorPanel.getEditorNode() != null) {
//							navigatorTree.getSelectionModel().removeTreeSelectionListener(treeSelectionListener);
//							final TreePath path = navigatorTree.creareTreePath(navigatorPanel.getEditorNode().getNodePath());
//							navigatorTree.expandPath(path.getParentPath());
//							navigatorTree.setSelectionPath(path);
//							navigatorTree.getSelectionModel().addTreeSelectionListener(treeSelectionListener);
//						}
//					}
//				}, true);
//			}
//		}
//
//		private class NavigatorTreeSelectionListener implements TreeSelectionListener {
//			/**
//			 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
//			 */
//			@Override
//			public void valueChanged(final TreeSelectionEvent e) {
//				GUIUtil.executeTask(new Runnable() {
//					@Override
//					public void run() {
//						navigatorPanel.removeChangeListener(panelSelectionListener);
//						if (navigatorTree.getSelectionPath() != null) {
//							final Node node = (Node) ((DefaultMutableTreeNode) navigatorTree.getSelectionPath().getLastPathComponent()).getUserObject();
//							navigatorPanel.loadNode(node);
//						}
//						else {
//							navigatorPanel.loadNode(null);
//						}
//						navigatorPanel.addChangeListener(panelSelectionListener);
//					}
//				}, true);
//			}
//		}
//
//		private class NavigatorTreeModelListener implements TreeModelListener {
//			/**
//			 * @see javax.swing.event.TreeModelListener#treeNodesChanged(javax.swing.event.TreeModelEvent)
//			 */
//			@Override
//			public void treeNodesChanged(final TreeModelEvent e) {
//				GUIUtil.executeTask(new Runnable() {
//					@Override
//					public void run() {
//						navigatorTree.expandPath(e.getTreePath());
//					}
//				}, true);
//			}
//
//			/**
//			 * @see javax.swing.event.TreeModelListener#treeNodesInserted(javax.swing.event.TreeModelEvent)
//			 */
//			@Override
//			public void treeNodesInserted(final TreeModelEvent e) {
//				GUIUtil.executeTask(new Runnable() {
//					@Override
//					public void run() {
//						navigatorTree.expandPath(e.getTreePath());
//					}
//				}, true);
//			}
//
//			/**
//			 * @see javax.swing.event.TreeModelListener#treeNodesRemoved(javax.swing.event.TreeModelEvent)
//			 */
//			@Override
//			public void treeNodesRemoved(final TreeModelEvent e) {
//			}
//
//			/**
//			 * @see javax.swing.event.TreeModelListener#treeStructureChanged(javax.swing.event.TreeModelEvent)
//			 */
//			@Override
//			public void treeStructureChanged(final TreeModelEvent e) {
//			}
//		}
//
//		/**
//		 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.ViewPanel#dispose()
//		 */
//		@Override
//		public void dispose() {
//			tree.getRootNode().removeTreeListener(navigatorTreeListener);
//			navigatorPanel.removeChangeListener(panelSelectionListener);
//			navigatorTree.getModel().removeTreeModelListener(treeModelListener);
//			navigatorTree.getSelectionModel().removeTreeSelectionListener(treeSelectionListener);
//			tree.getRootNode().setContext(null);
//			tree.getRootNode().setSession(null);
//			tree.getRootNode().dispose();
//		}
	}

	private class NavigatorNodeSession implements NodeSession {
		/**
		 * 
		 */
		public NavigatorNodeSession() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#appendAction(com.nextbreakpoint.nextfractal.core.tree.NodeAction)
		 */
		@Override
		public void appendAction(final NodeAction action) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#getActions()
		 */
		@Override
		public List<NodeAction> getActions() {
			return null;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#getSessionName()
		 */
		@Override
		public String getSessionName() {
			return "Navigator";
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#getTimestamp()
		 */
		@Override
		public long getTimestamp() {
			return 0;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#isAcceptImmediatly()
		 */
		@Override
		public boolean isAcceptImmediatly() {
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#setAcceptImmediatly(boolean)
		 */
		@Override
		public void setAcceptImmediatly(final boolean isApplyImmediatly) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#setTimestamp(long)
		 */
		@Override
		public void setTimestamp(final long timestamp) {
		}

		@Override
		public void fireSessionAccepted() {
		}

		@Override
		public void fireSessionCancelled() {
		}

		@Override
		public void fireSessionChanged() {
		}

		@Override
		public void addSessionListener(NodeSessionListener listener) {
		}

		@Override
		public void removeSessionListener(NodeSessionListener listener) {
		}
	}
}
