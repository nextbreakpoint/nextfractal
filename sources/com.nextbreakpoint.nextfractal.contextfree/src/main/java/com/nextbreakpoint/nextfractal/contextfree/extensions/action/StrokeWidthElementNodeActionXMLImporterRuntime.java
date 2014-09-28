/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.common.StrokeWidthElement;
import com.nextbreakpoint.nextfractal.contextfree.common.StrokeWidthElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class StrokeWidthElementNodeActionXMLImporterRuntime extends AbstractConfigElementNodeActionXMLImporterRuntime<StrokeWidthElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected StrokeWidthElementXMLImporter createImporter() {
		return new StrokeWidthElementXMLImporter();
	}
}
