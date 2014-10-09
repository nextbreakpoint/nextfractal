/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.shapeAdjustment.ShapeAdjustmentExtensionConfig;
import com.nextbreakpoint.nextfractal.core.common.FloatElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;

/**
 * @author Andrea Medeghini
 */
public class SizeShapeAdjustmentConfig extends ShapeAdjustmentExtensionConfig {
	private static final long serialVersionUID = 1L;
	private FloatElement scaleElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		scaleElement = new FloatElement(0f);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(scaleElement);
		return elements;
	}

	/**
	 * @return
	 */
	public FloatElement getScaleElement() {
		return scaleElement;
	}
	
	/**
	 * @return
	 */
	public Float getScale() {
		return scaleElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setScale(final Float value) {
		scaleElement.setValue(value);
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
		final SizeShapeAdjustmentConfig other = (SizeShapeAdjustmentConfig) obj;
		if (scaleElement == null) {
			if (other.scaleElement != null) {
				return false;
			}
		}
		else if (!scaleElement.equals(other.scaleElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public SizeShapeAdjustmentConfig clone() {
		final SizeShapeAdjustmentConfig config = new SizeShapeAdjustmentConfig();
		config.setScale(getScale());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		if (scaleElement.getValue() != null) {
			builder.append("s ");
			builder.append(scaleElement.getValue());
		}
	}
}

