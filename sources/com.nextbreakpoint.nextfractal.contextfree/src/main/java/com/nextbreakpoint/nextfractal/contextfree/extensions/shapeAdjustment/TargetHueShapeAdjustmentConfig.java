/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.extension.ShapeAdjustmentExtensionConfig;
import com.nextbreakpoint.nextfractal.core.common.FloatElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;

/**
 * @author Andrea Medeghini
 */
public class TargetHueShapeAdjustmentConfig extends ShapeAdjustmentExtensionConfig {
	private static final long serialVersionUID = 1L;
	private FloatElement valueElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		valueElement = new FloatElement(0f);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(valueElement);
		return elements;
	}

	/**
	 * @return
	 */
	public FloatElement getValueElement() {
		return valueElement;
	}
	
	/**
	 * @return
	 */
	public Float getValue() {
		return valueElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setValue(final Float value) {
		valueElement.setValue(value);
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
		final TargetHueShapeAdjustmentConfig other = (TargetHueShapeAdjustmentConfig) obj;
		if (valueElement == null) {
			if (other.valueElement != null) {
				return false;
			}
		}
		else if (!valueElement.equals(other.valueElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public TargetHueShapeAdjustmentConfig clone() {
		final TargetHueShapeAdjustmentConfig config = new TargetHueShapeAdjustmentConfig();
		config.setValue(getValue());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		if (valueElement.getValue() != null) {
			builder.append("|h ");
			builder.append(valueElement.getValue() * 360);
		}
	}
}

