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
public final class RenderClipDataRow implements Serializable {
	private static final long serialVersionUID = 1L;
	private int clipId;
	private int status;
	private final RenderClip clip;

	/**
	 * @param clip
	 */
	public RenderClipDataRow(final RenderClip clip) {
		this.clip = clip;
	}

	/**
	 * @return the id
	 */
	public int getClipId() {
		return clipId;
	}

	/**
	 * @param clipId the id to set
	 */
	public void setClipId(final int clipId) {
		this.clipId = clipId;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(final int status) {
		this.status = status;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("RenderClip [clipId = ");
		builder.append(clipId);
		builder.append(", name = ");
		builder.append(getClip().getClipName());
		builder.append(", description = ");
		builder.append(getClip().getDescription());
		builder.append(", duration = ");
		builder.append(getClip().getDuration());
		builder.append(", status = ");
		builder.append(getStatus());
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		return getClipId() == ((RenderClipDataRow) obj).getClipId();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getClipId();
	}

	/**
	 * @return the clip
	 */
	public RenderClip getClip() {
		return clip;
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.clip.RenderClip#getClipName()
	 */
	public String getClipName() {
		return clip.getClipName();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.clip.RenderClip#getDescription()
	 */
	public String getDescription() {
		return clip.getDescription();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.clip.RenderClip#getDuration()
	 */
	public long getDuration() {
		return clip.getDuration();
	}

	/**
	 * @param clipName
	 * @see com.nextbreakpoint.nextfractal.queue.clip.RenderClip#setClipName(java.lang.String)
	 */
	public void setClipName(final String clipName) {
		clip.setClipName(clipName);
	}

	/**
	 * @param description
	 * @see com.nextbreakpoint.nextfractal.queue.clip.RenderClip#setDescription(java.lang.String)
	 */
	public void setDescription(final String description) {
		clip.setDescription(description);
	}

	/**
	 * @param duration
	 * @see com.nextbreakpoint.nextfractal.queue.clip.RenderClip#setDuration(long)
	 */
	public void setDuration(final long duration) {
		clip.setDuration(duration);
	}
}
