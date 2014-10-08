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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeEvent;
import com.nextbreakpoint.nextfractal.core.tree.NodeListener;
import com.nextbreakpoint.nextfractal.core.tree.RootNode;
import com.nextbreakpoint.nextfractal.core.ui.swing.editor.extension.EditorExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class NavigatorPanel extends JPanel {
	private static final Logger logger = Logger.getLogger(NavigatorPanel.class.getName());
	private static final long serialVersionUID = 1L;
	private final JPanel iconsPanel = new JPanel(new NavigatorLayout(130, 80));
	private final JPanel editorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private final List<ChangeListener> listeners = new ArrayList<ChangeListener>(1);
	private final ChangeEvent event = new ChangeEvent(this);
	private final ViewContext viewContext;
	private NodeEditorComponent editor;
	// private RootNode rootNode;
	private Node editorNode;
	private Node viewNode;
	private final RootNode rootNode;
	private final NavigatorTreeListener navigatorTreeListener;

	/**
	 * @param viewContext
	 * @param rootNode
	 */
	public NavigatorPanel(final ViewContext viewContext, final RootNode rootNode) {
		this.viewContext = viewContext;
		this.rootNode = rootNode;
		setLayout(new BorderLayout());
		// this.rootNode = rootNode;
		// iconsPanel.setPreferredSize(new Dimension(200, 120));
		// iconsPanel.setMinimumSize(new Dimension(200, 120));
		// iconsPanel.setMinimumSize(new Dimension(200, 120));
		iconsPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		final JScrollPane scrollPane = new JScrollPane(iconsPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		final Box editorContainer = Box.createVerticalBox();
		editorContainer.add(scrollPane);
		editorContainer.add(Box.createGlue());
		editorContainer.add(editorPanel);
		editorContainer.add(Box.createGlue());
		add(editorContainer, BorderLayout.CENTER);
		loadNode(rootNode);
		navigatorTreeListener = new NavigatorTreeListener();
		rootNode.addNodeListener(navigatorTreeListener);
	}

	public void dispose() {
		rootNode.removeTreeListener(navigatorTreeListener);
		listeners.clear();
		if (editor != null) {
			editor.dispose();
			editor = null;
		}
	}

	/**
	 * @param listener
	 * @see javax.swing.SingleSelectionModel#addChangeListener(javax.swing.event.ChangeListener)
	 */
	public void addChangeListener(final ChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * @param listener
	 * @see javax.swing.SingleSelectionModel#removeChangeListener(javax.swing.event.ChangeListener)
	 */
	public void removeChangeListener(final ChangeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * 
	 */
	protected void fireChangeEvent() {
		for (final ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}

	/**
	 * @return
	 */
	public Node getViewNode() {
		return viewNode;
	}

	/**
	 * @return
	 */
	public Node getEditorNode() {
		return editorNode;
	}

	public void reloadNode(final Node node) {
		iconsPanel.removeAll();
		if (node != null) {
			if (node.getChildNodeCount() == 0) {
				loadIcons(node.getParentNode());
			}
			else {
				loadIcons(node);
			}
		}
		if (viewContext != null) {
			viewContext.resize();
		}
	}

	public void loadNode(final Node node) {
		if (viewNode != node) {
			iconsPanel.removeAll();
			viewNode = node;
			if (node != null) {
				if (node.getChildNodeCount() == 0) {
					loadIcons(node.getParentNode());
					viewNode = node.getParentNode();
				}
				else {
					loadIcons(node);
				}
			}
			updateEditor(node);
		}
		else if (editorNode != node) {
			updateEditor(node);
		}
		else {
			if (editor != null) {
				editor.reloadValue();
			}
		}
		if (viewContext != null) {
			viewContext.resize();
		}
	}

	private void loadIcons(final Node node) {
		// if (node != rootNode) {
		// NavigatorIcon icon = new NavigatorIcon("Root", rootNode);
		// icon.addIconListener(new NavigatorIconListener() {
		// public void actionPerformed(NavigatorIconEvent e) {
		// if (e.getEventId() == NavigatorIconEvent.OPEN_EVENT) {
		// Node childNode = ((NavigatorIcon) e.getSource()).getNode();
		// if (e.getClickCount() == 2 && childNode.getChildNodeCount() > 0) {
		// loadNode(childNode);
		// }
		// else {
		// updateEditor(childNode);
		// revalidate();
		// repaint();
		// }
		// }
		// }
		// });
		// iconsPanel.add(icon);
		// }
		if (node != null) {
			// if (node.getParentNode() != null) {
			// final NavigatorIcon icon = new NavigatorIcon(node.getParentNode());
			// icon.addIconListener(new NavigatorIconListener() {
			// public void actionPerformed(final NavigatorIconEvent e) {
			// if (e.getEventId() == NavigatorIconEvent.OPEN_EVENT) {
			// final Node childNode = ((NavigatorIcon) e.getSource()).getNode();
			// if ((e.getClickCount() == 2) && (childNode.getChildNodeCount() > 0)) {
			// loadNode(childNode);
			// }
			// else {
			// updateEditor(childNode);
			// }
			// }
			// }
			// });
			// iconsPanel.add(icon);
			// }
			for (int i = 0; i < node.getChildNodeCount(); i++) {
				final NavigatorIcon icon = new NavigatorIcon(node.getChildNode(i));
				icon.addIconListener(new NavigatorIconListener() {
					@Override
					public void actionPerformed(final NavigatorIconEvent e) {
						if (e.getEventId() == NavigatorIconEvent.OPEN_EVENT) {
							final Node childNode = ((NavigatorIcon) e.getSource()).getNode();
							if ((e.getClickCount() == 2) && (childNode.getChildNodeCount() > 0)) {
								loadNode(childNode);
							}
							else {
								updateEditor(childNode);
								if (viewContext != null) {
									viewContext.resize();
								}
							}
						}
					}
				});
				iconsPanel.add(icon);
			}
			// NavigatorPanel.this.revalidate();
			// NavigatorPanel.this.repaint();
			// if (viewContext != null) {
			// viewContext.resize();
			// }
		}
	}

	private void updateEditor(final Node node) {
		editorNode = node;
		if (editor != null) {
			editorPanel.remove(editor.getComponent());
			editor.dispose();
			editor = null;
		}
		if (node != null) {
			if (node.getNodeEditor() != null) {
				try {
					final Extension<EditorExtensionRuntime> extension = CoreSwingRegistry.getInstance().getEditorExtension(node.getNodeId());
					final EditorExtensionRuntime runtime = extension.createExtensionRuntime();
					if (NavigatorPanel.logger.isLoggable(Level.INFO)) {
						NavigatorPanel.logger.info("Found editor for node = " + node.getNodeId());
					}
					editor = runtime.createEditor(node.getNodeEditor());
				}
				catch (final ExtensionNotFoundException x) {
					if (NavigatorPanel.logger.isLoggable(Level.INFO)) {
						NavigatorPanel.logger.info("Can't find editor for node = " + node.getNodeId());
					}
				}
				catch (final Exception x) {
					x.printStackTrace();
				}
				if (editor == null) {
					try {
						final Extension<EditorExtensionRuntime> extension = CoreSwingRegistry.getInstance().getEditorExtension(node.getNodeClass());
						final EditorExtensionRuntime runtime = extension.createExtensionRuntime();
						NavigatorPanel.logger.info("Found editor for node class = " + node.getNodeClass());
						editor = runtime.createEditor(node.getNodeEditor());
					}
					catch (final ExtensionNotFoundException x) {
						NavigatorPanel.logger.info("Can't find editor for node class = " + node.getNodeClass());
					}
					catch (final Exception x) {
						x.printStackTrace();
					}
				}
			}
			else {
				if (NavigatorPanel.logger.isLoggable(Level.INFO)) {
					NavigatorPanel.logger.info("Undefined editor for node = " + node.getNodeId());
				}
			}
		}
		else {
			if (NavigatorPanel.logger.isLoggable(Level.INFO)) {
				NavigatorPanel.logger.info("Remove editor");
			}
		}
		if (editor != null) {
			editorPanel.add(editor.getComponent());
		}
		// NavigatorPanel.this.revalidate();
		// NavigatorPanel.this.repaint();
		fireChangeEvent();
	}

	private final class NavigatorTreeListener implements NodeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeChanged(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
		 */
		@Override
		public void nodeChanged(final NodeEvent e) {
			if ((e.getNode() == viewNode) || ((viewNode != null) && viewNode.isChildNode(e.getNode())) || ((viewNode != null) && (e.getNode() == viewNode.getParentNode()))) {
				if (!e.getNode().isHighFrequency()) {
					reloadNode(viewNode);
					if ((editor != null) && (editorNode == e.getNode())) {
						// updateEditor(editorNode);
						editor.reloadValue();
					}
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeAdded(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
		 */
		@Override
		public void nodeAdded(final NodeEvent e) {
			if (((viewNode != null) && viewNode.isChildNode(e.getNode())) || ((viewNode != null) && (e.getNode() == viewNode.getParentNode()))) {
				reloadNode(viewNode);
				if ((editor != null) && (editorNode == e.getNode())) {
					// updateEditor(editorNode);
					// editor.reloadValue();
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeRemoved(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
		 */
		@Override
		public void nodeRemoved(final NodeEvent e) {
			if (e.getNode() == viewNode) {
				reloadNode(null);
				updateEditor(null);
			}
			else if (((viewNode != null) && viewNode.isChildNode(e.getNode())) || ((viewNode != null) && (e.getNode() == viewNode.getParentNode()))) {
				reloadNode(viewNode);
				if ((editor != null) && (editorNode == e.getNode())) {
					// updateEditor(null);
					// editor.reloadValue();
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeAccepted(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
		 */
		@Override
		public void nodeAccepted(final NodeEvent e) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeListener#nodeCancelled(com.nextbreakpoint.nextfractal.core.tree.NodeEvent)
		 */
		@Override
		public void nodeCancelled(final NodeEvent e) {
		}
	}

	private class NavigatorIcon extends JComponent {
		private static final long serialVersionUID = 1L;
		private final List<NavigatorIconListener> listeners = new ArrayList<NavigatorIconListener>();
		private ImageIcon nodeIcon;
		private ImageIcon leafIcon;
		private ImageIcon icon;
		private final Node node;
		private int textH1 = 0;
		private int textW1 = 0;
		private int textH2 = 0;
		private int textW2 = 0;
		// private int textH = 0;
		// private int textW = 0;
		private String label1;
		private String label2;
		private boolean over;
		private final Font font1 = new Font("arial", Font.PLAIN, 10);
		private final Font font2 = new Font("arial", Font.PLAIN, 9);

		/**
		 * @param node
		 */
		public NavigatorIcon(final Node node) {
			try {
				nodeIcon = new ImageIcon(ImageIO.read(NavigatorPanel.class.getResourceAsStream("/icons/treeNode-icon.png")));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			try {
				leafIcon = new ImageIcon(ImageIO.read(NavigatorPanel.class.getResourceAsStream("/icons/treeLeaf-icon.png")));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			this.node = node;
			setOpaque(false);
			if (node.isMutable()) {
				icon = nodeIcon;
				label1 = node.getNodeLabel();
				label2 = (node.hasPendingCommands() ? "*" : "") + node.getValueAsString();
				if (label2.length() > 30) {
					label2 = label2.substring(0, 30) + "...";
				}
			}
			else if (node.getChildNodeCount() > 0) {
				icon = nodeIcon;
				label1 = node.getNodeLabel();
				label2 = (node.hasPendingCommands() ? "*" : "") + node.getChildNodeCount() + " " + CoreSwingResources.getInstance().getString("label.elements");
			}
			else {
				icon = leafIcon;
				label1 = node.getNodeLabel();
				label2 = (node.hasPendingCommands() ? "*" : "") + node.getValueAsString();
				if (label2.length() > 30) {
					label2 = label2.substring(0, 30) + "...";
				}
			}
			label1 = label1 != null ? label1 : "";
			label2 = label2 != null ? label2 : "";
			final FontMetrics fm1 = getFontMetrics(font1);
			final FontMetrics fm2 = getFontMetrics(font2);
			textH1 = fm1.getHeight();
			textW1 = SwingUtilities.computeStringWidth(fm1, label1);
			textH2 = fm2.getHeight();
			textW2 = SwingUtilities.computeStringWidth(fm2, label2);
			// textW = Math.max(textW1, textW2);
			// textH = Math.max(textH1, textH2);
			// Dimension size = null;
			// if (icon == null) {
			// size = new Dimension(textW + 8, textH1 + textH2 + 16);
			// }
			// else {
			// size = new Dimension(Math.max(icon.getIconWidth(), textW) + 8, icon.getIconHeight() + textH1 + textH2 + 16);
			// }
			// setPreferredSize(size);
			// setMinimumSize(size);
			// setMaximumSize(size);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(final MouseEvent e) {
					fireIconEvent(new NavigatorIconEvent(NavigatorIcon.this, NavigatorIconEvent.OPEN_EVENT, e.getClickCount()));
				}

				@Override
				public void mouseEntered(final MouseEvent e) {
					over = true;
					repaint();
				}

				@Override
				public void mouseExited(final MouseEvent e) {
					over = false;
					repaint();
				}
			});
		}

		/**
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		@Override
		protected void paintComponent(final Graphics g) {
			paintComponent((Graphics2D) g);
		}

		private void paintComponent(final Graphics2D g) {
			g.setPaintMode();
			g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			final Rectangle r = SwingUtilities.calculateInnerArea(this, null);
			if (icon != null) {
				g.drawImage(icon.getImage(), (r.width - icon.getIconWidth()) / 2, 0, null);
				if (over) {
					g.setColor(Color.GRAY);
					g.fillRoundRect((r.width - textW1) / 2 - 4, icon.getIconHeight() + 4, textW1 + 8, textH1 + /* textH2 */+6, 16, 16);
					g.setColor(Color.WHITE);
					g.setFont(font1);
					g.drawString(label1, (r.width - textW1) / 2, icon.getIconHeight() + textH1 + 4);
					// g.setFont(font2);
					// g.drawString(label2, (r.width - textW2) / 2, icon.getIconHeight() + textH1 + textH2 + 4);
				}
				else {
					g.setColor(Color.BLACK);
					g.setFont(font1);
					g.drawString(label1, (r.width - textW1) / 2, icon.getIconHeight() + textH1 + 4);
					// g.setFont(font2);
					// g.drawString(label2, (r.width - textW2) / 2, icon.getIconHeight() + textH1 + textH2 + 4);
				}
				g.setColor(Color.DARK_GRAY);
				g.setFont(font2);
				g.drawString(label2, (r.width - textW2) / 2, icon.getIconHeight() + textH1 + textH2 + 7);
			}
			else {
				if (over) {
					g.setColor(Color.GRAY);
					g.fillRoundRect((r.width - textW1) / 2 - 4, 4, textW1 + 8, textH1 + /* textH2 */+6, 16, 16);
					g.setColor(Color.WHITE);
					g.setFont(font1);
					g.drawString(label1, (r.width - textW1) / 2, textH1 + 4);
					// g.setFont(font2);
					// g.drawString(label2, (r.width - textW2) / 2, textH1 + textH2 + 4);
				}
				else {
					g.setColor(Color.BLACK);
					g.setFont(font1);
					g.drawString(label1, (r.width - textW1) / 2, textH1 + 4);
					// g.setFont(font2);
					// g.drawString(label2, (r.width - textW2) / 2, textH1 + textH2 + 4);
				}
				g.setColor(Color.DARK_GRAY);
				g.setFont(font2);
				g.drawString(label2, (r.width - textW2) / 2, textH1 + textH2 + 7);
			}
		}

		/**
		 * @return the node
		 */
		public Node getNode() {
			return node;
		}

		/**
		 * @param listener
		 */
		public void addIconListener(final NavigatorIconListener listener) {
			listeners.add(listener);
		}

		/**
		 * @param listener
		 */
		public void removeIconListener(final NavigatorIconListener listener) {
			listeners.remove(listener);
		}

		/**
		 * @param e
		 */
		protected void fireIconEvent(final NavigatorIconEvent e) {
			for (final NavigatorIconListener listener : listeners) {
				listener.actionPerformed(e);
			}
		}
	}

	private class NavigatorIconEvent extends EventObject {
		private static final long serialVersionUID = 1L;
		public static final int OPEN_EVENT = 0;
		private final int eventId;
		private final int clickCount;

		/**
		 * @param icon
		 * @param eventId
		 * @param clickCount
		 */
		public NavigatorIconEvent(final NavigatorIcon icon, final int eventId, final int clickCount) {
			super(icon);
			this.eventId = eventId;
			this.clickCount = clickCount;
		}

		/**
		 * @return the clickCount
		 */
		public int getClickCount() {
			return clickCount;
		}

		/**
		 * @return the eventId
		 */
		public int getEventId() {
			return eventId;
		}

		/**
		 * @see java.util.EventObject#toString()
		 */
		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			builder.append(((NavigatorIcon) getSource()).getNode());
			builder.append(", ");
			builder.append(eventId);
			builder.append(", ");
			builder.append(clickCount);
			return builder.toString();
		}
	}

	private interface NavigatorIconListener {
		/**
		 * @param e
		 */
		public void actionPerformed(NavigatorIconEvent e);
	}
}
