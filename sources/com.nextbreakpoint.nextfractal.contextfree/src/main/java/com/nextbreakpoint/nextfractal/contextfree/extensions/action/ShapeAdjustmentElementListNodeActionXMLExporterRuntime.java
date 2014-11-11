/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementListNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class ShapeAdjustmentElementListNodeActionXMLExporterRuntime extends AbstractConfigElementListNodeActionXMLExporterRuntime<ShapeAdjustmentConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementListNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected ShapeAdjustmentConfigElementXMLExporter createExporter() {
		return new ShapeAdjustmentConfigElementXMLExporter();
	}
}
