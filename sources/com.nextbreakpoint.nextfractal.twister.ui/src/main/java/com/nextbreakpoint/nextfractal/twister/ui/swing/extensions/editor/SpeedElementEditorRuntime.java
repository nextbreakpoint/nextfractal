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
package com.nextbreakpoint.nextfractal.twister.ui.swing.extensions.editor;

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

import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.ui.swing.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.swing.extensionPoints.editor.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector4D;
import com.nextbreakpoint.nextfractal.twister.elements.SpeedElementNodeValue;
import com.nextbreakpoint.nextfractal.twister.ui.swing.extensions.TwisterSwingExtensionResources;
import com.nextbreakpoint.nextfractal.twister.util.Speed;

/**
 * @author Andrea Medeghini
 */
public class SpeedElementEditorRuntime extends EditorExtensionRuntime {
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
		private final VectorPanel[] panels = new VectorPanel[2];

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			setLayout(new BorderLayout());
			final Speed speed = ((SpeedElementNodeValue) nodeEditor.getNodeValue()).getValue();
			final JPanel panel = new JPanel(new GridLayout(1, 2, 4, 4));
			panels[0] = new VectorPanel(TwisterSwingExtensionResources.getInstance().getString("label.position"), speed.getPosition());
			panel.add(panels[0]);
			panels[1] = new VectorPanel(TwisterSwingExtensionResources.getInstance().getString("label.rotation"), speed.getRotation());
			panel.add(panels[1]);
			this.add(panel, BorderLayout.CENTER);
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
			final Speed speed = ((SpeedElementNodeValue) nodeEditor.getNodeValue()).getValue();
			panels[0].textFields[0].setText(String.valueOf(speed.getPosition().getX()));
			panels[0].textFields[0].setCaretPosition(0);
			panels[0].textFields[1].setText(String.valueOf(speed.getPosition().getY()));
			panels[0].textFields[1].setCaretPosition(0);
			panels[0].textFields[2].setText(String.valueOf(speed.getPosition().getZ()));
			panels[0].textFields[2].setCaretPosition(0);
			panels[0].textFields[3].setText(String.valueOf(speed.getPosition().getW()));
			panels[0].textFields[3].setCaretPosition(0);
			panels[1].textFields[0].setText(String.valueOf(speed.getRotation().getX()));
			panels[1].textFields[0].setCaretPosition(0);
			panels[1].textFields[1].setText(String.valueOf(speed.getRotation().getY()));
			panels[1].textFields[1].setCaretPosition(0);
			panels[1].textFields[2].setText(String.valueOf(speed.getRotation().getZ()));
			panels[1].textFields[2].setCaretPosition(0);
			panels[1].textFields[3].setText(String.valueOf(speed.getRotation().getW()));
			panels[1].textFields[3].setCaretPosition(0);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.swing.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}

		private class VectorPanel extends JPanel {
			private static final long serialVersionUID = 1L;
			private final VectorField[] textFields = new VectorField[4];

			public VectorPanel(final String label, final DoubleVector4D vector) {
				setLayout(new GridLayout(5, 1, 4, 4));
				textFields[0] = new VectorField();
				textFields[0].setText(String.valueOf(vector.getX()));
				textFields[0].addActionListener(new FieldListener(nodeEditor));
				textFields[0].addFocusListener(new FieldListener(nodeEditor));
				textFields[0].setColumns(10);
				textFields[0].setCaretPosition(0);
				textFields[0].setToolTipText(TwisterSwingExtensionResources.getInstance().getString("tooltip.vectorX"));
				textFields[1] = new VectorField();
				textFields[1].setText(String.valueOf(vector.getY()));
				textFields[1].addActionListener(new FieldListener(nodeEditor));
				textFields[1].addFocusListener(new FieldListener(nodeEditor));
				textFields[1].setColumns(10);
				textFields[1].setCaretPosition(0);
				textFields[1].setToolTipText(TwisterSwingExtensionResources.getInstance().getString("tooltip.vectorY"));
				textFields[2] = new VectorField();
				textFields[2].setText(String.valueOf(vector.getZ()));
				textFields[2].addActionListener(new FieldListener(nodeEditor));
				textFields[2].addFocusListener(new FieldListener(nodeEditor));
				textFields[2].setColumns(10);
				textFields[2].setCaretPosition(0);
				textFields[2].setToolTipText(TwisterSwingExtensionResources.getInstance().getString("tooltip.vectorZ"));
				textFields[3] = new VectorField();
				textFields[3].setText(String.valueOf(vector.getW()));
				textFields[3].addActionListener(new FieldListener(nodeEditor));
				textFields[3].addFocusListener(new FieldListener(nodeEditor));
				textFields[3].setColumns(10);
				textFields[3].setCaretPosition(0);
				textFields[3].setToolTipText(TwisterSwingExtensionResources.getInstance().getString("tooltip.vectorW"));
				this.add(GUIFactory.createLabel(label, SwingConstants.CENTER));
				this.add(createTextFieldPanel("X", textFields[0]));
				this.add(createTextFieldPanel("Y", textFields[1]));
				this.add(createTextFieldPanel("Z", textFields[2]));
				this.add(createTextFieldPanel("W", textFields[3]));
			}
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

		private class VectorField extends JTextField {
			private static final long serialVersionUID = 1L;

			public VectorField() {
				setColumns(6);
			}

			@Override
			public void setText(final String text) {
				super.setText(text);
				setCaretPosition(0);
			}
		}

		private class FieldListener implements ActionListener, FocusListener {
			private final NodeEditor nodeEditor;

			public FieldListener(final NodeEditor nodeEditor) {
				this.nodeEditor = nodeEditor;
			}

			@Override
			public void actionPerformed(final ActionEvent e) {
				Speed speed = ((SpeedElementNodeValue) nodeEditor.getNodeValue()).getValue();
				double x = speed.getPosition().getX();
				double y = speed.getPosition().getY();
				double z = speed.getPosition().getZ();
				double w = speed.getPosition().getW();
				double a = speed.getRotation().getX();
				double b = speed.getRotation().getY();
				double c = speed.getRotation().getZ();
				double d = speed.getRotation().getW();
				try {
					final String text = panels[0].textFields[0].getText();
					x = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[0].textFields[0].setText(String.valueOf(speed.getPosition().getX()));
				}
				try {
					final String text = panels[0].textFields[1].getText();
					y = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[0].textFields[1].setText(String.valueOf(speed.getPosition().getY()));
				}
				try {
					final String text = panels[0].textFields[2].getText();
					z = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[0].textFields[2].setText(String.valueOf(speed.getPosition().getZ()));
				}
				try {
					final String text = panels[0].textFields[3].getText();
					w = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[0].textFields[3].setText(String.valueOf(speed.getPosition().getW()));
				}
				try {
					final String text = panels[1].textFields[0].getText();
					a = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[1].textFields[0].setText(String.valueOf(speed.getRotation().getX()));
				}
				try {
					final String text = panels[1].textFields[1].getText();
					b = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[1].textFields[1].setText(String.valueOf(speed.getRotation().getY()));
				}
				try {
					final String text = panels[1].textFields[2].getText();
					c = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[1].textFields[2].setText(String.valueOf(speed.getRotation().getZ()));
				}
				try {
					final String text = panels[1].textFields[3].getText();
					d = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[1].textFields[3].setText(String.valueOf(speed.getRotation().getW()));
				}
				speed = new Speed(new DoubleVector4D(x, y, z, w), new DoubleVector4D(a, b, c, d));
				nodeEditor.setNodeValue(new SpeedElementNodeValue(speed));
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
				Speed speed = ((SpeedElementNodeValue) nodeEditor.getNodeValue()).getValue();
				double x = speed.getPosition().getX();
				double y = speed.getPosition().getY();
				double z = speed.getPosition().getZ();
				double w = speed.getPosition().getW();
				double a = speed.getRotation().getX();
				double b = speed.getRotation().getY();
				double c = speed.getRotation().getZ();
				double d = speed.getRotation().getW();
				try {
					final String text = panels[0].textFields[0].getText();
					x = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[0].textFields[0].setText(String.valueOf(speed.getPosition().getX()));
				}
				try {
					final String text = panels[0].textFields[1].getText();
					y = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[0].textFields[1].setText(String.valueOf(speed.getPosition().getY()));
				}
				try {
					final String text = panels[0].textFields[2].getText();
					z = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[0].textFields[2].setText(String.valueOf(speed.getPosition().getZ()));
				}
				try {
					final String text = panels[0].textFields[3].getText();
					w = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[0].textFields[3].setText(String.valueOf(speed.getPosition().getW()));
				}
				try {
					final String text = panels[1].textFields[0].getText();
					a = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[1].textFields[0].setText(String.valueOf(speed.getRotation().getX()));
				}
				try {
					final String text = panels[1].textFields[1].getText();
					b = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[1].textFields[1].setText(String.valueOf(speed.getRotation().getY()));
				}
				try {
					final String text = panels[1].textFields[2].getText();
					c = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[1].textFields[2].setText(String.valueOf(speed.getRotation().getZ()));
				}
				try {
					final String text = panels[1].textFields[3].getText();
					d = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[1].textFields[3].setText(String.valueOf(speed.getRotation().getW()));
				}
				speed = new Speed(new DoubleVector4D(x, y, z, w), new DoubleVector4D(a, b, c, d));
				nodeEditor.setNodeValue(new SpeedElementNodeValue(speed));
			}
		}
	}
}
