/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.cfdg.CFDGConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.cfdg.CFDGConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class CFDGElementNodeActionXMLExporterRuntime extends AbstractConfigElementNodeActionXMLExporterRuntime<CFDGConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected CFDGConfigElementXMLExporter createExporter() {
		return new CFDGConfigElementXMLExporter();
	}
}
