/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.elements.StrokeCapElement;
import com.nextbreakpoint.nextfractal.contextfree.elements.StrokeCapElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class StrokeCapElementNodeActionXMLExporterRuntime extends AbstractConfigElementNodeActionXMLExporterRuntime<StrokeCapElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected StrokeCapElementXMLExporter createExporter() {
		return new StrokeCapElementXMLExporter();
	}
}
