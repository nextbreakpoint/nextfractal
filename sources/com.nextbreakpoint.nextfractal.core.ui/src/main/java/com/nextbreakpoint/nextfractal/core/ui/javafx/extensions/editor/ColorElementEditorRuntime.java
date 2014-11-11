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

import com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class ColorElementEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class EditorComponent extends Pane implements NodeEditorComponent {
		private final NodeEditor nodeEditor;
//		private final ColorField field;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
//			setLayout(new FlowLayout(FlowLayout.CENTER));
//			field = new ColorField(new Color(((ColorElementNodeValue) nodeEditor.getNodeValue()).getValue().getARGB(), true));
//			field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//			final Dimension size = new Dimension(50, 50);
//			field.setMinimumSize(size);
//			field.setMaximumSize(size);
//			field.setPreferredSize(size);
//			if (nodeEditor.isNodeEditable()) {
//				field.addMouseListener(new FieldMouseListener(field, nodeEditor));
//			}
//			add(GUIFactory.createLabel(nodeEditor.getNodeLabel(), SwingConstants.CENTER));
//			this.add(field);
//			if (nodeEditor.isNodeEditable()) {
//				final JButton button = GUIFactory.createButton(new EditActon(field, nodeEditor), CoreUIExtensionResources.getInstance().getString("tooltip.editColor"));
//				this.add(button);
//			}
		}

//		private class FieldMouseListener extends MouseAdapter {
//			private final ColorField field;
//			private final NodeEditor nodeEditor;
//
//			/**
//			 * @param field
//			 * @param nodeEditor
//			 */
//			public FieldMouseListener(final ColorField field, final NodeEditor nodeEditor) {
//				this.field = field;
//				this.nodeEditor = nodeEditor;
//			}
//
//			@Override
//			public void mouseClicked(final MouseEvent e) {
//				final Color color = ColorChooser.showColorChooser(field, nodeEditor.getNodeLabel(), field.getColor());
//				if (color != null) {
//					field.setColor(color);
//					nodeEditor.setNodeValue(new ColorElementNodeValue(new Color32bit(color.getRGB())));
//				}
//			}
//		}
//
//		private class EditActon extends AbstractAction {
//			private static final long serialVersionUID = 1L;
//			private final ColorField field;
//			private final NodeEditor nodeEditor;
//
//			/**
//			 * @param field
//			 * @param nodeEditor
//			 */
//			public EditActon(final ColorField field, final NodeEditor nodeEditor) {
//				super(CoreUIExtensionResources.getInstance().getString("action.edit"));
//				this.field = field;
//				this.nodeEditor = nodeEditor;
//			}
//
//			@Override
//			public void actionPerformed(final ActionEvent e) {
//				final Color color = ColorChooser.showColorChooser(field, nodeEditor.getNodeLabel(), field.getColor());
//				if (color != null) {
//					field.setColor(color);
//					nodeEditor.setNodeValue(new ColorElementNodeValue(new Color32bit(color.getRGB())));
//				}
//			}
//		}

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
//				field.getModel().setColor(new Color(((ColorElementNodeValue) nodeEditor.getNodeValue()).getValue().getARGB(), true), false);
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}
	}
}
