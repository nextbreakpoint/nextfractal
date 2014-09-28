/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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
package com.nextbreakpoint.nextfractal.twister.swing.extensions.editor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.nextbreakpoint.nextfractal.core.swing.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.swing.editor.extension.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.swing.util.StackLayout;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.twister.swing.extensions.TwisterSwingExtensionResources;

import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerConfigElement;
import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerConfigElementNodeValue;

/**
 * @author Andrea Medeghini
 */
public class GroupLayerElementListEditorRuntime extends EditorExtensionRuntime {
	/**
	 * 
	 */
	public GroupLayerElementListEditorRuntime() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.extension.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class AppendAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;

		/**
		 * @param nodeEditor
		 */
		public AppendAction(final NodeEditor nodeEditor) {
			super(TwisterSwingExtensionResources.getInstance().getString("action.appendGroupLayer"));
			this.nodeEditor = nodeEditor;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			final GroupLayerConfigElement element = new GroupLayerConfigElement();
			nodeEditor.appendChildNode(new GroupLayerConfigElementNodeValue(element.clone()));
		}
	}

	private class RemoveAllAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;

		/**
		 * @param nodeEditor
		 */
		public RemoveAllAction(final NodeEditor nodeEditor) {
			super(TwisterSwingExtensionResources.getInstance().getString("action.removeAllGroupLayers"));
			this.nodeEditor = nodeEditor;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			nodeEditor.removeAllChildNodes();
		}
	}

	protected class EditorComponent extends JPanel implements NodeEditorComponent {
		private static final long serialVersionUID = 1L;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			setLayout(new StackLayout());
			final JButton appendButton = GUIFactory.createButton(new AppendAction(nodeEditor), TwisterSwingExtensionResources.getInstance().getString("tooltip.appendGroupLayer"));
			final JButton removeButton = GUIFactory.createButton(new RemoveAllAction(nodeEditor), TwisterSwingExtensionResources.getInstance().getString("tooltip.removeAllGroupLayers"));
			this.add(appendButton);
			this.add(Box.createVerticalStrut(8));
			this.add(removeButton);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.NodeEditorComponent#getComponent()
		 */
		public JComponent getComponent() {
			return this;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.NodeEditorComponent#reloadValue()
		 */
		public void reloadValue() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.NodeEditorComponent#dispose()
		 */
		public void dispose() {
		}
	}
}
