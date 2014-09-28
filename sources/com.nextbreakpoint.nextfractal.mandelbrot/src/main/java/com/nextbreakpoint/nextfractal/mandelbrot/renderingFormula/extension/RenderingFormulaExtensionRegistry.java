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
package com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension;

import com.nextbreakpoint.nextfractal.core.extension.sl.SLConfigurableExtensionBuilder;
import com.nextbreakpoint.nextfractal.core.extension.sl.SLConfigurableExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class RenderingFormulaExtensionRegistry extends SLConfigurableExtensionRegistry<RenderingFormulaExtensionRuntime<? extends RenderingFormulaExtensionConfig>, RenderingFormulaExtensionConfig> {
	/**
	 * the extension point name.
	 */
	public static final String EXTENSION_POINT_NAME = "com.nextbreakpoint.nextfractal.mandelbrot.extensions";
	/**
	 * the configuration element name.
	 */
	public static final String CONFIGURATION_ELEMENT_NAME = "RenderingFormula";
	/**
	 * the extension descriptor class.
	 */
	public static final Class<? extends RenderingFormulaExtensionDescriptor> EXTENSION_DESCRIPTOR_CLASS = RenderingFormulaExtensionDescriptor.class;

	/**
	 * Constructs a new registry.
	 */
	public RenderingFormulaExtensionRegistry() {
		super(RenderingFormulaExtensionRegistry.EXTENSION_DESCRIPTOR_CLASS, RenderingFormulaExtensionRegistry.EXTENSION_POINT_NAME, new SLConfigurableExtensionBuilder<RenderingFormulaExtensionRuntime<? extends RenderingFormulaExtensionConfig>, RenderingFormulaExtensionConfig>(RenderingFormulaExtensionRegistry.CONFIGURATION_ELEMENT_NAME));
	}
}
