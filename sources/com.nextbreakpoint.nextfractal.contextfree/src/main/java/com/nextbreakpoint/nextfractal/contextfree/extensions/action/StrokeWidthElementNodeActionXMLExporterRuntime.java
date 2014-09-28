/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.common.StrokeWidthElement;
import com.nextbreakpoint.nextfractal.contextfree.common.StrokeWidthElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class StrokeWidthElementNodeActionXMLExporterRuntime extends AbstractConfigElementNodeActionXMLExporterRuntime<StrokeWidthElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected StrokeWidthElementXMLExporter createExporter() {
		return new StrokeWidthElementXMLExporter();
	}
}
