/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeReplacement;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.shapeReplacement.ShapeReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElement;
import com.nextbreakpoint.nextfractal.core.common.IntegerElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ListConfigElement;

/**
 * @author Andrea Medeghini
 */
public class MultiShapeReplacementConfig extends ShapeReplacementExtensionConfig {
	private static final long serialVersionUID = 1L;
	private IntegerElement timesElement;
	private ListConfigElement<ShapeReplacementConfigElement> shapeReplacementListElement;
	private ListConfigElement<ShapeAdjustmentConfigElement> shapeAdjustmentListElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		timesElement = new IntegerElement(1);
		shapeReplacementListElement = new ListConfigElement<ShapeReplacementConfigElement>("shapeReplacementListElement");
		shapeAdjustmentListElement = new ListConfigElement<ShapeAdjustmentConfigElement>("shapeAdjustmentListElement");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(timesElement);
		elements.add(shapeReplacementListElement);
		elements.add(shapeAdjustmentListElement);
		return elements;
	}

	/**
	 * @return
	 */
	public IntegerElement getTimesElement() {
		return timesElement;
	}
	
	/**
	 * @return
	 */
	public Integer getTimes() {
		return timesElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setTimes(final Integer value) {
		timesElement.setValue(value);
	}
	/**
	 * @return
	 */
	public ListConfigElement<ShapeReplacementConfigElement> getShapeReplacementListElement() {
		return shapeReplacementListElement;
	}

	/**
	 * Returns a shapeReplacement element.
	 * 
	 * @param index the shapeReplacement index.
	 * @return the shapeReplacement.
	 */
	public ShapeReplacementConfigElement getShapeReplacementConfigElement(final int index) {
		return shapeReplacementListElement.getElement(index);
	}

	/**
	 * Returns a shapeReplacement element index.
	 * 
	 * @param shapeReplacementElement the shapeReplacement element.
	 * @return the index.
	 */
	public int indexOfShapeReplacementConfigElement(final ShapeReplacementConfigElement shapeReplacementElement) {
		return shapeReplacementListElement.indexOfElement(shapeReplacementElement);
	}

	/**
	 * Returns the number of shapeReplacement elements.
	 * 
	 * @return the number of shapeReplacement elements.
	 */
	public int getShapeReplacementConfigElementCount() {
		return shapeReplacementListElement.getElementCount();
	}

	/**
	 * Adds a shapeReplacement element.
	 * 
	 * @param shapeReplacementElement the shapeReplacement to add.
	 */
	public void appendShapeReplacementConfigElement(final ShapeReplacementConfigElement shapeReplacementElement) {
		shapeReplacementListElement.appendElement(shapeReplacementElement);
	}

	/**
	 * Adds a shapeReplacement element.
	 * 
	 * @param index the index.
	 * @param shapeReplacementElement the shapeReplacement to add.
	 */
	public void insertShapeReplacementConfigElementAfter(final int index, final ShapeReplacementConfigElement shapeReplacementElement) {
		shapeReplacementListElement.insertElementAfter(index, shapeReplacementElement);
	}

	/**
	 * Adds a shapeReplacement element.
	 * 
	 * @param index the index.
	 * @param shapeReplacementElement the shapeReplacement to add.
	 */
	public void insertShapeReplacementConfigElementBefore(final int index, final ShapeReplacementConfigElement shapeReplacementElement) {
		shapeReplacementListElement.insertElementBefore(index, shapeReplacementElement);
	}

	/**
	 * Removes a shapeReplacement element.
	 * 
	 * @param index the element index to remove.
	 */
	public void removeShapeReplacementConfigElement(final int index) {
		shapeReplacementListElement.removeElement(index);
	}

	/**
	 * Removes a shapeReplacement element.
	 * 
	 * @param shapeReplacementElement the shapeReplacement to remove.
	 */
	public void removeShapeReplacementConfigElement(final ShapeReplacementConfigElement shapeReplacementElement) {
		shapeReplacementListElement.removeElement(shapeReplacementElement);
	}
	/**
	 * @return
	 */
	public ListConfigElement<ShapeAdjustmentConfigElement> getShapeAdjustmentListElement() {
		return shapeAdjustmentListElement;
	}

	/**
	 * Returns a shapeAdjustment element.
	 * 
	 * @param index the shapeAdjustment index.
	 * @return the shapeAdjustment.
	 */
	public ShapeAdjustmentConfigElement getShapeAdjustmentConfigElement(final int index) {
		return shapeAdjustmentListElement.getElement(index);
	}

	/**
	 * Returns a shapeAdjustment element index.
	 * 
	 * @param shapeAdjustmentElement the shapeAdjustment element.
	 * @return the index.
	 */
	public int indexOfShapeAdjustmentConfigElement(final ShapeAdjustmentConfigElement shapeAdjustmentElement) {
		return shapeAdjustmentListElement.indexOfElement(shapeAdjustmentElement);
	}

	/**
	 * Returns the number of shapeAdjustment elements.
	 * 
	 * @return the number of shapeAdjustment elements.
	 */
	public int getShapeAdjustmentConfigElementCount() {
		return shapeAdjustmentListElement.getElementCount();
	}

	/**
	 * Adds a shapeAdjustment element.
	 * 
	 * @param shapeAdjustmentElement the shapeAdjustment to add.
	 */
	public void appendShapeAdjustmentConfigElement(final ShapeAdjustmentConfigElement shapeAdjustmentElement) {
		shapeAdjustmentListElement.appendElement(shapeAdjustmentElement);
	}

	/**
	 * Adds a shapeAdjustment element.
	 * 
	 * @param index the index.
	 * @param shapeAdjustmentElement the shapeAdjustment to add.
	 */
	public void insertShapeAdjustmentConfigElementAfter(final int index, final ShapeAdjustmentConfigElement shapeAdjustmentElement) {
		shapeAdjustmentListElement.insertElementAfter(index, shapeAdjustmentElement);
	}

	/**
	 * Adds a shapeAdjustment element.
	 * 
	 * @param index the index.
	 * @param shapeAdjustmentElement the shapeAdjustment to add.
	 */
	public void insertShapeAdjustmentConfigElementBefore(final int index, final ShapeAdjustmentConfigElement shapeAdjustmentElement) {
		shapeAdjustmentListElement.insertElementBefore(index, shapeAdjustmentElement);
	}

	/**
	 * Removes a shapeAdjustment element.
	 * 
	 * @param index the element index to remove.
	 */
	public void removeShapeAdjustmentConfigElement(final int index) {
		shapeAdjustmentListElement.removeElement(index);
	}

	/**
	 * Removes a shapeAdjustment element.
	 * 
	 * @param shapeAdjustmentElement the shapeAdjustment to remove.
	 */
	public void removeShapeAdjustmentConfigElement(final ShapeAdjustmentConfigElement shapeAdjustmentElement) {
		shapeAdjustmentListElement.removeElement(shapeAdjustmentElement);
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
		final MultiShapeReplacementConfig other = (MultiShapeReplacementConfig) obj;
		if (timesElement == null) {
			if (other.timesElement != null) {
				return false;
			}
		}
		else if (!timesElement.equals(other.timesElement)) {
			return false;
		}
		if (shapeReplacementListElement == null) {
			if (other.shapeReplacementListElement != null) {
				return false;
			}
		}
		else if (!shapeReplacementListElement.equals(other.shapeReplacementListElement)) {
			return false;
		}
		if (shapeAdjustmentListElement == null) {
			if (other.shapeAdjustmentListElement != null) {
				return false;
			}
		}
		else if (!shapeAdjustmentListElement.equals(other.shapeAdjustmentListElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public MultiShapeReplacementConfig clone() {
		final MultiShapeReplacementConfig config = new MultiShapeReplacementConfig();
		config.setTimes(getTimes());
		config.shapeReplacementListElement.copyFrom(getShapeReplacementListElement());
		config.shapeAdjustmentListElement.copyFrom(getShapeAdjustmentListElement());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		builder.appendTabs();
		if (timesElement.getValue() != null) {
			builder.append(timesElement.getValue());
			builder.append(" * ");
		}
		if (shapeReplacementListElement.getElementCount() > 0) {
			if (shapeAdjustmentListElement.getElementCount() > 0) {
				builder.append("[");
				for (int i = 0; i < shapeAdjustmentListElement.getElementCount(); i++) {
					builder.append(" ");
					shapeAdjustmentListElement.getElement(i).toCFDG(builder);
				}
				builder.append("] ");
			}
			builder.append("{\n");
			builder.addTab();
			for (int i = 0; i < shapeReplacementListElement.getElementCount(); i++) {
				shapeReplacementListElement.getElement(i).toCFDG(builder);
				builder.append("\n");
			}
			builder.removeTab();
			builder.append("}");
		}
	}
}
