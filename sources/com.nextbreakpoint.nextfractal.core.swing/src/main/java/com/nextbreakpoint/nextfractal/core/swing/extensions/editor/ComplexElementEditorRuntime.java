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
package com.nextbreakpoint.nextfractal.core.swing.extensions.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import com.nextbreakpoint.nextfractal.core.common.ComplexElementNodeValue;
import com.nextbreakpoint.nextfractal.core.swing.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.swing.editor.extension.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.swing.extensions.CoreSwingExtensionResources;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;

/**
 * @author Andrea Medeghini
 */
public class ComplexElementEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.extension.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class EditorComponent extends JPanel implements NodeEditorComponent {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;
		private final JTextField[] textFields = new JTextField[2];

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			setLayout(new GridLayout(3, 1, 4, 4));
			final DoubleVector2D c = ((ComplexElementNodeValue) nodeEditor.getNodeValue()).getValue();
			final JLabel complexLabel = GUIFactory.createLabel(nodeEditor.getNodeLabel(), SwingConstants.CENTER);
			textFields[0] = GUIFactory.createTextField(String.valueOf(c.getX()), null);
			textFields[0].addActionListener(new FieldListener(nodeEditor, textFields));
			textFields[0].addFocusListener(new FieldListener(nodeEditor, textFields));
			textFields[0].setColumns(20);
			textFields[0].setCaretPosition(0);
			textFields[0].setToolTipText(CoreSwingExtensionResources.getInstance().getString("tooltip.complexRe"));
			textFields[1] = GUIFactory.createTextField(String.valueOf(c.getY()), null);
			textFields[1].addActionListener(new FieldListener(nodeEditor, textFields));
			textFields[1].addFocusListener(new FieldListener(nodeEditor, textFields));
			textFields[1].setColumns(20);
			textFields[1].setCaretPosition(0);
			textFields[1].setToolTipText(CoreSwingExtensionResources.getInstance().getString("tooltip.complexIm"));
			this.add(complexLabel);
			this.add(createTextFieldPanel("Re", textFields[0]));
			this.add(createTextFieldPanel("Im", textFields[1]));
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.NodeEditorComponent#getComponent()
		 */
		@Override
		public JComponent getComponent() {
			return this;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.NodeEditorComponent#reloadValue()
		 */
		@Override
		public void reloadValue() {
			final DoubleVector2D c = ((ComplexElementNodeValue) nodeEditor.getNodeValue()).getValue();
			textFields[0].setText(String.valueOf(c.getX()));
			textFields[0].setCaretPosition(0);
			textFields[1].setText(String.valueOf(c.getY()));
			textFields[1].setCaretPosition(0);
		}

		/**
		 * @param label
		 * @param textField
		 * @return
		 */
		protected JPanel createTextFieldPanel(final String text, final JTextField textField) {
			final JPanel panel = new JPanel(new BorderLayout(4, 4));
			final JLabel label = GUIFactory.createLabel(text, SwingConstants.CENTER);
			label.setPreferredSize(new Dimension(20, 20));
			panel.add(label, BorderLayout.WEST);
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
				final DoubleVector2D c = ((ComplexElementNodeValue) nodeEditor.getNodeValue()).getValue();
				double r = c.getX();
				double i = c.getY();
				try {
					final String text = textFields[0].getText();
					r = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					textFields[0].setText(String.valueOf(c.getX()));
					textFields[0].setCaretPosition(0);
				}
				try {
					final String text = textFields[1].getText();
					i = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					textFields[1].setText(String.valueOf(c.getY()));
					textFields[1].setCaretPosition(0);
				}
				final DoubleVector2D value = new DoubleVector2D(r, i);
				if (!nodeEditor.getNodeValue().getValue().equals(value)) {
					nodeEditor.setNodeValue(new ComplexElementNodeValue(value));
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
				final DoubleVector2D c = ((ComplexElementNodeValue) nodeEditor.getNodeValue()).getValue();
				double r = c.getX();
				double i = c.getY();
				try {
					final String text = textFields[0].getText();
					r = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					textFields[0].setText(String.valueOf(c.getX()));
					textFields[0].setCaretPosition(0);
				}
				try {
					final String text = textFields[1].getText();
					i = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					textFields[1].setText(String.valueOf(c.getY()));
					textFields[1].setCaretPosition(0);
				}
				final DoubleVector2D value = new DoubleVector2D(r, i);
				if (!nodeEditor.getNodeValue().getValue().equals(value)) {
					nodeEditor.setNodeValue(new ComplexElementNodeValue(value));
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}
	}
}
