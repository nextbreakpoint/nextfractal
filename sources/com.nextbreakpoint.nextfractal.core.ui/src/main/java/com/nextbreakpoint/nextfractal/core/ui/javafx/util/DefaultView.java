package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.runtime.extension.Extension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.ui.javafx.CoreUIRegistry;
import com.nextbreakpoint.nextfractal.core.ui.javafx.Disposable;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;

public class DefaultView extends Pane implements Disposable {
	private static final Logger logger = Logger.getLogger(DefaultView.class.getName());
	
	/**
	 * @param viewContext
	 * @param context
	 * @param tree
	 */
	public DefaultView(ViewContext viewContext, RenderContext context, NodeObject rootNode) {
		VBox panel = new VBox(10);
		panel.setAlignment(Pos.CENTER_LEFT);
		panel.setMaxWidth(viewContext.getConfigViewSize().getWidth());
		for (int i = 0; i < rootNode.getChildNodeCount(); i++) {
			NodeObject node = rootNode.getChildNode(i);
			NodeEditorComponent editor = createEditor(node);
			if (editor != null) {
				panel.getChildren().add(editor.getComponent());
			} else {
				if (node.getChildNodeCount() > 0) {
					panel.getChildren().add(new DefaultNodeEditorComponent(viewContext, context, node));
				}
			}
		}
		setStyle("-fx-background-color:#777777");
		getChildren().add(panel);
	}
	
	protected NodeEditorComponent createEditor(NodeObject node) {
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
				if (DefaultView.logger.isLoggable(Level.WARNING)) {
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
					if (DefaultView.logger.isLoggable(Level.WARNING)) {
						DefaultView.logger.log(Level.WARNING, "Can't create editor for node class = " + node.getNodeClass(), x);
					}
				}
			}
			if (editor == null) {
				if (DefaultView.logger.isLoggable(Level.FINE)) {	
					DefaultView.logger.fine("Can't find editor for node = " + node.getNodeId() + " (" + node.getNodeClass() + ")");
				}
			}
		}
		else {
			if (DefaultView.logger.isLoggable(Level.FINE)) {	
				DefaultView.logger.fine("Undefined editor for node = " + node.getNodeId());
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
//		tree.getRootNode().removeTreeListener(navigatorTreeListener);
//		navigatorPanel.removeChangeListener(panelSelectionListener);
//		navigatorTree.getModel().removeTreeModelListener(treeModelListener);
//		navigatorTree.getSelectionModel().removeTreeSelectionListener(treeSelectionListener);
//		tree.getRootNode().setContext(null);
//		tree.getRootNode().setSession(null);
//		tree.getRootNode().dispose();
	}
}