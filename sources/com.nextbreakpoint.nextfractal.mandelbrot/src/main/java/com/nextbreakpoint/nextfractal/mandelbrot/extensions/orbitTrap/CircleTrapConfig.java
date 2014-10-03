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

import com.nextbreakpoint.nextfractal.core.common.DoubleElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.common.CriteriaElement;

/**
 * @author Andrea Medeghini
 */
public class CircleTrapConfig extends AbstractOrbitTrapConfig {
	private static final long serialVersionUID = 1L;
	private static final Double DEFAULT_SIZE = new Double(2);
	private static final Double DEFAULT_THRESHOLD = new Double(0.5);
	private static final Integer DEFAULT_CRITERIA = new Integer(0);
	private CriteriaElement criteriaElement;
	private DoubleElement thresholdElement;
	private DoubleElement sizeElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		criteriaElement = new CriteriaElement(getDefaultCriteria());
		thresholdElement = new DoubleElement(getDefaultThreshold());
		sizeElement = new DoubleElement(getDefaultSize());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(criteriaElement);
		elements.add(thresholdElement);
		elements.add(sizeElement);
		return elements;
	}

	/**
	 * @param size
	 */
	public void setSize(final Double size) {
		sizeElement.setValue(size);
	}

	/**
	 * @return the size.
	 */
	public Double getSize() {
		return sizeElement.getValue();
	}

	/**
	 * @return the default size.
	 */
	public Double getDefaultSize() {
		return CircleTrapConfig.DEFAULT_SIZE;
	}

	/**
	 * @return
	 */
	protected DoubleElement getSizeElement() {
		return sizeElement;
	}

	/**
	 * @param threshold
	 */
	public void setThreshold(final Double threshold) {
		thresholdElement.setValue(threshold);
	}

	/**
	 * @return the threshold.
	 */
	public Double getThreshold() {
		return thresholdElement.getValue();
	}

	/**
	 * @return the default threshold.
	 */
	public Double getDefaultThreshold() {
		return CircleTrapConfig.DEFAULT_THRESHOLD;
	}

	/**
	 * @return
	 */
	protected DoubleElement getThresholdElement() {
		return thresholdElement;
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
	public CircleTrapConfig clone() {
		final CircleTrapConfig config = new CircleTrapConfig();
		config.setCriteria(getCriteria());
		config.setThreshold(getThreshold());
		config.setSize(getSize());
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
		final CircleTrapConfig other = (CircleTrapConfig) obj;
		if (sizeElement == null) {
			if (other.sizeElement != null) {
				return false;
			}
		}
		else if (!sizeElement.equals(other.sizeElement)) {
			return false;
		}
		if (thresholdElement == null) {
			if (other.thresholdElement != null) {
				return false;
			}
		}
		else if (!thresholdElement.equals(other.thresholdElement)) {
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
