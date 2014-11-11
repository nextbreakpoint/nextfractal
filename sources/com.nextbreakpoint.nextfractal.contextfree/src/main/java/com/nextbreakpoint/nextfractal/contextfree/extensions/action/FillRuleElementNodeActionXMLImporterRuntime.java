/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.elements.FillRuleElement;
import com.nextbreakpoint.nextfractal.contextfree.elements.FillRuleElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class FillRuleElementNodeActionXMLImporterRuntime extends AbstractConfigElementNodeActionXMLImporterRuntime<FillRuleElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected FillRuleElementXMLImporter createImporter() {
		return new FillRuleElementXMLImporter();
	}
}
