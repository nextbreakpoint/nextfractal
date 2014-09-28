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

public final class ClipSoundSequence extends Sequence {
	private final AbstractClipSound object;
	private int frames = 0;
	private int frame = 0;

	public ClipSoundSequence(final AbstractClipSound object, final int frames) {
		this.object = object;
		this.frames = frames;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new ClipSoundSequence((AbstractClipSound) object.clone(), frames);
	}

	@Override
	public AbstractObject getObject() {
		return object;
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
		if (object != null) {
			object.reset();
		}
	}

	@Override
	void setFrame(final int frame) {
		this.frame = frame;
		if (isFirstFrame()) {
			object.play();
		}
	}

	@Override
	void nextFrame() {
		frame = frame + 1;
		if (isFirstFrame()) {
			object.play();
		}
	}

	@Override
	void prevFrame() {
		frame = frame - 1;
		if (isFirstFrame()) {
			object.play();
		}
	}
}
