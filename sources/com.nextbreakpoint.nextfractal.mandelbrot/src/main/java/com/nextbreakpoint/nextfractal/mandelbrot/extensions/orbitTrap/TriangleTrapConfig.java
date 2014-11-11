/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.orbitTrap;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.elements.DoubleElement;
import com.nextbreakpoint.nextfractal.core.runtime.ConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.CriteriaElement;

/**
 * @author Andrea Medeghini
 */
public class TriangleTrapConfig extends AbstractOrbitTrapConfig {
	private static final long serialVersionUID = 1L;
	private static final Double DEFAULT_WIDTH = new Double(2);
	private static final Double DEFAULT_HEIGHT = new Double(2);
	private static final Double DEFAULT_ROTATION = new Double(0);
	private static final Integer DEFAULT_CRITERIA = new Integer(0);
	private CriteriaElement criteriaElement;
	private DoubleElement widthElement;
	private DoubleElement heightElement;
	private DoubleElement rotationElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		criteriaElement = new CriteriaElement(getDefaultCriteria());
		widthElement = new DoubleElement(getDefaultWidth());
		heightElement = new DoubleElement(getDefaultHeight());
		rotationElement = new DoubleElement(getDefaultRotation());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(criteriaElement);
		elements.add(widthElement);
		elements.add(heightElement);
		elements.add(rotationElement);
		return elements;
	}

	/**
	 * @param length
	 */
	public void setHeight(final Double length) {
		heightElement.setValue(length);
	}

	/**
	 * @return the length.
	 */
	public Double getHeight() {
		return heightElement.getValue();
	}

	/**
	 * @return the default length.
	 */
	public Double getDefaultHeight() {
		return TriangleTrapConfig.DEFAULT_HEIGHT;
	}

	/**
	 * @return
	 */
	protected DoubleElement getHeightElement() {
		return heightElement;
	}

	/**
	 * @param threshold
	 */
	public void setWidth(final Double threshold) {
		widthElement.setValue(threshold);
	}

	/**
	 * @return the threshold.
	 */
	public Double getWidth() {
		return widthElement.getValue();
	}

	/**
	 * @return the default threshold.
	 */
	public Double getDefaultWidth() {
		return TriangleTrapConfig.DEFAULT_WIDTH;
	}

	/**
	 * @return
	 */
	protected DoubleElement getWidthElement() {
		return widthElement;
	}

	/**
	 * @param rotation
	 */
	public void setRotation(final Double rotation) {
		rotationElement.setValue(rotation);
	}

	/**
	 * @return the rotation.
	 */
	public Double getRotation() {
		return rotationElement.getValue();
	}

	/**
	 * @return the default rotation.
	 */
	public Double getDefaultRotation() {
		return TriangleTrapConfig.DEFAULT_ROTATION;
	}

	/**
	 * @return
	 */
	protected DoubleElement getRotationElement() {
		return rotationElement;
	}

	/**
	 * @param criteria
	 */
	public void setCriteria(final Integer criteria) {
		criteriaElement.setValue(criteria);
	}

	/**
	 * @return the criteria.
	 */
	public Integer getCriteria() {
		return criteriaElement.getValue();
	}

	/**
	 * @return the default criteria.
	 */
	public Integer getDefaultCriteria() {
		return DEFAULT_CRITERIA;
	}

	/**
	 * @return
	 */
	protected CriteriaElement getCriteriaElement() {
		return criteriaElement;
	}

	/**
	 * @return
	 */
	@Override
	public TriangleTrapConfig clone() {
		final TriangleTrapConfig config = new TriangleTrapConfig();
		config.setCriteria(getCriteria());
		config.setWidth(getWidth());
		config.setHeight(getHeight());
		config.setRotation(getRotation());
		return config;
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
		final TriangleTrapConfig other = (TriangleTrapConfig) obj;
		if (heightElement == null) {
			if (other.heightElement != null) {
				return false;
			}
		}
		else if (!heightElement.equals(other.heightElement)) {
			return false;
		}
		if (widthElement == null) {
			if (other.widthElement != null) {
				return false;
			}
		}
		else if (!widthElement.equals(other.widthElement)) {
			return false;
		}
		if (rotationElement == null) {
			if (other.rotationElement != null) {
				return false;
			}
		}
		else if (!rotationElement.equals(other.rotationElement)) {
			return false;
		}
		if (criteriaElement == null) {
			if (other.criteriaElement != null) {
				return false;
			}
		}
		else if (!criteriaElement.equals(other.criteriaElement)) {
			return false;
		}
		return true;
	}
}
