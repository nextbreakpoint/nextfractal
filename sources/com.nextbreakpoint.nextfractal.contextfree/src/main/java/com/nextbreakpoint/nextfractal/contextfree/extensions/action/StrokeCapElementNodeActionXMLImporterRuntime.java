/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.common.StrokeCapElement;
import com.nextbreakpoint.nextfractal.contextfree.common.StrokeCapElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class StrokeCapElementNodeActionXMLImporterRuntime extends AbstractConfigElementNodeActionXMLImporterRuntime<StrokeCapElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected StrokeCapElementXMLImporter createImporter() {
		return new StrokeCapElementXMLImporter();
	}
}
