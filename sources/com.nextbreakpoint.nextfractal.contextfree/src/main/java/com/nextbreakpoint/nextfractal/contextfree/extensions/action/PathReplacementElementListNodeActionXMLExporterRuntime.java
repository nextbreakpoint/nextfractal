/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementListNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class PathReplacementElementListNodeActionXMLExporterRuntime extends AbstractConfigElementListNodeActionXMLExporterRuntime<PathReplacementConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementListNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected PathReplacementConfigElementXMLExporter createExporter() {
		return new PathReplacementConfigElementXMLExporter();
	}
}
