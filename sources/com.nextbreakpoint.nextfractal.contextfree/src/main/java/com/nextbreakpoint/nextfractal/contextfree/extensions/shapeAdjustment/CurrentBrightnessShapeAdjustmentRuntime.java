/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment;

import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.shapeAdjustment.ShapeAdjustmentExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFColor;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFModification;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class CurrentBrightnessShapeAdjustmentRuntime extends ShapeAdjustmentExtensionRuntime<CurrentBrightnessShapeAdjustmentConfig> {
	private Float value;
	private ValueListener valueListener;
	private Boolean target;
	private TargetListener targetListener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setValue(getConfig().getValue());
		valueListener = new ValueListener();
		getConfig().getValueElement().addChangeListener(valueListener);
		setTarget(getConfig().isTarget());
		targetListener = new TargetListener();
		getConfig().getTargetElement().addChangeListener(targetListener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (valueListener != null)) {
			getConfig().getValueElement().removeChangeListener(valueListener);
		}
		valueListener = null;
		if ((getConfig() != null) && (targetListener != null)) {
			getConfig().getTargetElement().removeChangeListener(targetListener);
		}
		targetListener = null;
		super.dispose();
	}
	
	/**
	 * @return the value.
	 */
	public Float getValue() {
		return value;
	}

	private void setValue(final Float value) {
		this.value = value;
	}
	
	private class ValueListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setValue((Float) e.getParams()[0]);
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
	 * @return the target.
	 */
	public Boolean isTarget() {
		return target;
	}

	private void setTarget(final Boolean target) {
		this.target = target;
	}
	
	private class TargetListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setTarget((Boolean) e.getParams()[0]);
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
	public void apply(CFModification mod) {
		if (target) {
			mod.getColor().setUseTarget(CFColor.BRIGHTNESS_TARGET);
		}
		mod.getColor().setBrightness(value);
	}
}

