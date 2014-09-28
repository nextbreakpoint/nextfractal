/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.pathReplacement.extension;

import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFRule;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class PathReplacementExtensionRuntime<T extends PathReplacementExtensionConfig> extends ConfigurableExtensionRuntime<T> {
	/**
	 * @param rule 
	 */
	public abstract void process(CFRule rule);
}
