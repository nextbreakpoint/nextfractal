/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.common.StrokeCapElement;
import com.nextbreakpoint.nextfractal.contextfree.common.StrokeCapElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class StrokeCapElementNodeActionXMLExporterRuntime extends AbstractConfigElementNodeActionXMLExporterRuntime<StrokeCapElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected StrokeCapElementXMLExporter createExporter() {
		return new StrokeCapElementXMLExporter();
	}
}
