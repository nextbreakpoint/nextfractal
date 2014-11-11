/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensionPoints.figure;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.core.runtime.ConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;


/**
 * @author Andrea Medeghini
 */
public abstract class FigureExtensionConfig extends ExtensionConfig {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		return elements;
	}


	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		final FigureExtensionConfig other = (FigureExtensionConfig) obj;
		return true;
	}

	public abstract void toCFDG(CFDGBuilder builder);
}
