/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class FigureElementListNodeActionXMLImporterRuntime extends AbstractConfigElementListNodeActionXMLImporterRuntime<FigureConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected FigureConfigElementXMLImporter createImporter() {
		return new FigureConfigElementXMLImporter();
	}
}
