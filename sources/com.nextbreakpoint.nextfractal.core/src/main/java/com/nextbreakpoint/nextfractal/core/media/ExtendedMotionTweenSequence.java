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

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;

public final class ExtendedMotionTweenSequence extends GraphicsSequence {
	private final AbstractGraphics object;
	private final Effect effect;
	private final Shape guide;
	private final boolean normal;
	private int frames = 0;
	private int frame = 0;
	private float rotation;
	private float shearx;
	private float sheary;
	private final float delta_rotation;
	private final float delta_shearx;
	private final float delta_sheary;
	private final float copy_rotation;
	private final float copy_shearx;
	private final float copy_sheary;
	private final AffineTransform transform;
	private final Polygon guida = new Polygon();

	public ExtendedMotionTweenSequence(final AbstractGraphics object, final int frames, final Shape guide, final boolean normal, final float rotation, final float shearx, final float sheary, final Effect effect) {
		this.object = object;
		this.effect = effect;
		this.frames = frames;
		this.normal = normal;
		this.guide = guide;
		copy_rotation = rotation;
		copy_shearx = shearx;
		copy_sheary = sheary;
		delta_rotation = rotation / (frames - 1);
		delta_shearx = shearx / (frames - 1);
		delta_sheary = sheary / (frames - 1);
		transform = (AffineTransform) object.getTransform().clone();
		PathIterator path = guide.getPathIterator(null, 0.0);
		double total_length = 0;
		final double[] s1 = new double[6];
		final double[] s2 = new double[6];
		int type = path.currentSegment(s1);
		path.next();
		while (type != PathIterator.SEG_CLOSE) {
			type = path.currentSegment(s2);
			path.next();
			if (type != PathIterator.SEG_CLOSE) {
				total_length += Math.sqrt(((s2[0] - s1[0]) * (s2[0] - s1[0])) + ((s2[1] - s1[1]) * (s2[1] - s1[1])));
				s1[0] = s2[0];
				s1[1] = s2[1];
			}
		}
		final double delta_length = total_length / frames;
		path = guide.getPathIterator(null, 0.0);
		total_length = 0;
		double old_length = 0;
		double segment_length = 0;
		double length = 0;
		type = path.currentSegment(s1);
		path.next();
		while (guida.npoints < frames) {
			while (type != PathIterator.SEG_CLOSE) {
				type = path.currentSegment(s2);
				path.next();
				if (type != PathIterator.SEG_CLOSE) {
					segment_length = Math.sqrt(((s2[0] - s1[0]) * (s2[0] - s1[0])) + ((s2[1] - s1[1]) * (s2[1] - s1[1])));
					old_length = total_length;
					total_length += segment_length;
					if (length < total_length) {
						break;
					}
					s1[0] = s2[0];
					s1[1] = s2[1];
				}
			}
			while (length < total_length) {
				final double x = s1[0] + (((s2[0] - s1[0]) * (length - old_length)) / segment_length);
				final double y = s1[1] + (((s2[1] - s1[1]) * (length - old_length)) / segment_length);
				guida.addPoint((int) Math.rint(x), (int) Math.rint(y));
				length += delta_length;
			}
			s1[0] = s2[0];
			s1[1] = s2[1];
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		if (effect != null) {
			return new ExtendedMotionTweenSequence((AbstractGraphics) object.clone(), frames, guide, normal, copy_rotation, copy_shearx, copy_sheary, (Effect) effect.clone());
		}
		else {
			return new ExtendedMotionTweenSequence((AbstractGraphics) object.clone(), frames, guide, normal, copy_rotation, copy_shearx, copy_sheary, null);
		}
	}

	@Override
	public AbstractObject getObject() {
		return object;
	}

	@Override
	public Effect getEffect() {
		return effect;
	}

	@Override
	public int getFrames() {
		return frames;
	}

	@Override
	public int getFrame() {
		return frame;
	}

	@Override
	public boolean isFirstFrame() {
		return (frame == 0);
	}

	@Override
	public boolean isLastFrame() {
		return (frame == (frames - 1));
	}

	@Override
	void build(final Controller controller, final Movie parent, final Layer layer) {
		if (object != null) {
			object.build(controller, parent, layer, this);
		}
	}

	@Override
	void init() {
		if (effect != null) {
			effect.init(frames);
		}
		if (object != null) {
			object.init();
		}
		frame = 0;
		rotation = 0;
		shearx = 0;
		sheary = 0;
	}

	@Override
	void kill() {
		if (object != null) {
			object.kill();
		}
	}

	@Override
	void reset() {
		if (effect != null) {
			effect.reset();
		}
		if (object != null) {
			object.reset();
		}
		frame = 0;
		rotation = 0;
		shearx = 0;
		sheary = 0;
	}

	@Override
	void setFrame(final int frame) {
		this.frame = frame;
		if ((this.frame >= 0) && (this.frame < frames)) {
			final float x = guida.xpoints[frame];
			final float y = guida.ypoints[frame];
			object.setTransform(transform);
			object.translate(x, y);
			float angle = 0;
			if ((frame > 0) && normal) {
				final float ox = guida.xpoints[frame - 1];
				final float oy = guida.ypoints[frame - 1];
				angle = (float) Math.atan2(y - oy, x - ox);
			}
			rotation = delta_rotation * frame;
			shearx = delta_shearx * frame;
			sheary = delta_sheary * frame;
			object.rotate(rotation + angle);
			object.shear(shearx, sheary);
			if (effect != null) {
				effect.setFrame(frame);
			}
			if (object instanceof Movie) {
				((Movie) object).setFrame(frame);
			}
		}
	}

	@Override
	void nextFrame() {
		frame = frame + 1;
		if ((frame >= 0) && (frame < frames)) {
			final float x = guida.xpoints[frame];
			final float y = guida.ypoints[frame];
			object.setTransform(transform);
			object.translate(x, y);
			float angle = 0;
			if ((frame > 0) && normal) {
				final float ox = guida.xpoints[frame - 1];
				final float oy = guida.ypoints[frame - 1];
				angle = (float) Math.atan2(y - oy, x - ox);
			}
			rotation += delta_rotation;
			shearx += delta_shearx;
			sheary += delta_sheary;
			object.rotate(rotation + angle);
			object.shear(shearx, sheary);
			if (effect != null) {
				effect.nextFrame();
			}
			if (object instanceof Animate) {
				((Animate) object).nextFrame();
			}
		}
	}

	@Override
	void prevFrame() {
		frame = frame - 1;
		if ((frame >= 0) && (frame < frames)) {
			final float x = guida.xpoints[frame];
			final float y = guida.ypoints[frame];
			object.setTransform(transform);
			object.translate(x, y);
			float angle = 0;
			if ((frame > 0) && normal) {
				final float ox = guida.xpoints[frame - 1];
				final float oy = guida.ypoints[frame - 1];
				angle = (float) Math.atan2(y - oy, x - ox);
			}
			rotation -= delta_rotation;
			shearx -= delta_shearx;
			sheary -= delta_sheary;
			object.rotate(rotation + angle);
			object.shear(shearx, sheary);
			if (effect != null) {
				effect.prevFrame();
			}
			if (object instanceof Animate) {
				((Animate) object).prevFrame();
			}
		}
	}
}
