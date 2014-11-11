/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class PathAdjustmentElementNodeActionXMLExporterRuntime extends AbstractConfigElementNodeActionXMLExporterRuntime<PathAdjustmentConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected PathAdjustmentConfigElementXMLExporter createExporter() {
		return new PathAdjustmentConfigElementXMLExporter();
	}
}
