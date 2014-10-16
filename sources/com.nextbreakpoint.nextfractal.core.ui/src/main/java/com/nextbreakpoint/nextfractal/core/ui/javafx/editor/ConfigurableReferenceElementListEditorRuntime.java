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

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.NullConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.ui.javafx.CoreUIResources;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class ConfigurableReferenceElementListEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.javafx.editor.extension.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	/**
	 * @param reference
	 * @return the node value.
	 */
	protected abstract NodeValue<?> createNodeValue(ConfigurableExtensionReference<?> reference);

	/**
	 * @return
	 */
	protected abstract String getAppendLabel();

	/**
	 * @return
	 */
	protected abstract String getRemoveAllLabel();

	/**
	 * @return
	 */
	protected abstract String getAppendTooltip();

	/**
	 * @return
	 */
	protected abstract String getRemoveAllTooltip();

	/**
	 * @return
	 */
	protected abstract List<ConfigurableExtension<?, ?>> getExtensionList();

	private class EditorComponent extends VBox implements NodeEditorComponent {
		private final ComboBox<ConfigurableExtension<?, ?>> extensionComboBox;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			Label label = new Label(CoreUIResources.getInstance().getString("label.extension"));
			extensionComboBox = new ComboBox<>();
			List<ConfigurableExtension<?, ?>> extensions = getExtensionList();
			extensionComboBox.getItems().add(NullConfigurableExtension.getInstance());
			extensionComboBox.getItems().addAll(extensions);
			setAlignment(Pos.CENTER_LEFT);
			setSpacing(10);
			getChildren().add(label);
			getChildren().add(extensionComboBox);
			Button append = new Button(getAppendLabel());
			Button removeAll = new Button(getRemoveAllLabel());
			append.setTooltip(new Tooltip(getAppendTooltip()));
			removeAll.setTooltip(new Tooltip(getRemoveAllTooltip()));
			getChildren().add(append);
			getChildren().add(removeAll);
			append.setOnAction(e -> {
				try {
					final ConfigurableExtension<?, ?> extension = extensionComboBox.getSelectionModel().getSelectedItem();
					if (extension instanceof NullConfigurableExtension) {
						nodeEditor.appendChildNode(createNodeValue(null));
					} else {
						final ConfigurableExtensionReference<?> reference = extension.createConfigurableExtensionReference();
						nodeEditor.appendChildNode(createNodeValue(reference));
					}
				} catch (final ExtensionException x) {
					x.printStackTrace();
				}
			});
			removeAll.setOnAction(e -> {
				nodeEditor.removeAllChildNodes();
			});
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
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.javafx.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}
	}
}
