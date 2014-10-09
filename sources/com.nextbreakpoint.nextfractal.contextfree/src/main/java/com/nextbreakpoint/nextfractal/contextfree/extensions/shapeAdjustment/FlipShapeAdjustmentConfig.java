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
public class FlipShapeAdjustmentConfig extends ShapeAdjustmentExtensionConfig {
	private static final long serialVersionUID = 1L;
	private FloatElement angleElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		angleElement = new FloatElement(0f);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(angleElement);
		return elements;
	}

	/**
	 * @return
	 */
	public FloatElement getAngleElement() {
		return angleElement;
	}
	
	/**
	 * @return
	 */
	public Float getAngle() {
		return angleElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setAngle(final Float value) {
		angleElement.setValue(value);
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
		final FlipShapeAdjustmentConfig other = (FlipShapeAdjustmentConfig) obj;
		if (angleElement == null) {
			if (other.angleElement != null) {
				return false;
			}
		}
		else if (!angleElement.equals(other.angleElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public FlipShapeAdjustmentConfig clone() {
		final FlipShapeAdjustmentConfig config = new FlipShapeAdjustmentConfig();
		config.setAngle(getAngle());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		if (angleElement.getValue() != null) {
			builder.append("flip ");
			builder.append(angleElement.getValue() * 180f / Math.PI);
		}
	}
}
