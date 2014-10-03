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
package com.nextbreakpoint.nextfractal.twister;

import com.nextbreakpoint.nextfractal.core.util.RenderContext;

public interface TwisterController {
	/**
	 * @param context
	 */
	public void setRenderContext(RenderContext context);

	/**
	 * @param listener
	 */
	public abstract void addControllerListener(ControllerListener listener);

	/**
	 * @param listener
	 */
	public abstract void removeControllerListener(ControllerListener listener);

	/**
	 * @param sameTimestamp
	 * @return
	 */
	public abstract boolean redoAction(boolean sameTimestamp);

	/**
	 * @param sameTimestamp
	 * @return
	 */
	public abstract boolean undoAction(boolean sameTimestamp);

	/**
	 * @param timestamp
	 * @return
	 */
	public abstract boolean redoAction(long timestamp, boolean relative);

	/**
	 * @param timestamp
	 * @return
	 */
	public abstract boolean undoAction(long timestamp, boolean relative);

	/**
	 * @return
	 */
	public abstract boolean redoActionAndSleep();

	/**
	 * @return
	 */
	public abstract boolean undoActionAndSleep();

	/**
	 * @return the stopTime
	 */
	public abstract long getDuration();

	/**
	 * @return
	 */
	public abstract long getTime();

	/**
	 * 
	 */
	public abstract boolean undoAll();

	/**
	 * 
	 */
	public abstract boolean redoAll();

	/**
	 * 
	 */
	public void init();
}
