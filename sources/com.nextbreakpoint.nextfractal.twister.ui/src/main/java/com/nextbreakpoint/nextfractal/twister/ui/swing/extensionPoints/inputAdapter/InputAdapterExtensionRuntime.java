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
package com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.twister.util.AdapterContext;

/**
 * @author Andrea Medeghini
 */
public abstract class InputAdapterExtensionRuntime extends ExtensionRuntime {
	/**
	 * @return the renderContext
	 */
	public abstract RenderContext getRenderContext();

	/**
	 * @return the adapterContext
	 */
	public abstract AdapterContext getAdapterContext();

	/**
	 * @param handler
	 * @param e
	 */
	public abstract void processMousePressed(MouseEvent e);

	/**
	 * @param handler
	 * @param e
	 */
	public abstract void processMouseReleased(MouseEvent e);

	/**
	 * @param handler
	 * @param e
	 */
	public abstract void processMouseClicked(MouseEvent e);

	/**
	 * @param handler
	 * @param e
	 */
	public abstract void processMouseEntered(MouseEvent e);

	/**
	 * @param handler
	 * @param e
	 */
	public abstract void processMouseExited(MouseEvent e);

	/**
	 * @param handler
	 * @param e
	 */
	public abstract void processMouseMoved(MouseEvent e);

	/**
	 * @param handler
	 * @param e
	 */
	public abstract void processMouseDragged(MouseEvent e);

	/**
	 * @param handler
	 * @param e
	 */
	public abstract void processKeyPressed(KeyEvent e);

	/**
	 * @param handler
	 * @param e
	 */
	public abstract void processKeyReleased(KeyEvent e);

	/**
	 * @param handler
	 * @param e
	 */
	public abstract void processKeyTyped(KeyEvent e);

	/**
	 * 
	 */
	public abstract void refresh();

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionRuntime#dispose()
	 */
	@Override
	public void dispose() {
	}

	/**
	 * @param renderContext
	 * @param config
	 */
	public void init(final RenderContext renderContext, final ExtensionConfig config) {
	}
}
