/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.elements.StrokeCapElement;
import com.nextbreakpoint.nextfractal.contextfree.elements.StrokeJoinElement;
import com.nextbreakpoint.nextfractal.contextfree.elements.StrokeWidthElement;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathReplacement.PathReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.ConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.ListConfigElement;

/**
 * @author Andrea Medeghini
 */
public class StrokePathReplacementConfig extends PathReplacementExtensionConfig {
	private static final long serialVersionUID = 1L;
	private StrokeWidthElement widthElement;
	private StrokeJoinElement capElement;
	private StrokeCapElement joinElement;
	private ListConfigElement<PathAdjustmentConfigElement> pathAdjustmentListElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		widthElement = new StrokeWidthElement(0.1f);
		widthElement.setMaximum(getStrokeWidthMax());
		widthElement.setMinimum(getStrokeWidthMin());
		widthElement.setStep(getStrokeWidthStep());
		capElement = new StrokeJoinElement("buttcap");
		joinElement = new StrokeCapElement("miterjoin");
		pathAdjustmentListElement = new ListConfigElement<PathAdjustmentConfigElement>("pathAdjustmentListElement");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(widthElement);
		elements.add(capElement);
		elements.add(joinElement);
		elements.add(pathAdjustmentListElement);
		return elements;
	}

	/**
	 * @return
	 */
	public StrokeWidthElement getWidthElement() {
		return widthElement;
	}
	
	/**
	 * @return
	 */
	public Float getWidth() {
		return widthElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setWidth(final Float value) {
		widthElement.setValue(value);
	}
	/**
	 * @return
	 */
	public StrokeJoinElement getCapElement() {
		return capElement;
	}
	
	/**
	 * @return
	 */
	public String getCap() {
		return capElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setCap(final String value) {
		capElement.setValue(value);
	}
	/**
	 * @return
	 */
	public StrokeCapElement getJoinElement() {
		return joinElement;
	}
	
	/**
	 * @return
	 */
	public String getJoin() {
		return joinElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setJoin(final String value) {
		joinElement.setValue(value);
	}
	
	/**
	 * @return
	 */
	public Float getStrokeWidthMin() {
		return 0.001f;
	}

	/**
	 * @return
	 */
	public Float getStrokeWidthMax() {
		return 100f;
	}

	/**
	 * @return
	 */
	public Float getStrokeWidthStep() {
		return 0.001f;
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
		final StrokePathReplacementConfig other = (StrokePathReplacementConfig) obj;
		if (widthElement == null) {
			if (other.widthElement != null) {
				return false;
			}
		}
		else if (!widthElement.equals(other.widthElement)) {
			return false;
		}
		if (capElement == null) {
			if (other.capElement != null) {
				return false;
			}
		}
		else if (!capElement.equals(other.capElement)) {
			return false;
		}
		if (joinElement == null) {
			if (other.joinElement != null) {
				return false;
			}
		}
		else if (!joinElement.equals(other.joinElement)) {
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
	public StrokePathReplacementConfig clone() {
		final StrokePathReplacementConfig config = new StrokePathReplacementConfig();
		config.setWidth(getWidth());
		config.setCap(getCap());
		config.setJoin(getJoin());
		config.pathAdjustmentListElement.copyFrom(getPathAdjustmentListElement());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		builder.appendTabs();
		builder.append("STROKE {");
		if (widthElement.getValue() != null) {
			builder.append(" width ");
			builder.append(widthElement.getValue());
		}
		if (capElement.getValue() != null) {
			builder.append(" p cap");
			builder.append(capElement.getValue());
		}
		if (joinElement.getValue() != null) {
			builder.append(" p join");
			builder.append(joinElement.getValue());
		}
		for (int i = 0; i < pathAdjustmentListElement.getElementCount(); i++) {
			builder.append(" ");
			pathAdjustmentListElement.getElement(i).toCFDG(builder);
		}
		builder.append(" }");
	}
}
