/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementListNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class PathAdjustmentElementListNodeActionXMLImporterRuntime extends AbstractConfigElementListNodeActionXMLImporterRuntime<PathAdjustmentConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementListNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected PathAdjustmentConfigElementXMLImporter createImporter() {
		return new PathAdjustmentConfigElementXMLImporter();
	}
}
