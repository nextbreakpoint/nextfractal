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
package com.nextbreakpoint.nextfractal.core.ui.javafx.editor;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue;
import com.nextbreakpoint.nextfractal.core.runtime.model.NumberNodeEditor;
import com.nextbreakpoint.nextfractal.core.ui.javafx.AdvancedTextField;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensions.CoreUIExtensionResources;

/**
 * @author Andrea Medeghini
 */
public abstract class NumberEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.javafx.editor.extension.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent((NumberNodeEditor) nodeEditor);
	}

	private class EditorComponent extends VBox implements NodeEditorComponent {
		private final NumberNodeEditor nodeEditor;
		private final AdvancedTextField textField;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NumberNodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			Label label = new Label(nodeEditor.getNodeLabel());
			textField = new AdvancedTextField();
			textField.setRestrict(getRestriction());
			textField.setTooltip(new Tooltip(CoreUIExtensionResources.getInstance().getString("tooltip." + nodeEditor.getNodeId())));
			textField.setOnAction(e -> { updateValue(e); });
			textField.focusedProperty().addListener((observable, oldValue, newValue) -> { if (!newValue) { updateValue(null); } });
			setAlignment(Pos.CENTER_LEFT);
			setSpacing(10);
			getChildren().add(label);
			getChildren().add(textField);
			textField.setText(nodeEditor.getNodeValueAsString());
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.javafx.NodeEditorComponent#getComponent()
		 */
		@Override
		public Node getComponent() {
			return this;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.javafx.NodeEditorComponent#reloadValue()
		 */
		@Override
		public void reloadValue() {
			if (nodeEditor.getNodeValue() != null) {
				textField.setText(nodeEditor.getNodeValueAsString());
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.javafx.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}

		private void updateValue(ActionEvent e) {
			Number value = parseValue(textField.getText());
			if ((value.doubleValue() >= nodeEditor.getMinimum().doubleValue()) && (value.doubleValue() <= nodeEditor.getMaximum().doubleValue())) {
				if (!nodeEditor.getNodeValue().getValue().equals(value)) {
					nodeEditor.setNodeValue(createNodeValue(value));
				}
			} else {
				textField.setText(nodeEditor.getNodeValueAsString());
			}
		}
	}

	protected String getRestriction() {
		return "-?\\d*\\.?\\d*";
	}

	protected abstract Number parseValue(String text);

	protected abstract NodeValue<?> createNodeValue(Number value);
}
