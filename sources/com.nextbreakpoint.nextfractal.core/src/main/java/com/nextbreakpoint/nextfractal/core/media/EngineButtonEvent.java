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
package com.nextbreakpoint.nextfractal.core.media;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public final class EngineButtonEvent extends EngineEvent {
	public static final int RELEASED = 0;
	public static final int PRESSED = 1;
	public static final int ENTERED = 2;
	public static final int EXITED = 3;
	AffineTransform transform;
	Point2D.Float mouse;
	int event;

	public EngineButtonEvent(final int event, final AffineTransform transform, final Point2D.Float mouse) {
		this.transform = transform;
		this.mouse = mouse;
		this.event = event;
	}

	@Override
	public String toString() {
		return "EngineButtonEvent[" + event + "," + mouse.x + "," + mouse.y + "]";
	}
}
