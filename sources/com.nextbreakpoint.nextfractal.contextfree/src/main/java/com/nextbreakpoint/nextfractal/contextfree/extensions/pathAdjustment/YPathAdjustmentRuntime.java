/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment;

import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathAdjustment.PathAdjustmentExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFModification;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class YPathAdjustmentRuntime extends PathAdjustmentExtensionRuntime<YPathAdjustmentConfig> {
	private Float value;
	private ValueListener valueListener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setValue(getConfig().getValue());
		valueListener = new ValueListener();
		getConfig().getValueElement().addChangeListener(valueListener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (valueListener != null)) {
			getConfig().getValueElement().removeChangeListener(valueListener);
		}
		valueListener = null;
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

	@Override
	public void apply(CFModification mod) {
		mod.translate(0, value, 0);
	}
}
