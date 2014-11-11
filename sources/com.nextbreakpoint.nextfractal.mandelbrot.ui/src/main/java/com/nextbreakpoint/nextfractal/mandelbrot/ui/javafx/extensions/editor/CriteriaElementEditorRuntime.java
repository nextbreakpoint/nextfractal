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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;

import com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.CriteriaElementNodeValue;

/**
 * @author Andrea Medeghini
 */
public class CriteriaElementEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class EditorComponent extends VBox implements NodeEditorComponent {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;
//		private final JComboBox criteriaComboBox;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
//			criteriaComboBox = GUIFactory.createComboBox(new CriteriaComboBoxModel(), MandelbrotSwingExtensionResources.getInstance().getString("tooltip.orbitTrapCriteria"));
//			criteriaComboBox.setRenderer(new CriteriaListCellRenderer());
//			criteriaComboBox.setSelectedIndex((((CriteriaElementNodeValue) nodeEditor.getNodeValue()).getValue().intValue()));
//			criteriaComboBox.addActionListener(new NodeEditorActionListener(nodeEditor));
//			this.add(GUIFactory.createLabel(nodeEditor.getNodeLabel(), SwingConstants.CENTER));
//			this.add(criteriaComboBox);
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
//				criteriaComboBox.setSelectedIndex((((CriteriaElementNodeValue) nodeEditor.getNodeValue()).getValue().intValue()));
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}
	}

	private class NodeEditorActionListener implements ActionListener {
		private final NodeEditor nodeEditor;

		/**
		 * @param nodeEditor
		 */
		public NodeEditorActionListener(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			final Integer mode = (Integer) ((Object[]) ((JComboBox) e.getSource()).getSelectedItem())[1];
			if (!mode.equals(nodeEditor.getNodeValue().getValue())) {
				nodeEditor.setNodeValue(new CriteriaElementNodeValue(mode));
			}
		}
	}

	private class CriteriaComboBoxModel extends DefaultComboBoxModel {
		private static final long serialVersionUID = 1L;

		public CriteriaComboBoxModel() {
			addElement(new Object[] { "First Out", new Integer(0) });
			addElement(new Object[] { "First In", new Integer(1) });
		}
	}

	private class CriteriaListCellRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;

		/**
		 * @see javax.javafx.DefaultListCellRenderer#getListCellRendererComponent(javax.javafx.JList, java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
			return super.getListCellRendererComponent(list, ((Object[]) value)[0], index, isSelected, cellHasFocus);
		}
	}
}
