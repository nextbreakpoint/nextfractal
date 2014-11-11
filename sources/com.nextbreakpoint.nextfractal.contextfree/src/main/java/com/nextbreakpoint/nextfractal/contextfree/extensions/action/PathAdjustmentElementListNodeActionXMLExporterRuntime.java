/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementListNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class PathAdjustmentElementListNodeActionXMLExporterRuntime extends AbstractConfigElementListNodeActionXMLExporterRuntime<PathAdjustmentConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementListNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected PathAdjustmentConfigElementXMLExporter createExporter() {
		return new PathAdjustmentConfigElementXMLExporter();
	}
}
