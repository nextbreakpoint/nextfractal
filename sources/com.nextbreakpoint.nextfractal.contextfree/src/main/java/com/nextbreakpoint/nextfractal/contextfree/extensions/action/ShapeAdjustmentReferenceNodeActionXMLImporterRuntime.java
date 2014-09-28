/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.extension.ShapeAdjustmentExtensionConfig;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigurableExtensionReferenceElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class ShapeAdjustmentReferenceNodeActionXMLImporterRuntime extends AbstractConfigurableExtensionReferenceElementNodeActionXMLImporterRuntime<ShapeAdjustmentExtensionConfig> {
	/**
	 * 
	 */
	public ShapeAdjustmentReferenceNodeActionXMLImporterRuntime() {
		super(ContextFreeRegistry.getInstance().getShapeAdjustmentRegistry());
	}
}
