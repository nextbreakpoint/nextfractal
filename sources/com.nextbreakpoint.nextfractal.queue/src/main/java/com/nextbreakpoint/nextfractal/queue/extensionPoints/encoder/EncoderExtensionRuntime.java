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
package com.nextbreakpoint.nextfractal.queue.extensionPoints.encoder;

import java.io.File;

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.util.ProgressListener;
import com.nextbreakpoint.nextfractal.queue.encoder.EncoderContext;
import com.nextbreakpoint.nextfractal.queue.encoder.EncoderException;
import com.nextbreakpoint.nextfractal.queue.encoder.EncoderHook;

/**
 * @author Andrea Medeghini
 */
public abstract class EncoderExtensionRuntime<T extends EncoderExtensionConfig> extends ConfigurableExtensionRuntime<T> {
	/**
	 * @param context
	 * @param path
	 * @throws EncoderException
	 */
	public abstract void encode(EncoderContext context, File path) throws EncoderException;

	/**
	 * @param listener
	 */
	public abstract void addProgressListener(ProgressListener listener);

	/**
	 * @param listener
	 */
	public abstract void removeProgressListener(ProgressListener listener);

	/**
	 * @return
	 */
	public abstract boolean isAlphaSupported();

	/**
	 * @return
	 */
	public abstract boolean isImageSupported();

	/**
	 * @return
	 */
	public abstract boolean isMovieSupported();

	/**
	 * @return
	 */
	public abstract String getImageSuffix();

	/**
	 * @return
	 */
	public abstract String getMovieSuffix();

	/**
	 * @param hook
	 */
	public abstract void setInterruptHook(EncoderHook hook);
}
