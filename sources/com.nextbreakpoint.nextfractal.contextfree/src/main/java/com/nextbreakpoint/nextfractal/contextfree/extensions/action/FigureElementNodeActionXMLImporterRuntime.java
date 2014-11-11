/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class FigureElementNodeActionXMLImporterRuntime extends AbstractConfigElementNodeActionXMLImporterRuntime<FigureConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected FigureConfigElementXMLImporter createImporter() {
		return new FigureConfigElementXMLImporter();
	}
}
