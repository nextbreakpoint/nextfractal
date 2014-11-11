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

import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.runtime.extension.NullConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.ui.javafx.CoreUIResources;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class ConfigurableReferenceElementEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.javafx.editor.extension.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	/**
	 * @return
	 */
	protected abstract NodeValue<?> createChildValue();

	/**
	 * @param reference
	 * @return the node value.
	 */
	protected abstract NodeValue<?> createNodeValue(ConfigurableExtensionReference<?> reference);

	/**
	 * @return
	 */
	protected abstract List<ConfigurableExtension<?, ?>> getExtensionList();

	/**
	 * @return
	 */
	protected abstract String getInsertBeforeLabel();

	/**
	 * @return
	 */
	protected abstract String getInsertAfterLabel();

	/**
	 * @return
	 */
	protected abstract String getRemoveLabel();

	/**
	 * @return
	 */
	protected abstract String getMoveUpLabel();

	/**
	 * @return
	 */
	protected abstract String getMoveDownLabel();

	/**
	 * @return
	 */
	protected abstract String getInsertBeforeTooltip();

	/**
	 * @return
	 */
	protected abstract String getInsertAfterTooltip();

	/**
	 * @return
	 */
	protected abstract String getRemoveTooltip();

	/**
	 * @return
	 */
	protected abstract String getMoveUpTooltip();

	/**
	 * @return
	 */
	protected abstract String getMoveDownTooltip();

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
			if (nodeEditor.isParentMutable()) {
				getChildren().add(label);
				getChildren().add(extensionComboBox);
				Button insertBefore = new Button(getInsertBeforeLabel());
				Button insertAfter = new Button(getInsertAfterLabel());
				Button remove = new Button(getRemoveLabel());
				Button moveUp = new Button(getMoveUpLabel());
				Button moveDown = new Button(getMoveDownLabel());
				insertBefore.setTooltip(new Tooltip(getInsertBeforeTooltip()));
				insertAfter.setTooltip(new Tooltip(getInsertAfterTooltip()));
				remove.setTooltip(new Tooltip(getRemoveTooltip()));
				moveUp.setTooltip(new Tooltip(getMoveUpTooltip()));
				moveDown.setTooltip(new Tooltip(getMoveDownTooltip()));
				getChildren().add(insertBefore);
				getChildren().add(insertAfter);
				getChildren().add(remove);
				getChildren().add(moveUp);
				getChildren().add(moveDown);
				insertBefore.setOnAction(e -> {
					try {
						final ConfigurableExtension<?, ?> extension = extensionComboBox.getSelectionModel().getSelectedItem();
						if (extension instanceof NullConfigurableExtension) {
							nodeEditor.getParentNodeEditor().insertChildNodeBefore(nodeEditor.getIndex(), createNodeValue(null));
						}
						else {
							final ConfigurableExtensionReference<?> reference = extension.createConfigurableExtensionReference();
							nodeEditor.getParentNodeEditor().insertChildNodeBefore(nodeEditor.getIndex(), createNodeValue(reference));
						}
					} catch (final ExtensionException x) {
						x.printStackTrace();
					}
				});
				insertAfter.setOnAction(e -> {
					try {
						final ConfigurableExtension<?, ?> extension = extensionComboBox.getSelectionModel().getSelectedItem();
						if (extension instanceof NullConfigurableExtension) {
							nodeEditor.getParentNodeEditor().insertChildNodeAfter(nodeEditor.getIndex(), createNodeValue(null));
						}
						else {
							final ConfigurableExtensionReference<?> reference = extension.createConfigurableExtensionReference();
							nodeEditor.getParentNodeEditor().insertChildNodeAfter(nodeEditor.getIndex(), createNodeValue(reference));
						}
					} catch (final ExtensionException x) {
						x.printStackTrace();
					}
				});
				remove.setOnAction(e -> {
					nodeEditor.getParentNodeEditor().removeChildNode(nodeEditor.getIndex());
				});
				moveUp.setOnAction(e -> {
					nodeEditor.getParentNodeEditor().moveUpChildNode(nodeEditor.getIndex());
				});
				moveDown.setOnAction(e -> {
					nodeEditor.getParentNodeEditor().moveDownChildNode(nodeEditor.getIndex());
				});
			}
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
