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
public class SkewShapeAdjustmentConfig extends ShapeAdjustmentExtensionConfig {
	private static final long serialVersionUID = 1L;
	private FloatElement shearXElement;
	private FloatElement shearYElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		shearXElement = new FloatElement(0f);
		shearYElement = new FloatElement(0f);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(shearXElement);
		elements.add(shearYElement);
		return elements;
	}

	/**
	 * @return
	 */
	public FloatElement getShearXElement() {
		return shearXElement;
	}
	
	/**
	 * @return
	 */
	public Float getShearX() {
		return shearXElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setShearX(final Float value) {
		shearXElement.setValue(value);
	}
	/**
	 * @return
	 */
	public FloatElement getShearYElement() {
		return shearYElement;
	}
	
	/**
	 * @return
	 */
	public Float getShearY() {
		return shearYElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setShearY(final Float value) {
		shearYElement.setValue(value);
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
		final SkewShapeAdjustmentConfig other = (SkewShapeAdjustmentConfig) obj;
		if (shearXElement == null) {
			if (other.shearXElement != null) {
				return false;
			}
		}
		else if (!shearXElement.equals(other.shearXElement)) {
			return false;
		}
		if (shearYElement == null) {
			if (other.shearYElement != null) {
				return false;
			}
		}
		else if (!shearYElement.equals(other.shearYElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public SkewShapeAdjustmentConfig clone() {
		final SkewShapeAdjustmentConfig config = new SkewShapeAdjustmentConfig();
		config.setShearX(getShearX());
		config.setShearY(getShearY());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		if (shearXElement.getValue() != null && shearYElement.getValue() != null) {
			builder.append("skew ");
			builder.append(shearXElement.getValue() * 180f / Math.PI);
			builder.append(" ");
			builder.append(shearYElement.getValue() * 180f / Math.PI);
		}
	}
}

