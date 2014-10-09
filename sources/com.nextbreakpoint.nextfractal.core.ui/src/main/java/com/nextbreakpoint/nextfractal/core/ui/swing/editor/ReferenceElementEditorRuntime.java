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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.extension.NullExtension;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.ui.swing.CoreSwingResources;
import com.nextbreakpoint.nextfractal.core.ui.swing.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.swing.extension.ExtensionComboBoxModel;
import com.nextbreakpoint.nextfractal.core.ui.swing.extension.ExtensionListCellRenderer;
import com.nextbreakpoint.nextfractal.core.ui.swing.extensionPoints.editor.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.StackLayout;

/**
 * @author Andrea Medeghini
 */
public abstract class ReferenceElementEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.editor.extension.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.tree.NodeEditor)
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
	protected abstract NodeValue<?> createNodeValue(ExtensionReference reference);

	/**
	 * @return the model.
	 */
	protected abstract ExtensionComboBoxModel createModel();

	/**
	 * @return
	 */
	protected abstract String getInsertBeforeLabel();

	/**
	 * @return
	 */
	protected abstract String getInsertAfterLabel();

	/**
	 * @return
	 */
	protected abstract String getRemoveLabel();

	/**
	 * @return
	 */
	protected abstract String getInsertBeforeTooltip();

	/**
	 * @return
	 */
	protected abstract String getInsertAfterTooltip();

	/**
	 * @return
	 */
	protected abstract String getRemoveTooltip();

	private class EditorComponent extends JPanel implements NodeEditorComponent {
		private static final long serialVersionUID = 1L;
		private final JComboBox combo = GUIFactory.createComboBox(createModel(), CoreSwingResources.getInstance().getString("tooltip.selectExtension"));

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			setLayout(new StackLayout());
			combo.setRenderer(new ExtensionListCellRenderer());
			if (nodeEditor.isParentMutable()) {
				final JButton insertBeforeButton = GUIFactory.createButton(new InsertBeforeAction(combo, nodeEditor), getInsertBeforeTooltip());
				final JButton insertAfterButton = GUIFactory.createButton(new InsertAfterAction(combo, nodeEditor), getInsertAfterTooltip());
				final JButton removeButton = GUIFactory.createButton(new RemoveAction(nodeEditor), getRemoveTooltip());
				this.add(GUIFactory.createLabel(CoreSwingResources.getInstance().getString("label.extension"), SwingConstants.CENTER));
				this.add(Box.createVerticalStrut(8));
				this.add(combo);
				this.add(Box.createVerticalStrut(8));
				this.add(insertBeforeButton);
				this.add(Box.createVerticalStrut(8));
				this.add(insertAfterButton);
				this.add(Box.createVerticalStrut(8));
				this.add(removeButton);
			}
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
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.swing.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}
	}

	private class InsertAfterAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;
		private final JComboBox combo;

		/**
		 * @param combo
		 * @param nodeEditor
		 */
		public InsertAfterAction(final JComboBox combo, final NodeEditor nodeEditor) {
			super(getInsertAfterLabel());
			this.nodeEditor = nodeEditor;
			this.combo = combo;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			final Extension<?> extension = (Extension<?>) combo.getSelectedItem();
			if (extension instanceof NullExtension) {
				nodeEditor.getParentNodeEditor().insertChildNodeAfter(nodeEditor.getIndex(), createNodeValue(null));
			}
			else {
				final ExtensionReference reference = extension.getExtensionReference();
				nodeEditor.getParentNodeEditor().insertChildNodeAfter(nodeEditor.getIndex(), createNodeValue(reference));
			}
		}
	}

	private class InsertBeforeAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;
		private final JComboBox combo;

		/**
		 * @param combo
		 * @param nodeEditor
		 */
		public InsertBeforeAction(final JComboBox combo, final NodeEditor nodeEditor) {
			super(getInsertBeforeLabel());
			this.nodeEditor = nodeEditor;
			this.combo = combo;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			final Extension<?> extension = (Extension<?>) combo.getSelectedItem();
			if (extension instanceof NullExtension) {
				nodeEditor.getParentNodeEditor().insertChildNodeBefore(nodeEditor.getIndex(), createNodeValue(null));
			}
			else {
				final ExtensionReference reference = extension.getExtensionReference();
				nodeEditor.getParentNodeEditor().insertChildNodeBefore(nodeEditor.getIndex(), createNodeValue(reference));
			}
		}
	}

	private class RemoveAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;

		/**
		 * @param nodeEditor
		 */
		public RemoveAction(final NodeEditor nodeEditor) {
			super(getRemoveLabel());
			this.nodeEditor = nodeEditor;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			nodeEditor.getParentNodeEditor().removeChildNode(nodeEditor.getIndex());
		}
	}
}
