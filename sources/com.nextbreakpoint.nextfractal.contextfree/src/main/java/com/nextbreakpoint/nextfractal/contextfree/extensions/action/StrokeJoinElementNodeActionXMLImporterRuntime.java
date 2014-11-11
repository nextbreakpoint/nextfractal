/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.elements.StrokeJoinElement;
import com.nextbreakpoint.nextfractal.contextfree.elements.StrokeJoinElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class StrokeJoinElementNodeActionXMLImporterRuntime extends AbstractConfigElementNodeActionXMLImporterRuntime<StrokeJoinElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected StrokeJoinElementXMLImporter createImporter() {
		return new StrokeJoinElementXMLImporter();
	}
}
