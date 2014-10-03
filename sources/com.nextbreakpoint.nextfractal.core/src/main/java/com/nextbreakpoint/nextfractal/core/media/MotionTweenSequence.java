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

public final class MotionTweenSequence extends GraphicsSequence {
	private final AbstractGraphics object;
	private final Effect effect;
	private int frames = 0;
	private int frame = 0;
	private float rotation;
	private float translationx;
	private float translationy;
	private float shearx;
	private float sheary;
	private float scalex;
	private float scaley;
	private final float delta_rotation;
	private final float delta_translationx;
	private final float delta_translationy;
	private final float delta_shearx;
	private final float delta_sheary;
	private final float delta_scalex;
	private final float delta_scaley;
	private final float copy_rotation;
	private final float copy_translationx;
	private final float copy_translationy;
	private final float copy_shearx;
	private final float copy_sheary;
	private final float copy_scalex;
	private final float copy_scaley;
	private final AffineTransform transform;

	public MotionTweenSequence(final AbstractGraphics object, final int frames, final float rotation, final float translationx, final float translationy, final float shearx, final float sheary, final float scalex, final float scaley, final Effect effect) {
		this.object = object;
		this.effect = effect;
		this.frames = frames;
		copy_rotation = rotation;
		copy_translationx = translationx;
		copy_translationy = translationy;
		copy_shearx = shearx;
		copy_sheary = sheary;
		copy_scalex = scalex;
		copy_scaley = scaley;
		delta_rotation = rotation / (frames - 1);
		delta_translationx = translationx / (frames - 1);
		delta_translationy = translationy / (frames - 1);
		delta_shearx = shearx / (frames - 1);
		delta_sheary = sheary / (frames - 1);
		delta_scalex = scalex / (frames - 1);
		delta_scaley = scaley / (frames - 1);
		transform = (AffineTransform) object.getTransform().clone();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		if (effect != null) {
			return new MotionTweenSequence((AbstractGraphics) object.clone(), frames, copy_rotation, copy_translationx, copy_translationy, copy_shearx, copy_sheary, copy_scalex, copy_scaley, (Effect) effect.clone());
		}
		else {
			return new MotionTweenSequence((AbstractGraphics) object.clone(), frames, copy_rotation, copy_translationx, copy_translationy, copy_shearx, copy_sheary, copy_scalex, copy_scaley, null);
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
		translationx = 0;
		translationy = 0;
		shearx = 0;
		sheary = 0;
		scalex = 0;
		scaley = 0;
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
		translationx = 0;
		translationy = 0;
		shearx = 0;
		sheary = 0;
		scalex = 0;
		scaley = 0;
	}

	@Override
	void setFrame(final int frame) {
		this.frame = frame;
		if ((this.frame >= 0) && (this.frame < frames)) {
			rotation = delta_rotation * frame;
			translationx = delta_translationx * frame;
			translationy = delta_translationy * frame;
			shearx = delta_shearx * frame;
			sheary = delta_sheary * frame;
			scalex = delta_scalex * frame;
			scaley = delta_scaley * frame;
			object.setTransform(transform);
			object.translate(translationx, translationy);
			object.rotate(rotation);
			object.shear(shearx, sheary);
			object.scale(1 + scalex, 1 + scaley);
			if (effect != null) {
				effect.setFrame(frame);
			}
			if (object instanceof Animate) {
				((Animate) object).setFrame(frame);
			}
		}
	}

	@Override
	void nextFrame() {
		frame = frame + 1;
		if ((frame >= 0) && (frame < frames)) {
			rotation += delta_rotation;
			translationx += delta_translationx;
			translationy += delta_translationy;
			shearx += delta_shearx;
			sheary += delta_sheary;
			scalex += delta_scalex;
			scaley += delta_scaley;
			object.setTransform(transform);
			object.translate(translationx, translationy);
			object.rotate(rotation);
			object.shear(shearx, sheary);
			object.scale(1 + scalex, 1 + scaley);
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
			rotation -= delta_rotation;
			translationx -= delta_translationx;
			translationy -= delta_translationy;
			shearx -= delta_shearx;
			sheary -= delta_sheary;
			scalex -= delta_scalex;
			scaley -= delta_scaley;
			object.setTransform(transform);
			object.translate(translationx, translationy);
			object.rotate(rotation);
			object.shear(shearx, sheary);
			object.scale(1 + scalex, 1 + scaley);
			if (effect != null) {
				effect.prevFrame();
			}
			if (object instanceof Animate) {
				((Animate) object).prevFrame();
			}
		}
	}
}
