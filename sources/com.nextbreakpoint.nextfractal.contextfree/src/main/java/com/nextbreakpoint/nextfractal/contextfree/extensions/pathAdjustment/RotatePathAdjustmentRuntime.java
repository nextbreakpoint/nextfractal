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
public class RotatePathAdjustmentRuntime extends PathAdjustmentExtensionRuntime<RotatePathAdjustmentConfig> {
	private Float angle;
	private AngleListener angleListener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setAngle(getConfig().getAngle());
		angleListener = new AngleListener();
		getConfig().getAngleElement().addChangeListener(angleListener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (angleListener != null)) {
			getConfig().getAngleElement().removeChangeListener(angleListener);
		}
		angleListener = null;
		super.dispose();
	}
	
	/**
	 * @return the angle.
	 */
	public Float getAngle() {
		return angle;
	}

	private void setAngle(final Float angle) {
		this.angle = angle;
	}
	
	private class AngleListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setAngle((Float) e.getParams()[0]);
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
		mod.rotate(angle);
	}
}
