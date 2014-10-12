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

import javafx.scene.Node;
import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class ReferenceEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.javafx.editor.extension.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.tree.NodeEditor)
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

//	/**
//	 * @return the model.
//	 */
//	protected abstract ExtensionComboBoxModel createModel();

	private class EditorComponent extends VBox implements NodeEditorComponent {
		private final NodeEditor nodeEditor;
//		private final JComboBox combo = GUIFactory.createComboBox(createModel(), CoreSwingResources.getInstance().getString("tooltip.extension"));
//		private final JButton clearButton = GUIFactory.createButton(new ClearAction(), CoreSwingResources.getInstance().getString("tooltip.clearReference"));
//		private final ReferenceSelectionListener referenceSelectionListener;

		/**
		 * @param nodeEditor
		 */
		@SuppressWarnings("unchecked")
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
//			if (nodeEditor.getNodeValue() != null) {
//				final ExtensionReference value = ((ExtensionReferenceElementNodeValue<ExtensionReference>) nodeEditor.getNodeValue()).getValue();
//				if (value != null) {
//					((ExtensionComboBoxModel) combo.getModel()).setSelectedItemByExtensionId(value.getExtensionId());
//				}
//			}
//			combo.setRenderer(new ExtensionListCellRenderer());
//			referenceSelectionListener = new ReferenceSelectionListener(nodeEditor);
//			combo.addActionListener(referenceSelectionListener);
//			this.add(GUIFactory.createLabel(CoreSwingResources.getInstance().getString("label.extension"), SwingConstants.CENTER));
//			this.add(Box.createVerticalStrut(8));
//			this.add(combo);
//			this.add(Box.createVerticalStrut(8));
//			this.add(clearButton);
//			updateButtons();
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
//				super(CoreSwingResources.getInstance().getString("action.clearReference"));
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
