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
package com.nextbreakpoint.nextfractal.core.swing.editor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.extension.NullExtension;
import com.nextbreakpoint.nextfractal.core.swing.CoreSwingResources;
import com.nextbreakpoint.nextfractal.core.swing.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.swing.editor.extension.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.swing.extension.ExtensionComboBoxModel;
import com.nextbreakpoint.nextfractal.core.swing.extension.ExtensionListCellRenderer;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.swing.util.StackLayout;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;

/**
 * @author Andrea Medeghini
 */
public abstract class ReferenceElementListEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.editor.extension.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

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
	protected abstract String getAppendLabel();

	/**
	 * @return
	 */
	protected abstract String getRemoveAllLabel();

	/**
	 * @return
	 */
	protected abstract String getAppendTooltip();

	/**
	 * @return
	 */
	protected abstract String getRemoveAllTooltip();

	private class EditorComponent extends JPanel implements NodeEditorComponent {
		private static final long serialVersionUID = 1L;
		private final JComboBox combo = GUIFactory.createComboBox(createModel(), CoreSwingResources.getInstance().getString("tooltip.selectExtension"));

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			setLayout(new StackLayout());
			combo.setRenderer(new ExtensionListCellRenderer());
			final JButton appendButton = GUIFactory.createButton(new AppendAction(combo, nodeEditor), getAppendTooltip());
			final JButton removeButton = GUIFactory.createButton(new RemoveAllAction(nodeEditor), getRemoveAllTooltip());
			this.add(combo);
			this.add(Box.createVerticalStrut(8));
			this.add(appendButton);
			this.add(Box.createVerticalStrut(8));
			this.add(removeButton);
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

	private class AppendAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final JComboBox combo;
		private final NodeEditor nodeEditor;

		/**
		 * @param combo
		 * @param nodeEditor
		 */
		public AppendAction(final JComboBox combo, final NodeEditor nodeEditor) {
			super(getAppendLabel());
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
				nodeEditor.appendChildNode(createNodeValue(null));
			}
			else {
				final ExtensionReference reference = extension.getExtensionReference();
				nodeEditor.appendChildNode(createNodeValue(reference));
			}
		}
	}

	private class RemoveAllAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;

		/**
		 * @param nodeEditor
		 */
		public RemoveAllAction(final NodeEditor nodeEditor) {
			super(getRemoveAllLabel());
			this.nodeEditor = nodeEditor;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			nodeEditor.removeAllChildNodes();
		}
	}
}
