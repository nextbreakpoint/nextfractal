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

import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class Movie extends AbstractGraphics implements Animate {
	private Controller controller;
	private Sequence sequence;
	private Movie parent;
	private Layer layer;
	private int rate = 24;
	private Dimension size;
	private Point2D center;
	private final String name;
	private final Timeline timeline;
	private AffineTransform transform;
	private List<AnimationListener> listeners = new LinkedList<AnimationListener>();

	public Movie(final String name, final Timeline timeline, final Dimension size) {
		this.name = name;
		this.size = size;
		this.timeline = timeline;
		center = new Point2D.Float(0, 0);
		transform = new AffineTransform();
	}

	@Override
	public final Object clone() throws CloneNotSupportedException {
		return new Movie(name + "_copy", (Timeline) timeline.clone(), size);
	}

	@Override
	public final AffineTransform getTransform() {
		return transform;
	}

	@Override
	public Point2D getCenter() {
		return center;
	}

	public Dimension getSize() {
		return size;
	}

	@Override
	public void setCenter(Point2D center) {
		this.center = center;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public final Timeline getTimeline() {
		return timeline;
	}

	protected Layer getLayer() {
		return layer;
	}

	protected Sequence getSequence() {
		return sequence;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Movie getParent() {
		return parent;
	}

	public final Controller getEngine() {
		return controller;
	}

	@Override
	public final void build(final Controller controller, final Movie parent, final Layer layer, final Sequence sequence) {
		this.controller = controller;
		this.parent = parent;
		this.layer = layer;
		this.sequence = sequence;
		timeline.build(controller, this);
	}

	@Override
	public final void init() {
		timeline.init();
	}

	@Override
	public final void kill() {
		timeline.kill();
	}

	@Override
	public final void reset() {
		timeline.reset();
	}

	public int getFrames() {
		return timeline.getFrames();
	}

	public int getFrame() {
		return timeline.getFrame();
	}

	public final void setFrame(final int frame) {
		timeline.setFrame(frame);
		fireFrameChanged(frame);
	}

	public final void nextFrame() {
		timeline.nextFrame();
		fireFrameChanged(getFrame());
	}

	public final void prevFrame() {
		timeline.prevFrame();
		fireFrameChanged(getFrame());
	}

	public final int getFrameRate() {
		if (parent != null) {
			return parent.getFrameRate();
		}
		else {
			return rate;
		}
	}

	protected final void setFrameRate(final int rate) {
		this.rate = rate;
	}

	public void load() {
	}

	public void flush() {
	}

	public void addAnimationListener(AnimationListener listener) {
		listeners.add(listener);
	}

	public void removeAnimationListener(AnimationListener listener) {
		listeners.remove(listener);
	}

	protected final void fireFrameChanged(int frame) {
		for (AnimationListener listener : listeners) {
			listener.frameChanged(frame);
		}
	}

	public int getWidth() {
		return (int) size.getWidth();
	}

	public int getHeight() {
		return (int) size.getHeight();
	}

	public final void resize(int width, int height) {
		getTransform().setToScale(width / (float) getSize().width, height / (float) getSize().height);
		translate((float) -getCenter().getX(), (float) -getCenter().getY());
	}
}
