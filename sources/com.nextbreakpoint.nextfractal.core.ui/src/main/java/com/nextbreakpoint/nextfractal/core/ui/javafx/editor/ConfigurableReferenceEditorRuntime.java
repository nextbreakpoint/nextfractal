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

import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElementNodeValue;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.extension.NullConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class ConfigurableReferenceEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.javafx.editor.extension.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.tree.NodeEditor)
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

	private class EditorComponent extends VBox implements NodeEditorComponent {
//		private final JFileChooser clipChooser = new JFileChooser(System.getProperty("user.home"));
		private final ComboBox<ConfigurableExtension<?, ?>> extensionComboBox;
		private final NodeEditor nodeEditor;

		/**
		 * @param nodeEditor
		 */
		@SuppressWarnings("unchecked")
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			Label label = new Label(nodeEditor.getNodeLabel());
			extensionComboBox = new ComboBox<>();
			List<ConfigurableExtension<?, ?>> extensions = getExtensionList();
			extensionComboBox.getItems().add(NullConfigurableExtension.getInstance());
			extensionComboBox.getItems().addAll(extensions);
			if (nodeEditor.getNodeValue() != null) {
				final ConfigurableExtensionReference<?> value = ((ExtensionReferenceElementNodeValue<ConfigurableExtensionReference<?>>) nodeEditor.getNodeValue()).getValue();
				if (value != null) {
					for (ConfigurableExtension<?, ?> item : extensionComboBox.getItems()) {
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
//			importButton.setEnabled(!isNullExtension());
//			exportButton.setEnabled(!isNullExtension());
		}

//		private boolean isNullExtension() {
//			return combo.getSelectedItem() instanceof NullConfigurableExtension;
//		}

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
//
//		private class ImportAction extends AbstractAction {
//			private static final long serialVersionUID = 1L;
//
//			/**
//			 * 
//			 */
//			public ImportAction() {
//				super(CoreUIResources.getInstance().getString("action.importConfig"));
//			}
//
//			/**
//			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//			 */
//			@Override
//			@SuppressWarnings("unchecked")
//			public void actionPerformed(final ActionEvent e) {
//				clipChooser.setDialogTitle(CoreUIResources.getInstance().getString("label.importConfig"));
//				final int returnVal = clipChooser.showOpenDialog(EditorComponent.this);
//				if (returnVal == JFileChooser.APPROVE_OPTION) {
//					final File file = clipChooser.getSelectedFile();
//					try {
//						final ExtensionConfigXMLImporter importer = new ExtensionConfigXMLImporter();
//						final InputStream is = new FileInputStream(file);
//						final Document doc = XML.loadDocument(is, "extension-config.xml");
//						final ExtensionConfig config = importer.importFromElement(doc.getDocumentElement());
//						is.close();
//						final ConfigurableExtension extension = (ConfigurableExtension) combo.getSelectedItem();
//						if (!(extension instanceof NullConfigurableExtension)) {
//							final ConfigurableExtensionReference reference = extension.createConfigurableExtensionReference(config);
//							nodeEditor.setNodeValue(createNodeValue(reference));
//						}
//					}
//					catch (final Exception x) {
//						logger.log(Level.WARNING, "Can't import the config", x);
//						x.printStackTrace();
//						JOptionPane.showMessageDialog(EditorComponent.this, CoreUIResources.getInstance().getString("error.importConfig"), CoreUIResources.getInstance().getString("label.importConfig"), JOptionPane.ERROR_MESSAGE);
//					}
//				}
//			}
//		}
//
//		private class ExportAction extends AbstractAction {
//			private static final long serialVersionUID = 1L;
//
//			/**
//			 * 
//			 */
//			public ExportAction() {
//				super(CoreUIResources.getInstance().getString("action.exportConfig"));
//			}
//
//			/**
//			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//			 */
//			@Override
//			@SuppressWarnings("unchecked")
//			public void actionPerformed(final ActionEvent e) {
//				clipChooser.setDialogTitle(CoreUIResources.getInstance().getString("label.exportConfig"));
//				final int returnVal = clipChooser.showSaveDialog(EditorComponent.this);
//				if (returnVal == JFileChooser.APPROVE_OPTION) {
//					final File file = clipChooser.getSelectedFile();
//					if (file.exists()) {
//						if (JOptionPane.showConfirmDialog(EditorComponent.this, CoreUIResources.getInstance().getString("message.confirmOverwrite"), CoreUIResources.getInstance().getString("label.exportConfig"), JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
//							try {
//								final ExtensionConfigXMLExporter exporter = new ExtensionConfigXMLExporter();
//								final Document doc = XML.createDocument();
//								if ((nodeEditor.getNodeValue() != null) && (nodeEditor.getNodeValue().getValue() != null)) {
//									final ExtensionConfig config = ((ConfigurableExtensionReference) nodeEditor.getNodeValue().getValue()).getExtensionConfig();
//									final XMLNodeBuilder builder = XML.createDefaultXMLNodeBuilder(doc);
//									final Element element = exporter.exportToElement(config, builder);
//									doc.appendChild(element);
//									final OutputStream os = new FileOutputStream(file);
//									XML.saveDocument(os, "extension-config.xml", doc);
//									os.close();
//								}
//							}
//							catch (final Exception x) {
//								logger.log(Level.WARNING, "Can't export the config", x);
//								x.printStackTrace();
//								JOptionPane.showMessageDialog(EditorComponent.this, CoreUIResources.getInstance().getString("error.exportConfig"), CoreUIResources.getInstance().getString("label.exportConfig"), JOptionPane.ERROR_MESSAGE);
//							}
//						}
//					}
//					else {
//						try {
//							final ExtensionConfigXMLExporter exporter = new ExtensionConfigXMLExporter();
//							final Document doc = XML.createDocument();
//							if ((nodeEditor.getNodeValue() != null) && (nodeEditor.getNodeValue().getValue() != null)) {
//								final ExtensionConfig config = ((ConfigurableExtensionReference) nodeEditor.getNodeValue().getValue()).getExtensionConfig();
//								final XMLNodeBuilder builder = XML.createDefaultXMLNodeBuilder(doc);
//								final Element element = exporter.exportToElement(config, builder);
//								doc.appendChild(element);
//								final OutputStream os = new FileOutputStream(file);
//								XML.saveDocument(os, "extension-config.xml", doc);
//								os.close();
//							}
//						}
//						catch (final Exception x) {
//							logger.log(Level.WARNING, "Can't export the config", x);
//							x.printStackTrace();
//							JOptionPane.showMessageDialog(EditorComponent.this, CoreUIResources.getInstance().getString("error.exportConfig"), CoreUIResources.getInstance().getString("label.exportConfig"), JOptionPane.ERROR_MESSAGE);
//						}
//					}
//				}
//			}
//		}
//
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
//				try {
//					final ConfigurableExtension<?, ?> extension = (ConfigurableExtension<?, ?>) ((JComboBox) e.getSource()).getSelectedItem();
//					if (extension instanceof NullConfigurableExtension) {
//						if (nodeEditor.getNodeValue().getValue() != null) {
//							nodeEditor.setNodeValue(createNodeValue(null));
//						}
//					}
//					else {
//						final ConfigurableExtensionReference<?> reference = extension.createConfigurableExtensionReference();
//						if (!extension.equals(nodeEditor.getNodeValue().getValue())) {
//							nodeEditor.setNodeValue(createNodeValue(reference));
//						}
//					}
//					updateButtons();
//				}
//				catch (final ExtensionException x) {
//					x.printStackTrace();
//				}
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
		@Override
		@SuppressWarnings("unchecked")
		public void reloadValue() {
//			combo.removeActionListener(referenceSelectionListener);
//			if (nodeEditor.getNodeValue() != null) {
//				final ExtensionReference value = ((ExtensionReferenceElementNodeValue<ExtensionReference>) nodeEditor.getNodeValue()).getValue();
//				if (value != null) {
//					((ConfigurableExtensionComboBoxModel) combo.getModel()).setSelectedItemByExtensionId(value.getExtensionId());
//				}
//				else {
//					((ConfigurableExtensionComboBoxModel) combo.getModel()).setSelectedItem(combo.getModel().getElementAt(0));
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
