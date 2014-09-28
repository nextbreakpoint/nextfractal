/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.common.FillRuleElement;
import com.nextbreakpoint.nextfractal.contextfree.common.FillRuleElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLExporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class FillRuleElementNodeActionXMLExporterRuntime extends AbstractConfigElementNodeActionXMLExporterRuntime<FillRuleElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected FillRuleElementXMLExporter createExporter() {
		return new FillRuleElementXMLExporter();
	}
}
