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

public abstract class Renderer {
	private int frames;
	private int frame;

	protected Renderer(int frames) {
		this.frames = frames;
	}

	public final void setFrame(int frame) {
		this.frame = frame;
		frameChanged();
	}

	protected void frameChanged() {
	}

	public final int getFrame() {
		return frame;
	}

	public final int getFrames() {
		return frames;
	}

	public abstract void drawImage(Graphics2D g2d);

	public abstract void render();

	public abstract void init();

	public abstract void kill();

	public abstract void reset();

	public abstract void build(Controller controller, Movie parent, Layer layer, Sequence sequence);

	@Override
	public abstract Object clone();

	public abstract int getWidth();

	public abstract int getHeight();

	public abstract void applyEffect(Effect effect);
}
