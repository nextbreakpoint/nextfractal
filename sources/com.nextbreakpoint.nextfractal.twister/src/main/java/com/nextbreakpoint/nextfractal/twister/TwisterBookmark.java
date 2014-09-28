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
package com.nextbreakpoint.nextfractal.twister;

import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;

/**
 * @author Andrea Medeghini
 */
public class TwisterBookmark {
	private TwisterConfig config;
	private TwisterRenderer renderer;

	/**
	 *
	 */
	public TwisterBookmark() {
	}

	/**
	 * @return the config
	 */
	public TwisterConfig getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(final TwisterConfig config) {
		this.config = config;
	}

	/**
	 * @return the renderer
	 */
	public TwisterRenderer getRenderer() {
		return renderer;
	}

	/**
	 * @param renderer the renderer to set
	 */
	public void setRenderer(final TwisterRenderer renderer) {
		this.renderer = renderer;
	}
}
