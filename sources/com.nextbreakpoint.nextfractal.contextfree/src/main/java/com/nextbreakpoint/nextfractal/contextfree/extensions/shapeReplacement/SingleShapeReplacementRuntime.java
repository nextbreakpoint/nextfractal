/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeReplacement;

import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFBuilder;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFModification;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFReplacement;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFRule;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentRuntimeElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.extension.ShapeReplacementExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.config.ListConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ListRuntimeElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class SingleShapeReplacementRuntime<T extends SingleShapeReplacementConfig> extends ShapeReplacementExtensionRuntime<T> {
	private String shape;
	private CFModification stateChange;
	private ShapeListener shapeListener;
	private ListRuntimeElement<ShapeAdjustmentRuntimeElement> shapeAdjustmentListElement;
	private ShapeAdjustmentListElementListener shapeAdjustmentListElementListener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setShape(getConfig().getShape());
		shapeListener = new ShapeListener();
		getConfig().getShapeElement().addChangeListener(shapeListener);
		shapeAdjustmentListElement = new ListRuntimeElement<ShapeAdjustmentRuntimeElement>();
		for (int i = 0; i < getConfig().getShapeAdjustmentConfigElementCount(); i++) {
			shapeAdjustmentListElement.appendElement(new ShapeAdjustmentRuntimeElement(getConfig().getShapeAdjustmentConfigElement(i)));
		}
		shapeAdjustmentListElementListener = new ShapeAdjustmentListElementListener();
		getConfig().getShapeAdjustmentListElement().addChangeListener(shapeAdjustmentListElementListener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (shapeListener != null)) {
			getConfig().getShapeElement().removeChangeListener(shapeListener);
		}
		shapeListener = null;
		if ((getConfig() != null) && (shapeAdjustmentListElementListener != null)) {
			getConfig().getShapeAdjustmentListElement().removeChangeListener(shapeAdjustmentListElementListener);
		}
		shapeAdjustmentListElementListener = null;
		super.dispose();
	}
	
	/**
	 * @return the shape.
	 */
	public String getShape() {
		return shape;
	}

	private void setShape(final String shape) {
		this.shape = shape;
	}
	
	private class ShapeListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setShape((String) e.getParams()[0]);
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
	 * Returns a shapeAdjustment element.
	 * 
	 * @param index the shapeAdjustment index.
	 * @return the shapeAdjustment.
	 */
	public ShapeAdjustmentRuntimeElement getShapeAdjustmentElement(final int index) {
		return shapeAdjustmentListElement.getElement(index);
	}

	/**
	 * Returns a shapeAdjustment element index.
	 * 
	 * @param shapeAdjustmentElement the shapeAdjustment element.
	 * @return the index.
	 */
	public int indexOfShapeAdjustmentElement(final ShapeAdjustmentRuntimeElement shapeAdjustmentElement) {
		return shapeAdjustmentListElement.indexOfElement(shapeAdjustmentElement);
	}

	/**
	 * Returns the number of shapeAdjustment elements.
	 * 
	 * @return the number of shapeAdjustment elements.
	 */
	public int getShapeAdjustmentElementCount() {
		return shapeAdjustmentListElement.getElementCount();
	}

	private void setShapeAdjustmentElement(final int index, ShapeAdjustmentRuntimeElement element) {
		shapeAdjustmentListElement.setElement(index, element);
	}

	private void appendShapeAdjustmentElement(final ShapeAdjustmentRuntimeElement shapeAdjustmentElement) {
		shapeAdjustmentListElement.appendElement(shapeAdjustmentElement);
	}

	private void insertShapeAdjustmentElementAfter(final int index, final ShapeAdjustmentRuntimeElement shapeAdjustmentElement) {
		shapeAdjustmentListElement.insertElementAfter(index, shapeAdjustmentElement);
	}

	private void insertShapeAdjustmentElementBefore(final int index, final ShapeAdjustmentRuntimeElement shapeAdjustmentElement) {
		shapeAdjustmentListElement.insertElementBefore(index, shapeAdjustmentElement);
	}

	private void removeShapeAdjustmentElement(final int index) {
		shapeAdjustmentListElement.removeElement(index);
	}

	private void moveUpShapeAdjustmentElement(final int index) {
		shapeAdjustmentListElement.moveElementUp(index);
	}

	private void moveDownShapeAdjustmentElement(final int index) {
		shapeAdjustmentListElement.moveElementDown(index);
	}
	
	private class ShapeAdjustmentListElementListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ListConfigElement.ELEMENT_ADDED: {
					appendShapeAdjustmentElement(new ShapeAdjustmentRuntimeElement ((ShapeAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_AFTER: {
					insertShapeAdjustmentElementAfter(((Integer) e.getParams()[1]).intValue(), new ShapeAdjustmentRuntimeElement ((ShapeAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
					insertShapeAdjustmentElementBefore(((Integer) e.getParams()[1]).intValue(), new ShapeAdjustmentRuntimeElement ((ShapeAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_REMOVED: {
					removeShapeAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_UP: {
					moveUpShapeAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_DOWN: {
					moveDownShapeAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_CHANGED: {
					setShapeAdjustmentElement(((Integer) e.getParams()[1]).intValue(), new ShapeAdjustmentRuntimeElement ((ShapeAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	
	public void process(CFBuilder builder, CFRule rule) {
		if (stateChange == null) {
			stateChange = new CFModification();
			for (int i = 0; i < shapeAdjustmentListElement.getElementCount(); i++) {
				ShapeAdjustmentRuntimeElement shapeAdjustmentRuntime = shapeAdjustmentListElement.getElement(i);
				shapeAdjustmentRuntime.apply(stateChange);
			}
		}
		int shapeType = builder.encodeShapeName(shape);
		rule.addReplacement(new CFReplacement(shapeType, 0, stateChange));
	}
}
