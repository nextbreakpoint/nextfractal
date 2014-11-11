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
package com.nextbreakpoint.nextfractal.core.ui.swing.extensions.editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.nextbreakpoint.nextfractal.core.elements.FloatElementNodeValue;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.ui.swing.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.swing.extensionPoints.editor.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIFactory;

/**
 * @author Andrea Medeghini
 */
public class FloatElementEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.extensionPoints.editor.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class EditorComponent extends JPanel implements NodeEditorComponent {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;
		private final JTextField[] textFields = new JTextField[1];

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			setLayout(new GridLayout(2, 1, 4, 4));
			final Float value = ((FloatElementNodeValue) nodeEditor.getNodeValue()).getValue();
			final JLabel label = GUIFactory.createLabel(nodeEditor.getNodeLabel(), SwingConstants.CENTER);
			textFields[0] = GUIFactory.createTextField(String.valueOf(value), null);
			textFields[0].addActionListener(new FieldListener(nodeEditor, textFields));
			textFields[0].addFocusListener(new FieldListener(nodeEditor, textFields));
			textFields[0].setColumns(20);
			textFields[0].setCaretPosition(0);
			// textFields[0].setToolTipText(TwisterSwingExtensionResources.getInstance().getString("tooltip." + nodeEditor.getNodeId()));
			this.add(label);
			this.add(createTextFieldPanel(nodeEditor.getNodeLabel(), textFields[0]));
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.swing.NodeEditorComponent#getComponent()
		 */
		@Override
		public JComponent getComponent() {
			return this;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.swing.NodeEditorComponent#reloadValue()
		 */
		@Override
		public void reloadValue() {
			final Float value = ((FloatElementNodeValue) nodeEditor.getNodeValue()).getValue();
			textFields[0].setText(String.valueOf(value));
			textFields[0].setCaretPosition(0);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.swing.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}

		/**
		 * @param label
		 * @param textField
		 * @return
		 */
		protected JPanel createTextFieldPanel(final String text, final JTextField textField) {
			final JPanel panel = new JPanel(new BorderLayout(4, 4));
			panel.add(textField, BorderLayout.CENTER);
			return panel;
		}

		private class FieldListener implements ActionListener, FocusListener {
			private final NodeEditor nodeEditor;
			private final JTextField[] textFields;

			public FieldListener(final NodeEditor nodeEditor, final JTextField[] textFields) {
				this.nodeEditor = nodeEditor;
				this.textFields = textFields;
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				Float value = ((FloatElementNodeValue) nodeEditor.getNodeValue()).getValue();
				try {
					final String text = textFields[0].getText();
					value = Float.parseFloat(text);
				}
				catch (final NumberFormatException nfe) {
					textFields[0].setText(String.valueOf(value));
					textFields[0].setCaretPosition(0);
				}
				if (!nodeEditor.getNodeValue().getValue().equals(value)) {
					nodeEditor.setNodeValue(new FloatElementNodeValue(value));
				}
			}

			/**
			 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
			 */
			@Override
			public void focusGained(final FocusEvent e) {
			}

			/**
			 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
			 */
			@Override
			public void focusLost(final FocusEvent e) {
				Float value = ((FloatElementNodeValue) nodeEditor.getNodeValue()).getValue();
				try {
					final String text = textFields[0].getText();
					value = Float.parseFloat(text);
				}
				catch (final NumberFormatException nfe) {
					textFields[0].setText(String.valueOf(value));
					textFields[0].setCaretPosition(0);
				}
				if (!nodeEditor.getNodeValue().getValue().equals(value)) {
					nodeEditor.setNodeValue(new FloatElementNodeValue(value));
				}
			}
		}
	}
}
