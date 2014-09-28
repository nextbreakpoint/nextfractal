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
package com.nextbreakpoint.nextfractal.core.swing;

import java.awt.Cursor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.nextbreakpoint.nextfractal.core.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIUtil;
import com.nextbreakpoint.nextfractal.core.swing.util.MutableTreeNodeAdapter;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeEvent;
import com.nextbreakpoint.nextfractal.core.tree.NodeListener;
import com.nextbreakpoint.nextfractal.core.tree.NodePath;
import com.nextbreakpoint.nextfractal.core.tree.RootNode;
import com.nextbreakpoint.nextfractal.core.tree.TransferableNodeValue;

/**
 * @author Andrea Medeghini
 */
public class NavigatorTree extends JTree implements NodeListener, DropTargetListener, DragGestureListener, DragSourceListener {
	private static final Logger logger = Logger.getLogger(NavigatorTree.class.getName());
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new adapter.
	 * 
	 * @param RootNode the root node.
	 */
	public NavigatorTree(final RootNode rootNode) {
		setCellRenderer(new SimpleTreeCellRenderer());
		final MutableTreeNodeAdapter root = new MutableTreeNodeAdapter(rootNode);
		final DefaultTreeModel model = new DefaultTreeModel(root);
		setModel(model);
		if (DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, this) != null) {
			setDropTarget(new DropTarget(this, this));
		}
		rootNode.addNodeListener(this);
		setFont(GUIFactory.NORMAL_FONT);
	}

	/**
	 * @param path
	 * @return the path.
	 */
	public TreePath creareTreePath(final NodePath path) {
		final Integer[] elements = path.getPathElements();
		final Object[] nodePath = new Object[elements.length + 1];
		nodePath[0] = getModel().getRoot();
		Object node = getModel().getRoot();
		for (int i = 0; i < elements.length; i++) {
			nodePath[i + 1] = getModel().getChild(node, elements[i]);
			node = nodePath[i + 1];
		}
		if (nodePath.length > 0) {
			return new TreePath(nodePath);
		}
		else {
			return new TreePath(getModel().getRoot());
		}
	}

	private void expandNode(final DefaultMutableTreeNode node) {
		if (node.getParent() != null) {
			expandPath(new TreePath(((DefaultMutableTreeNode) node.getParent()).getPath()));
		}
		if (node.getChildCount() > 0) {
			for (int i = 0; i < node.getChildCount(); i++) {
				expandNode((DefaultMutableTreeNode) node.getChildAt(i));
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeChanged(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
	 */
	public void nodeChanged(final NodeEvent e) {
		GUIUtil.executeTask(new Runnable() {
				public void run() {
				final TreePath path = creareTreePath(e.getPath());
				getModel().valueForPathChanged(path, e.getNode());
				}
						}, true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeAdded(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
	 */
	public void nodeAdded(final NodeEvent e) {
		GUIUtil.executeTask(new Runnable() {
				public void run() {
				final Integer[] nodePath = e.getPath().getPathElements();
				final Integer[] parentPath = new Integer[nodePath.length - 1];
				System.arraycopy(nodePath, 0, parentPath, 0, parentPath.length);
				final TreePath path = creareTreePath(new NodePath(parentPath));
				((DefaultTreeModel) getModel()).insertNodeInto(new MutableTreeNodeAdapter(e.getNode()), (DefaultMutableTreeNode) path.getLastPathComponent(), nodePath[nodePath.length - 1]);
				expandPath(new TreePath(((DefaultMutableTreeNode) path.getLastPathComponent()).getPath()));
								// expandNode((DefaultMutableTreeNode) path.getLastPathComponent());
			}
						}, true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeRemoved(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
	 */
	public void nodeRemoved(final NodeEvent e) {
		GUIUtil.executeTask(new Runnable() {
				public void run() {
				final TreePath path = creareTreePath(e.getPath());
				((DefaultTreeModel) getModel()).removeNodeFromParent((DefaultMutableTreeNode) path.getLastPathComponent());
				}
						}, true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeAccepted(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
	 */
	public void nodeAccepted(final NodeEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeCancelled(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
	 */
	public void nodeCancelled(final NodeEvent e) {
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragEnter(final DropTargetDragEvent dtde) {
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
	 */
	public void dragExit(final DropTargetEvent dte) {
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragOver(final DropTargetDragEvent dtde) {
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
	 */
	public void drop(final DropTargetDropEvent dtde) {
		if (dtde.getDropAction() == DnDConstants.ACTION_MOVE) {
			if (dtde.isDataFlavorSupported(TransferableNodeValueAdapter.NODE_VALUE_FLAVOR)) {
				final Transferable transferable = dtde.getTransferable();
				try {
					final TransferableNodeValue nodeValue = (TransferableNodeValue) transferable.getTransferData(TransferableNodeValueAdapter.NODE_VALUE_FLAVOR);
					final TreePath path = getPathForLocation(dtde.getLocation().x, dtde.getLocation().y);
					if (path != null) {
						final NodeEditor nodeEditor = ((Node) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject()).getNodeEditor();
						if (nodeEditor != null) {
							if (nodeValue.getType().isAssignableFrom(nodeEditor.getNodeValueType())) {
								NavigatorTree.logger.fine("Drop on node = " + nodeEditor.getNodeId());
								dtde.acceptDrop(DnDConstants.ACTION_MOVE);
								if (nodeEditor.isNodeEditable()) {
									nodeEditor.setNodeValue(nodeValue.getValue());
								}
								dtde.dropComplete(true);
							}
							else {
								dtde.rejectDrop();
							}
						}
						else {
							dtde.rejectDrop();
						}
					}
				}
				catch (final UnsupportedFlavorException e) {
					dtde.rejectDrop();
				}
				catch (final IOException e) {
					NavigatorTree.logger.fine("Drop failed: " + e.getLocalizedMessage());
					dtde.dropComplete(false);
				}
			}
		}
		else if (dtde.getDropAction() == DnDConstants.ACTION_COPY) {
			if (dtde.isDataFlavorSupported(TransferableNodeValueAdapter.NODE_VALUE_FLAVOR)) {
				final Transferable transferable = dtde.getTransferable();
				try {
					final TransferableNodeValue nodeValue = (TransferableNodeValue) transferable.getTransferData(TransferableNodeValueAdapter.NODE_VALUE_FLAVOR);
					final TreePath path = getPathForLocation(dtde.getLocation().x, dtde.getLocation().y);
					if (path != null) {
						final NodeEditor nodeEditor = ((Node) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject()).getNodeEditor();
						if (nodeEditor != null) {
							if (nodeValue.getType().isAssignableFrom(nodeEditor.getNodeValueType())) {
								NavigatorTree.logger.fine("Drop on node = " + nodeEditor.getNodeId());
								dtde.acceptDrop(DnDConstants.ACTION_COPY);
								if (nodeEditor.isNodeEditable()) {
									nodeEditor.setNodeValue(nodeValue.getValue());
								}
								dtde.dropComplete(true);
							}
							else {
								dtde.rejectDrop();
							}
						}
						else {
							dtde.rejectDrop();
						}
					}
				}
				catch (final UnsupportedFlavorException e) {
					dtde.rejectDrop();
				}
				catch (final IOException e) {
					NavigatorTree.logger.fine("Drop failed: " + e.getLocalizedMessage());
					dtde.dropComplete(false);
				}
			}
		}
		else {
			dtde.rejectDrop();
		}
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dropActionChanged(final DropTargetDragEvent dtde) {
	}

	/**
	 * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(java.awt.dnd.DragGestureEvent)
	 */
	public void dragGestureRecognized(final DragGestureEvent dge) {
		if (getSelectionPath() != null) {
			final NodeEditor nodeEditor = ((Node) ((DefaultMutableTreeNode) getSelectionPath().getLastPathComponent()).getUserObject()).getNodeEditor();
			if (nodeEditor != null) {
				NavigatorTree.logger.fine("Drag node = " + nodeEditor.getNodeId());
				dge.startDrag(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR), new TransferableNodeValueAdapter(nodeEditor.getNodeValueAsTransferable()), this);
			}
		}
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragDropEnd(java.awt.dnd.DragSourceDropEvent)
	 */
	public void dragDropEnd(final DragSourceDropEvent dsde) {
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragEnter(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dragEnter(final DragSourceDragEvent dsde) {
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragExit(java.awt.dnd.DragSourceEvent)
	 */
	public void dragExit(final DragSourceEvent dse) {
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragOver(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dragOver(final DragSourceDragEvent dsde) {
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dropActionChanged(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dropActionChanged(final DragSourceDragEvent dsde) {
	}

	/**
	 * 
	 */
	public void expandAll() {
		GUIUtil.executeTask(new Runnable() {
				public void run() {
				expandNode((DefaultMutableTreeNode) getModel().getRoot());
				}
						}, true);
	}
}
