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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.ui.swing.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.swing.editor.extension.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.StackLayout;
import com.nextbreakpoint.nextfractal.twister.image.ImageConfigElement;
import com.nextbreakpoint.nextfractal.twister.layer.ImageLayerConfigElement;
import com.nextbreakpoint.nextfractal.twister.layer.ImageLayerConfigElementNodeValue;
import com.nextbreakpoint.nextfractal.twister.ui.TwisterUIExtensionResources;

/**
 * @author Andrea Medeghini
 */
public class ImageLayerElementEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime#createChildValue()
	 */
	protected NodeValue<?> createChildValue() {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.extension.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class EditorComponent extends JPanel implements NodeEditorComponent {
		private static final long serialVersionUID = 1L;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			setLayout(new StackLayout());
			if (nodeEditor.isParentMutable()) {
				final JButton insertBeforeButton = GUIFactory.createButton(new InsertBeforeAction(nodeEditor), TwisterUIExtensionResources.getInstance().getString("tooltip.insertImageLayerBefore"));
				final JButton insertAfterButton = GUIFactory.createButton(new InsertAfterAction(nodeEditor), TwisterUIExtensionResources.getInstance().getString("tooltip.insertImageLayerAfter"));
				final JButton removeButton = GUIFactory.createButton(new RemoveAction(nodeEditor), TwisterUIExtensionResources.getInstance().getString("tooltip.removeImageLayer"));
				final JButton moveUpButton = GUIFactory.createButton(new MoveUpAction(nodeEditor), TwisterUIExtensionResources.getInstance().getString("tooltip.moveUpImageLayer"));
				final JButton moveDownButton = GUIFactory.createButton(new MoveDownAction(nodeEditor), TwisterUIExtensionResources.getInstance().getString("tooltip.moveDownImageLayer"));
				add(insertBeforeButton);
				this.add(Box.createVerticalStrut(8));
				add(insertAfterButton);
				this.add(Box.createVerticalStrut(8));
				add(removeButton);
				this.add(Box.createVerticalStrut(8));
				add(moveUpButton);
				this.add(Box.createVerticalStrut(8));
				add(moveDownButton);
			}
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
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.swing.NodeEditorComponent#dispose()
		 */
		@Override
		public void dispose() {
		}
	}

	private class InsertAfterAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;

		/**
		 * @param nodeEditor
		 */
		public InsertAfterAction(final NodeEditor nodeEditor) {
			super(TwisterUIExtensionResources.getInstance().getString("action.insertImageLayerAfter"));
			this.nodeEditor = nodeEditor;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			final ImageLayerConfigElement element = new ImageLayerConfigElement();
			element.setImageConfigElement(new ImageConfigElement());
			nodeEditor.getParentNodeEditor().insertChildNodeAfter(nodeEditor.getIndex(), new ImageLayerConfigElementNodeValue(element.clone()));
		}
	}

	private class InsertBeforeAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;

		/**
		 * @param nodeEditor
		 */
		public InsertBeforeAction(final NodeEditor nodeEditor) {
			super(TwisterUIExtensionResources.getInstance().getString("action.insertImageLayerBefore"));
			this.nodeEditor = nodeEditor;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			final ImageLayerConfigElement element = new ImageLayerConfigElement();
			element.setImageConfigElement(new ImageConfigElement());
			nodeEditor.getParentNodeEditor().insertChildNodeBefore(nodeEditor.getIndex(), new ImageLayerConfigElementNodeValue(element.clone()));
		}
	}

	private class RemoveAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;

		/**
		 * @param nodeEditor
		 */
		public RemoveAction(final NodeEditor nodeEditor) {
			super(TwisterUIExtensionResources.getInstance().getString("action.removeImageLayer"));
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

	private class MoveUpAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;

		/**
		 * @param nodeEditor
		 */
		public MoveUpAction(final NodeEditor nodeEditor) {
			super(TwisterUIExtensionResources.getInstance().getString("action.moveUpImageLayer"));
			this.nodeEditor = nodeEditor;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			nodeEditor.getParentNodeEditor().moveUpChildNode(nodeEditor.getIndex());
		}
	}

	private class MoveDownAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;

		/**
		 * @param nodeEditor
		 */
		public MoveDownAction(final NodeEditor nodeEditor) {
			super(TwisterUIExtensionResources.getInstance().getString("action.moveDownImageLayer"));
			this.nodeEditor = nodeEditor;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			nodeEditor.getParentNodeEditor().moveDownChildNode(nodeEditor.getIndex());
		}
	}
}
