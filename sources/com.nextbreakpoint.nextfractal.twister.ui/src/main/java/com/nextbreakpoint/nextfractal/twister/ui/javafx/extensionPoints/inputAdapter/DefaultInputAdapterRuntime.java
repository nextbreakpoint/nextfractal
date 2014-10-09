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
package com.nextbreakpoint.nextfractal.twister.ui.javafx.extensionPoints.inputAdapter;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.twister.util.AdapterContext;
import com.nextbreakpoint.nextfractal.twister.util.DefaultAdapterContext;

public class DefaultInputAdapterRuntime extends InputAdapterExtensionRuntime {
	private AdapterContext adapterContext;
	private RenderContext renderContext;

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter.InputAdapterExtensionRuntime#init(com.nextbreakpoint.nextfractal.core.util.RenderContext, com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig)
	 */
	@Override
	public void init(final RenderContext renderContext, final ExtensionConfig config) {
		adapterContext = new DefaultAdapterContext(config);
		this.renderContext = renderContext;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter.InputAdapterExtensionRuntime#getRenderContext()
	 */
	@Override
	public RenderContext getRenderContext() {
		return renderContext;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter.InputAdapterExtensionRuntime#getAdapterContext()
	 */
	@Override
	public AdapterContext getAdapterContext() {
		return adapterContext;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter.InputAdapterExtensionRuntime#processKeyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void processKeyPressed(final KeyEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter.InputAdapterExtensionRuntime#processKeyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void processKeyReleased(final KeyEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter.InputAdapterExtensionRuntime#processKeyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void processKeyTyped(final KeyEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter.InputAdapterExtensionRuntime#processMouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseClicked(final MouseEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter.InputAdapterExtensionRuntime#processMouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseDragged(final MouseEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter.InputAdapterExtensionRuntime#processMouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseEntered(final MouseEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter.InputAdapterExtensionRuntime#processMouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseExited(final MouseEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter.InputAdapterExtensionRuntime#processMouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseMoved(final MouseEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter.InputAdapterExtensionRuntime#processMousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMousePressed(final MouseEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter.InputAdapterExtensionRuntime#processMouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseReleased(final MouseEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter.InputAdapterExtensionRuntime#refresh()
	 */
	@Override
	public void refresh() {
	}
}
