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
package com.nextbreakpoint.nextfractal.twister.swing;

import com.nextbreakpoint.nextfractal.queue.LibraryServiceListener;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow;
import com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow;
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataRow;

/**
 * @author Andrea Medeghini
 */
public class ServiceAdapter implements LibraryServiceListener {
	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#clipCreated(com.nextbreakpoint.nextfractal.service.clip.RenderClipDataRow)
	 */
	@Override
	public void clipCreated(final RenderClipDataRow clip) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#clipDeleted(com.nextbreakpoint.nextfractal.service.clip.RenderClipDataRow)
	 */
	@Override
	public void clipDeleted(final RenderClipDataRow clip) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#clipUpdated(com.nextbreakpoint.nextfractal.service.clip.RenderClipDataRow)
	 */
	@Override
	public void clipUpdated(final RenderClipDataRow clip) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#clipLoaded(com.nextbreakpoint.nextfractal.service.clip.RenderClipDataRow)
	 */
	@Override
	public void clipLoaded(final RenderClipDataRow clip) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#profileCreated(com.nextbreakpoint.nextfractal.service.profile.RenderProfileDataRow)
	 */
	@Override
	public void profileCreated(final RenderProfileDataRow profile) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#profileDeleted(com.nextbreakpoint.nextfractal.service.profile.RenderProfileDataRow)
	 */
	@Override
	public void profileDeleted(final RenderProfileDataRow profile) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#profileUpdated(com.nextbreakpoint.nextfractal.service.profile.RenderProfileDataRow)
	 */
	@Override
	public void profileUpdated(final RenderProfileDataRow profile) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#profileLoaded(com.nextbreakpoint.nextfractal.service.profile.RenderProfileDataRow)
	 */
	@Override
	public void profileLoaded(final RenderProfileDataRow profile) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#jobCreated(com.nextbreakpoint.nextfractal.service.job.RenderJobDataRow)
	 */
	@Override
	public void jobCreated(final RenderJobDataRow job) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#jobDeleted(com.nextbreakpoint.nextfractal.service.job.RenderJobDataRow)
	 */
	@Override
	public void jobDeleted(final RenderJobDataRow job) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#jobStarted(com.nextbreakpoint.nextfractal.service.job.RenderJobDataRow)
	 */
	@Override
	public void jobStarted(final RenderJobDataRow job) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#jobAborted(com.nextbreakpoint.nextfractal.service.job.RenderJobDataRow)
	 */
	@Override
	public void jobAborted(final RenderJobDataRow job) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#jobStopped(com.nextbreakpoint.nextfractal.service.job.RenderJobDataRow)
	 */
	@Override
	public void jobStopped(final RenderJobDataRow job) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#jobUpdated(com.nextbreakpoint.nextfractal.service.job.RenderJobDataRow)
	 */
	@Override
	public void jobUpdated(final RenderJobDataRow job) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.service.LibraryServiceListener#jobResumed(com.nextbreakpoint.nextfractal.service.job.RenderJobDataRow)
	 */
	@Override
	public void jobResumed(final RenderJobDataRow job) {
	}
}
