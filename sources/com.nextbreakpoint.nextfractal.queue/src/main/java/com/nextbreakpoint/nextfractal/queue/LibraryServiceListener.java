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
package com.nextbreakpoint.nextfractal.queue;

import com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow;
import com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow;
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataRow;

/**
 * @author Andrea Medeghini
 */
public interface LibraryServiceListener {
	/**
	 * @param clip
	 */
	public void clipCreated(RenderClipDataRow clip);

	/**
	 * @param clip
	 */
	public void clipDeleted(RenderClipDataRow clip);

	/**
	 * @param clip
	 */
	public void clipUpdated(RenderClipDataRow clip);

	/**
	 * @param clip
	 */
	public void clipLoaded(RenderClipDataRow clip);

	/**
	 * @param profile
	 */
	public void profileCreated(RenderProfileDataRow profile);

	/**
	 * @param profile
	 */
	public void profileDeleted(RenderProfileDataRow profile);

	/**
	 * @param profile
	 */
	public void profileUpdated(RenderProfileDataRow profile);

	/**
	 * @param profile
	 */
	public void profileLoaded(RenderProfileDataRow profile);

	/**
	 * @param job
	 */
	public void jobCreated(RenderJobDataRow job);

	/**
	 * @param job
	 */
	public void jobDeleted(RenderJobDataRow job);

	/**
	 * @param job
	 */
	public void jobUpdated(RenderJobDataRow job);

	/**
	 * @param job
	 */
	public void jobStarted(RenderJobDataRow job);

	/**
	 * @param job
	 */
	public void jobAborted(RenderJobDataRow job);

	/**
	 * @param job
	 */
	public void jobStopped(RenderJobDataRow job);

	/**
	 * @param job
	 */
	public void jobResumed(RenderJobDataRow job);
}
