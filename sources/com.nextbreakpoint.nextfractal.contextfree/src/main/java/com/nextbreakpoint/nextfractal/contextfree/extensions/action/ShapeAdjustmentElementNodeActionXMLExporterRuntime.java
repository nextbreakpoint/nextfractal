/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class ShapeAdjustmentElementNodeActionXMLExporterRuntime extends AbstractConfigElementNodeActionXMLExporterRuntime<ShapeAdjustmentConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected ShapeAdjustmentConfigElementXMLExporter createExporter() {
		return new ShapeAdjustmentConfigElementXMLExporter();
	}
}
