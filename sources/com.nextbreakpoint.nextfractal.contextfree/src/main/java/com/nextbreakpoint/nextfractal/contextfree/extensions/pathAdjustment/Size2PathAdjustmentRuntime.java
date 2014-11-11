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
public class Size2PathAdjustmentRuntime extends PathAdjustmentExtensionRuntime<Size2PathAdjustmentConfig> {
	private Float scaleX;
	private ScaleXListener scaleXListener;
	private Float scaleY;
	private ScaleYListener scaleYListener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setScaleX(getConfig().getScaleX());
		scaleXListener = new ScaleXListener();
		getConfig().getScaleXElement().addChangeListener(scaleXListener);
		setScaleY(getConfig().getScaleY());
		scaleYListener = new ScaleYListener();
		getConfig().getScaleYElement().addChangeListener(scaleYListener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (scaleXListener != null)) {
			getConfig().getScaleXElement().removeChangeListener(scaleXListener);
		}
		scaleXListener = null;
		if ((getConfig() != null) && (scaleYListener != null)) {
			getConfig().getScaleYElement().removeChangeListener(scaleYListener);
		}
		scaleYListener = null;
		super.dispose();
	}
	
	/**
	 * @return the scaleX.
	 */
	public Float getScaleX() {
		return scaleX;
	}

	private void setScaleX(final Float scaleX) {
		this.scaleX = scaleX;
	}
	
	private class ScaleXListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setScaleX((Float) e.getParams()[0]);
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
	 * @return the scaleY.
	 */
	public Float getScaleY() {
		return scaleY;
	}

	private void setScaleY(final Float scaleY) {
		this.scaleY = scaleY;
	}
	
	private class ScaleYListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setScaleY((Float) e.getParams()[0]);
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
		mod.scale(scaleX, scaleY, 1);
	}
}
