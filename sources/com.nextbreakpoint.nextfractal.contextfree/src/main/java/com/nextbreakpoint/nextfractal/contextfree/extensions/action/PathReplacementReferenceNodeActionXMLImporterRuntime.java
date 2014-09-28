/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.action;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.extension.PathReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigurableExtensionReferenceElementNodeActionXMLImporterRuntime;
/**
 * @author Andrea Medeghini
 */
public class PathReplacementReferenceNodeActionXMLImporterRuntime extends AbstractConfigurableExtensionReferenceElementNodeActionXMLImporterRuntime<PathReplacementExtensionConfig> {
	/**
	 * 
	 */
	public PathReplacementReferenceNodeActionXMLImporterRuntime() {
		super(ContextFreeRegistry.getInstance().getPathReplacementRegistry());
	}
}
