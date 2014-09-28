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
package com.nextbreakpoint.nextfractal.queue.encoder.extension;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.util.ProgressListener;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractEncoderExtensionRuntime<T extends EncoderExtensionConfig> extends EncoderExtensionRuntime<T> {
	private final List<ProgressListener> listeners = new ArrayList<ProgressListener>();

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.encoder.extension.EncoderExtensionRuntime#addProgressListener(com.nextbreakpoint.nextfractal.queue.ProgressListener)
	 */
	@Override
	public void addProgressListener(final ProgressListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.encoder.extension.EncoderExtensionRuntime#removeProgressListener(com.nextbreakpoint.nextfractal.queue.ProgressListener)
	 */
	@Override
	public void removeProgressListener(final ProgressListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * @param percent
	 */
	protected void fireStateChanged(final int percent) {
		for (final ProgressListener listener : listeners) {
			listener.stateChanged("Encoding...", percent);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionRuntime#dispose()
	 */
	@Override
	public void dispose() {
	}
}
