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
package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx.extensions.editor;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.elements.IntegerElementNodeValue;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class ImageModeElementEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class EditorComponent extends VBox implements NodeEditorComponent {
		private final NodeEditor nodeEditor;
		private final ComboBox<Mode> modeComboBox;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			Label label = new Label(nodeEditor.getNodeLabel());
			Mode mode = Mode.getByValue(((IntegerElementNodeValue) nodeEditor.getNodeValue()).getValue().intValue());
			modeComboBox = new ComboBox<Mode>();
			modeComboBox.getItems().add(Mode.MANDELBROT);
			modeComboBox.getItems().add(Mode.JULIA);
			modeComboBox.getSelectionModel().select(mode);
			modeComboBox.setOnAction(e -> { updateValue(e); });
			setAlignment(Pos.CENTER_LEFT);
			setSpacing(10);
			getChildren().add(label);
			getChildren().add(modeComboBox);
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
				Mode mode = Mode.getByValue(((IntegerElementNodeValue) nodeEditor.getNodeValue()).getValue().intValue());
				modeComboBox.getSelectionModel().select(mode);
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}
		
		@SuppressWarnings("unchecked")
		private void updateValue(ActionEvent e) {
			final Mode mode = ((ComboBox<Mode>) e.getSource()).getSelectionModel().getSelectedItem();
			if (!mode.getValue().equals(nodeEditor.getNodeValue().getValue())) {
				nodeEditor.setNodeValue(new IntegerElementNodeValue(mode.getValue()));
			}
		}
	}
	
	private enum Mode {
		MANDELBROT("Mandelbrot", 0), JULIA("Julia", 1);
		
		private String label;
		private Integer value;
		
		private Mode(String label, Integer value) {
			this.label = label;
			this.value = value;
		}

		public String getLabel() {
			return label;
		}

		public Integer getValue() {
			return value;
		}
		
		public static Mode getByValue(Integer value) {
			switch (value) {
				case 0:
					return MANDELBROT;
	
				case 1:
					return JULIA;
			}
			return null;
		}
		
		public String toString() {
			return getLabel();
		}
	}
}
