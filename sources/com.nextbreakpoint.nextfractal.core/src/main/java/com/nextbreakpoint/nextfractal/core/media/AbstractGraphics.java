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
package com.nextbreakpoint.nextfractal.core.media;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

abstract class AbstractGraphics extends AbstractObject {
	private List<EventListener> eventListeners = new LinkedList<EventListener>();

	public final void setTransform(final AffineTransform transform) {
		if (transform != null) {
			getTransform().setTransform(transform);
		}
	}

	public abstract Point2D getCenter();

	public abstract void setCenter(Point2D center);

	public abstract AffineTransform getTransform();

	public final void translate(final float x, final float y) {
		getTransform().translate(x, y);
	}

	public final void shear(final float x, final float y) {
		getTransform().shear(x, y);
	}

	public final void scale(final float x, final float y) {
		getTransform().scale(x, y);
	}

	public final void rotate(final float a) {
		getTransform().rotate(a, getCenter().getX(), getCenter().getY());
	}

	public final void addEventListener(EventListener listener) {
		eventListeners.add(listener);
	}

	public final void removeEventListener(EventListener listener) {
		eventListeners.remove(listener);
	}

	protected final void fireEvent(AffineTransform inverse, EngineEvent event) {
		for (EventListener eventListener : eventListeners) {
			eventListener.processEvent(this, inverse, event);
		}
	}

	public void dispatchEvent(AffineTransform inverse, EngineEvent event) {
		fireEvent(inverse, event);
	}
}
