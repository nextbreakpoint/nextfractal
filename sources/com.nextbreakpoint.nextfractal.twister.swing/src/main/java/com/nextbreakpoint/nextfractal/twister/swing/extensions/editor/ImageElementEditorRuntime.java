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

import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.nextbreakpoint.nextfractal.core.swing.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.swing.editor.extension.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;

/**
 * @author Andrea Medeghini
 */
public class ImageElementEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.extension.EditorExtensionRuntime#createEditor(com.nextbreakpoint.nextfractal.core.tree.NodeEditor)
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
			setLayout(new FlowLayout(FlowLayout.CENTER));
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
