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
public class SkewPathAdjustmentRuntime extends PathAdjustmentExtensionRuntime<SkewPathAdjustmentConfig> {
	private Float shearX;
	private ShearXListener shearXListener;
	private Float shearY;
	private ShearYListener shearYListener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime#configReloaded()
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
	
	private class ShearXListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
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
	
	private class ShearYListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
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
