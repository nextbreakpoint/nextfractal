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

import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.fractal.MandelbrotFractalConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.OrbitTrapConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.ProcessingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.RenderingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.TransformingFormulaConfigElement;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotConfigBuilder {
	public static final String DEFAULT_RENDERING_FORMULA_EXTENSION_ID = "twister.mandelbrot.fractal.rendering.formula.z2";
	public static final String DEFAULT_TRANSFORMING_FORMULA_EXTENSION_ID = "twister.mandelbrot.fractal.transforming.formula.z";
	public static final String DEFAULT_INCOLOURING_FORMULA_EXTENSION_ID = "twister.mandelbrot.fractal.incolouring.formula.color";
	public static final String DEFAULT_OUTCOLOURING_FORMULA_EXTENSION_ID = "twister.mandelbrot.fractal.outcolouring.formula.continuousPotential";

	/**
	 * Constructs a new builder.
	 * 
	 * @param config the config.
	 */
	public MandelbrotConfigBuilder(final MandelbrotImageConfig config) {
	}

	/**
	 * @param context
	 * @return
	 * @throws ExtensionNotFoundException
	 * @throws ExtensionException
	 */
	public MandelbrotConfig createDefaultConfig() throws ExtensionNotFoundException, ExtensionException {
		final MandelbrotConfig config = new MandelbrotConfig();
		final MandelbrotFractalConfigElement fractalElement = new MandelbrotFractalConfigElement();
		final RenderingFormulaConfigElement renderingFormulaElement = new RenderingFormulaConfigElement();
		final TransformingFormulaConfigElement transformingFormulaElement = new TransformingFormulaConfigElement();
		final ProcessingFormulaConfigElement processingFormulaElement = new ProcessingFormulaConfigElement();
		final IncolouringFormulaConfigElement incolouringFormulaElement = new IncolouringFormulaConfigElement();
		final OutcolouringFormulaConfigElement outcolouringFormulaElement = new OutcolouringFormulaConfigElement();
		final OrbitTrapConfigElement orbitTrapElement = new OrbitTrapConfigElement();
		config.setMandelbrotFractal(fractalElement);
		renderingFormulaElement.setReference(MandelbrotRegistry.getInstance().getRenderingFormulaExtension(MandelbrotConfigBuilder.DEFAULT_RENDERING_FORMULA_EXTENSION_ID).createConfigurableExtensionReference());
		fractalElement.setRenderingFormulaConfigElement(renderingFormulaElement);
		transformingFormulaElement.setReference(MandelbrotRegistry.getInstance().getTransformingFormulaExtension(MandelbrotConfigBuilder.DEFAULT_TRANSFORMING_FORMULA_EXTENSION_ID).createConfigurableExtensionReference());
		fractalElement.setTransformingFormulaConfigElement(transformingFormulaElement);
		fractalElement.setProcessingFormulaConfigElement(processingFormulaElement);
		fractalElement.setOrbitTrapConfigElement(orbitTrapElement);
		incolouringFormulaElement.setReference(MandelbrotRegistry.getInstance().getIncolouringFormulaExtension(MandelbrotConfigBuilder.DEFAULT_INCOLOURING_FORMULA_EXTENSION_ID).createConfigurableExtensionReference());
		fractalElement.appendIncolouringFormulaConfigElement(incolouringFormulaElement);
		outcolouringFormulaElement.setReference(MandelbrotRegistry.getInstance().getOutcolouringFormulaExtension(MandelbrotConfigBuilder.DEFAULT_OUTCOLOURING_FORMULA_EXTENSION_ID).createConfigurableExtensionReference());
		fractalElement.appendOutcolouringFormulaConfigElement(outcolouringFormulaElement);
		return config;
	}
}
