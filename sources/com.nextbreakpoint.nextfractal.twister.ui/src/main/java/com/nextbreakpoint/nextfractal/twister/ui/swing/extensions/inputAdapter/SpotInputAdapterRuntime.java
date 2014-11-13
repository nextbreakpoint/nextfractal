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
package com.nextbreakpoint.nextfractal.twister.ui.swing.extensions.inputAdapter;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.twister.extensions.effect.SpotConfig;
import com.nextbreakpoint.nextfractal.twister.ui.swing.DefaultInputAdapterRuntime;

/**
 * @author Andrea Medeghini
 */
public class SpotInputAdapterRuntime extends DefaultInputAdapterRuntime {
	/**
	 * 
	 */
	public SpotInputAdapterRuntime() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.DefaultInputAdapterRuntime#refresh()
	 */
	@Override
	public void refresh() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.DefaultInputAdapterRuntime#processKeyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void processKeyPressed(final KeyEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.DefaultInputAdapterRuntime#processKeyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void processKeyReleased(final KeyEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.DefaultInputAdapterRuntime#processKeyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void processKeyTyped(final KeyEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.DefaultInputAdapterRuntime#processMouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseClicked(final MouseEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.DefaultInputAdapterRuntime#processMouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseDragged(final MouseEvent e) {
		final SpotConfig config = (SpotConfig) getAdapterContext().getConfig();
		if (config != null) {
			final double w = getRenderContext().getImageSize().getX();
			final double h = getRenderContext().getImageSize().getY();
			final DoubleVector2D center = new DoubleVector2D(e.getX() / w, e.getY() / h);
			config.getContext().updateTimestamp();
			config.setCenter(center);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.DefaultInputAdapterRuntime#processMouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseEntered(final MouseEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.DefaultInputAdapterRuntime#processMouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseExited(final MouseEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.DefaultInputAdapterRuntime#processMouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseMoved(final MouseEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.DefaultInputAdapterRuntime#processMousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMousePressed(final MouseEvent e) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.DefaultInputAdapterRuntime#processMouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseReleased(final MouseEvent e) {
		final SpotConfig config = (SpotConfig) getAdapterContext().getConfig();
		if (config != null) {
			final double w = getRenderContext().getImageSize().getX();
			final double h = getRenderContext().getImageSize().getY();
			final DoubleVector2D center = new DoubleVector2D(e.getX() / w, e.getY() / h);
			config.getContext().updateTimestamp();
			config.setCenter(center);
		}
	}
}
