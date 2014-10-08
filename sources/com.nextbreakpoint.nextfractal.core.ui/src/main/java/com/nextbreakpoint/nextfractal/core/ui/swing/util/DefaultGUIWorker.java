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
package com.nextbreakpoint.nextfractal.core.ui.swing.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingWorker;

import com.nextbreakpoint.nextfractal.core.util.ProgressListener;

/**
 * @author Andrea Medeghini
 */
public abstract class DefaultGUIWorker extends SwingWorker<Object, String> implements GUIWorker {
	protected List<ProgressListener> progressListenerList = new ArrayList<ProgressListener>();

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIWorker#addProgressListener(com.nextbreakpoint.nextfractal.core.util.ProgressListener)
	 */
	@Override
	public void addProgressListener(final ProgressListener progressListener) {
		progressListenerList.add(progressListener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIWorker#removeProgressListener(com.nextbreakpoint.nextfractal.core.util.ProgressListener)
	 */
	@Override
	public void removeProgressListener(final ProgressListener progressListener) {
		progressListenerList.remove(progressListener);
	}

	/**
	 * @see javax.swing.SwingWorker#process(java.util.List)
	 */
	@Override
	protected void process(final List<String> messages) {
		List<ProgressListener> tmpProgressListenerList = new LinkedList<ProgressListener>(progressListenerList);
		for (ProgressListener progressListener : tmpProgressListenerList) {
			for (String message : messages) {
				progressListener.stateChanged(message);
			}
		}
	}

	/**
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	public void done() {
		List<ProgressListener> tmpProgressListenerList = new LinkedList<ProgressListener>(progressListenerList);
		for (ProgressListener progressListener : tmpProgressListenerList) {
			progressListener.done();
		}
	}

	/**
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	public abstract Object doInBackground() throws Exception;
}
