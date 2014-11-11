/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathAdjustment.PathAdjustmentExtensionConfig;
import com.nextbreakpoint.nextfractal.core.elements.BooleanElement;
import com.nextbreakpoint.nextfractal.core.elements.FloatElement;
import com.nextbreakpoint.nextfractal.core.runtime.ConfigElement;

/**
 * @author Andrea Medeghini
 */
public class CurrentBrightnessPathAdjustmentConfig extends PathAdjustmentExtensionConfig {
	private static final long serialVersionUID = 1L;
	private FloatElement valueElement;
	private BooleanElement targetElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		valueElement = new FloatElement(0f);
		targetElement = new BooleanElement(false);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(valueElement);
		elements.add(targetElement);
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
	 * @return
	 */
	public BooleanElement getTargetElement() {
		return targetElement;
	}
	
	/**
	 * @return
	 */
	public Boolean isTarget() {
		return targetElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setTarget(final Boolean value) {
		targetElement.setValue(value);
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
		final CurrentBrightnessPathAdjustmentConfig other = (CurrentBrightnessPathAdjustmentConfig) obj;
		if (valueElement == null) {
			if (other.valueElement != null) {
				return false;
			}
		}
		else if (!valueElement.equals(other.valueElement)) {
			return false;
		}
		if (targetElement == null) {
			if (other.targetElement != null) {
				return false;
			}
		}
		else if (!targetElement.equals(other.targetElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public CurrentBrightnessPathAdjustmentConfig clone() {
		final CurrentBrightnessPathAdjustmentConfig config = new CurrentBrightnessPathAdjustmentConfig();
		config.setValue(getValue());
		config.setTarget(isTarget());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		if (valueElement.getValue() != null) {
			builder.append("b ");
			builder.append(valueElement.getValue());
		}
		if (targetElement.getValue() != null) {
			builder.append(targetElement.getValue() ? "|" : "");
		}
	}
}
