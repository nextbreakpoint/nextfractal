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
public class SizePathAdjustmentRuntime extends PathAdjustmentExtensionRuntime<SizePathAdjustmentConfig> {
	private Float scale;
	private ScaleListener scaleListener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setScale(getConfig().getScale());
		scaleListener = new ScaleListener();
		getConfig().getScaleElement().addChangeListener(scaleListener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (scaleListener != null)) {
			getConfig().getScaleElement().removeChangeListener(scaleListener);
		}
		scaleListener = null;
		super.dispose();
	}
	
	/**
	 * @return the scale.
	 */
	public Float getScale() {
		return scale;
	}

	private void setScale(final Float scale) {
		this.scale = scale;
	}
	
	private class ScaleListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setScale((Float) e.getParams()[0]);
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
		mod.scale(scale, scale, 1);
	}
}
