/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment;

import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFColor;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFModification;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.extension.ShapeAdjustmentExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class CurrentSaturationShapeAdjustmentRuntime extends ShapeAdjustmentExtensionRuntime<CurrentSaturationShapeAdjustmentConfig> {
	private Float value;
	private ValueListener valueListener;
	private Boolean target;
	private TargetListener targetListener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime#configReloaded()
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
	
	private class ValueListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
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
	
	private class TargetListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
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
			mod.getColor().setUseTarget(CFColor.SATURATION_TARGET);
		}
		mod.getColor().setSaturation(value);
	}
}

