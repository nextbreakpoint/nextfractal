/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.elements.StrokeWidthElement;
import com.nextbreakpoint.nextfractal.contextfree.elements.StrokeWidthElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class StrokeWidthElementNodeActionXMLImporterRuntime extends AbstractConfigElementNodeActionXMLImporterRuntime<StrokeWidthElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected StrokeWidthElementXMLImporter createImporter() {
		return new StrokeWidthElementXMLImporter();
	}
}
