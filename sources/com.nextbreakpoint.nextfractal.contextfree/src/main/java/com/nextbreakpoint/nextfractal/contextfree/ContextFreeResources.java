/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree;

import java.util.ResourceBundle;

import com.nextbreakpoint.nextfractal.core.util.Resources;

/**
 * @author Andrea Medeghini
 */
public class ContextFreeResources extends Resources {
	private static final ContextFreeResources instance = new ContextFreeResources();

	private ContextFreeResources() {
		super(ResourceBundle.getBundle("contextfree_resources"));
	}

	/**
	 * Returns the instance.
	 * 
	 * @return the instance.
	 */
	public static ContextFreeResources getInstance() {
		return instance;
	}
}
