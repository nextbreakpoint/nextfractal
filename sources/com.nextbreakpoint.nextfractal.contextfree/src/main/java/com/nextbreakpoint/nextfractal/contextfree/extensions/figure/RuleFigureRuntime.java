/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.figure;

import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.figure.FigureExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFBuilder;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFRule;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementRuntimeElement;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener;
import com.nextbreakpoint.nextfractal.core.runtime.ListConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.ListRuntimeElement;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class RuleFigureRuntime extends FigureExtensionRuntime<RuleFigureConfig> {
	private String name;
	private NameListener nameListener;
	private Float probability;
	private ProbabilityListener probabilityListener;
	private ListRuntimeElement<ShapeReplacementRuntimeElement> shapeReplacementListElement;
	private ShapeReplacementListElementListener shapeReplacementListElementListener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setName(getConfig().getName());
		nameListener = new NameListener();
		getConfig().getNameElement().addChangeListener(nameListener);
		setProbability(getConfig().getProbability());
		probabilityListener = new ProbabilityListener();
		getConfig().getProbabilityElement().addChangeListener(probabilityListener);
		shapeReplacementListElement = new ListRuntimeElement<ShapeReplacementRuntimeElement>();
		for (int i = 0; i < getConfig().getShapeReplacementConfigElementCount(); i++) {
			shapeReplacementListElement.appendElement(new ShapeReplacementRuntimeElement(getConfig().getShapeReplacementConfigElement(i)));
		}
		shapeReplacementListElementListener = new ShapeReplacementListElementListener();
		getConfig().getShapeReplacementListElement().addChangeListener(shapeReplacementListElementListener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (nameListener != null)) {
			getConfig().getNameElement().removeChangeListener(nameListener);
		}
		nameListener = null;
		if ((getConfig() != null) && (probabilityListener != null)) {
			getConfig().getProbabilityElement().removeChangeListener(probabilityListener);
		}
		probabilityListener = null;
		if ((getConfig() != null) && (shapeReplacementListElementListener != null)) {
			getConfig().getShapeReplacementListElement().removeChangeListener(shapeReplacementListElementListener);
		}
		shapeReplacementListElementListener = null;
		super.dispose();
	}
	
	/**
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	private void setName(final String name) {
		this.name = name;
	}
	
	private class NameListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setName((String) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the probability.
	 */
	public Float getProbability() {
		return probability;
	}

	private void setProbability(final Float probability) {
		this.probability = probability;
	}
	
	private class ProbabilityListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setProbability((Float) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * Returns a shapeReplacement element.
	 * 
	 * @param index the shapeReplacement index.
	 * @return the shapeReplacement.
	 */
	public ShapeReplacementRuntimeElement getShapeReplacementElement(final int index) {
		return shapeReplacementListElement.getElement(index);
	}

	/**
	 * Returns a shapeReplacement element index.
	 * 
	 * @param shapeReplacementElement the shapeReplacement element.
	 * @return the index.
	 */
	public int indexOfShapeReplacementElement(final ShapeReplacementRuntimeElement shapeReplacementElement) {
		return shapeReplacementListElement.indexOfElement(shapeReplacementElement);
	}

	/**
	 * Returns the number of shapeReplacement elements.
	 * 
	 * @return the number of shapeReplacement elements.
	 */
	public int getShapeReplacementElementCount() {
		return shapeReplacementListElement.getElementCount();
	}

	private void setShapeReplacementElement(final int index, ShapeReplacementRuntimeElement element) {
		shapeReplacementListElement.setElement(index, element);
	}

	private void appendShapeReplacementElement(final ShapeReplacementRuntimeElement shapeReplacementElement) {
		shapeReplacementListElement.appendElement(shapeReplacementElement);
	}

	private void insertShapeReplacementElementAfter(final int index, final ShapeReplacementRuntimeElement shapeReplacementElement) {
		shapeReplacementListElement.insertElementAfter(index, shapeReplacementElement);
	}

	private void insertShapeReplacementElementBefore(final int index, final ShapeReplacementRuntimeElement shapeReplacementElement) {
		shapeReplacementListElement.insertElementBefore(index, shapeReplacementElement);
	}

	private void removeShapeReplacementElement(final int index) {
		shapeReplacementListElement.removeElement(index);
	}

	private void moveUpShapeReplacementElement(final int index) {
		shapeReplacementListElement.moveElementUp(index);
	}

	private void moveDownShapeReplacementElement(final int index) {
		shapeReplacementListElement.moveElementDown(index);
	}
	
	private class ShapeReplacementListElementListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ListConfigElement.ELEMENT_ADDED: {
					appendShapeReplacementElement(new ShapeReplacementRuntimeElement ((ShapeReplacementConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_AFTER: {
					insertShapeReplacementElementAfter(((Integer) e.getParams()[1]).intValue(), new ShapeReplacementRuntimeElement ((ShapeReplacementConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
					insertShapeReplacementElementBefore(((Integer) e.getParams()[1]).intValue(), new ShapeReplacementRuntimeElement ((ShapeReplacementConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_REMOVED: {
					removeShapeReplacementElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_UP: {
					moveUpShapeReplacementElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_DOWN: {
					moveDownShapeReplacementElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_CHANGED: {
					setShapeReplacementElement(((Integer) e.getParams()[1]).intValue(), new ShapeReplacementRuntimeElement ((ShapeReplacementConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	@Override
	public void process(CFBuilder builder) {
		int shapeType = builder.encodeShapeName(name);
		CFRule rule = new CFRule(shapeType, probability);
		for (int i = 0; i < shapeReplacementListElement.getElementCount(); i++) {
			ShapeReplacementRuntimeElement shapeReplacementRuntime = shapeReplacementListElement.getElement(i);
			shapeReplacementRuntime.process(builder, rule);
		}
		builder.addRule(rule);
	}
}