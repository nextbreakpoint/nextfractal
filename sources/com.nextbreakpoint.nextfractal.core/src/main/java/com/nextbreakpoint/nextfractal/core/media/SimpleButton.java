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

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public final class SimpleButton extends AbstractButton {
	private Controller controller;
	private Movie parent;
	private Layer layer;
	private Sequence sequence;
	private Point2D center;
	private final String name;
	private final Shape hit;
	private final AbstractShape released_shape;
	private final AbstractShape pressed_shape;
	private final AbstractShape over_shape;
	private final ButtonHandler handler;
	private final AffineTransform transform;

	public SimpleButton(final String name, final AbstractShape released, final AbstractShape pressed, final AbstractShape over, final Shape hit, final ButtonHandler handler) {
		this.name = name;
		this.hit = hit;
		this.handler = handler;
		released_shape = released;
		pressed_shape = pressed;
		over_shape = over;
		center = new Point2D.Float(0, 0);
		transform = new AffineTransform();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		AbstractShape r = null;
		AbstractShape p = null;
		AbstractShape o = null;
		ButtonHandler h = null;
		if (released_shape != null) {
			r = (AbstractShape) released_shape.clone();
		}
		if (pressed_shape != null) {
			p = (AbstractShape) pressed_shape.clone();
		}
		if (over_shape != null) {
			o = (AbstractShape) over_shape.clone();
		}
		if (handler != null) {
			h = (ButtonHandler) handler.clone();
		}
		final SimpleButton copy = new SimpleButton(name + "_copy", r, p, o, hit, h);
		copy.setTransform((AffineTransform) transform.clone());
		return copy;
	}

	@Override
	public String getName() {
		return name;
	}

	protected Layer getLayer() {
		return layer;
	}

	protected Sequence getSequence() {
		return sequence;
	}

	protected Controller getController() {
		return controller;
	}

	@Override
	public Movie getParent() {
		return parent;
	}

	@Override
	public Point2D getCenter() {
		return center;
	}

	@Override
	public void setCenter(Point2D center) {
		this.center = center;
	}

	@Override
	public AffineTransform getTransform() {
		return transform;
	}

	@Override
	void build(final Controller controller, final Movie parent, final Layer layer, final Sequence sequence) {
		this.controller = controller;
		this.parent = parent;
		this.layer = layer;
		this.sequence = sequence;
		if (handler != null) {
			handler.build(controller, parent, layer, sequence);
		}
		if (released_shape != null) {
			released_shape.build(controller, parent, layer, sequence);
		}
		if (pressed_shape != null) {
			pressed_shape.build(controller, parent, layer, sequence);
		}
		if (over_shape != null) {
			over_shape.build(controller, parent, layer, sequence);
		}
	}

	@Override
	void init() {
		if (handler != null) {
			handler.init();
		}
		if (released_shape != null) {
			released_shape.init();
		}
		if (pressed_shape != null) {
			pressed_shape.init();
		}
		if (over_shape != null) {
			over_shape.init();
		}
	}

	@Override
	void kill() {
		if (handler != null) {
			handler.kill();
		}
		if (released_shape != null) {
			released_shape.kill();
		}
		if (pressed_shape != null) {
			pressed_shape.kill();
		}
		if (over_shape != null) {
			over_shape.kill();
		}
	}

	@Override
	void reset() {
		if (handler != null) {
			handler.reset();
		}
		if (released_shape != null) {
			released_shape.reset();
		}
		if (pressed_shape != null) {
			pressed_shape.reset();
		}
		if (over_shape != null) {
			over_shape.reset();
		}
	}

	@Override
	ButtonHandler getHandler() {
		return handler;
	}

	@Override
	AbstractShape getReleased() {
		return released_shape;
	}

	@Override
	AbstractShape getPressed() {
		return pressed_shape;
	}

	@Override
	AbstractShape getOver() {
		return over_shape;
	}

	@Override
	Shape getHit() {
		return hit;
	}
}
