/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.extension.PathAdjustmentExtensionConfig;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigurableExtensionReferenceElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class PathAdjustmentReferenceNodeActionXMLImporterRuntime extends AbstractConfigurableExtensionReferenceElementNodeActionXMLImporterRuntime<PathAdjustmentExtensionConfig> {
	/**
	 * 
	 */
	public PathAdjustmentReferenceNodeActionXMLImporterRuntime() {
		super(ContextFreeRegistry.getInstance().getPathAdjustmentRegistry());
	}
}
