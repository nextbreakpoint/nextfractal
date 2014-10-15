package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.ui.javafx.CoreUIRegistry;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.View;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;

public class DefaultView extends View {
	private static final Logger logger = Logger.getLogger(DefaultView.class.getName());
	
	/**
	 * @param viewContext
	 * @param context
	 * @param tree
	 */
	public DefaultView(ViewContext viewContext, RenderContext context, Node rootNode) {
		VBox panel = new VBox(10);
		panel.setAlignment(Pos.CENTER_LEFT);
		for (int i = 0; i < rootNode.getChildNodeCount(); i++) {
			Node node = rootNode.getChildNode(i);
			NodeEditorComponent editor = createEditor(node);
			if (editor != null) {
				panel.getChildren().add(editor.getComponent());
			} else {
				if (node.getChildNodeCount() > 0) {
					panel.getChildren().add(new DefaultNodeEditorComponent(viewContext, context, node));
				}
			}
		}
		panel.setStyle("-fx-padding:10px");
		setStyle("-fx-background-color:#777777");
		getChildren().add(panel);
	}
	
	protected NodeEditorComponent createEditor(Node node) {
		NodeEditorComponent editor = null;
		if (node.getNodeEditor() != null) {
			try {
				final Extension<EditorExtensionRuntime> extension = CoreUIRegistry.getInstance().getEditorExtension(node.getNodeId());
				final EditorExtensionRuntime runtime = extension.createExtensionRuntime();
				if (DefaultView.logger.isLoggable(Level.FINE)) {	
					DefaultView.logger.fine("Editor found for node = " + node.getNodeId());
				}
				editor = runtime.createEditor(node.getNodeEditor());
			}
			catch (final ExtensionNotFoundException x) {
			}
			catch (final Exception x) {
				if (DefaultView.logger.isLoggable(Level.INFO)) {
					DefaultView.logger.log(Level.WARNING, "Can't create editor for node = " + node.getNodeId(), x);
				}
			}
			if (editor == null) {
				try {
					final Extension<EditorExtensionRuntime> extension = CoreUIRegistry.getInstance().getEditorExtension(node.getNodeClass());
					final EditorExtensionRuntime runtime = extension.createExtensionRuntime();
					if (DefaultView.logger.isLoggable(Level.FINE)) {	
						DefaultView.logger.fine("Editor found for node class = " + node.getNodeClass());
					}
					editor = runtime.createEditor(node.getNodeEditor());
				}
				catch (final ExtensionNotFoundException x) {
				}
				catch (final Exception x) {
					if (DefaultView.logger.isLoggable(Level.INFO)) {
						DefaultView.logger.log(Level.WARNING, "Can't create editor for node class = " + node.getNodeClass(), x);
					}
				}
			}
			if (editor == null) {
				if (DefaultView.logger.isLoggable(Level.INFO)) {
					DefaultView.logger.info("Can't find editor for node = " + node.getNodeId() + " (" + node.getNodeClass() + ")");
				}
			}
		}
		else {
			if (DefaultView.logger.isLoggable(Level.INFO)) {
				DefaultView.logger.info("Undefined editor for node = " + node.getNodeId());
			}
		}
		return editor;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.View#dispose()
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