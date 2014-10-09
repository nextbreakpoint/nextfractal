/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensionPoints.figure;

import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFBuilder;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class FigureExtensionRuntime<T extends FigureExtensionConfig> extends ConfigurableExtensionRuntime<T> {
	/**
	 * @param context 
	 */
	public abstract void process(CFBuilder context);
}
