/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.elements.FillRuleElement;
import com.nextbreakpoint.nextfractal.contextfree.elements.FillRuleElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class FillRuleElementNodeActionXMLExporterRuntime extends AbstractConfigElementNodeActionXMLExporterRuntime<FillRuleElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected FillRuleElementXMLExporter createExporter() {
		return new FillRuleElementXMLExporter();
	}
}
