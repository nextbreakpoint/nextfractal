/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.elements.StrokeCapElement;
import com.nextbreakpoint.nextfractal.contextfree.elements.StrokeCapElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class StrokeCapElementNodeActionXMLImporterRuntime extends AbstractConfigElementNodeActionXMLImporterRuntime<StrokeCapElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected StrokeCapElementXMLImporter createImporter() {
		return new StrokeCapElementXMLImporter();
	}
}
