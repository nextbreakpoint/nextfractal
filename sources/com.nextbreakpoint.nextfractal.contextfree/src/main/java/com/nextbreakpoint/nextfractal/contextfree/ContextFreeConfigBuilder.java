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
package com.nextbreakpoint.nextfractal.contextfree;

import com.nextbreakpoint.nextfractal.contextfree.cfdg.CFDGConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.extensions.figure.RuleFigureConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.image.ContextFreeImageConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeReplacement.SingleShapeReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.figure.extension.FigureExtensionConfig;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.extension.ShapeReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.util.Color32bit;

/**
 * @author Andrea Medeghini
 */
public class ContextFreeConfigBuilder {
	/**
	 * Constructs a new builder.
	 * 
	 * @param config
	 *        the config.
	 */
	public ContextFreeConfigBuilder(final ContextFreeImageConfig config) {
	}

	/**
	 * @param context
	 * @return
	 * @throws ExtensionNotFoundException
	 * @throws ExtensionException
	 */
	public ContextFreeConfig createDefaultConfig() throws ExtensionNotFoundException, ExtensionException {
		final ContextFreeConfig config = new ContextFreeConfig();
		CFDGConfigElement cfdgElement = new CFDGConfigElement();
		cfdgElement.setBackground(new Color32bit(0xFFFFFFFF));
		cfdgElement.setStartshape("square");
		config.setCFDG(cfdgElement);
		FigureConfigElement triangleFigureElement = new FigureConfigElement();
		cfdgElement.appendFigureConfigElement(triangleFigureElement);
		ConfigurableExtensionReference<FigureExtensionConfig> triangleReference = ContextFreeRegistry.getInstance().getFigureExtension("contextfree.figure.triangle").createConfigurableExtensionReference();
		triangleFigureElement.setExtensionReference(triangleReference);
		FigureConfigElement squareFigureElement = new FigureConfigElement();
		cfdgElement.appendFigureConfigElement(squareFigureElement);
		ConfigurableExtensionReference<FigureExtensionConfig> squareReference = ContextFreeRegistry.getInstance().getFigureExtension("contextfree.figure.square").createConfigurableExtensionReference();
		squareFigureElement.setExtensionReference(squareReference);
		FigureConfigElement circleFigureElement = new FigureConfigElement();
		cfdgElement.appendFigureConfigElement(circleFigureElement);
		ConfigurableExtensionReference<FigureExtensionConfig> circleReference = ContextFreeRegistry.getInstance().getFigureExtension("contextfree.figure.circle").createConfigurableExtensionReference();
		circleFigureElement.setExtensionReference(circleReference);
		FigureConfigElement ruleFigureElement = new FigureConfigElement();
		cfdgElement.appendFigureConfigElement(ruleFigureElement);
		ConfigurableExtensionReference<FigureExtensionConfig> ruleReference = ContextFreeRegistry.getInstance().getFigureExtension("contextfree.figure.rule").createConfigurableExtensionReference();
		ruleFigureElement.setExtensionReference(ruleReference);
		ShapeReplacementConfigElement replacementElement = new ShapeReplacementConfigElement();
		ConfigurableExtensionReference<ShapeReplacementExtensionConfig> replacementReference = ContextFreeRegistry.getInstance().getShapeReplacementExtension("contextfree.shape.replacement.single").createConfigurableExtensionReference();
		replacementElement.setExtensionReference(replacementReference);
		((RuleFigureConfig) ruleReference.getExtensionConfig()).setName("square");
		((RuleFigureConfig) ruleReference.getExtensionConfig()).appendShapeReplacementConfigElement(replacementElement);
		((SingleShapeReplacementConfig) replacementReference.getExtensionConfig()).setShape("SQUARE");
		return config;
	}
}
