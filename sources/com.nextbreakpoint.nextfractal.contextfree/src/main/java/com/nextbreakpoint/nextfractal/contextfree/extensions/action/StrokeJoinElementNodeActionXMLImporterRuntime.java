/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.common.StrokeJoinElement;
import com.nextbreakpoint.nextfractal.contextfree.common.StrokeJoinElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class StrokeJoinElementNodeActionXMLImporterRuntime extends AbstractConfigElementNodeActionXMLImporterRuntime<StrokeJoinElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected StrokeJoinElementXMLImporter createImporter() {
		return new StrokeJoinElementXMLImporter();
	}
}
