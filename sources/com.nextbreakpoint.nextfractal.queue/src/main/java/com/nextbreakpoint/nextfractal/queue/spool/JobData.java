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
package com.nextbreakpoint.nextfractal.queue.spool;

import java.io.Serializable;

/**
 * @author Andrea Medeghini
 */
public interface JobData extends Serializable {
	public static final int PROCESS_JOB = 0;
	public static final int COPY_PROCESS_JOB = 1;
	public static final int POST_PROCESS_JOB = 2;

	/**
	 * @return the id
	 */
	public int getJobId();

	/**
	 * @return the profileId
	 */
	public int getProfileId();

	/**
	 * @return the clipId
	 */
	public int getClipId();

	/**
	 * @return
	 */
	public int getBorderHeight();

	/**
	 * @return
	 */
	public int getBorderWidth();

	/**
	 * @return
	 */
	public int getFrameNumber();

	/**
	 * @return
	 */
	public int getFrameRate();

	/**
	 * @return
	 */
	public int getImageHeight();

	/**
	 * @return
	 */
	public int getImageWidth();

	/**
	 * @return
	 */
	public int getQuality();

	/**
	 * @return
	 */
	public int getStartTime();

	/**
	 * @return
	 */
	public int getStopTime();

	/**
	 * @return
	 */
	public int getTileHeight();

	/**
	 * @return
	 */
	public int getTileOffsetX();

	/**
	 * @return
	 */
	public int getTileOffsetY();

	/**
	 * @return
	 */
	public int getTileWidth();

	/**
	 * @param frame
	 */
	public void setFrameNumber(final int frame);

	/**
	 * @return
	 */
	public int getJobType();
}
