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
package com.nextbreakpoint.nextfractal.core.ui.javafx.extensions.editor;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class BooleanElementEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class EditorComponent extends Pane implements NodeEditorComponent {
		private final NodeEditor nodeEditor;
//		private final JCheckBox checkbox;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
//			setLayout(new FlowLayout(FlowLayout.CENTER));
//			checkbox = GUIFactory.createCheckBox(nodeEditor.getNodeLabel(), null);
//			checkbox.setSelected((((BooleanElementNodeValue) nodeEditor.getNodeValue()).getValue()).booleanValue());
//			checkbox.addActionListener(new NodeEditorActionListener(nodeEditor));
//			this.add(checkbox);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent#getComponent()
		 */
		@Override
		public Node getComponent() {
			return this;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent#reloadValue()
		 */
		@Override
		public void reloadValue() {
			if (nodeEditor.getNodeValue() != null) {
//				checkbox.getModel().setSelected((((BooleanElementNodeValue) nodeEditor.getNodeValue()).getValue()).booleanValue());
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}
	}
//
//	private class NodeEditorActionListener implements ActionListener {
//		private final NodeEditor nodeEditor;
//
//		/**
//		 * @param nodeEditor
//		 */
//		public NodeEditorActionListener(final NodeEditor nodeEditor) {
//			this.nodeEditor = nodeEditor;
//		}
//
//		/**
//		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//		 */
//		@Override
//		public void actionPerformed(final ActionEvent e) {
//			nodeEditor.setNodeValue(new BooleanElementNodeValue(new Boolean(((JCheckBox) e.getSource()).isSelected())));
//		}
//	}
}
