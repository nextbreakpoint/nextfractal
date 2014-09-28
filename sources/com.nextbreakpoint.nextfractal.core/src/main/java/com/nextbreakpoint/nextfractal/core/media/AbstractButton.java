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

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

abstract class AbstractButton extends AbstractGraphics {
	boolean pressed = false;
	boolean entered = false;
	boolean actived = false;
	private float mouse_x = 0;
	private float mouse_y = 0;
	private boolean mouse_pressed = false;

	abstract ButtonHandler getHandler();

	abstract AbstractShape getReleased();

	abstract AbstractShape getPressed();

	abstract AbstractShape getOver();

	abstract Shape getHit();

	final AbstractShape getButtonShape() {
		if (pressed) {
			return getPressed();
		}
		else if (entered) {
			return getOver();
		}
		else {
			return getReleased();
		}
	}

	@Override
	public final void dispatchEvent(AffineTransform inverse, EngineEvent event) {
		super.dispatchEvent(inverse, event);
		if (event instanceof EngineMouseEvent) {
			final EngineMouseEvent event2 = (EngineMouseEvent) event;
			// if (debug)
			// {
			// System.out.println(event);
			// }
			switch (event2.event) {
				case EngineMouseEvent.PRESSED: {
					mouse_pressed = true;
					break;
				}
				case EngineMouseEvent.RELEASED: {
					mouse_pressed = false;
					break;
				}
				case EngineMouseEvent.MOVED: {
					mouse_x = event2.mouse.x;
					mouse_y = event2.mouse.y;
					break;
				}
				default:
					break;
			}
		}
		final Shape hit = getHit();
		if (hit != null) {
			final ButtonHandler handler = getHandler();
			final Point2D.Float mouse = new Point2D.Float(mouse_x, mouse_y);
			inverse.transform(mouse, mouse);
			if (hit.contains(mouse.x, mouse.y)) {
				if (!mouse_pressed) {
					actived = true;
				}
				if (!entered) {
					if (handler != null) {
						handler.process(new EngineButtonEvent(EngineButtonEvent.ENTERED, inverse, mouse));
					}
					entered = true;
				}
				if ((mouse_pressed) && (actived) && (!pressed)) {
					if (handler != null) {
						handler.process(new EngineButtonEvent(EngineButtonEvent.PRESSED, inverse, mouse));
					}
					pressed = true;
				}
				else if ((!mouse_pressed) && (actived) && (pressed)) {
					if (handler != null) {
						handler.process(new EngineButtonEvent(EngineButtonEvent.RELEASED, inverse, mouse));
					}
					pressed = false;
				}
			}
			else {
				if (!mouse_pressed) {
					actived = false;
				}
				pressed = false;
				if (entered) {
					if (handler != null) {
						handler.process(new EngineButtonEvent(EngineButtonEvent.EXITED, inverse, mouse));
					}
					entered = false;
				}
			}
		}
	}
}
