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

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.net.URL;

public final class SimpleImage extends AbstractImage {
	private Controller controller;
	private Movie parent;
	private Layer layer;
	private Sequence sequence;
	private Point2D center;
	private final String name;
	private final EngineImage image;
	private final AffineTransform transform;

	public SimpleImage(final String name, final URL url) {
		this(name, new EngineBufferedImage(url));
	}

	public SimpleImage(final String name, final BufferedImage image) {
		this(name, new EngineBufferedImage(image));
	}

	private SimpleImage(final String name, final EngineImage image) {
		this.image = image;
		this.name = name;
		center = new Point2D.Float(0, 0);
		transform = new AffineTransform();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		EngineImage img = null;
		if (image != null) {
			img = (EngineImage) image.clone();
		}
		final SimpleImage copy = new SimpleImage(name + "_copy", img);
		copy.setTransform((AffineTransform) transform.clone());
		return copy;
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
	public void drawImage(Graphics2D g2d) {
		if (image != null) {
			image.drawImage(g2d);
		}
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
		if (image != null) {
			image.applyEffect(effect);
		}
	}
}
