/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class ShapeReplacementElementListNodeActionXMLExporterRuntime extends AbstractConfigElementListNodeActionXMLExporterRuntime<ShapeReplacementConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected ShapeReplacementConfigElementXMLExporter createExporter() {
		return new ShapeReplacementConfigElementXMLExporter();
	}
}
