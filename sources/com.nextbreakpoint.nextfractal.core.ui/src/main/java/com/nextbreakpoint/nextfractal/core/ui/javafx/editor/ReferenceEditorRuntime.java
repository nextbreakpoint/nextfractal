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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.elements.ExtensionReferenceElementNodeValue;
import com.nextbreakpoint.nextfractal.core.runtime.extension.Extension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.extension.NullExtension;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class ReferenceEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.javafx.editor.extension.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	/**
	 * @return the
	 */
	protected abstract NodeValue<?> createChildValue();

	/**
	 * @param reference
	 * @return the node value.
	 */
	protected abstract NodeValue<?> createNodeValue(ExtensionReference reference);

	/**
	 * @return
	 */
	protected abstract List<Extension<?>> getExtensionList();

	private class EditorComponent extends VBox implements NodeEditorComponent {
		private final ComboBox<Extension<?>> extensionComboBox;
		private final NodeEditor nodeEditor;

		/**
		 * @param nodeEditor
		 */
		@SuppressWarnings("unchecked")
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			Label label = new Label(nodeEditor.getNodeLabel());
			extensionComboBox = new ComboBox<>();
			List<Extension<?>> extensions = getExtensionList();
			extensionComboBox.getItems().add(NullExtension.getInstance());
			extensionComboBox.getItems().addAll(extensions);
			if (nodeEditor.getNodeValue() != null) {
				final ExtensionReference value = ((ExtensionReferenceElementNodeValue<ExtensionReference>) nodeEditor.getNodeValue()).getValue();
				if (value != null) {
					for (Extension<?> item : extensionComboBox.getItems()) {
						if (item.getExtensionId().equals(value.getExtensionId())) {
							extensionComboBox.getSelectionModel().select(item);
							break;
						}
					}
				}
			}
			updateButtons();
			setAlignment(Pos.CENTER_LEFT);
			setSpacing(10);
			getChildren().add(label);
			getChildren().add(extensionComboBox);
		}

		private void updateButtons() {
//			clearButton.setEnabled(!isNullExtension());
		}

//		private boolean isNullExtension() {
//			return combo.getSelectedItem() instanceof NullConfigurableExtension;
//		}

//		private class ReferenceSelectionListener implements ActionListener {
//			private final NodeEditor nodeEditor;
//
//			/**
//			 * @param nodeEditor
//			 */
//			public ReferenceSelectionListener(final NodeEditor nodeEditor) {
//				this.nodeEditor = nodeEditor;
//			}
//
//			/**
//			 * @param e
//			 */
//			@Override
//			public void actionPerformed(final ActionEvent e) {
//				final Extension<?> extension = (Extension<?>) ((JComboBox) e.getSource()).getSelectedItem();
//				if (extension instanceof NullExtension) {
//					if (nodeEditor.getNodeValue().getValue() != null) {
//						nodeEditor.setNodeValue(createNodeValue(null));
//					}
//				}
//				else {
//					final ExtensionReference reference = extension.getExtensionReference();
//					if (!extension.equals(nodeEditor.getNodeValue().getValue())) {
//						nodeEditor.setNodeValue(createNodeValue(reference));
//					}
//				}
//				updateButtons();
//			}
//		}
//
//		private class ClearAction extends AbstractAction {
//			private static final long serialVersionUID = 1L;
//
//			/**
//			 * 
//			 */
//			public ClearAction() {
//				super(CoreUIResources.getInstance().getString("action.clearReference"));
//			}
//
//			/**
//			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//			 */
//			@Override
//			public void actionPerformed(final ActionEvent e) {
//				combo.setSelectedIndex(0);
//			}
//		}

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
		/**
		 * @see com.nextbreakpoint.nextfractal.twister.javafx.NodeEditorComponent#reloadValue()
		 */
		@Override
		@SuppressWarnings("unchecked")
		public void reloadValue() {
//			combo.removeActionListener(referenceSelectionListener);
//			if (nodeEditor.getNodeValue() != null) {
//				final ExtensionReference value = ((ExtensionReferenceElementNodeValue<ExtensionReference>) nodeEditor.getNodeValue()).getValue();
//				if (value != null) {
//					((ExtensionComboBoxModel) combo.getModel()).setSelectedItemByExtensionId(value.getExtensionId());
//				}
//				else {
//					((ExtensionComboBoxModel) combo.getModel()).setSelectedItem(combo.getModel().getElementAt(0));
//				}
//			}
//			combo.addActionListener(referenceSelectionListener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.javafx.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}
	}
}
