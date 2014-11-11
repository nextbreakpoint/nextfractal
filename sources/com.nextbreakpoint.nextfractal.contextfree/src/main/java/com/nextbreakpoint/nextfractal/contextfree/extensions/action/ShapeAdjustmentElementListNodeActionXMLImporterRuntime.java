/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementListNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class ShapeAdjustmentElementListNodeActionXMLImporterRuntime extends AbstractConfigElementListNodeActionXMLImporterRuntime<ShapeAdjustmentConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementListNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected ShapeAdjustmentConfigElementXMLImporter createImporter() {
		return new ShapeAdjustmentConfigElementXMLImporter();
	}
}
