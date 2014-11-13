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
package com.nextbreakpoint.nextfractal.core.ui.swing.editor;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.nextbreakpoint.nextfractal.core.runtime.common.NumberNodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.ui.swing.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.swing.extensionPoints.editor.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIFactory;

/**
 * @author Andrea Medeghini
 */
public abstract class NumberEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.editor.extension.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent((NumberNodeEditor) nodeEditor);
	}

	private class EditorComponent extends JPanel implements NodeEditorComponent {
		private static final long serialVersionUID = 1L;
		private final NumberNodeEditor nodeEditor;
		private final JTextField textfield;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NumberNodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			setLayout(new FlowLayout(FlowLayout.CENTER));
			textfield = GUIFactory.createTextField(nodeEditor.getNodeValueAsString(), null);
			textfield.setMinimumSize(new Dimension(80, GUIFactory.DEFAULT_HEIGHT));
			textfield.setMaximumSize(new Dimension(80, GUIFactory.DEFAULT_HEIGHT));
			textfield.setPreferredSize(new Dimension(80, GUIFactory.DEFAULT_HEIGHT));
			this.add(GUIFactory.createLabel(nodeEditor.getNodeLabel(), SwingConstants.CENTER));
			this.add(textfield);
			this.add(GUIFactory.createLabel("[" + nodeEditor.getMinimum() + ", " + nodeEditor.getMaximum() + "]", SwingConstants.CENTER));
			NodeEditorChangeListener listener = new NodeEditorChangeListener(textfield, nodeEditor);
			textfield.addActionListener(listener);
			textfield.addFocusListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.swing.NodeEditorComponent#getComponent()
		 */
		@Override
		public JComponent getComponent() {
			return this;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.swing.NodeEditorComponent#reloadValue()
		 */
		@Override
		public void reloadValue() {
			if (nodeEditor.getNodeValue() != null) {
				textfield.setText(nodeEditor.getNodeValueAsString());
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.swing.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}
	}

	private class NodeEditorChangeListener implements ActionListener, FocusListener {
		private final NumberNodeEditor nodeEditor;
		private final JTextField textfield;

		/**
		 * @param textfield
		 * @param nodeEditor
		 */
		public NodeEditorChangeListener(final JTextField textfield, final NumberNodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			this.textfield = textfield;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			Number value = parseValue(textfield.getText());
			if ((value.doubleValue() >= nodeEditor.getMinimum().doubleValue()) && (value.doubleValue() <= nodeEditor.getMaximum().doubleValue())) {
				if (!nodeEditor.getNodeValue().getValue().equals(value)) {
					nodeEditor.setNodeValue(createNodeValue(value));
				}
			}
			else {
				textfield.setText(nodeEditor.getNodeValueAsString());
			}
		}

		@Override
		public void focusGained(final FocusEvent e) {
		}

		@Override
		public void focusLost(final FocusEvent e) {
			Number value = parseValue(textfield.getText());
			if ((value.doubleValue() >= nodeEditor.getMinimum().doubleValue()) && (value.doubleValue() <= nodeEditor.getMaximum().doubleValue())) {
				if (!nodeEditor.getNodeValue().getValue().equals(value)) {
					nodeEditor.setNodeValue(createNodeValue(value));
				}
			}
			else {
				textfield.setText(nodeEditor.getNodeValueAsString());
			}
		}
	}

	protected abstract Number parseValue(String text);

	protected abstract NodeValue<?> createNodeValue(Number value);
}
