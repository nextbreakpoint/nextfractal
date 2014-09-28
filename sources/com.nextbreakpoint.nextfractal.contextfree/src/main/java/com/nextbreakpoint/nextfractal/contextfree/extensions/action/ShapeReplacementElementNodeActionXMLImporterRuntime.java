/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class ShapeReplacementElementNodeActionXMLImporterRuntime extends AbstractConfigElementNodeActionXMLImporterRuntime<ShapeReplacementConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected ShapeReplacementConfigElementXMLImporter createImporter() {
		return new ShapeReplacementConfigElementXMLImporter();
	}
}
