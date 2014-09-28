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

public final class SimpleSequence extends GraphicsSequence {
	private final AbstractGraphics object;
	private final Effect effect;
	private int frames = 0;
	private int frame = 0;

	public SimpleSequence(final AbstractGraphics object, final int frames, final Effect effect) {
		this.object = object;
		this.frames = frames;
		this.effect = effect;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new SimpleSequence((AbstractGraphics) object.clone(), frames, (Effect) effect.clone());
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
	}

	@Override
	void setFrame(final int frame) {
		this.frame = frame;
		if ((this.frame >= 0) && (this.frame < frames)) {
			if (effect != null) {
				effect.setFrame(frame);
			}
			if (object instanceof Animate) {
				((Animate) object).setFrame(this.frame);
			}
		}
	}

	@Override
	void nextFrame() {
		frame = frame + 1;
		if ((frame >= 0) && (frame < frames)) {
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
			if (effect != null) {
				effect.prevFrame();
			}
			if (object instanceof Animate) {
				((Animate) object).prevFrame();
			}
		}
	}
}
