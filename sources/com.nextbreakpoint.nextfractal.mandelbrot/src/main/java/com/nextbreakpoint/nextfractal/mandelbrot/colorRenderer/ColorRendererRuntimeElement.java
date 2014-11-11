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
package com.nextbreakpoint.nextfractal.mandelbrot.colorRenderer;

import com.nextbreakpoint.nextfractal.core.elements.ExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener;
import com.nextbreakpoint.nextfractal.core.runtime.RuntimeElement;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.colorRenderer.ColorRendererExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.colorRenderer.ColorRendererExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class ColorRendererRuntimeElement extends RuntimeElement {
	private ColorRendererExtensionRuntime<?> rendererRuntime;
	private ColorRendererConfigElement rendererElement;
	private ExtensionListener extensionListener;

	/**
	 * Constructs a new renderer element.
	 * 
	 * @param registry
	 * @param rendererElement
	 */
	public ColorRendererRuntimeElement(final ColorRendererConfigElement rendererElement) {
		if (rendererElement == null) {
			throw new IllegalArgumentException("rendererElement is null");
		}
		this.rendererElement = rendererElement;
		createRuntime(rendererElement.getReference());
		extensionListener = new ExtensionListener();
		rendererElement.getExtensionElement().addChangeListener(extensionListener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if ((rendererElement != null) && (extensionListener != null)) {
			rendererElement.getExtensionElement().removeChangeListener(extensionListener);
		}
		extensionListener = null;
		if (rendererRuntime != null) {
			rendererRuntime.dispose();
			rendererRuntime = null;
		}
		rendererElement = null;
		super.dispose();
	}

	@SuppressWarnings("unchecked")
	private void createRuntime(final ConfigurableExtensionReference<ColorRendererExtensionConfig> reference) {
		try {
			if (reference != null) {
				final ColorRendererExtensionRuntime rendererRuntime = MandelbrotRegistry.getInstance().getColorRendererExtension(reference.getExtensionId()).createExtensionRuntime();
				final ColorRendererExtensionConfig rendererConfig = reference.getExtensionConfig();
				rendererRuntime.setConfig(rendererConfig);
				setRendererRuntime(rendererRuntime);
			}
			else {
				setRendererRuntime(null);
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
	 * @return the rendererRuntime
	 */
	public ColorRendererExtensionRuntime<?> getRendererRuntime() {
		return rendererRuntime;
	}

	private void setRendererRuntime(final ColorRendererExtensionRuntime<?> rendererRuntime) {
		if (this.rendererRuntime != null) {
			this.rendererRuntime.dispose();
		}
		this.rendererRuntime = rendererRuntime;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		final boolean rendererChanged = (rendererRuntime != null) && rendererRuntime.isChanged();
		return super.isChanged() || rendererChanged;
	}

	private class ExtensionListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		@SuppressWarnings("unchecked")
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
					createRuntime((ConfigurableExtensionReference<ColorRendererExtensionConfig>) e.getParams()[0]);
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
