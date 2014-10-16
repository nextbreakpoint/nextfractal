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

import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElementNodeValue;
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
			setAlignment(Pos.CENTER_LEFT);
			setSpacing(10);
			getChildren().add(label);
			getChildren().add(extensionComboBox);
			Button clear = new Button(CoreUIResources.getInstance().getString("action.clearReference"));
			clear.setTooltip(new Tooltip(CoreUIResources.getInstance().getString("action.clearReference")));
			getChildren().add(clear);
			clear.setOnAction(e -> {
				extensionComboBox.getSelectionModel().selectFirst();
			});
			Button exportConfig = new Button(CoreUIResources.getInstance().getString("action.exportConfig"));
			exportConfig.setTooltip(new Tooltip(CoreUIResources.getInstance().getString("action.exportConfig")));
			getChildren().add(exportConfig);
			exportConfig.setOnAction(e -> {
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
			});
			Button importConfig = new Button(CoreUIResources.getInstance().getString("action.importConfig"));
			importConfig.setTooltip(new Tooltip(CoreUIResources.getInstance().getString("action.importConfig")));
			getChildren().add(importConfig);
			importConfig.setOnAction(e -> {
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
			});
			UpdateButtons updateButtons = () -> {
				clear.setDisable(isNullExtension());
				importConfig.setDisable(isNullExtension());
				exportConfig.setDisable(isNullExtension());
			};
			extensionComboBox.setOnAction(e -> {
				try {
					final ConfigurableExtension<?, ?> extension = extensionComboBox.getSelectionModel().getSelectedItem();
					if (extension instanceof NullConfigurableExtension) {
						if (nodeEditor.getNodeValue().getValue() != null) {
							nodeEditor.setNodeValue(createNodeValue(null));
						}
					}
					else {
						final ConfigurableExtensionReference<?> reference = extension.createConfigurableExtensionReference();
						if (!extension.equals(nodeEditor.getNodeValue().getValue())) {
							nodeEditor.setNodeValue(createNodeValue(reference));
						}
					}
					updateButtons.update();
				}
				catch (final ExtensionException x) {
					x.printStackTrace();
				}
			});
			updateButtons.update();
		}

		private boolean isNullExtension() {
			return extensionComboBox.getSelectionModel().getSelectedItem() instanceof NullConfigurableExtension;
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

	@FunctionalInterface
	public interface UpdateButtons {
		public void update();
	}
}
