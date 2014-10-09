/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment;

import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.shapeAdjustment.ShapeAdjustmentExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFModification;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class Size2ShapeAdjustmentRuntime extends ShapeAdjustmentExtensionRuntime<Size2ShapeAdjustmentConfig> {
	private Float scaleX;
	private ScaleXListener scaleXListener;
	private Float scaleY;
	private ScaleYListener scaleYListener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime#configReloaded()
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
	
	private class ScaleXListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
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
	
	private class ScaleYListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
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

	@Override
	public boolean isSizeChange() {
		return true;
	}

	@Override
	public float getSize() {
		return Math.max(scaleX, scaleY);
	}
}
