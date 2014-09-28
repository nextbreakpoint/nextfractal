/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.common.FillRuleElement;
import com.nextbreakpoint.nextfractal.contextfree.common.FillRuleElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class FillRuleElementNodeActionXMLImporterRuntime extends AbstractConfigElementNodeActionXMLImporterRuntime<FillRuleElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected FillRuleElementXMLImporter createImporter() {
		return new FillRuleElementXMLImporter();
	}
}
