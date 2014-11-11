/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.figure;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.figure.FigureExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.ConfigElement;

/**
 * @author Andrea Medeghini
 */
public class CircleFigureConfig extends FigureExtensionConfig {
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
		final CircleFigureConfig other = (CircleFigureConfig) obj;
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public CircleFigureConfig clone() {
		final CircleFigureConfig config = new CircleFigureConfig();
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		builder.append("/* CIRCLE */\n");
	}
}
