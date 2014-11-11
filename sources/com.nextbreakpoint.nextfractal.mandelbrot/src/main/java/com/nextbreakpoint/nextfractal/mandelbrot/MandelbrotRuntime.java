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
package com.nextbreakpoint.nextfractal.mandelbrot;

import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener;
import com.nextbreakpoint.nextfractal.core.runtime.RuntimeElement;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.mandelbrot.fractal.MandelbrotFractalRuntimeElement;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotRuntime extends RuntimeElement {
	private MandelbrotConfig config;
	private MandelbrotFractalRuntimeElement fractalElement;
	private FractalElementListener fractalListener;
	private ViewElementListener viewListener;
	private ElementListener elementListener;
	private boolean viewChanged;

	/**
	 * @param config
	 */
	public MandelbrotRuntime(final MandelbrotConfig config) {
		this.config = config;
		fractalElement = new MandelbrotFractalRuntimeElement(config.getMandelbrotFractal());
		fractalListener = new FractalElementListener();
		config.getFractalSingleElement().addChangeListener(fractalListener);
		viewListener = new ViewElementListener();
		config.getViewElement().addChangeListener(viewListener);
		elementListener = new ElementListener();
		config.getImageModeElement().addChangeListener(elementListener);
		config.getConstantElement().addChangeListener(elementListener);
		config.getShowOrbitElement().addChangeListener(elementListener);
		config.getShowOrbitTrapElement().addChangeListener(elementListener);
		config.getShowPreviewElement().addChangeListener(elementListener);
		config.getRectangleElement().addChangeListener(elementListener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if ((config != null) && (elementListener != null)) {
			config.getRectangleElement().removeChangeListener(elementListener);
		}
		if ((config != null) && (elementListener != null)) {
			config.getShowPreviewElement().removeChangeListener(elementListener);
		}
		if ((config != null) && (elementListener != null)) {
			config.getShowOrbitElement().removeChangeListener(elementListener);
		}
		if ((config != null) && (elementListener != null)) {
			config.getShowOrbitTrapElement().removeChangeListener(elementListener);
		}
		if ((config != null) && (elementListener != null)) {
			config.getConstantElement().removeChangeListener(elementListener);
		}
		if ((config != null) && (elementListener != null)) {
			config.getImageModeElement().removeChangeListener(elementListener);
		}
		elementListener = null;
		if ((config != null) && (viewListener != null)) {
			config.getViewElement().removeChangeListener(viewListener);
		}
		viewListener = null;
		if ((config != null) && (fractalListener != null)) {
			config.getFractalSingleElement().removeChangeListener(fractalListener);
		}
		fractalListener = null;
		if (fractalElement != null) {
			fractalElement.dispose();
			fractalElement = null;
		}
		config = null;
		super.dispose();
	}

	/**
	 * Returns the fractal.
	 * 
	 * @return the fractal.
	 */
	public MandelbrotFractalRuntimeElement getMandelbrotFractal() {
		return fractalElement;
	}

	private void setMandelbrotFractal(final MandelbrotFractalRuntimeElement fractalElement) {
		if (this.fractalElement != null) {
			this.fractalElement.dispose();
		}
		this.fractalElement = fractalElement;
	}

	/**
	 * @return
	 */
	public boolean isMandelbrotMode() {
		return config.isMandelbrotMode();
	}

	/**
	 * @return
	 */
	public DoubleVector2D getConstant() {
		return config.getConstant();
	}

	/**
	 * @return
	 */
	public boolean getViewChanged() {
		return viewChanged;
	}

	/**
	 * @return
	 */
	public boolean isViewChanged() {
		final boolean fractalChanged = viewChanged;
		viewChanged = false;
		return fractalChanged;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		final boolean fractalChanged = (fractalElement != null) && fractalElement.isChanged();
		return super.isChanged() || fractalChanged;
	}

	private class FractalElementListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setMandelbrotFractal(new MandelbrotFractalRuntimeElement(config.getMandelbrotFractal()));
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class ViewElementListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					viewChanged = true;
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class ElementListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
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
