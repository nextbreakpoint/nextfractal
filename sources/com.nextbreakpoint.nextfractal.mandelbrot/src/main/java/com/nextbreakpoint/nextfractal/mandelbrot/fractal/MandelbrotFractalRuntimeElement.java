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
package com.nextbreakpoint.nextfractal.mandelbrot.fractal;

import com.nextbreakpoint.nextfractal.core.config.ListConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ListRuntimeElement;
import com.nextbreakpoint.nextfractal.core.config.RuntimeElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaRuntimeElement;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.OrbitTrapConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.OrbitTrapRuntimeElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaRuntimeElement;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.ProcessingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.ProcessingFormulaRuntimeElement;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.RenderingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.RenderingFormulaRuntimeElement;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.TransformingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.TransformingFormulaRuntimeElement;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotFractalRuntimeElement extends RuntimeElement {
	private IncolouringFormulaListElementListener incolouringFormulasListener;
	private OutcolouringFormulaListElementListener outcolouringFormulasListener;
	private RenderingFormulaElementListener renderingFormulaListener;
	private TransformingFormulaElementListener transformingFormulaListener;
	private ProcessingFormulaElementListener processingFormulaListener;
	private OrbitTrapElementListener orbitTrapListener;
	private MandelbrotFractalConfigElement fractalElement;
	private RenderingFormulaRuntimeElement renderingFormulaElement;
	private TransformingFormulaRuntimeElement transformingFormulaElement;
	private ProcessingFormulaRuntimeElement processingFormulaElement;
	private OrbitTrapRuntimeElement orbitTrapElement;
	private final ListRuntimeElement<IncolouringFormulaRuntimeElement> incolouringFormulaListElement = new ListRuntimeElement<IncolouringFormulaRuntimeElement>();
	private final ListRuntimeElement<OutcolouringFormulaRuntimeElement> outcolouringFormulaListElement = new ListRuntimeElement<OutcolouringFormulaRuntimeElement>();
	private boolean renderingFormulaChanged;
	private boolean transformingFormulaChanged;
	private boolean processingFormulaChanged;
	private boolean incolouringFormulaChanged;
	private boolean outcolouringFormulaChanged;
	private boolean orbitTrapChanged;

	/**
	 * 
	 */
	public MandelbrotFractalRuntimeElement(final MandelbrotFractalConfigElement fractalElement) {
		if (fractalElement == null) {
			throw new IllegalArgumentException("fractalElement is null");
		}
		this.fractalElement = fractalElement;
		createRenderingFormulaRuntime(fractalElement);
		renderingFormulaListener = new RenderingFormulaElementListener();
		fractalElement.getRenderingFormulaSingleElement().addChangeListener(renderingFormulaListener);
		createTransformingFormulaRuntime(fractalElement);
		transformingFormulaListener = new TransformingFormulaElementListener();
		fractalElement.getTransformingFormulaSingleElement().addChangeListener(transformingFormulaListener);
		createProcessingFormulaRuntime(fractalElement);
		processingFormulaListener = new ProcessingFormulaElementListener();
		fractalElement.getProcessingFormulaSingleElement().addChangeListener(processingFormulaListener);
		createIncolouringFormulaRuntimes(fractalElement);
		incolouringFormulasListener = new IncolouringFormulaListElementListener();
		fractalElement.getIncolouringFormulaListElement().addChangeListener(incolouringFormulasListener);
		createOutcolouringFormulaRuntimes(fractalElement);
		outcolouringFormulasListener = new OutcolouringFormulaListElementListener();
		fractalElement.getOutcolouringFormulaListElement().addChangeListener(outcolouringFormulasListener);
		createOrbitTrapRuntime(fractalElement);
		orbitTrapListener = new OrbitTrapElementListener();
		fractalElement.getOrbitTrapSingleElement().addChangeListener(orbitTrapListener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if ((fractalElement != null) && (incolouringFormulasListener != null)) {
			fractalElement.getIncolouringFormulaListElement().removeChangeListener(incolouringFormulasListener);
		}
		incolouringFormulasListener = null;
		if ((fractalElement != null) && (outcolouringFormulasListener != null)) {
			fractalElement.getOutcolouringFormulaListElement().removeChangeListener(outcolouringFormulasListener);
		}
		outcolouringFormulasListener = null;
		if ((fractalElement != null) && (renderingFormulaListener != null)) {
			fractalElement.getRenderingFormulaSingleElement().removeChangeListener(renderingFormulaListener);
		}
		renderingFormulaListener = null;
		if ((fractalElement != null) && (transformingFormulaListener != null)) {
			fractalElement.getTransformingFormulaSingleElement().removeChangeListener(transformingFormulaListener);
		}
		transformingFormulaListener = null;
		if ((fractalElement != null) && (processingFormulaListener != null)) {
			fractalElement.getProcessingFormulaSingleElement().removeChangeListener(processingFormulaListener);
		}
		processingFormulaListener = null;
		if ((fractalElement != null) && (orbitTrapListener != null)) {
			fractalElement.getOrbitTrapSingleElement().removeChangeListener(orbitTrapListener);
		}
		orbitTrapListener = null;
		fractalElement = null;
		if (renderingFormulaElement != null) {
			renderingFormulaElement.dispose();
			renderingFormulaElement = null;
		}
		if (transformingFormulaElement != null) {
			transformingFormulaElement.dispose();
			transformingFormulaElement = null;
		}
		if (processingFormulaElement != null) {
			processingFormulaElement.dispose();
			processingFormulaElement = null;
		}
		if (orbitTrapElement != null) {
			orbitTrapElement.dispose();
			orbitTrapElement = null;
		}
		incolouringFormulaListElement.dispose();
		outcolouringFormulaListElement.dispose();
		super.dispose();
	}

	private void createRenderingFormulaRuntime(final MandelbrotFractalConfigElement fractalElement) {
		final RenderingFormulaConfigElement renderingFormulaElement = fractalElement.getRenderingFormulaConfigElement();
		if (renderingFormulaElement != null) {
			final RenderingFormulaRuntimeElement renderingFormula = new RenderingFormulaRuntimeElement(renderingFormulaElement);
			setRenderingFormula(renderingFormula);
		}
	}

	private void createTransformingFormulaRuntime(final MandelbrotFractalConfigElement fractalElement) {
		final TransformingFormulaConfigElement transformingFormulaElement = fractalElement.getTransformingFormulaConfigElement();
		if (transformingFormulaElement != null) {
			final TransformingFormulaRuntimeElement transformingFormula = new TransformingFormulaRuntimeElement(transformingFormulaElement);
			setTransformingFormula(transformingFormula);
		}
	}

	private void createProcessingFormulaRuntime(final MandelbrotFractalConfigElement fractalElement) {
		final ProcessingFormulaConfigElement processingFormulaElement = fractalElement.getProcessingFormulaConfigElement();
		if (processingFormulaElement != null) {
			final ProcessingFormulaRuntimeElement transformingFormula = new ProcessingFormulaRuntimeElement(processingFormulaElement);
			setProcessingFormula(transformingFormula);
		}
	}

	private void createOrbitTrapRuntime(final MandelbrotFractalConfigElement fractalElement) {
		final OrbitTrapConfigElement orbitTrapElement = fractalElement.getOrbitTrapConfigElement();
		if (orbitTrapElement != null) {
			final OrbitTrapRuntimeElement transformingFormula = new OrbitTrapRuntimeElement(orbitTrapElement);
			setOrbitTrap(transformingFormula);
		}
	}

	private void createIncolouringFormulaRuntimes(final MandelbrotFractalConfigElement fractalElement) {
		for (int i = 0; i < fractalElement.getIncolouringFormulaConfigElementCount(); i++) {
			final IncolouringFormulaConfigElement incolouringFormulaElement = fractalElement.getIncolouringFormulaConfigElement(i);
			if (incolouringFormulaElement != null) {
				final IncolouringFormulaRuntimeElement incolouringFormula = new IncolouringFormulaRuntimeElement(incolouringFormulaElement);
				appendIncolouringFormula(incolouringFormula);
			}
		}
	}

	private void createOutcolouringFormulaRuntimes(final MandelbrotFractalConfigElement fractalElement) {
		for (int i = 0; i < fractalElement.getOutcolouringFormulaConfigElementCount(); i++) {
			final OutcolouringFormulaConfigElement outcolouringFormulaElement = fractalElement.getOutcolouringFormulaConfigElement(i);
			if (outcolouringFormulaElement != null) {
				final OutcolouringFormulaRuntimeElement outcolouringFormula = new OutcolouringFormulaRuntimeElement(outcolouringFormulaElement);
				appendOutcolouringFormula(outcolouringFormula);
			}
		}
	}

	/**
	 * Returns the renderingFormula.
	 * 
	 * @return the renderingFormula.
	 */
	public RenderingFormulaRuntimeElement getRenderingFormula() {
		return renderingFormulaElement;
	}

	private void setRenderingFormula(final RenderingFormulaRuntimeElement renderingFormula) {
		if (renderingFormulaElement != null) {
			renderingFormulaElement.dispose();
		}
		renderingFormulaElement = renderingFormula;
		renderingFormulaChanged = true;
	}

	/**
	 * Returns the transformingFormula.
	 * 
	 * @return the transformingFormula.
	 */
	public TransformingFormulaRuntimeElement getTransformingFormula() {
		return transformingFormulaElement;
	}

	private void setTransformingFormula(final TransformingFormulaRuntimeElement transformingFormula) {
		if (transformingFormulaElement != null) {
			transformingFormulaElement.dispose();
		}
		transformingFormulaElement = transformingFormula;
		transformingFormulaChanged = true;
	}

	/**
	 * Returns the processingFormula.
	 * 
	 * @return the processingFormula.
	 */
	public ProcessingFormulaRuntimeElement getProcessingFormula() {
		return processingFormulaElement;
	}

	private void setProcessingFormula(final ProcessingFormulaRuntimeElement processingFormula) {
		if (processingFormulaElement != null) {
			processingFormulaElement.dispose();
		}
		processingFormulaElement = processingFormula;
		processingFormulaChanged = true;
	}

	/**
	 * Returns the orbitTrap.
	 * 
	 * @return the orbitTrap.
	 */
	public OrbitTrapRuntimeElement getOrbitTrap() {
		return orbitTrapElement;
	}

	private void setOrbitTrap(final OrbitTrapRuntimeElement orbitTrap) {
		if (orbitTrapElement != null) {
			orbitTrapElement.dispose();
		}
		orbitTrapElement = orbitTrap;
		orbitTrapChanged = true;
	}

	/**
	 * Returns an incolouring formula.
	 * 
	 * @param index the formula index.
	 * @return the incolouring formula.
	 */
	public IncolouringFormulaRuntimeElement getIncolouringFormula(final int index) {
		return incolouringFormulaListElement.getElement(index);
	}

	/**
	 * Returns an incolouring formula index.
	 * 
	 * @param formulaElement the incolouring formula.
	 * @return the index.
	 */
	public int indexOfIncolouringFormula(final IncolouringFormulaRuntimeElement formulaElement) {
		return incolouringFormulaListElement.indexOfElement(formulaElement);
	}

	/**
	 * Returns the number of incolouring formulas.
	 * 
	 * @return the number of incolouring formulas.
	 */
	public int getIncolouringFormulaCount() {
		return incolouringFormulaListElement.getElementCount();
	}

	private void appendIncolouringFormula(final IncolouringFormulaRuntimeElement formula) {
		incolouringFormulaListElement.appendElement(formula);
	}

	private void insertIncolouringFormulaAfter(final int index, final IncolouringFormulaRuntimeElement formula) {
		incolouringFormulaListElement.insertElementAfter(index, formula);
	}

	private void insertIncolouringFormulaBefore(final int index, final IncolouringFormulaRuntimeElement formula) {
		incolouringFormulaListElement.insertElementBefore(index, formula);
	}

	private void removeIncolouringFormula(final int index) {
		incolouringFormulaListElement.getElement(index).dispose();
		incolouringFormulaListElement.removeElement(index);
	}

	/**
	 * Returns an outcolouring formula.
	 * 
	 * @param index the formula index.
	 * @return the outcolouring formula.
	 */
	public OutcolouringFormulaRuntimeElement getOutcolouringFormula(final int index) {
		return outcolouringFormulaListElement.getElement(index);
	}

	/**
	 * Returns an outcolouring formula index.
	 * 
	 * @param formula the outcolouring formula.
	 * @return the index.
	 */
	public int indexOfOutcolouringFormula(final OutcolouringFormulaRuntimeElement formula) {
		return outcolouringFormulaListElement.indexOfElement(formula);
	}

	/**
	 * Returns the number of outcolouring formulas.
	 * 
	 * @return the number of outcolouring formulas.
	 */
	public int getOutcolouringFormulaCount() {
		return outcolouringFormulaListElement.getElementCount();
	}

	private void appendOutcolouringFormula(final OutcolouringFormulaRuntimeElement formula) {
		outcolouringFormulaListElement.appendElement(formula);
	}

	private void insertOutcolouringFormulaAfter(final int index, final OutcolouringFormulaRuntimeElement formula) {
		outcolouringFormulaListElement.insertElementAfter(index, formula);
	}

	private void insertOutcolouringFormulaBefore(final int index, final OutcolouringFormulaRuntimeElement formula) {
		outcolouringFormulaListElement.insertElementBefore(index, formula);
	}

	private void removeOutcolouringFormula(final int index) {
		outcolouringFormulaListElement.getElement(index).dispose();
		outcolouringFormulaListElement.removeElement(index);
	}

	private void moveUpIncolouringFormula(final int index) {
		incolouringFormulaListElement.moveElementUp(index);
	}

	private void moveDownIncolouringFormula(final int index) {
		incolouringFormulaListElement.moveElementDown(index);
	}

	private void setIncolouringFormula(final int index, final IncolouringFormulaRuntimeElement formula) {
		incolouringFormulaListElement.setElement(index, formula);
	}

	private void moveUpOutcolouringFormula(final int index) {
		outcolouringFormulaListElement.moveElementUp(index);
	}

	private void moveDownOutcolouringFormula(final int index) {
		outcolouringFormulaListElement.moveElementDown(index);
	}

	private void setOutcolouringFormula(final int index, final OutcolouringFormulaRuntimeElement formula) {
		outcolouringFormulaListElement.setElement(index, formula);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		boolean fractalChanged = false;
		if (renderingFormulaElement.isChanged()) {
			renderingFormulaChanged = true;
			fractalChanged = true;
		}
		if (transformingFormulaElement.isChanged()) {
			transformingFormulaChanged = true;
			fractalChanged = true;
		}
		if (processingFormulaElement.isChanged()) {
			processingFormulaChanged = true;
			fractalChanged = true;
		}
		if (orbitTrapElement.isChanged()) {
			orbitTrapChanged = true;
			fractalChanged = true;
		}
		if (incolouringFormulaListElement.isChanged()) {
			incolouringFormulaChanged = true;
			fractalChanged = true;
		}
		if (outcolouringFormulaListElement.isChanged()) {
			outcolouringFormulaChanged = true;
			fractalChanged = true;
		}
		return super.isChanged() || fractalChanged;
	}

	/**
	 * @return the incolouringFormulaChanged
	 */
	public boolean isIncolouringFormulaChanged() {
		final boolean value = incolouringFormulaChanged;
		incolouringFormulaChanged = false;
		return value;
	}

	/**
	 * @return the outcolouringFormulaChanged
	 */
	public boolean isOutcolouringFormulaChanged() {
		final boolean value = outcolouringFormulaChanged;
		outcolouringFormulaChanged = false;
		return value;
	}

	/**
	 * @return the renderingFormulaChanged
	 */
	public boolean isRenderingFormulaChanged() {
		final boolean value = renderingFormulaChanged;
		renderingFormulaChanged = false;
		return value;
	}

	/**
	 * @return the transformingFormulaChanged
	 */
	public boolean isTransformingFormulaChanged() {
		final boolean value = transformingFormulaChanged;
		transformingFormulaChanged = false;
		return value;
	}

	/**
	 * @return the processingFormulaChanged
	 */
	public boolean isProcessingFormulaChanged() {
		final boolean value = processingFormulaChanged;
		processingFormulaChanged = false;
		return value;
	}

	/**
	 * @return the orbitTrapChanged
	 */
	public boolean isOrbitTrapChanged() {
		final boolean value = orbitTrapChanged;
		orbitTrapChanged = false;
		return value;
	}

	private class IncolouringFormulaListElementListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ListConfigElement.ELEMENT_ADDED: {
					appendIncolouringFormula(new IncolouringFormulaRuntimeElement((IncolouringFormulaConfigElement) e.getParams()[0]));
					incolouringFormulaChanged = true;
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_AFTER: {
					insertIncolouringFormulaAfter(((Integer) e.getParams()[1]).intValue(), new IncolouringFormulaRuntimeElement((IncolouringFormulaConfigElement) e.getParams()[0]));
					incolouringFormulaChanged = true;
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
					insertIncolouringFormulaBefore(((Integer) e.getParams()[1]).intValue(), new IncolouringFormulaRuntimeElement((IncolouringFormulaConfigElement) e.getParams()[0]));
					incolouringFormulaChanged = true;
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_REMOVED: {
					removeIncolouringFormula(((Integer) e.getParams()[1]).intValue());
					incolouringFormulaChanged = true;
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_UP: {
					moveUpIncolouringFormula(((Integer) e.getParams()[1]).intValue());
					incolouringFormulaChanged = true;
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_DOWN: {
					moveDownIncolouringFormula(((Integer) e.getParams()[1]).intValue());
					incolouringFormulaChanged = true;
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_CHANGED: {
					setIncolouringFormula(((Integer) e.getParams()[1]).intValue(), new IncolouringFormulaRuntimeElement((IncolouringFormulaConfigElement) e.getParams()[0]));
					incolouringFormulaChanged = true;
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class OutcolouringFormulaListElementListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ListConfigElement.ELEMENT_ADDED: {
					appendOutcolouringFormula(new OutcolouringFormulaRuntimeElement((OutcolouringFormulaConfigElement) e.getParams()[0]));
					outcolouringFormulaChanged = true;
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_AFTER: {
					insertOutcolouringFormulaAfter(((Integer) e.getParams()[1]).intValue(), new OutcolouringFormulaRuntimeElement((OutcolouringFormulaConfigElement) e.getParams()[0]));
					outcolouringFormulaChanged = true;
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
					insertOutcolouringFormulaBefore(((Integer) e.getParams()[1]).intValue(), new OutcolouringFormulaRuntimeElement((OutcolouringFormulaConfigElement) e.getParams()[0]));
					outcolouringFormulaChanged = true;
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_REMOVED: {
					removeOutcolouringFormula(((Integer) e.getParams()[1]).intValue());
					outcolouringFormulaChanged = true;
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_UP: {
					moveUpOutcolouringFormula(((Integer) e.getParams()[1]).intValue());
					outcolouringFormulaChanged = true;
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_DOWN: {
					moveDownOutcolouringFormula(((Integer) e.getParams()[1]).intValue());
					outcolouringFormulaChanged = true;
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_CHANGED: {
					setOutcolouringFormula(((Integer) e.getParams()[1]).intValue(), new OutcolouringFormulaRuntimeElement((OutcolouringFormulaConfigElement) e.getParams()[0]));
					outcolouringFormulaChanged = true;
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class RenderingFormulaElementListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setRenderingFormula(new RenderingFormulaRuntimeElement((RenderingFormulaConfigElement) e.getParams()[0]));
					renderingFormulaChanged = true;
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class TransformingFormulaElementListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setTransformingFormula(new TransformingFormulaRuntimeElement((TransformingFormulaConfigElement) e.getParams()[0]));
					transformingFormulaChanged = true;
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class ProcessingFormulaElementListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setProcessingFormula(new ProcessingFormulaRuntimeElement((ProcessingFormulaConfigElement) e.getParams()[0]));
					processingFormulaChanged = true;
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class OrbitTrapElementListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setOrbitTrap(new OrbitTrapRuntimeElement((OrbitTrapConfigElement) e.getParams()[0]));
					orbitTrapChanged = true;
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
}
