/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class PathReplacementElementNodeActionXMLExporterRuntime extends AbstractConfigElementNodeActionXMLExporterRuntime<PathReplacementConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected PathReplacementConfigElementXMLExporter createExporter() {
		return new PathReplacementConfigElementXMLExporter();
	}
}
