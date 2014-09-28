/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.common.StrokeJoinElement;
import com.nextbreakpoint.nextfractal.contextfree.common.StrokeJoinElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class StrokeJoinElementNodeActionXMLExporterRuntime extends AbstractConfigElementNodeActionXMLExporterRuntime<StrokeJoinElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected StrokeJoinElementXMLExporter createExporter() {
		return new StrokeJoinElementXMLExporter();
	}
}
