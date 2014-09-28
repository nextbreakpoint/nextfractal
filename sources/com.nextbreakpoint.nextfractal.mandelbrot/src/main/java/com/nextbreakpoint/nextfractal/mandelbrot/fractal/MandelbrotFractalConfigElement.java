/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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
package com.nextbreakpoint.nextfractal.mandelbrot.fractal;

import com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ListConfigElement;
import com.nextbreakpoint.nextfractal.core.config.SingleConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.OrbitTrapConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.ProcessingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.RenderingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.TransformingFormulaConfigElement;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotFractalConfigElement extends AbstractConfigElement {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "MandelbrotFractal";
	private final SingleConfigElement<RenderingFormulaConfigElement> renderingFormulaSingleElement = new SingleConfigElement<RenderingFormulaConfigElement>("RenderingFormulaSingleElement");
	private final SingleConfigElement<TransformingFormulaConfigElement> transformingFormulaSingleElement = new SingleConfigElement<TransformingFormulaConfigElement>("TransformingFormulaSingleElement");
	private final SingleConfigElement<ProcessingFormulaConfigElement> processingFormulaSingleElement = new SingleConfigElement<ProcessingFormulaConfigElement>("ProcessingFormulaSingleElement");
	private final SingleConfigElement<OrbitTrapConfigElement> orbitTrapSingleElement = new SingleConfigElement<OrbitTrapConfigElement>("OrbitTrapSingleElement");
	private final ListConfigElement<IncolouringFormulaConfigElement> incolouringFormulaListElement = new ListConfigElement<IncolouringFormulaConfigElement>("IncolouringFormulaListElement", 1);
	private final ListConfigElement<OutcolouringFormulaConfigElement> outcolouringFormulaListElement = new ListConfigElement<OutcolouringFormulaConfigElement>("OutcolouringFormulaListElement", 1);

	/**
	 * Constructs a new element.
	 */
	public MandelbrotFractalConfigElement() {
		super(MandelbrotFractalConfigElement.CLASS_ID);
	}

	/**
	 * Returns the renderingFormulaElement.
	 * 
	 * @return the renderingFormulaElement.
	 */
	public RenderingFormulaConfigElement getRenderingFormulaConfigElement() {
		return renderingFormulaSingleElement.getValue();
	}

	/**
	 * Sets the renderingFormulaElement.
	 * 
	 * @param renderingFormulaElement the renderingFormulaElement to set.
	 */
	public void setRenderingFormulaConfigElement(final RenderingFormulaConfigElement renderingFormulaElement) {
		renderingFormulaSingleElement.setValue(renderingFormulaElement);
	}

	/**
	 * Returns the transformingFormulaElement.
	 * 
	 * @return the transformingFormulaElement.
	 */
	public TransformingFormulaConfigElement getTransformingFormulaConfigElement() {
		return transformingFormulaSingleElement.getValue();
	}

	/**
	 * Sets the transformingFormulaElement.
	 * 
	 * @param transformingFormulaElement the transformingFormulaElement to set.
	 */
	public void setTransformingFormulaConfigElement(final TransformingFormulaConfigElement transformingFormulaElement) {
		transformingFormulaSingleElement.setValue(transformingFormulaElement);
	}

	/**
	 * Returns the processingFormulaElement.
	 * 
	 * @return the processingFormulaElement.
	 */
	public ProcessingFormulaConfigElement getProcessingFormulaConfigElement() {
		return processingFormulaSingleElement.getValue();
	}

	/**
	 * Sets the processingFormulaElement.
	 * 
	 * @param processingFormulaElement the processingFormulaElement to set.
	 */
	public void setProcessingFormulaConfigElement(final ProcessingFormulaConfigElement processingFormulaElement) {
		processingFormulaSingleElement.setValue(processingFormulaElement);
	}

	/**
	 * Returns the orbitTrapElement.
	 * 
	 * @return the orbitTrapElement.
	 */
	public OrbitTrapConfigElement getOrbitTrapConfigElement() {
		return orbitTrapSingleElement.getValue();
	}

	/**
	 * Sets the orbitTrapElement.
	 * 
	 * @param orbitTrapElement the orbitTrapElement to set.
	 */
	public void setOrbitTrapConfigElement(final OrbitTrapConfigElement orbitTrapElement) {
		orbitTrapSingleElement.setValue(orbitTrapElement);
	}

	/**
	 * Returns an incolouring formula element.
	 * 
	 * @param index the formula index.
	 * @return the incolouring formula.
	 */
	public IncolouringFormulaConfigElement getIncolouringFormulaConfigElement(final int index) {
		return incolouringFormulaListElement.getElement(index);
	}

	/**
	 * Returns an incolouring formula element index.
	 * 
	 * @param formulaElement the incolouring formula element.
	 * @return the index.
	 */
	public int indexOfIncolouringFormulaConfigElement(final IncolouringFormulaConfigElement formulaElement) {
		return incolouringFormulaListElement.indexOfElement(formulaElement);
	}

	/**
	 * Returns the number of incolouring formula elements.
	 * 
	 * @return the number of incolouring formula elements.
	 */
	public int getIncolouringFormulaConfigElementCount() {
		return incolouringFormulaListElement.getElementCount();
	}

	/**
	 * Adds a incolouring formula element.
	 * 
	 * @param formulaElement the incolouring formula element.
	 */
	public void appendIncolouringFormulaConfigElement(final IncolouringFormulaConfigElement formulaElement) {
		incolouringFormulaListElement.appendElement(formulaElement);
	}

	/**
	 * Adds a incolouring formula element.
	 * 
	 * @param index the index.
	 * @param formulaElement the incolouring formula element.
	 */
	public void insertIncolouringFormulaConfigElementAfter(final int index, final IncolouringFormulaConfigElement formulaElement) {
		incolouringFormulaListElement.insertElementAfter(index, formulaElement);
	}

	/**
	 * Adds a incolouring formula element.
	 * 
	 * @param index the index.
	 * @param formulaElement the incolouring formula element.
	 */
	public void insertIncolouringFormulaConfigElementBefore(final int index, final IncolouringFormulaConfigElement formulaElement) {
		incolouringFormulaListElement.insertElementBefore(index, formulaElement);
	}

	/**
	 * Removes a incolouring formula element.
	 * 
	 * @param index the element index.
	 */
	public void removeIncolouringFormulaConfigElement(final int index) {
		incolouringFormulaListElement.removeElement(index);
	}

	/**
	 * Removes a incolouring formula element.
	 * 
	 * @param formulaElement the incolouring formula element to remove.
	 */
	public void removeIncolouringFormulaConfigElement(final IncolouringFormulaConfigElement formulaElement) {
		incolouringFormulaListElement.removeElement(formulaElement);
	}

	/**
	 * Returns an outcolouring formula element.
	 * 
	 * @param index the formula index.
	 * @return the outcolouring formula.
	 */
	public OutcolouringFormulaConfigElement getOutcolouringFormulaConfigElement(final int index) {
		return outcolouringFormulaListElement.getElement(index);
	}

	/**
	 * Returns an outcolouring formula element index.
	 * 
	 * @param formulaElement the outcolouring formula element.
	 * @return the index.
	 */
	public int indexOfOutcolouringFormulaConfigElement(final OutcolouringFormulaConfigElement formulaElement) {
		return outcolouringFormulaListElement.indexOfElement(formulaElement);
	}

	/**
	 * Returns the number of outcolouring formula elements.
	 * 
	 * @return the number of outcolouring formula elements.
	 */
	public int getOutcolouringFormulaConfigElementCount() {
		return outcolouringFormulaListElement.getElementCount();
	}

	/**
	 * Adds a outcolouring formula element.
	 * 
	 * @param formulaElement the outcolouring formula element.
	 */
	public void appendOutcolouringFormulaConfigElement(final OutcolouringFormulaConfigElement formulaElement) {
		outcolouringFormulaListElement.appendElement(formulaElement);
	}

	/**
	 * Adds a outcolouring formula element.
	 * 
	 * @param index the index.
	 * @param formulaElement the outcolouring formula element.
	 */
	public void insertOutcolouringFormulaConfigElementAfter(final int index, final OutcolouringFormulaConfigElement formulaElement) {
		outcolouringFormulaListElement.insertElementAfter(index, formulaElement);
	}

	/**
	 * Adds a outcolouring formula element.
	 * 
	 * @param index the index.
	 * @param formulaElement the outcolouring formula element.
	 */
	public void insertOutcolouringFormulaConfigElementBefore(final int index, final OutcolouringFormulaConfigElement formulaElement) {
		outcolouringFormulaListElement.insertElementBefore(index, formulaElement);
	}

	/**
	 * Removes a outcolouring formula element.
	 * 
	 * @param index the element index to remove.
	 */
	public void removeOutcolouringFormulaConfigElement(final int index) {
		outcolouringFormulaListElement.removeElement(index);
	}

	/**
	 * Removes a outcolouring formula element.
	 * 
	 * @param formulaElement the outcolouring formula element to remove.
	 */
	public void removeOutcolouringFormulaConfigElement(final OutcolouringFormulaConfigElement formulaElement) {
		outcolouringFormulaListElement.removeElement(formulaElement);
	}

	/**
	 * @param index
	 */
	public void moveUpIncolouringFormulaConfigElement(final int index) {
		incolouringFormulaListElement.moveElementUp(index);
	}

	/**
	 * @param index
	 */
	public void moveDownIncolouringFormulaConfigElement(final int index) {
		incolouringFormulaListElement.moveElementDown(index);
	}

	/**
	 * @param index
	 */
	public void moveUpOutcolouringFormulaConfigElement(final int index) {
		outcolouringFormulaListElement.moveElementUp(index);
	}

	/**
	 * @param index
	 */
	public void moveDownOutcolouringFormulaConfigElement(final int index) {
		outcolouringFormulaListElement.moveElementDown(index);
	}

	/**
	 * @return
	 */
	@Override
	public MandelbrotFractalConfigElement clone() {
		final MandelbrotFractalConfigElement element = new MandelbrotFractalConfigElement();
		element.setRenderingFormulaConfigElement(getRenderingFormulaConfigElement().clone());
		element.setTransformingFormulaConfigElement(getTransformingFormulaConfigElement().clone());
		element.setProcessingFormulaConfigElement(getProcessingFormulaConfigElement().clone());
		element.setOrbitTrapConfigElement(getOrbitTrapConfigElement().clone());
		for (int i = 0; i < incolouringFormulaListElement.getElementCount(); i++) {
			element.appendIncolouringFormulaConfigElement(incolouringFormulaListElement.getElement(i).clone());
		}
		for (int i = 0; i < outcolouringFormulaListElement.getElementCount(); i++) {
			element.appendOutcolouringFormulaConfigElement(outcolouringFormulaListElement.getElement(i).clone());
		}
		return element;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
	 */
	public void copyFrom(ConfigElement source) {
		MandelbrotFractalConfigElement element = (MandelbrotFractalConfigElement) source;
		getRenderingFormulaConfigElement().copyFrom(element.getRenderingFormulaConfigElement());
		getTransformingFormulaConfigElement().copyFrom(element.getTransformingFormulaConfigElement());
		getProcessingFormulaConfigElement().copyFrom(element.getProcessingFormulaConfigElement());
		getOrbitTrapConfigElement().copyFrom(element.getOrbitTrapConfigElement());
		getIncolouringFormulaListElement().copyFrom(element.getIncolouringFormulaListElement());
		getOutcolouringFormulaListElement().copyFrom(element.getOutcolouringFormulaListElement());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#setContext(com.nextbreakpoint.nextfractal.core.config.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		renderingFormulaSingleElement.setContext(getContext());
		transformingFormulaSingleElement.setContext(getContext());
		processingFormulaSingleElement.setContext(getContext());
		incolouringFormulaListElement.setContext(context);
		outcolouringFormulaListElement.setContext(context);
		orbitTrapSingleElement.setContext(getContext());
	}

	/**
	 * @return
	 */
	public SingleConfigElement<RenderingFormulaConfigElement> getRenderingFormulaSingleElement() {
		return renderingFormulaSingleElement;
	}

	/**
	 * @return
	 */
	public SingleConfigElement<TransformingFormulaConfigElement> getTransformingFormulaSingleElement() {
		return transformingFormulaSingleElement;
	}

	/**
	 * @return
	 */
	public SingleConfigElement<ProcessingFormulaConfigElement> getProcessingFormulaSingleElement() {
		return processingFormulaSingleElement;
	}

	/**
	 * @return
	 */
	public SingleConfigElement<OrbitTrapConfigElement> getOrbitTrapSingleElement() {
		return orbitTrapSingleElement;
	}

	/**
	 * @return the incolouringFormulaListElement
	 */
	public ListConfigElement<IncolouringFormulaConfigElement> getIncolouringFormulaListElement() {
		return incolouringFormulaListElement;
	}

	/**
	 * @return the outcolouringFormulaListElement
	 */
	public ListConfigElement<OutcolouringFormulaConfigElement> getOutcolouringFormulaListElement() {
		return outcolouringFormulaListElement;
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
		final MandelbrotFractalConfigElement other = (MandelbrotFractalConfigElement) obj;
		if (incolouringFormulaListElement == null) {
			if (other.incolouringFormulaListElement != null) {
				return false;
			}
		}
		else if (!incolouringFormulaListElement.equals(other.incolouringFormulaListElement)) {
			return false;
		}
		if (outcolouringFormulaListElement == null) {
			if (other.outcolouringFormulaListElement != null) {
				return false;
			}
		}
		else if (!outcolouringFormulaListElement.equals(other.outcolouringFormulaListElement)) {
			return false;
		}
		if (renderingFormulaSingleElement == null) {
			if (other.renderingFormulaSingleElement != null) {
				return false;
			}
		}
		else if (!renderingFormulaSingleElement.equals(other.renderingFormulaSingleElement)) {
			return false;
		}
		if (transformingFormulaSingleElement == null) {
			if (other.transformingFormulaSingleElement != null) {
				return false;
			}
		}
		else if (!transformingFormulaSingleElement.equals(other.transformingFormulaSingleElement)) {
			return false;
		}
		if (processingFormulaSingleElement == null) {
			if (other.processingFormulaSingleElement != null) {
				return false;
			}
		}
		else if (!processingFormulaSingleElement.equals(other.processingFormulaSingleElement)) {
			return false;
		}
		if (orbitTrapSingleElement == null) {
			if (other.orbitTrapSingleElement != null) {
				return false;
			}
		}
		else if (!orbitTrapSingleElement.equals(other.orbitTrapSingleElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		renderingFormulaSingleElement.dispose();
		transformingFormulaSingleElement.dispose();
		processingFormulaSingleElement.dispose();
		incolouringFormulaListElement.dispose();
		outcolouringFormulaListElement.dispose();
		orbitTrapSingleElement.dispose();
		super.dispose();
	}
}
