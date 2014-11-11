/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.figure.FigureExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigurableExtensionReferenceElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class FigureReferenceNodeActionXMLImporterRuntime extends AbstractConfigurableExtensionReferenceElementNodeActionXMLImporterRuntime<FigureExtensionConfig> {
	/**
	 * 
	 */
	public FigureReferenceNodeActionXMLImporterRuntime() {
		super(ContextFreeRegistry.getInstance().getFigureRegistry());
	}
}
