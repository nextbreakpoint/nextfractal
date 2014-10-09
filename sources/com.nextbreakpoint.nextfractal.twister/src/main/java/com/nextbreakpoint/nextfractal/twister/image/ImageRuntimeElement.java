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
package com.nextbreakpoint.nextfractal.twister.image;

import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.config.RuntimeElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.twister.TwisterRegistry;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class ImageRuntimeElement extends RuntimeElement {
	private ImageExtensionRuntime<?> imageRuntime;
	private ImageConfigElement imageElement;
	private ExtensionListener extensionListener;

	/**
	 * Constructs a new image.
	 * 
	 * @param imageElement
	 */
	public ImageRuntimeElement(final ImageConfigElement imageElement) {
		if (imageElement == null) {
			throw new IllegalArgumentException("imageElement is null");
		}
		this.imageElement = imageElement;
		createRuntime(imageElement.getReference());
		extensionListener = new ExtensionListener();
		imageElement.getExtensionElement().addChangeListener(extensionListener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if ((imageElement != null) && (extensionListener != null)) {
			imageElement.getExtensionElement().removeChangeListener(extensionListener);
		}
		extensionListener = null;
		if (imageRuntime != null) {
			imageRuntime.dispose();
			imageRuntime = null;
		}
		imageElement = null;
		super.dispose();
	}

	@SuppressWarnings("unchecked")
	private void createRuntime(final ConfigurableExtensionReference<ImageExtensionConfig> reference) {
		try {
			if (reference != null) {
				final ImageExtensionRuntime imageRuntime = TwisterRegistry.getInstance().getImageExtension(reference.getExtensionId()).createExtensionRuntime();
				final ImageExtensionConfig imageConfig = reference.getExtensionConfig();
				imageRuntime.setConfig(imageConfig);
				setImageRuntime(imageRuntime);
			}
			else {
				setImageRuntime(null);
			}
		}
		catch (final ExtensionNotFoundException e) {
			e.printStackTrace();
		}
		catch (final ExtensionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		final boolean imageChanged = (imageRuntime != null) && imageRuntime.isChanged();
		return super.isChanged() || imageChanged;
	}

	/**
	 * @return the imageRuntime
	 */
	public ImageExtensionRuntime<?> getImageRuntime() {
		return imageRuntime;
	}

	private void setImageRuntime(final ImageExtensionRuntime<?> imageRuntime) {
		if (this.imageRuntime != null) {
			this.imageRuntime.dispose();
		}
		this.imageRuntime = imageRuntime;
	}

	private class ExtensionListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		@SuppressWarnings("unchecked")
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
					createRuntime((ConfigurableExtensionReference<ImageExtensionConfig>) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
}
