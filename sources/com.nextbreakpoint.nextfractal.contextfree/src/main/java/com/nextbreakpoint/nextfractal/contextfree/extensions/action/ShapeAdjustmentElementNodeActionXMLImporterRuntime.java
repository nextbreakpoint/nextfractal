/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class ShapeAdjustmentElementNodeActionXMLImporterRuntime extends AbstractConfigElementNodeActionXMLImporterRuntime<ShapeAdjustmentConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected ShapeAdjustmentConfigElementXMLImporter createImporter() {
		return new ShapeAdjustmentConfigElementXMLImporter();
	}
}
