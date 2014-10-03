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

public final class SimpleRenderedImage extends AbstractRenderedImage implements AnimationListener {
	private Controller controller;
	private Movie parent;
	private Layer layer;
	private Sequence sequence;
	private Point2D center;
	private final String name;
	private final EngineRenderedImage image;
	private final AffineTransform transform;

	public SimpleRenderedImage(final String name, final Renderer renderer) {
		this(name, new EngineRenderedImage(renderer));
	}

	private SimpleRenderedImage(final String name, final EngineRenderedImage image) {
		super(image.getFrames());
		this.image = image;
		this.name = name;
		center = new Point2D.Float(0, 0);
		transform = new AffineTransform();
		addAnimationListener(this);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		final SimpleRenderedImage copy = new SimpleRenderedImage(name + "_copy", (EngineRenderedImage) image.clone());
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
		if (image != null) {
			image.build(controller, parent, layer, sequence);
		}
	}

	@Override
	void init() {
		if (image != null) {
			image.init();
		}
	}

	@Override
	void kill() {
		if (image != null) {
			image.kill();
		}
	}

	@Override
	void reset() {
		if (image != null) {
			image.reset();
		}
	}

	@Override
	void render() {
		if (image != null) {
			image.render();
		}
	}

	@Override
	void update() {
		if (image != null) {
			image.update();
		}
	}

	@Override
	void applyEffect(final Effect effect) {
		if (image != null) {
			image.applyEffect(effect);
		}
	}

	@Override
	public void frameChanged(int frame) {
		if (image != null) {
			image.setFrame(frame);
		}
	}
}
