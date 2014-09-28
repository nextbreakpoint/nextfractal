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

import java.awt.Graphics2D;

final class EngineRenderedImage implements EngineImage {
	private Renderer renderer;

	public EngineRenderedImage(final Renderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new EngineRenderedImage((Renderer) renderer.clone());
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.core.media.Renderer#getFrame()
	 */
	public final int getFrame() {
		return renderer.getFrame();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.core.media.Renderer#getFrames()
	 */
	public final int getFrames() {
		return renderer.getFrames();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.media.Renderer#init()
	 */
	public void init() {
		renderer.init();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.media.Renderer#kill()
	 */
	public void kill() {
		renderer.kill();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.media.Renderer#render()
	 */
	public void render() {
		renderer.render();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.media.Renderer#reset()
	 */
	public void reset() {
		renderer.reset();
	}

	/**
	 * @param frame
	 * @see com.nextbreakpoint.nextfractal.core.media.Renderer#setFrame(int)
	 */
	public final void setFrame(int frame) {
		renderer.setFrame(frame);
	}

	/**
	 * @param controller
	 * @param parent
	 * @param layer
	 * @param sequence
	 * @see com.nextbreakpoint.nextfractal.core.media.Renderer#build(com.nextbreakpoint.nextfractal.core.media.Controller, com.nextbreakpoint.nextfractal.core.media.Movie, com.nextbreakpoint.nextfractal.core.media.Layer, com.nextbreakpoint.nextfractal.core.media.Sequence)
	 */
	public void build(Controller controller, Movie parent, Layer layer, Sequence sequence) {
		renderer.build(controller, parent, layer, sequence);
	}

	@Override
	public void finalize() throws Throwable {
		renderer = null;
		super.finalize();
	}

	public void applyEffect(Effect effect) {
		renderer.applyEffect(effect);
	}

	public void drawImage(Graphics2D g2d) {
		renderer.drawImage(g2d);
	}

	public int getWidth() {
		return renderer.getWidth();
	}

	public int getHeight() {
		return renderer.getHeight();
	}

	public void update() {
	}
}
