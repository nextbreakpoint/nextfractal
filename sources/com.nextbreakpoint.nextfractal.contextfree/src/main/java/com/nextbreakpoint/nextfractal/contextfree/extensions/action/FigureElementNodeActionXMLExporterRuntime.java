/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class FigureElementNodeActionXMLExporterRuntime extends AbstractConfigElementNodeActionXMLExporterRuntime<FigureConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected FigureConfigElementXMLExporter createExporter() {
		return new FigureConfigElementXMLExporter();
	}
}
