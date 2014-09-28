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
package com.nextbreakpoint.nextfractal.queue.clip;

import java.io.Serializable;

/**
 * @author Andrea Medeghini
 */
public class RenderClip implements Serializable {
	private static final long serialVersionUID = 1L;
	private String clipName;
	private String description;
	private long duration;

	/**
	 * 
	 */
	public RenderClip() {
	}

	/**
	 * @param clipName
	 */
	public RenderClip(final String clipName) {
		this.clipName = clipName;
	}

	/**
	 * @return the name
	 */
	public String getClipName() {
		return clipName;
	}

	/**
	 * @param clipName the name to set
	 */
	public void setClipName(final String clipName) {
		this.clipName = clipName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(final long duration) {
		this.duration = duration;
	}
}
