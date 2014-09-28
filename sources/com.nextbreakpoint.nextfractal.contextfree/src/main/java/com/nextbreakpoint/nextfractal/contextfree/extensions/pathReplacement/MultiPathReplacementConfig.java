/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.extension.PathReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.core.common.IntegerElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ListConfigElement;

/**
 * @author Andrea Medeghini
 */
public class MultiPathReplacementConfig extends PathReplacementExtensionConfig {
	private static final long serialVersionUID = 1L;
	private IntegerElement timesElement;
	private ListConfigElement<PathReplacementConfigElement> pathReplacementListElement;
	private ListConfigElement<PathAdjustmentConfigElement> pathAdjustmentListElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		timesElement = new IntegerElement(1);
		pathReplacementListElement = new ListConfigElement<PathReplacementConfigElement>("pathReplacementListElement");
		pathAdjustmentListElement = new ListConfigElement<PathAdjustmentConfigElement>("pathAdjustmentListElement");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(timesElement);
		elements.add(pathReplacementListElement);
		elements.add(pathAdjustmentListElement);
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
	public ListConfigElement<PathReplacementConfigElement> getPathReplacementListElement() {
		return pathReplacementListElement;
	}

	/**
	 * Returns a pathReplacement element.
	 * 
	 * @param index the pathReplacement index.
	 * @return the pathReplacement.
	 */
	public PathReplacementConfigElement getPathReplacementConfigElement(final int index) {
		return pathReplacementListElement.getElement(index);
	}

	/**
	 * Returns a pathReplacement element index.
	 * 
	 * @param pathReplacementElement the pathReplacement element.
	 * @return the index.
	 */
	public int indexOfPathReplacementConfigElement(final PathReplacementConfigElement pathReplacementElement) {
		return pathReplacementListElement.indexOfElement(pathReplacementElement);
	}

	/**
	 * Returns the number of pathReplacement elements.
	 * 
	 * @return the number of pathReplacement elements.
	 */
	public int getPathReplacementConfigElementCount() {
		return pathReplacementListElement.getElementCount();
	}

	/**
	 * Adds a pathReplacement element.
	 * 
	 * @param pathReplacementElement the pathReplacement to add.
	 */
	public void appendPathReplacementConfigElement(final PathReplacementConfigElement pathReplacementElement) {
		pathReplacementListElement.appendElement(pathReplacementElement);
	}

	/**
	 * Adds a pathReplacement element.
	 * 
	 * @param index the index.
	 * @param pathReplacementElement the pathReplacement to add.
	 */
	public void insertPathReplacementConfigElementAfter(final int index, final PathReplacementConfigElement pathReplacementElement) {
		pathReplacementListElement.insertElementAfter(index, pathReplacementElement);
	}

	/**
	 * Adds a pathReplacement element.
	 * 
	 * @param index the index.
	 * @param pathReplacementElement the pathReplacement to add.
	 */
	public void insertPathReplacementConfigElementBefore(final int index, final PathReplacementConfigElement pathReplacementElement) {
		pathReplacementListElement.insertElementBefore(index, pathReplacementElement);
	}

	/**
	 * Removes a pathReplacement element.
	 * 
	 * @param index the element index to remove.
	 */
	public void removePathReplacementConfigElement(final int index) {
		pathReplacementListElement.removeElement(index);
	}

	/**
	 * Removes a pathReplacement element.
	 * 
	 * @param pathReplacementElement the pathReplacement to remove.
	 */
	public void removePathReplacementConfigElement(final PathReplacementConfigElement pathReplacementElement) {
		pathReplacementListElement.removeElement(pathReplacementElement);
	}
	/**
	 * @return
	 */
	public ListConfigElement<PathAdjustmentConfigElement> getPathAdjustmentListElement() {
		return pathAdjustmentListElement;
	}

	/**
	 * Returns a pathAdjustment element.
	 * 
	 * @param index the pathAdjustment index.
	 * @return the pathAdjustment.
	 */
	public PathAdjustmentConfigElement getPathAdjustmentConfigElement(final int index) {
		return pathAdjustmentListElement.getElement(index);
	}

	/**
	 * Returns a pathAdjustment element index.
	 * 
	 * @param pathAdjustmentElement the pathAdjustment element.
	 * @return the index.
	 */
	public int indexOfPathAdjustmentConfigElement(final PathAdjustmentConfigElement pathAdjustmentElement) {
		return pathAdjustmentListElement.indexOfElement(pathAdjustmentElement);
	}

	/**
	 * Returns the number of pathAdjustment elements.
	 * 
	 * @return the number of pathAdjustment elements.
	 */
	public int getPathAdjustmentConfigElementCount() {
		return pathAdjustmentListElement.getElementCount();
	}

	/**
	 * Adds a pathAdjustment element.
	 * 
	 * @param pathAdjustmentElement the pathAdjustment to add.
	 */
	public void appendPathAdjustmentConfigElement(final PathAdjustmentConfigElement pathAdjustmentElement) {
		pathAdjustmentListElement.appendElement(pathAdjustmentElement);
	}

	/**
	 * Adds a pathAdjustment element.
	 * 
	 * @param index the index.
	 * @param pathAdjustmentElement the pathAdjustment to add.
	 */
	public void insertPathAdjustmentConfigElementAfter(final int index, final PathAdjustmentConfigElement pathAdjustmentElement) {
		pathAdjustmentListElement.insertElementAfter(index, pathAdjustmentElement);
	}

	/**
	 * Adds a pathAdjustment element.
	 * 
	 * @param index the index.
	 * @param pathAdjustmentElement the pathAdjustment to add.
	 */
	public void insertPathAdjustmentConfigElementBefore(final int index, final PathAdjustmentConfigElement pathAdjustmentElement) {
		pathAdjustmentListElement.insertElementBefore(index, pathAdjustmentElement);
	}

	/**
	 * Removes a pathAdjustment element.
	 * 
	 * @param index the element index to remove.
	 */
	public void removePathAdjustmentConfigElement(final int index) {
		pathAdjustmentListElement.removeElement(index);
	}

	/**
	 * Removes a pathAdjustment element.
	 * 
	 * @param pathAdjustmentElement the pathAdjustment to remove.
	 */
	public void removePathAdjustmentConfigElement(final PathAdjustmentConfigElement pathAdjustmentElement) {
		pathAdjustmentListElement.removeElement(pathAdjustmentElement);
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
		final MultiPathReplacementConfig other = (MultiPathReplacementConfig) obj;
		if (timesElement == null) {
			if (other.timesElement != null) {
				return false;
			}
		}
		else if (!timesElement.equals(other.timesElement)) {
			return false;
		}
		if (pathReplacementListElement == null) {
			if (other.pathReplacementListElement != null) {
				return false;
			}
		}
		else if (!pathReplacementListElement.equals(other.pathReplacementListElement)) {
			return false;
		}
		if (pathAdjustmentListElement == null) {
			if (other.pathAdjustmentListElement != null) {
				return false;
			}
		}
		else if (!pathAdjustmentListElement.equals(other.pathAdjustmentListElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public MultiPathReplacementConfig clone() {
		final MultiPathReplacementConfig config = new MultiPathReplacementConfig();
		config.setTimes(getTimes());
		config.pathReplacementListElement.copyFrom(getPathReplacementListElement());
		config.pathAdjustmentListElement.copyFrom(getPathAdjustmentListElement());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		builder.appendTabs();
		if (timesElement.getValue() != null) {
			builder.append(timesElement.getValue());
			builder.append(" * ");
		}
		if (pathReplacementListElement.getElementCount() > 0) {
			if (pathAdjustmentListElement.getElementCount() > 0) {
				builder.append("[");
				for (int i = 0; i < pathAdjustmentListElement.getElementCount(); i++) {
					builder.append(" ");
					pathAdjustmentListElement.getElement(i).toCFDG(builder);
				}
				builder.append("] ");
			}
			builder.append("{\n");
			builder.addTab();
			for (int i = 0; i < pathReplacementListElement.getElementCount(); i++) {
				pathReplacementListElement.getElement(i).toCFDG(builder);
				builder.append("\n");
			}
			builder.removeTab();
			builder.append("}");
		}
	}
}
