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

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.common.LongElementNodeValue;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.ui.javafx.AdvancedTextField;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensions.CoreUIExtensionResources;

/**
 * @author Andrea Medeghini
 */
public class LongElementEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class EditorComponent extends VBox implements NodeEditorComponent {
		private final NodeEditor nodeEditor;
		private final AdvancedTextField[] textFields = new AdvancedTextField[1];

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			Label label = new Label(nodeEditor.getNodeLabel());
			final Long value = ((LongElementNodeValue) nodeEditor.getNodeValue()).getValue();
			textFields[0] = new AdvancedTextField();
			textFields[0].setRestrict("-?\\d+");
			textFields[0].setText(String.valueOf(value));
			textFields[0].setTooltip(new Tooltip(CoreUIExtensionResources.getInstance().getString("tooltip." + nodeEditor.getNodeId())));
			textFields[0].setOnAction(e -> { updateValue(e); });
			textFields[0].focusedProperty().addListener((observable, oldValue, newValue) -> { if (!newValue) { updateValue(null); } });
			setAlignment(Pos.CENTER_LEFT);
			setSpacing(10);
			getChildren().add(label);
			getChildren().add(textFields[0]);
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
			final Long value = ((LongElementNodeValue) nodeEditor.getNodeValue()).getValue();
			textFields[0].setText(String.valueOf(value));
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}

		private void updateValue(ActionEvent e) {
			Long value = ((LongElementNodeValue) nodeEditor.getNodeValue()).getValue();
			try {
				final String text = textFields[0].getText();
				value = Long.parseLong(text);
			}
			catch (final NumberFormatException nfe) {
				textFields[0].setText(String.valueOf(value));
			}
			if (!nodeEditor.getNodeValue().getValue().equals(value)) {
				nodeEditor.setNodeValue(new LongElementNodeValue(value));
			}
		}
	}
}
