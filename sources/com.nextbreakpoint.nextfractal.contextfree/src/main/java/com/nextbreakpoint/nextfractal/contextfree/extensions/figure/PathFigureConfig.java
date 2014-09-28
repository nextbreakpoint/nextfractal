/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.figure;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.figure.extension.FigureExtensionConfig;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElement;
import com.nextbreakpoint.nextfractal.core.common.StringElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ListConfigElement;

/**
 * @author Andrea Medeghini
 */
public class PathFigureConfig extends FigureExtensionConfig {
	private static final long serialVersionUID = 1L;
	private StringElement nameElement;
	private ListConfigElement<PathReplacementConfigElement> pathReplacementListElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		nameElement = new StringElement("");
		pathReplacementListElement = new ListConfigElement<PathReplacementConfigElement>("pathReplacementListElement");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(nameElement);
		elements.add(pathReplacementListElement);
		return elements;
	}

	/**
	 * @return
	 */
	public StringElement getNameElement() {
		return nameElement;
	}
	
	/**
	 * @return
	 */
	public String getName() {
		return nameElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setName(final String value) {
		nameElement.setValue(value);
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
		final PathFigureConfig other = (PathFigureConfig) obj;
		if (nameElement == null) {
			if (other.nameElement != null) {
				return false;
			}
		}
		else if (!nameElement.equals(other.nameElement)) {
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
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public PathFigureConfig clone() {
		final PathFigureConfig config = new PathFigureConfig();
		config.setName(getName());
		config.pathReplacementListElement.copyFrom(getPathReplacementListElement());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		builder.append("path ");
		builder.append(nameElement.getValue());
		builder.append(" {\n");
		builder.addTab();
		for (int i = 0; i < pathReplacementListElement.getElementCount(); i++) {
			pathReplacementListElement.getElement(i).toCFDG(builder);
			builder.append("\n");
		}
		builder.removeTab();
		builder.append("}\n");
	}
}
