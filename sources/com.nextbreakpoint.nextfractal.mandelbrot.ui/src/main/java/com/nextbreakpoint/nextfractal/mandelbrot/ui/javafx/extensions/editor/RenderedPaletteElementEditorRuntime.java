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

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.swing.AbstractAction;

import com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.ui.swing.palette.PaletteChangeEvent;
import com.nextbreakpoint.nextfractal.core.ui.swing.palette.PaletteChangeListener;
import com.nextbreakpoint.nextfractal.core.ui.swing.palette.PaletteField;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.RenderedPaletteElementNodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.ui.swing.extensions.MandelbrotSwingExtensionResources;
import com.nextbreakpoint.nextfractal.mandelbrot.ui.swing.palette.RenderedPaletteModel;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPalette;

/**
 * @author Andrea Medeghini
 */
public class RenderedPaletteElementEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class EditorComponent extends VBox implements NodeEditorComponent {
		private final NodeEditor nodeEditor;
//		private final PaletteField field;
//		private final DefaultPaletteChangeListener listener;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
//			listener = new DefaultPaletteChangeListener();
//			final PaletteFieldModel model = new DefaultRenderedPaletteModel(((RenderedPaletteElementNodeValue) nodeEditor.getNodeValue()).getValue());
//			field = new PaletteField(model);
//			field.setDropEnabled(nodeEditor.isNodeEditable());
//			field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//			final Dimension size = new Dimension(200, 40);
//			field.setMinimumSize(size);
//			field.setMaximumSize(size);
//			field.setPreferredSize(size);
//			field.addMouseListener(new FieldMouseListener(field, nodeEditor));
//			field.getModel().addPaletteChangeListener(listener);
//			add(GUIFactory.createLabel(nodeEditor.getNodeLabel(), SwingConstants.CENTER));
//			this.add(field);
//			if (nodeEditor.isNodeEditable()) {
//				final JButton button = GUIFactory.createButton(new EditActon(field, nodeEditor), MandelbrotSwingExtensionResources.getInstance().getString("tooltip.editPalette"));
//				this.add(button);
//			}
		}

		private class DefaultPaletteChangeListener implements PaletteChangeListener {
			/**
			 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.palette.PaletteChangeListener#paletteChanged(com.nextbreakpoint.nextfractal.core.ui.javafx.palette.PaletteChangeEvent)
			 */
			@Override
			public void paletteChanged(final PaletteChangeEvent e) {
				nodeEditor.setNodeValue(new RenderedPaletteElementNodeValue((RenderedPalette) ((RenderedPaletteModel) e.getSource()).getPalette()));
			}
		}

		private class FieldMouseListener extends MouseAdapter {
			private final PaletteField field;
			private final NodeEditor nodeEditor;

			public FieldMouseListener(final PaletteField field, final NodeEditor nodeEditor) {
				this.field = field;
				this.nodeEditor = nodeEditor;
			}

			@Override
			public void mouseClicked(final MouseEvent e) {
//				final Palette palette = RenderedPaletteEditor.showRenderedPaletteEditor(field, nodeEditor.getNodeLabel(), (RenderedPalette) field.getPalette());
//				if (palette != null) {
//					field.getModel().removePaletteChangeListener(listener);
//					field.getModel().setPalette(palette, false);
//					nodeEditor.setNodeValue(new RenderedPaletteElementNodeValue((RenderedPalette) palette));
//					field.getModel().addPaletteChangeListener(listener);
//				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent#getComponent()
		 */
		@Override
		public Pane getComponent() {
			return this;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent#reloadValue()
		 */
		@Override
		public void reloadValue() {
			if (nodeEditor.getNodeValue() != null) {
//				field.getModel().removePaletteChangeListener(listener);
//				field.getModel().setPalette(((RenderedPaletteElementNodeValue) nodeEditor.getNodeValue()).getValue(), false);
//				field.getModel().addPaletteChangeListener(listener);
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}

		private class EditActon extends AbstractAction {
			private static final long serialVersionUID = 1L;
			private final PaletteField field;
			private final NodeEditor nodeEditor;

			/**
			 * @param field
			 * @param nodeEditor
			 */
			public EditActon(final PaletteField field, final NodeEditor nodeEditor) {
				super(MandelbrotSwingExtensionResources.getInstance().getString("action.edit"));
				this.field = field;
				this.nodeEditor = nodeEditor;
			}

			@Override
			public void actionPerformed(final ActionEvent e) {
//				final Palette palette = RenderedPaletteEditor.showRenderedPaletteEditor(field, nodeEditor.getNodeLabel(), (RenderedPalette) field.getPalette());
//				if (palette != null) {
//					field.getModel().removePaletteChangeListener(listener);
//					field.getModel().setPalette(palette, false);
//					nodeEditor.setNodeValue(new RenderedPaletteElementNodeValue((RenderedPalette) palette));
//					field.getModel().addPaletteChangeListener(listener);
//				}
			}
		}
	}
}
