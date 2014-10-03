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
package com.nextbreakpoint.nextfractal.contextfree.swing.extensions.editor;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.nextbreakpoint.nextfractal.contextfree.swing.extensions.ContextFreeSwingExtensionResources;
import com.nextbreakpoint.nextfractal.core.common.StringElementNodeValue;
import com.nextbreakpoint.nextfractal.core.swing.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.swing.editor.extension.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;

/**
 * @author Andrea Medeghini
 */
public class RuleNameEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.extension.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class EditorComponent extends JPanel implements NodeEditorComponent {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;
		private final JComboBox nameComboBox;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			setLayout(new FlowLayout(FlowLayout.CENTER));
			nameComboBox = GUIFactory.createComboBox(new RuleNameComboBoxModel(nodeEditor), ContextFreeSwingExtensionResources.getInstance().getString("tooltip.ruleName"));
			nameComboBox.setRenderer(new RuleNameListCellRenderer());
			nameComboBox.setSelectedItem((((StringElementNodeValue) nodeEditor.getNodeValue()).getValue()));
			nameComboBox.addActionListener(new NodeEditorActionListener(nodeEditor));
			this.add(GUIFactory.createLabel(nodeEditor.getNodeLabel(), SwingConstants.CENTER));
			this.add(nameComboBox);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.NodeEditorComponent#getComponent()
		 */
		@Override
		public JComponent getComponent() {
			return this;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.NodeEditorComponent#reloadValue()
		 */
		@Override
		public void reloadValue() {
			if (nodeEditor.getNodeValue() != null) {
				nameComboBox.setSelectedItem((((StringElementNodeValue) nodeEditor.getNodeValue()).getValue()));
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}
	}

	private class NodeEditorActionListener implements ActionListener {
		private final NodeEditor nodeEditor;

		/**
		 * @param nodeEditor
		 */
		public NodeEditorActionListener(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			final String rule = (String) ((JComboBox) e.getSource()).getSelectedItem();
			if (!rule.equals(nodeEditor.getNodeValue().getValue())) {
				nodeEditor.setNodeValue(new StringElementNodeValue(rule));
			}
		}
	}

	private class RuleNameComboBoxModel extends DefaultComboBoxModel {
		private static final long serialVersionUID = 1L;

		public RuleNameComboBoxModel(NodeEditor nodeEditor) {
			NodeEditor figureListNodeEditor = nodeEditor.getParentNodeEditor();
			while (!figureListNodeEditor.getNodeClass().equals("node.class.FigureListElement")) {
				figureListNodeEditor = figureListNodeEditor.getParentNodeEditor();
				if (figureListNodeEditor == null) {
					break;
				}
			}
			if (figureListNodeEditor != null) {
				for (int i = 0; i < figureListNodeEditor.getChildNodeCount(); i++) {
					addElement(figureListNodeEditor.getChildNodeEditor(i).getChildNodeEditor(0).getChildNodeValueAsString(0));
				}
			}
		}
	}

	private class RuleNameListCellRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;

		/**
		 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
			return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		}
	}
}
