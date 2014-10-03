/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment;

import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.extension.PathAdjustmentExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFModification;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class SkewPathAdjustmentRuntime extends PathAdjustmentExtensionRuntime<SkewPathAdjustmentConfig> {
	private Float shearX;
	private ShearXListener shearXListener;
	private Float shearY;
	private ShearYListener shearYListener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setShearX(getConfig().getShearX());
		shearXListener = new ShearXListener();
		getConfig().getShearXElement().addChangeListener(shearXListener);
		setShearY(getConfig().getShearY());
		shearYListener = new ShearYListener();
		getConfig().getShearYElement().addChangeListener(shearYListener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (shearXListener != null)) {
			getConfig().getShearXElement().removeChangeListener(shearXListener);
		}
		shearXListener = null;
		if ((getConfig() != null) && (shearYListener != null)) {
			getConfig().getShearYElement().removeChangeListener(shearYListener);
		}
		shearYListener = null;
		super.dispose();
	}
	
	/**
	 * @return the shearX.
	 */
	public Float getShearX() {
		return shearX;
	}

	private void setShearX(final Float shearX) {
		this.shearX = shearX;
	}
	
	private class ShearXListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setShearX((Float) e.getParams()[0]);
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
	 * @return the shearY.
	 */
	public Float getShearY() {
		return shearY;
	}

	private void setShearY(final Float shearY) {
		this.shearY = shearY;
	}
	
	private class ShearYListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setShearY((Float) e.getParams()[0]);
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
		mod.skew(shearX, shearY);
	}
}
