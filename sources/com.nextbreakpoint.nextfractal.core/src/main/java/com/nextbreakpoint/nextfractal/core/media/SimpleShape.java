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

import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public final class SimpleShape extends AbstractShape {
	private Controller controller;
	private Movie parent;
	private Layer layer;
	private Sequence sequence;
	private EnginePaint paint1;
	private EnginePaint paint2;
	private Stroke stroke;
	private Point2D center;
	private final String name;
	private final Shape shape;
	private final AffineTransform transform;

	public SimpleShape(final String name, final Shape shape, final EnginePaint paint1, final EnginePaint paint2, final Stroke stroke) {
		this.name = name;
		this.shape = shape;
		this.paint1 = paint1;
		this.paint2 = paint2;
		this.stroke = stroke;
		center = new Point2D.Float(0, 0);
		transform = new AffineTransform();
	}

	public SimpleShape(final String name, final Shape shape, final EnginePaint paint1, final EnginePaint paint2) {
		this(name, shape, paint1, paint2, null);
	}

	public SimpleShape(final String name, final Shape shape, final EnginePaint paint1, final Stroke stroke) {
		this(name, shape, paint1, null, stroke);
	}

	public SimpleShape(final String name, final Shape shape, final EnginePaint paint1) {
		this(name, shape, paint1, null, null);
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
	public String getName() {
		return name;
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
	public Shape getShape() {
		return shape;
	}

	@Override
	public Paint getPaint1() {
		if (paint1 != null) {
			return paint1.getPaint();
		}
		else {
			return null;
		}
	}

	@Override
	public Paint getPaint2() {
		if (paint2 != null) {
			return paint2.getPaint();
		}
		else {
			return null;
		}
	}

	@Override
	public Stroke getStroke() {
		return stroke;
	}

	public void setPaint(final EnginePaint paint1, final EnginePaint paint2) {
		this.paint1 = paint1;
		this.paint2 = paint2;
	}

	public void setStroke(final Stroke stroke) {
		this.stroke = stroke;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		EnginePaint p1 = null;
		EnginePaint p2 = null;
		if (paint1 != null) {
			p1 = (EnginePaint) paint1.clone();
		}
		if (paint2 != null) {
			p2 = (EnginePaint) paint2.clone();
		}
		final SimpleShape copy = new SimpleShape(name + "_copy", shape, p1, p2, stroke);
		copy.setTransform((AffineTransform) transform.clone());
		return copy;
	}

	@Override
	void build(final Controller controller, final Movie parent, final Layer layer, final Sequence sequence) {
		this.controller = controller;
		this.parent = parent;
		this.layer = layer;
		this.sequence = sequence;
	}

	@Override
	void init() {
	}

	@Override
	void kill() {
	}

	@Override
	void reset() {
	}

	@Override
	void applyEffect(final Effect effect) {
		if (paint1 != null) {
			paint1.applyEffect(effect);
		}
		if (paint2 != null) {
			paint2.applyEffect(effect);
		}
	}
}
