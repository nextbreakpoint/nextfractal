/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeReplacement;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.extension.ShapeReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.core.common.StringElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ListConfigElement;

/**
 * @author Andrea Medeghini
 */
public class SingleShapeReplacementConfig extends ShapeReplacementExtensionConfig {
	private static final long serialVersionUID = 1L;
	private StringElement shapeElement;
	private ListConfigElement<ShapeAdjustmentConfigElement> shapeAdjustmentListElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		shapeElement = new StringElement("");
		shapeAdjustmentListElement = new ListConfigElement<ShapeAdjustmentConfigElement>("shapeAdjustmentListElement");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(shapeElement);
		elements.add(shapeAdjustmentListElement);
		return elements;
	}

	/**
	 * @return
	 */
	public StringElement getShapeElement() {
		return shapeElement;
	}
	
	/**
	 * @return
	 */
	public String getShape() {
		return shapeElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setShape(final String value) {
		shapeElement.setValue(value);
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
		final SingleShapeReplacementConfig other = (SingleShapeReplacementConfig) obj;
		if (shapeElement == null) {
			if (other.shapeElement != null) {
				return false;
			}
		}
		else if (!shapeElement.equals(other.shapeElement)) {
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
	public SingleShapeReplacementConfig clone() {
		final SingleShapeReplacementConfig config = new SingleShapeReplacementConfig();
		config.setShape(getShape());
		config.shapeAdjustmentListElement.copyFrom(getShapeAdjustmentListElement());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		if (shapeElement.getValue() != null) {
			builder.appendTabs();
			builder.append(shapeElement.getValue());
			builder.append(" [");
			for (int i = 0; i < shapeAdjustmentListElement.getElementCount(); i++) {
				builder.append(" ");
				shapeAdjustmentListElement.getElement(i).toCFDG(builder);
			}
			builder.append("]");
		}
	}
}
