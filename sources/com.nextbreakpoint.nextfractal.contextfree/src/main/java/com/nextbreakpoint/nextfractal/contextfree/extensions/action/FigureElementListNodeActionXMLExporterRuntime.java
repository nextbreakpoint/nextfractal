/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class FigureElementListNodeActionXMLExporterRuntime extends AbstractConfigElementListNodeActionXMLExporterRuntime<FigureConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected FigureConfigElementXMLExporter createExporter() {
		return new FigureConfigElementXMLExporter();
	}
}
