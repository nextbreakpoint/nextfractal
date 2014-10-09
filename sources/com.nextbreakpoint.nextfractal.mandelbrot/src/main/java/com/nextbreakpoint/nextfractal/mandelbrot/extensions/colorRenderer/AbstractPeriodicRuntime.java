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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.colorRenderer;

import com.nextbreakpoint.nextfractal.mandelbrot.colorRendererFormula.ColorRendererFormulaRuntimeElement;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractPeriodicRuntime<T extends AbstractPeriodicConfig> extends AbstractColorRendererRuntime<T> {
	private static final double CONSTANT_2PI = 2d * Math.PI;
	protected ColorRendererFormulaRuntimeElement formulaElement;
	protected boolean absoluteEnabled;
	protected boolean timeEnabled;
	protected double amplitude;
	protected double frequency;
	protected double scale;
	protected double a;
	protected double w;
	protected double t;

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.colorRenderer.ColorRendererExtensionRuntime#prepareForRendering()
	 */
	@Override
	public void prepareForRendering() {
		final T config = getConfig();
		scale = config.getScale().doubleValue();
		frequency = config.getFrequency().doubleValue();
		amplitude = config.getAmplitude().doubleValue();
		absoluteEnabled = config.getAbsoluteEnabled();
		timeEnabled = config.getTimeEnabled();
		w = (AbstractPeriodicRuntime.CONSTANT_2PI * frequency) / scale;
		a = amplitude / 100d;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime#isChanged()
	 */
	@Override
	public boolean isChanged() {
		final boolean formulaChanged = (formulaElement != null) && formulaElement.isChanged();
		return super.isChanged() || formulaChanged;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		super.configReloaded();
		formulaElement = new ColorRendererFormulaRuntimeElement(getConfig().getColorRendererFormulaElement());
	}
}
