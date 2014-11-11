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

import com.nextbreakpoint.nextfractal.core.elements.ComplexElementNodeValue;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.ui.javafx.AdvancedTextField;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensions.CoreUIExtensionResources;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;

/**
 * @author Andrea Medeghini
 */
public class ComplexElementEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class EditorComponent extends VBox implements NodeEditorComponent {
		private final NodeEditor nodeEditor;
		private final AdvancedTextField[] textFields = new AdvancedTextField[2];

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			final DoubleVector2D c = ((ComplexElementNodeValue) nodeEditor.getNodeValue()).getValue();
			Label label = new Label(nodeEditor.getNodeLabel());
			textFields[0] = new AdvancedTextField();
			textFields[0].setRestrict("-?\\d*\\.?\\d*");
			textFields[0].setText(String.valueOf(c.getX()));
			textFields[0].setTooltip(new Tooltip(CoreUIExtensionResources.getInstance().getString("tooltip.complexRe")));
			textFields[0].setOnAction(e -> { updateValue(e); });
			textFields[0].focusedProperty().addListener((observable, oldValue, newValue) -> { if (!newValue) { updateValue(null); } });
			textFields[1] = new AdvancedTextField();
			textFields[1].setRestrict("-?\\d*\\.?\\d*");
			textFields[1].setText(String.valueOf(c.getY()));
			textFields[1].setTooltip(new Tooltip(CoreUIExtensionResources.getInstance().getString("tooltip.complexIm")));
			textFields[1].setOnAction(e -> { updateValue(e); });
			textFields[1].focusedProperty().addListener((observable, oldValue, newValue) -> { if (!newValue) { updateValue(null); } });
			setAlignment(Pos.CENTER_LEFT);
			setSpacing(10);
			getChildren().add(label);
			getChildren().add(textFields[0]);
			getChildren().add(textFields[1]);
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
			final DoubleVector2D c = ((ComplexElementNodeValue) nodeEditor.getNodeValue()).getValue();
			textFields[0].setText(String.valueOf(c.getX()));
			textFields[1].setText(String.valueOf(c.getY()));
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}

		private void updateValue(ActionEvent e) {
			final DoubleVector2D c = ((ComplexElementNodeValue) nodeEditor.getNodeValue()).getValue();
			double r = c.getX();
			double i = c.getY();
			try {
				final String text = textFields[0].getText();
				r = Double.parseDouble(text);
			}
			catch (final NumberFormatException nfe) {
				textFields[0].setText(String.valueOf(c.getX()));
			}
			try {
				final String text = textFields[1].getText();
				i = Double.parseDouble(text);
			}
			catch (final NumberFormatException nfe) {
				textFields[1].setText(String.valueOf(c.getY()));
			}
			final DoubleVector2D value = new DoubleVector2D(r, i);
			if (!nodeEditor.getNodeValue().getValue().equals(value)) {
				nodeEditor.setNodeValue(new ComplexElementNodeValue(value));
			}
		}
	}
}
